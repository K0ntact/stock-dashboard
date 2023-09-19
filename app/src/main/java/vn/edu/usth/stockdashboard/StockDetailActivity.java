package vn.edu.usth.stockdashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class StockDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);

        ViewPager2 viewPager = findViewById(R.id.viewPager2);
        ChartAdapter chartAdapter = new ChartAdapter(this);
        viewPager.setAdapter(chartAdapter);

        TabLayout dateRange = findViewById(R.id.tabLayout);
        TabLayout.OnTabSelectedListener listener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(dateRange.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        };
        dateRange.addOnTabSelectedListener(listener);

        TabLayoutMediator mediator = new TabLayoutMediator(dateRange, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("6M");
                    break;
                case 1:
                    tab.setText("1D");
                    break;
                case 2:
                    tab.setText("2D");
                    break;
                case 3:
                    tab.setText("5D");
                    break;
                case 4:
                    tab.setText("ALL");
                    break;
            }
        });
        mediator.attach();
    }
}