package vn.edu.usth.stockdashboard;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import vn.edu.usth.stockdashboard.utils.StockItem;
import vn.edu.usth.stockdashboard.utils.StockSearchAdapter;

public class SearchActivity extends AppCompatActivity {
    private static final long DEBOUNCE_DELAY_MS = 500;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable debounceRunnable;
    private static final String API_URL = "https://finnhub.io/api/v1/search?q=%s&token=ckeomppr01qvl18vgirgckeomppr01qvl18vgis0";
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private boolean buyStock = false;

    private boolean debounce = false;
    private boolean isLogin = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            buyStock = bundle.getBoolean("buyStock");
            isLogin = bundle.getBoolean("isLogin");
        }
        setContentView(R.layout.activity_menu_search);
        Log.i("IsBuying Stock", String.valueOf(buyStock));

        TextInputEditText searchInput = findViewById(R.id.searchEditText);

        // Add a TextWatcher to listen for text changes in the EditText
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // If keycode is enter, remove debounceRunnable
                if (i2 == 0) {
                    handler.removeCallbacks(debounceRunnable);
                }
                if (debounceRunnable != null) {
                    handler.removeCallbacks(debounceRunnable);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Create a new debounceRunnable
                debounceRunnable = () -> {
                    // Perform your API request here
                    String searchText = editable.toString();
                    //fetchDataFromAPI(searchText);
                    if (searchText.length() > 0) {
                        fetchDataFromAPI(searchText);
                    }
                };

                // Delay the execution of the debounceRunnable
                if (debounce) {
                    debounce = false;
                    handler.postDelayed(debounceRunnable, DEBOUNCE_DELAY_MS);
                }
                else {
                    debounce = true;
                }
            }
        });
    }

    private void fetchDataFromAPI(String searchText) {
        executorService.execute(() -> {
            try {
                URL url = new URL(String.format(API_URL, searchText));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();
                InputStream inputStream = conn.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String response = stringBuilder.toString();
                JSONObject jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray("result");
                ArrayList<StockItem> stockList = new ArrayList<>();
                for (int i = 0; i < result.length(); i++) {
                    JSONObject stock = result.getJSONObject(i);
                    stockList.add(new StockItem(stock.getString("symbol"), stock.getString("description")));
                }
                runOnUiThread(() -> {
                    RecyclerView.LayoutManager layoutManager = new androidx.recyclerview.widget.LinearLayoutManager(getApplicationContext());
                    StockSearchAdapter stockSearchAdapter = new StockSearchAdapter(stockList, buyStock, isLogin);
                    RecyclerView recyclerView = findViewById(R.id.listSearchView);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(stockSearchAdapter);

                });

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        });
    }
}
