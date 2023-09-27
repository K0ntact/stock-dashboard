package vn.edu.usth.stockdashboard.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;

import java.util.ArrayList;

import vn.edu.usth.stockdashboard.R;
import vn.edu.usth.stockdashboard.utils.SearchResultAdapter;
import vn.edu.usth.stockdashboard.utils.StockItem;

public class MenuSearchActivity extends AppCompatActivity {
    private final ArrayList<StockItem> originalList = new ArrayList<>();
    private SearchResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_search);

        SearchView searchView = findViewById(R.id.searchEditText);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                filterList(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filterList(s);
                return false;
            }

            private void filterList(String s) {
                ArrayList<StockItem> filteredList = new ArrayList<>();
                for (StockItem item: originalList){
                    if(item.getSymbol().toLowerCase().contains(s.toLowerCase())){
                        filteredList.add(item);
                    }
                }
                adapter.filterList(filteredList);
            }
        });

        setupRecyclerView();
    }

    public void setupRecyclerView(){
        RecyclerView listView = findViewById(R.id.listSearchView);
        originalList.add(new StockItem("VNM", "VanEck VietNam ETF", "15,56 US$", "0,19%"));
        originalList.add(new StockItem("AAPL", "Apple Inc.", "178,18 US$", "0,35%")); // Sample data point 3
        originalList.add(new StockItem("SBUX", "Starbucks Corporation", "95,28 US$","0,19%"));
        originalList.add(new StockItem("NKE", "NIKE, Inc.", "97,67 US$", "0,27%"));
        originalList.add(new StockItem("TSLA", "Tesla, Inc.", "709,44 US$", "0,19%"));
        originalList.add(new StockItem("AMZN", "Amazon.com, Inc.", "3.372,20 US$", "0,27%"));
        originalList.add(new StockItem("FB", "Facebook, Inc.", "331,26 US$", "0,19%"));
        originalList.add(new StockItem("GOOGL", "Alphabet Inc.", "2.431,38 US$", "0,27%"));
        originalList.add(new StockItem("MSFT", "Microsoft Corporation", "259,43 US$", "0,19%"));
        originalList.add(new StockItem("NVDA", "NVIDIA Corporation", "191,05 US$", "0,27%"));
        originalList.add(new StockItem("PYPL", "PayPal Holdings, Inc.", "279,50 US$", "0,19%"));
        originalList.add(new StockItem("TSM", "Taiwan Semiconductor Manufacturing Company Limited", "117,00 US$", "0,27%"));
        originalList.add(new StockItem("V", "Visa Inc.", "226,00 US$", "0,19%"));
        originalList.add(new StockItem("WMT", "Walmart Inc.", "142,00 US$", "0,27%"));

        adapter = new SearchResultAdapter(originalList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
    }

    private String dbQuery(String symbol){
        return symbol;
    }
}