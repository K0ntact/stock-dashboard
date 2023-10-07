package vn.edu.usth.stockdashboard;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import androidx.recyclerview.widget.SimpleItemAnimator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import vn.edu.usth.stockdashboard.HelperSideBar.FragmentNavigationManager;
import vn.edu.usth.stockdashboard.InterfaceSideBar.NavigationManager;
import vn.edu.usth.stockdashboard.utils.*;

public class StockMarketFragment extends Fragment implements WSDataNotify {
    private DrawerLayout drawerLayout;
    private String activityTitle;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private String[] items;

    private ExpandableListView expandableListView;
    private List<String> listTitle;
    private Map<String,List<String>> listChild;
    private NavigationManager navigationManager;
    private WSEndpoint wsEndpoint;
    private final ArrayList<StockItem> entries;
    private final StockListAdapter adapter;
    private boolean isLogin = false;


    public StockMarketFragment() {
        entries = new ArrayList<>();
        entries.add(new StockItem("AAPL", "Apple Inc.", 177.87F));
        entries.add(new StockItem("NKE", "NIKE, Inc.", 97.32F));
        entries.add(new StockItem("TSLA", "Tesla, Inc.", 206.73F));
        entries.add(new StockItem("AMZN", "Amazon.com, Inc.", 128.1F));
        entries.add(new StockItem("META", "Meta Platforms, Inc.", 315.69F));
        entries.add(new StockItem("GOOGL", "Alphabet Inc.", 137.63F));
        entries.add(new StockItem("MSFT", "Microsoft Corporation", 327.43F));
        entries.add(new StockItem("NVDA", "NVIDIA Corporation", 457.67F));
        entries.add(new StockItem("PYPL", "PayPal Holdings, Inc.", 57.76F));
        entries.add(new StockItem("TSM", "Taiwan Semiconductor Manufacturing Company Limited", 89.29F));
        entries.add(new StockItem("V", "Visa Inc.", 235.24F));
        entries.add(new StockItem("WMT", "Walmart Inc.", 156.41F));
        entries.add(new StockItem("BINANCE:BTCUSDT", "Bitcoin / TetherUS", 27886.16F));
        entries.add(new StockItem("BINANCE:ETHUSDT", "Ethereum / TetherUS", 1642.09F));
        entries.add(new StockItem("BINANCE:BNBUSDT", "Binance Coin / TetherUS", 213.4F));
        entries.add(new StockItem("BINANCE:ADAUSDT", "Cardano / TetherUS", 0.2463F));
        entries.add(new StockItem("BINANCE:DOTUSDT", "Polkadot / TetherUS",4.07F));
        entries.add(new StockItem("BINANCE:XRPUSDT", "XRP / TetherUS", 0.5237F));
        adapter = new StockListAdapter(entries);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            isLogin = savedInstanceState.getBoolean("isLogin");
        setHasOptionsMenu(true);
        Thread thread = new Thread(() -> {
            try {
                String[] symbols = {"AAPL", "NKE", "TSLA", "AMZN", "META", "GOOGL", "MSFT", "NVDA", "PYPL", "TSM", "V", "WMT", "BINANCE:BTCUSDT", "BINANCE:ETHUSDT", "BINANCE:BNBUSDT", "BINANCE:ADAUSDT", "BINANCE:DOTUSDT", "BINANCE:XRPUSDT"};
//                String[] symbols = {"BINANCE:BTCUSDT"};
//                String[] symbols = {"BINANCE:BTCUSDT", "BINANCE:ETHUSDT", "BINANCE:BNBUSDT", "BINANCE:ADAUSDT", "BINANCE:DOTUSDT", "BINANCE:XRPUSDT"};
//                WSEndpoint = new WSEndpoint(new URI("ws://146.190.83.69:8080/trade?uuid=bhhoang"), symbols);
                wsEndpoint = new WSEndpoint(new URI("ws://192.168.158.185:8080/trade?uuid=bhhoang"), symbols);
                wsEndpoint.setInterval(3000);
                wsEndpoint.addDataNotify(this);
                wsEndpoint.connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
//            catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        });
        thread.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock_market, container, false);
        RecyclerView listView = view.findViewById(R.id.listView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        listView.setHasFixedSize(true);
        listView.setItemViewCacheSize(20);
        ((SimpleItemAnimator) Objects.requireNonNull(listView.getItemAnimator())).setSupportsChangeAnimations(false);

        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        //SIDEBAR
        drawerLayout = view.findViewById(R.id.drawerLayout);
        activityTitle = getResources().toString();
        expandableListView = view.findViewById(R.id.navList);
        navigationManager = FragmentNavigationManager.getmInstance(this);
        initItems();
        //Header for sidebar
        View listHeaderView = getLayoutInflater().inflate(R.layout.slidebar_nav_header,null,false);
        //back button in header
        listHeaderView.setOnClickListener(v -> {
            if (drawerLayout != null){
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        expandableListView.addHeaderView(listHeaderView);
        genData();
        addDrawerItems();
        setUpDrawer();
        if (savedInstanceState == null) selectFirstItemAsDefault();
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle("AHIHI NUMBER 3");
        }
        ImageView imageView = view.findViewById(R.id.burgerIcon);
        imageView.setOnClickListener(v -> {
            if (drawerLayout != null) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        Button searchBtn = view.findViewById(R.id.searchHeaderBar);
        searchBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            intent.putExtra("buyStock", false);
            intent.putExtra("isLogin", isLogin);
            startActivity(intent);
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("On Destroy");
        wsEndpoint.close(1000, "Close from client");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectFirstItemAsDefault() {
        if (navigationManager != null){
            String firstItem = listTitle.get(0);
            navigationManager.showFragment(firstItem);
            ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
            if (actionBar != null){
                actionBar.setTitle(firstItem);
            }
        }
    }


    private void setUpDrawer() {
        ExpandableListAdapter expandableListAdapter = new SlideBarExpandableListAdapter(getActivity(), listTitle, listChild);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setGroupIndicator(null); // Set null indicator
        expandableListView.setOnGroupExpandListener(i -> {
            ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(listTitle.get(i));
            }
        });
        expandableListView.setOnGroupCollapseListener(i -> {
            ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle("AHIHI");
            }
        });
        expandableListView.setOnChildClickListener((expandableListView, view, i, i1, l) -> {
            String selectedItem = ((List<?>)(Objects.requireNonNull(listChild.get(listTitle.get(i)))))
                    .get(i1).toString();
            ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(selectedItem);
            }

            if (items[0].equals(listTitle.get(i)))
                navigationManager.showFragment(selectedItem);
            else throw new IllegalArgumentException("Not Supported Fragment");
            drawerLayout.closeDrawer(GravityCompat.START);
            return false;
        });
    }

    private void addDrawerItems() {

        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle("AHIHI NUMBER 2");
                }
                requireActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
                if (actionBar != null){
                    actionBar.setTitle(activityTitle);
                    requireActivity().invalidateOptionsMenu();
                }
            }
        };
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
    }

    private void genData() {
        List<String> title = Arrays.asList(
                "Favorite List",
                "Owned List",
                "Listed List",
                "Industry Portfolio",
                "Derivative List",
                "CW List"
        );
        List<String> childIndustryPortfolio = Arrays.asList(
                "Insurance",
                "Media",
                "Oil & Gas",
                "Technology",
                "Personal & Household Goods",
                "Gas, Water & Multi-utilities",
                "Food & Beverage",
                "Banks",
                "Real Estate"
        );
        List<String> nothing = Collections.emptyList();

        listChild = new TreeMap<>();

        listChild.put(title.get(0),nothing);
        listChild.put(title.get(1),nothing);
        listChild.put(title.get(2),nothing);
        listChild.put(title.get(3),childIndustryPortfolio);
        listChild.put(title.get(4),nothing);
        listChild.put(title.get(5),nothing);
        listTitle = new ArrayList<>(listChild.keySet());

    }

    private void initItems() {
        items = new String[]{"Testing ListItem","Oke","Alright"};
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        item.getItemId();
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNewData(HashMap<String, CustomCandleData> data) {
        requireActivity().runOnUiThread(() -> {
                for (StockItem entry : entries) {
                    CustomCandleData candleData = data.get(entry.getSymbol());
                    if (candleData != null) {
                        entry.setMoney(String.valueOf(candleData.current_price));
                        entry.insertChartData(candleData);
                        adapter.notifyItemChanged(entries.indexOf(entry));
                    }
                }
            }
        );
    }
}
