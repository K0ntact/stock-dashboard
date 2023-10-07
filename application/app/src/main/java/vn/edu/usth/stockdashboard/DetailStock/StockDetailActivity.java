package vn.edu.usth.stockdashboard.DetailStock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import vn.edu.usth.stockdashboard.R;
import vn.edu.usth.stockdashboard.utils.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

public class StockDetailActivity extends AppCompatActivity implements WSDataNotify, RestDataNotify {
    private String stockName;
    private TextView moneyView;
    private WSEndpoint wsEndpoint;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);

        moneyView = findViewById(R.id.stockPrice);

        Intent intent = getIntent();
        stockName = intent.getStringExtra("stockName");

        thread = new Thread(() -> {
            try {
//                WSEndpoint = new WSEndpoint(new URI("ws://146.190.83.69:8080/trade?uuid=bhhoang"), new String[]{stockName});
                wsEndpoint = new WSEndpoint(new URI("ws://192.168.158.185:8080/trade?uuid=bhhoang"), new String[]{stockName});
                wsEndpoint.addDataNotify(this);
                wsEndpoint.connect();
                DbQuery dbQuery = DbQuery.getInstance();
                dbQuery.addDataNotify(this);
                dbQuery.getStockInfo(stockName);
            } catch (IOException | URISyntaxException e) {
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

    @Override
    public void onRestDataReceived(String response) {
        runOnUiThread(() -> {
            try {
                updateDetail(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    public void updateDetail(String responseJson) throws JSONException {
        JSONArray rows = new JSONArray(responseJson);
        JSONObject data = rows.getJSONObject(0);
        // {"id":1,"symbol":"AAPL","companyname":"Apple Inc.","description":"Technology company","sector":"Technology","address":"Cupertino, CA",
        // "mktcap":2300000000000,"yearhigh":157.26,"yearlow":104.19,"esp":5.68,"peratio":28.78,"pegratio":1.65,
        // "movingavg50":148.42,"movingavg200":130.67}

        TextView compName = findViewById(R.id.compName);
//        TextView openVal = findViewById(R.id.open);
//        TextView highVal = findViewById(R.id.high);
//        TextView lowVal = findViewById(R.id.low);
//        TextView closeVal = findViewById(R.id.close);

        TextView mktcapVal = findViewById(R.id.mktCapVal);
        TextView yearHighVal = findViewById(R.id.yearHighVal);
        TextView yearLowVal = findViewById(R.id.yearLowVal);
        TextView espVal = findViewById(R.id.espVal);

        TextView peratioVal = findViewById(R.id.peRatioVal);
        TextView pegratioVal = findViewById(R.id.pegRatioVal);
        TextView movingavg50Val = findViewById(R.id.mvgAvg50dVal);
        TextView movingavg200Val = findViewById(R.id.mvgAvg200dVal);

        compName.setText(data.getString("companyname"));

        mktcapVal.setText(String.valueOf(data.getLong("mktcap")));
        yearHighVal.setText(String.valueOf(data.getDouble("yearhigh")));
        yearLowVal.setText(String.valueOf(data.getDouble("yearlow")));
        espVal.setText(String.valueOf(data.getDouble("esp")));
        peratioVal.setText(String.valueOf(data.getDouble("peratio")));
        pegratioVal.setText(String.valueOf(data.getDouble("pegratio")));
        movingavg50Val.setText(String.valueOf(data.getDouble("movingavg50")));
        movingavg200Val.setText(String.valueOf(data.getDouble("movingavg200")));
    }
}