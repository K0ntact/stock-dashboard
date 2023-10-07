package vn.edu.usth.stockdashboard.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;

public class TimestampFormatter extends ValueFormatter {

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        super.getAxisLabel(value, axis);
        long timestamp = (long) (value * 10000);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", java.util.Locale.getDefault());
        return sdf.format(timestamp);
    }
}
