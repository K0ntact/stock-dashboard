package vn.edu.usth.stockdashboard.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import vn.edu.usth.stockdashboard.R;
import vn.edu.usth.stockdashboard.DetailStock.StockDetailActivity;

public class StockListAdapter extends RecyclerView.Adapter<StockListAdapter.ViewHolder> {
    private ArrayList<StockItem> stockList;

    public StockListAdapter(ArrayList<StockItem> stockList){
        this.stockList = stockList;
    }

    @NonNull
    @Override
    public StockListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_list_items, parent, false);

        view.setOnClickListener(v -> {
            TextView stockSymbol = view.findViewById(R.id.symbolTextView);
            TextView companyName = view.findViewById(R.id.nameTextView);
            TextView money = view.findViewById(R.id.moneyTextView);
            TextView percentage = view.findViewById(R.id.percentage);
            StockItem item = new StockItem(stockSymbol.getText().toString(), companyName.getText().toString(), money.getText().toString(), percentage.getText().toString());

            Intent intent = new Intent(view.getContext(), StockDetailActivity.class);
            intent.putExtra("stockName", item.getSymbol());
            intent.putExtra("companyName", item.getName());
            intent.putExtra("money", item.getMoney());
            intent.putExtra("percentage", item.getPercentage());
            view.getContext().startActivity(intent);
        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Resources r = context.getResources();

        // Get the StockItem at this position
        StockItem currentItem = stockList.get(position);

        holder.symbolTextView.setText(currentItem.getSymbol());
        holder.nameTextView.setText(currentItem.getName());
        holder.moneyTextView.setText(currentItem.getMoney());

        // Generate random data
        if (currentItem.getRandomData() == null) {
            currentItem.generateRandomData(20);
        }

        ArrayList<Entry> randomData = currentItem.getRandomData();
        LineDataSet dataSet = new LineDataSet(randomData, "Random Data");
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER); // Smooth line
        dataSet.setDrawFilled(true);

        int graphColor;
        float lastValue = randomData.get(randomData.size() - 1).getY();
        float secondLastValue = randomData.get(randomData.size() - 2).getY();
        float percentageChange = ((lastValue - secondLastValue) / secondLastValue * 100);

        // Update the previousValue field in the StockItem
        currentItem.setPreviousValue(secondLastValue);

        // Set the percentage text with a plus or minus sign
        String percentageText;
        if (percentageChange > 0){
            percentageText = "+" + String.format("%.2f%%",percentageChange);
            graphColor = r.getColor(R.color.positive,null);
            holder.percentageTextView.setBackgroundResource(R.drawable.rounded_box_green);
            dataSet.setFillDrawable(ContextCompat.getDrawable(context, R.drawable.line_chart_gradient_positive));
        } else if (percentageChange < 0) {
            percentageText = String.format("%.2f%%",percentageChange);
            graphColor = r.getColor(R.color.negative,null);
            holder.percentageTextView.setBackgroundResource(R.drawable.round_box_red);
            dataSet.setFillDrawable(ContextCompat.getDrawable(context, R.drawable.line_chart_gradient_negative));
        } else {
            percentageText = "0.00%";
            graphColor = r.getColor(R.color.neutral,null);
            holder.percentageTextView.setBackgroundResource(R.drawable.round_box_yellow);
            dataSet.setFillDrawable(ContextCompat.getDrawable(context, R.drawable.line_chart_gradient_neutral));
        }
        holder.percentageTextView.setText(percentageText);

        dataSet.setColor(graphColor);
        dataSet.setLineWidth(1f); // Line width
        dataSet.setDrawCircles(false); // Do not draw circles on data points

        // Create a LineData object and set the dataSet
        LineData lineData = new LineData(dataSet);
        holder.lineChart.setData(lineData);
        holder.lineChart.getDescription().setEnabled(false); // Disable description label
        holder.lineChart.getLegend().setEnabled(false); // Disable legend
        holder.lineChart.setDrawGridBackground(false);
        dataSet.setDrawValues(false);

        // Disable X-axis
        XAxis xAxis = holder.lineChart.getXAxis();
        xAxis.setEnabled(false);
        // Disable Y-axis (left)
        YAxis leftYAxis = holder.lineChart.getAxisLeft();
        leftYAxis.setEnabled(false);
        // Disable Y-axis (right)
        YAxis rightYAxis = holder.lineChart.getAxisRight();
        rightYAxis.setEnabled(false);
        // Disable all user interaction
        holder.lineChart.setTouchEnabled(false);
        holder.lineChart.setDragEnabled(false);
        holder.lineChart.setScaleEnabled(false);
        holder.lineChart.setPinchZoom(false);
        holder.lineChart.setDoubleTapToZoomEnabled(false);
        holder.lineChart.setHighlightPerDragEnabled(false);
        holder.lineChart.setHighlightPerTapEnabled(false);

        // Invalidate the chart to refresh it
        holder.lineChart.invalidate();
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView symbolTextView;
        private final TextView nameTextView;
        private final TextView percentageTextView;
        private LineChart lineChart;
        private final TextView moneyTextView;
        private final View separatorView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            symbolTextView = itemView.findViewById(R.id.symbolTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            lineChart = itemView.findViewById(R.id.lineChart);
            moneyTextView = itemView.findViewById(R.id.moneyTextView);
            percentageTextView = itemView.findViewById(R.id.percentage);
            separatorView = itemView.findViewById(R.id.separatorView);
        }
    }
}
