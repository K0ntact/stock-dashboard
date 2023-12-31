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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(stockList.get(position));
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView symbolTextView;
        private final TextView nameTextView;
        private final TextView percentageTextView;
        private final LineChart lineChart;
        private final TextView moneyTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            symbolTextView = itemView.findViewById(R.id.symbolTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            moneyTextView = itemView.findViewById(R.id.moneyTextView);
            percentageTextView = itemView.findViewById(R.id.percentage);

            lineChart = itemView.findViewById(R.id.lineChart);
            lineChart.getDescription().setEnabled(false);   // Disable description label
            lineChart.getLegend().setEnabled(false);        // Disable legend
            lineChart.setDrawGridBackground(false);
            XAxis xAxis = lineChart.getXAxis();             // Disable X-axis
            xAxis.setEnabled(false);
            YAxis leftYAxis = lineChart.getAxisLeft();      // Disable Y-axis (left)
            leftYAxis.setEnabled(false);
            YAxis rightYAxis = lineChart.getAxisRight();    // Disable Y-axis (right)
            rightYAxis.setEnabled(false);
            lineChart.setTouchEnabled(false);               // Disable all user interaction
            lineChart.setDragEnabled(false);
            lineChart.setScaleEnabled(false);
            lineChart.setPinchZoom(false);
            lineChart.setDoubleTapToZoomEnabled(false);
            lineChart.setHighlightPerDragEnabled(false);
            lineChart.setHighlightPerTapEnabled(false);
            onClick();
        }

        private void setupChart(StockItem item) {
            Context context = itemView.getContext();
            Resources r = context.getResources();

            ArrayList<CustomCandleData> candleData = item.getChartData();
            if (candleData.isEmpty()) {
                System.out.println("No candle data");
                return;
            }
            ArrayList<Entry> entries = new ArrayList<>();
            for (CustomCandleData data : candleData) {
                int position = candleData.indexOf(data);
                entries.add(new Entry(position, data.current_price));
            }
//
            LineDataSet dataSet = new LineDataSet(entries, "Random Data");
            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER); // Smooth line
            dataSet.setDrawFilled(true);
//
            int graphColor;
            float currentPrice = candleData.get(candleData.size() - 1).current_price;
            float closePrice = item.getClosePrice();
            float percentageChange = (currentPrice - closePrice) / closePrice * 100;

//             Set the percentage text with a plus or minus sign
            String percentageText;
            if (percentageChange > 0){
                percentageText = "+" + String.format("%.2f%%", percentageChange);
                graphColor = r.getColor(R.color.positive, null);
                percentageTextView.setBackgroundResource(R.drawable.rounded_box_green);
                dataSet.setFillDrawable(ContextCompat.getDrawable(context, R.drawable.line_chart_gradient_positive));
            }
            else if (percentageChange < 0) {
                percentageText = String.format("%.2f%%", percentageChange);
                graphColor = r.getColor(R.color.negative, null);
                percentageTextView.setBackgroundResource(R.drawable.round_box_red);
                dataSet.setFillDrawable(ContextCompat.getDrawable(context, R.drawable.line_chart_gradient_negative));
            } else {
                percentageText = "0.00%";
                graphColor = r.getColor(R.color.neutral, null);
                percentageTextView.setBackgroundResource(R.drawable.round_box_yellow);
                dataSet.setFillDrawable(ContextCompat.getDrawable(context, R.drawable.line_chart_gradient_neutral));
            }
            percentageTextView.setText(percentageText);

            dataSet.setColor(graphColor);
            dataSet.setLineWidth(1f); // Line width
            dataSet.setDrawCircles(false); // Do not draw circles on data points

            // Create a LineData object and set the dataSet
            LineData lineData = new LineData(dataSet);
            lineChart.setData(lineData);
            dataSet.setDrawValues(false);

            // Invalidate the chart to refresh it
            lineChart.invalidate();
        }

        public void bindData(StockItem item){
            symbolTextView.setText(item.getSymbol());
            nameTextView.setText(item.getName());
            moneyTextView.setText(item.getMoney());
            setupChart(item);
        }

        public void onClick(){
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), StockDetailActivity.class);
                intent.putExtra("stockName", symbolTextView.getText().toString());
                intent.putExtra("companyName", nameTextView.getText().toString());
                intent.putExtra("money", moneyTextView.getText().toString());
                intent.putExtra("percentage", percentageTextView.getText().toString());
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
