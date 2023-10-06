package vn.edu.usth.stockdashboard.DetailStock;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import vn.edu.usth.stockdashboard.R;
import vn.edu.usth.stockdashboard.utils.WSDataNotify;
import vn.edu.usth.stockdashboard.utils.WSEndpoint;
import vn.edu.usth.stockdashboard.utils.CustomCandleData;

public class CandleChartFragment extends Fragment implements WSDataNotify {
    private CandleDataSet dataSet;
    private CandleData data;
    private ArrayList<CandleEntry> entries;
    private CandleStickChart candleStickChart;
    private WSEndpoint wsEndpoint;
    private String symbol;

    public CandleChartFragment() {
        // Required empty public constructor
    }

    public CandleChartFragment(String dateRange, String symbol) {
        this.symbol = symbol;
        System.out.println("CandleChartFragment created");
        if (dateRange.equals("1D")) {
            try {
//                WSEndpoint = new WSEndpoint(new java.net.URI("ws//146.190.83.69:8080/trade?uuid=bhhoang"), new String[]{symbol});
                wsEndpoint = new WSEndpoint(new java.net.URI("ws//192.168.242.185:8080/trade?uuid=bhhoangcandle"), new String[]{symbol});
                wsEndpoint.setInterval(3000);
                wsEndpoint.addDataNotify(this);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }

        entries = new ArrayList<>();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_candle_chart, container, false);

        candleStickChart = view.findViewById(R.id.candleChart);
//        //set background color for the chart to black
//        candleStickChart.setBackgroundColor(Color.parseColor("#06041a"));

        candleStickChart.setData(data);
        // Set the X-axis label count
        candleStickChart.getXAxis().setLabelCount(entries.size(), true);
        candleStickChart.getXAxis().setTextColor(Color.WHITE);
        candleStickChart.getAxisRight().setTextColor(Color.WHITE);
        XAxis xAxis = candleStickChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        candleStickChart.getLegend().setEnabled(false);
        // Set gridline style to dotted line
        candleStickChart.getXAxis().enableGridDashedLine(10f, 10f, 0f);
        candleStickChart.getAxisLeft().enableGridDashedLine(10f, 10f, 0f);
        candleStickChart.getAxisRight().enableGridDashedLine(10f, 10f, 0f);

        // Refresh the chart
        candleStickChart.invalidate();

        return view;
    }

    @Override
    public void onNewData(HashMap<String, CustomCandleData> data) {
        CustomCandleData candleData = data.get(symbol);
        if (candleData == null) return;
        System.out.println(candleData.timestamp);
//        entries.add(toCandleEntry(candleData));
//        dataSet.notifyDataSetChanged();
//        candleStickChart.notifyDataSetChanged();
    }

    private CandleEntry toCandleEntry(CustomCandleData data) {
        float compressedTimestamp = data.timestamp / 1000f;
        return new CandleEntry(compressedTimestamp, data.high, data.low, data.open, data.close);
    }
}