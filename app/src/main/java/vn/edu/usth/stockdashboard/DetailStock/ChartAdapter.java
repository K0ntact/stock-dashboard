package vn.edu.usth.stockdashboard.DetailStock;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ChartAdapter extends FragmentStateAdapter {
    public ChartAdapter(StockDetailActivity activity) {
        super(activity);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new CandleChartFragment("6M");
            case 1: return new CandleChartFragment("1D");
            case 2: return new CandleChartFragment("2D");
            case 3: return new CandleChartFragment("5D");
            case 4: return new CandleChartFragment("ALL");
        }
        return new CandleChartFragment("6M");
    }
}

