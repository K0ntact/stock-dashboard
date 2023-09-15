package vn.edu.usth.stockdashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class CandleChartFragment extends Fragment {
    private float[] highs = {3.5F, 4.5F, 5.5F, 6.5F, 7.5F, 8.5F, 9.5F};
    private float[] lows = {1F, 2.3F, 3.5F, 4.5F, 5.5F, 6.5F, 7.5F};
    private float[] closes = {2.5F, 3.5F, 4.5F, 5.5F, 6.5F, 7.5F, 8.5F};
    private float[] opens = {1.5F, 2.5F, 3.5F, 4.5F, 5.5F, 6.5F, 7.5F};

    public CandleChartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_candle_chart, container, false);

        CandleStickChart candleStickChart = view.findViewById(R.id.candleChart);

        List<CandleEntry> entries = new ArrayList<>();
        for (int i = 0; i < highs.length; i++) {
            entries.add(new CandleEntry(i, highs[i], lows[i], opens[i], closes[i]));
        }
        CandleDataSet dataSet = new CandleDataSet(entries, "Label");
//        dataSet.setDecreasingColor(R.color.white);
//        dataSet.setIncreasingColor(R.color.white);
        CandleData lineData = new CandleData(dataSet);
        candleStickChart.setData(lineData);
        candleStickChart.invalidate(); // refresh

        return view;
    }
}