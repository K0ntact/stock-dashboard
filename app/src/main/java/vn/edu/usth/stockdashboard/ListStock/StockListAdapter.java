package vn.edu.usth.stockdashboard.ListStock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import vn.edu.usth.stockdashboard.R;

public class StockListAdapter extends ArrayAdapter<StockItem> {
    public StockListAdapter(Context context, ArrayList<StockItem> stockList){
        super(context, 0, stockList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the list item layout (you can create a custom layout for it)
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.stock_list_items, parent, false);
        }

        // Get the StockItem at this position
        StockItem currentItem = getItem(position);

        // Populate the views in the list item layout with data from StockItem
        TextView symbolTextView = convertView.findViewById(R.id.symbolTextView);
        TextView nameTextView = convertView.findViewById(R.id.nameTextView);
        LineChart lineChart = convertView.findViewById(R.id.lineChart);
        TextView moneyTextView = convertView.findViewById(R.id.moneyTextView);
        TextView percentageTextView = convertView.findViewById(R.id.percentage);

        symbolTextView.setText(currentItem.getSymbol());
        nameTextView.setText(currentItem.getName());
        moneyTextView.setText(currentItem.getMoney());
        percentageTextView.setText(currentItem.getPercentage());


        // Get a reference to the LineChart
        // LineChart lineChart = convertView.findViewById(R.id.lineChart);

        // Create a LineDataSet with sample data
        if (currentItem.getRandomData() == null) {
            currentItem.generateRandomData(20);
        }

        ArrayList<Entry> randomData = currentItem.getRandomData();
        LineDataSet dataSet = new LineDataSet(randomData, "Random Data");
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER); // Smooth line

        float dottedLineY = 45f;
        float aboveArea = 0f;
        float belowArea = 0f;

        for (Entry entry: randomData){
            if(entry.getY() > dottedLineY){
                aboveArea += entry.getY() - dottedLineY;
            } else {
                belowArea += dottedLineY - entry.getY();
            }
        }
        dataSet.setDrawFilled(true);
        int graphColor;

        if(aboveArea > belowArea){
            graphColor = getContext().getColor(R.color.positive);
            percentageTextView.setBackgroundResource(R.drawable.rounded_box_green);
            addHorizontalDottedLine(lineChart, dottedLineY, getContext().getColor(R.color.positive));
            dataSet.setFillDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_chart_gradient_positive));
        } else {
            graphColor = getContext().getColor(R.color.negative);
            percentageTextView.setBackgroundResource(R.drawable.round_box_red);
            addHorizontalDottedLine(lineChart, dottedLineY, getContext().getColor(R.color.negative));
            dataSet.setFillDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_chart_gradient_negative));
        }

        dataSet.setColor(graphColor);
        dataSet.setLineWidth(1f); // Line width
        dataSet.setDrawCircles(false); // Do not draw circles on data points
        // Create a LineData object and set the dataSet
        LineData lineData = new LineData(dataSet);

        // Configure the LineChart
        lineChart.setData(lineData);
        lineChart.getDescription().setEnabled(false); // Disable description label
        lineChart.getLegend().setEnabled(false); // Disable legend
        lineChart.setDrawGridBackground(false);
        dataSet.setDrawValues(false);
        // Disable X-axis
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawAxisLine(false); // Disable X-axis line
        xAxis.setDrawGridLines(false); // Disable X-axis grid lines
        xAxis.setDrawLabels(false); // Disable X-axis labels
        // Disable all user interaction
        lineChart.setTouchEnabled(false); // Disable touch gestures
        lineChart.setDragEnabled(false);  // Disable panning
        lineChart.setScaleEnabled(false); // Disable zooming
        lineChart.setPinchZoom(false);    // Disable pinch zoom
        lineChart.setDoubleTapToZoomEnabled(false); // Disable double tap to zoom

        lineChart.setHighlightPerDragEnabled(false); // Disable highlighting on drag
        lineChart.setHighlightPerTapEnabled(false);  // Disable highlighting on tap


        // Disable Y-axis (left)
        YAxis leftYAxis = lineChart.getAxisLeft();
        leftYAxis.setDrawAxisLine(false); // Disable Y-axis line
        leftYAxis.setDrawGridLines(false); // Disable Y-axis grid lines
        leftYAxis.setDrawLabels(false); // Disable Y-axis labels

        // If you want to disable the right Y-axis (if present)
        YAxis rightYAxis = lineChart.getAxisRight();
        rightYAxis.setEnabled(false); // Disable the right Y-axis completely

        // Invalidate the chart to refresh it
        lineChart.invalidate();
        View separatorView = convertView.findViewById(R.id.separatorView);

        if (position == getCount() -1){
            separatorView.setVisibility(View.GONE);
        } else {
            separatorView.setVisibility(View.VISIBLE);
        }
        // Check if there are at least two data points
        if (randomData.size() >= 2){
            float lastValue = randomData.get(randomData.size() - 1).getY();
            float secondLastValue = randomData.get(randomData.size() - 2).getY();
            float percentageChange = ((lastValue - secondLastValue) / secondLastValue * 100);

            // Update the previousValue field in the StockItem
            currentItem.setPreviousValue(secondLastValue);

            // Set the percentage text with a plus or minus sign
            String percentageText;
            if (percentageChange > 0){
                percentageText = "+" + String.format("%.2f%%",percentageChange);
            } else if (percentageChange < 0) {
                percentageText = String.format("%.2f%%",percentageChange);
            } else {
                percentageText = "0.00%";
            }

        }
        return convertView;
    }

    private void addHorizontalDottedLine(LineChart lineChart, float yValue, int lineColor){
        LimitLine horizontalLine = new LimitLine(yValue, "");
        horizontalLine.setLineWidth(1f);
        horizontalLine.setLineColor(lineColor);
        horizontalLine.enableDashedLine(5f,5f,0f);
        lineChart.getAxisLeft().addLimitLine(horizontalLine);
    }
}
