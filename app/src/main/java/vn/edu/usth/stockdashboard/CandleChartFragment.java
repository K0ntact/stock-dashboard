package vn.edu.usth.stockdashboard;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;

import java.util.ArrayList;
import java.util.List;

public class CandleChartFragment extends Fragment {
    private CandleDataSet dataSet;
    private CandleData data;
    private CandleStickChart candleStickChart;
    private String dateRange;

    public CandleChartFragment() {
        // Required empty public constructor
    }

    public CandleChartFragment(String dateRange) {
        this.dateRange = dateRange;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_candle_chart, container, false);

        candleStickChart = view.findViewById(R.id.candleChart);

        // This Description is visible at Bottom Right side
        candleStickChart.getDescription().setText("GFG");

        //set background color for the chart to black
        candleStickChart.setBackgroundColor(Color.parseColor("#06041a"));
        // Creating a list to store CandleEntry objects
        List<CandleEntry> entries = new ArrayList<>();

        // Added candlestick dummy data entries here
        // Format will be like entries.add(new CandleEntry(index, high, low, open, close));
        // here f is denoting floating-point number
        entries.add(new CandleEntry(0, 80f, 90f, 70f, 85f));  // Up (green)
        entries.add(new CandleEntry(1, 85f, 95f, 75f, 88f));  // Up (green)
        entries.add(new CandleEntry(2, 88f, 75f, 82f, 85f));  // Down (red)
        entries.add(new CandleEntry(3, 85f, 70f, 78f, 72f));  // Down (red)
        entries.add(new CandleEntry(4, 72f, 68f, 70f, 70f));  // Down (red)
        entries.add(new CandleEntry(5, 70f, 85f, 68f, 82f));  // Up (green)
        entries.add(new CandleEntry(6, 82f, 78f, 80f, 75f));  // Down (red)
        entries.add(new CandleEntry(7, 75f, 70f, 73f, 72f));  // Down (red)
        entries.add(new CandleEntry(8, 72f, 82f, 70f, 80f));  // Up (green)
        entries.add(new CandleEntry(9, 80f, 88f, 75f, 85f));  // Up (green)
        entries.add(new CandleEntry(10, 85f, 92f, 82f, 90f)); // Up (green)
        entries.add(new CandleEntry(11, 90f, 98f, 88f, 95f)); // Up (green)
        entries.add(new CandleEntry(12, 95f, 88f, 90f, 85f)); // Down (red)
        entries.add(new CandleEntry(13, 85f, 78f, 82f, 72f)); // Down (red)
        entries.add(new CandleEntry(14, 72f, 70f, 70f, 68f)); // Down (red)

        // Created a CandleDataSet from the entries
        dataSet = new CandleDataSet(entries, "Data");

        dataSet.setDrawIcons(false);
        dataSet.setIncreasingColor(Color.GREEN);  // Color for up (green) candlesticks
        dataSet.setIncreasingPaintStyle(Paint.Style.FILL); // Set the paint style to Fill for green candlesticks
        dataSet.setDecreasingColor(Color.RED);    // Color for down (red) candlesticks
        dataSet.setShadowColorSameAsCandle(true); // Using the same color for shadows as the candlesticks
        dataSet.setDrawValues(false);             // Hiding the values on the chart if not needed

        // Created a CandleData object from the CandleDataSet
        data = new CandleData();
        data.addDataSet(dataSet);

        // Seinft the CandleData to the CandleStickChart
        candleStickChart.setData(data);
        candleStickChart.invalidate();

        return view;
    }
}