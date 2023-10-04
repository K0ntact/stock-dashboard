package vn.edu.usth.stockdashboard;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import vn.edu.usth.stockdashboard.HelperSideBar.FragmentNavigationManager;
import vn.edu.usth.stockdashboard.InterfaceSideBar.NavigationManager;
import vn.edu.usth.stockdashboard.utils.*;

public class StockMarketFragment extends Fragment implements DataNotify {
    private DrawerLayout drawerLayout;
    private String activityTitle;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private String[] items;

    private ExpandableListView expandableListView;
    private List<String> listTitle;
    private Map<String,List<String>> listChild;
    private NavigationManager navigationManager;
//    private SlideBarExpandableListAdapter adapter;
    private ClientEndpoint clientEndpoint;
    private final ArrayList<StockItem> entries;
    private final StockListAdapter adapter;


    public StockMarketFragment() {
        entries = new ArrayList<>();
        entries.add(new StockItem("VNM", "VanEck VietNam ETF"));
        entries.add(new StockItem("AAPL", "Apple Inc."));
        entries.add(new StockItem("SBUX", "Starbucks Corporation"));
        entries.add(new StockItem("NKE", "NIKE, Inc."));
        entries.add(new StockItem("TSLA", "Tesla, Inc."));
        entries.add(new StockItem("AMZN", "Amazon.com, Inc."));
        entries.add(new StockItem("META", "Meta Platforms, Inc."));
        entries.add(new StockItem("GOOGL", "Alphabet Inc."));
        entries.add(new StockItem("MSFT", "Microsoft Corporation"));
        entries.add(new StockItem("NVDA", "NVIDIA Corporation"));
        entries.add(new StockItem("PYPL", "PayPal Holdings, Inc."));
        entries.add(new StockItem("TSM", "Taiwan Semiconductor Manufacturing Company Limited"));
        entries.add(new StockItem("V", "Visa Inc."));
        entries.add(new StockItem("WMT", "Walmart Inc."));
        entries.add(new StockItem("BINANCE:BTCUSDT", "Bitcoin / TetherUS"));
        entries.add(new StockItem("BINANCE:ETHUSDT", "Ethereum / TetherUS"));
         adapter = new StockListAdapter(entries);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Thread thread = new Thread(() -> {
            try {
                // Should query last price from database first,
                // because with websocket the price only update when there is a new trade,
                // so for small stock like VNM, the price will stay at 0 for a while.
                // DB query here
                // ...

                // If running server on local computer, change IP address to IP address of your computer
                String[] symbols = {"VNM","AAPL", "SBUX", "NKE", "TSLA", "AMZN", "META", "GOOGL", "MSFT", "NVDA", "PYPL", "TSM", "V", "WMT", "BINANCE:BTCUSDT", "BINANCE:ETHUSDT"};
                clientEndpoint = new ClientEndpoint(new URI("ws://146.190.83.69:8080?uuid=bhhoang"), symbols);
                clientEndpoint.addDataNotify(this);
                clientEndpoint.connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
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
//        listView.setDrawingCacheEnabled(true);
//        listView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

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
        listHeaderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout != null){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
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
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout != null) {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    } else {
                        drawerLayout.openDrawer(GravityCompat.START);
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    public void onDestroy() {
        super.onDestroy();
        System.out.println("On Destroy");
        clientEndpoint.close(1000, "Close from client");
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
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int i) {
                ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle(listTitle.get(i).toString());
                }
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int i) {
                ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
                if (actionBar != null) {
                    actionBar.setTitle("AHIHI");
                }
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                String selectedItem = ((List)(listChild.get(listTitle.get(i))))
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
            }
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
                getActivity().invalidateOptionsMenu();
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
        List<String> nothing = Arrays.asList();

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
        int id = item.getItemId();
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
                        adapter.notifyItemChanged(entries.indexOf(entry));
                    }
                }
            }
        );
    }
}
