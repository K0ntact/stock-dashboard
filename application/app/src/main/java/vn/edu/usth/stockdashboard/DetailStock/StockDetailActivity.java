package vn.edu.usth.stockdashboard.DetailStock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import vn.edu.usth.stockdashboard.R;
import vn.edu.usth.stockdashboard.utils.WSEndpoint;
import vn.edu.usth.stockdashboard.utils.CustomCandleData;
import vn.edu.usth.stockdashboard.utils.WSDataNotify;
import vn.edu.usth.stockdashboard.utils.DbQuery;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

public class StockDetailActivity extends AppCompatActivity implements WSDataNotify {
    private String stockName;
    private TextView moneyView;
    private WSEndpoint wsEndpoint;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);

        Intent intent = getIntent();
        stockName = intent.getStringExtra("stockName");

        thread = new Thread(() -> {
            try {
                clientEndpoint = new ClientEndpoint(new URI("ws://192.168.1.2:8080/trade?uuid=bhhoang"), new String[]{stockName});
                clientEndpoint.addDataNotify(this);
                clientEndpoint.connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
//                WSEndpoint = new WSEndpoint(new URI("ws://146.190.83.69:8080/trade?uuid=bhhoang"), new String[]{stockName});
                wsEndpoint = new WSEndpoint(new URI("ws://192.168.1.155:8080/trade?uuid=bhhoang"), new String[]{stockName});
                wsEndpoint.addDataNotify(this);
                wsEndpoint.connect();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();

        if (stockName != null) {
            TextView stockNameView = findViewById(R.id.stockName);
            stockNameView.setText(stockName);
        }

        ViewPager2 viewPager = findViewById(R.id.viewPager2);
        viewPager.setUserInputEnabled(false);
        ChartAdapter chartAdapter = new ChartAdapter(this);
        chartAdapter.setSymbol(stockName);
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
                    tab.setText("1D");
                    break;
                case 1:
                    tab.setText("2D");
                    break;
                case 2:
                    tab.setText("5D");
                    break;
                case 3:
                    tab.setText("6M");
                    break;
                case 4:
                    tab.setText("ALL");
                    break;
            }
        });
        mediator.attach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        wsEndpoint.close(1000, "Close from client");
        thread.interrupt();
    }

    @Override
    public void onNewData(HashMap<String, CustomCandleData> data) {
        runOnUiThread(() -> {
            CustomCandleData candleData = data.get(stockName);
            if (candleData == null) return;
            moneyView.setText(String.valueOf(candleData.current_price));

        });
    }
}