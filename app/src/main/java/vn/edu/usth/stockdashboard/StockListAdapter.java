package vn.edu.usth.stockdashboard;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class StockListAdapter extends ArrayAdapter<StockItem> {
    public StockListAdapter(Context context, ArrayList<StockItem> stockList){
        super(context, 0, stockList);
    }
    MyStockFragment myStockFragment = new MyStockFragment();

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
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 10)); // Sample data point 1
        entries.add(new Entry(1, 20)); // Sample data point 2
        entries.add(new Entry(2, 15)); // Sample data point 3
        entries.add(new Entry(3, 25)); // Sample data point 4
        entries.add(new Entry(4, 9));
        entries.add(new Entry(5, 12));
        entries.add(new Entry(6, 4));
        entries.add(new Entry(7, 10));
        entries.add(new Entry(8, 25));// Sample data point 5

        LineDataSet dataSet = new LineDataSet(entries, "Stock Data");

        // Set gradient fill for the dataset
        dataSet.setDrawFilled(true);
        dataSet.setFillDrawable(ContextCompat.getDrawable(getContext(), R.drawable.line_chart_gradient));
        // Configure the appearance of the line
        dataSet.setColor(Color.GREEN); // Line color
        dataSet.setLineWidth(2f); // Line width
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
        return convertView;
    }

}
