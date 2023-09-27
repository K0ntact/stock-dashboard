package vn.edu.usth.stockdashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import vn.edu.usth.stockdashboard.DetailStock.StockDetailActivity;

import vn.edu.usth.stockdashboard.utils.StockItem;
import vn.edu.usth.stockdashboard.utils.StockListAdapter;

public class StockMarketFragment extends Fragment {
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private boolean openSlideBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock_market, container, false);
        RecyclerView listView = view.findViewById(R.id.listView);

        ArrayList<StockItem> entries = new ArrayList<>();
        entries.add(new StockItem("VNM", "VanEck VietNam ETF", "15,56 US$", "0,19%"));
        entries.add(new StockItem("AAPL", "Apple Inc.", "178,18 US$", "0,35%")); // Sample data point 3
        entries.add(new StockItem("SBUX", "Starbucks Corporation", "95,28 US$","0,19%"));
        entries.add(new StockItem("NKE", "NIKE, Inc.", "97,67 US$", "0,27%"));
        entries.add(new StockItem("TSLA", "Tesla, Inc.", "709,44 US$", "0,19%"));
        entries.add(new StockItem("AMZN", "Amazon.com, Inc.", "3.372,20 US$", "0,27%"));
        entries.add(new StockItem("FB", "Facebook, Inc.", "331,26 US$", "0,19%"));
        entries.add(new StockItem("GOOGL", "Alphabet Inc.", "2.431,38 US$", "0,27%"));
        entries.add(new StockItem("MSFT", "Microsoft Corporation", "259,43 US$", "0,19%"));
        entries.add(new StockItem("NVDA", "NVIDIA Corporation", "191,05 US$", "0,27%"));
        entries.add(new StockItem("PYPL", "PayPal Holdings, Inc.", "279,50 US$", "0,19%"));
        entries.add(new StockItem("TSM", "Taiwan Semiconductor Manufacturing Company Limited", "117,00 US$", "0,27%"));
        entries.add(new StockItem("V", "Visa Inc.", "226,00 US$", "0,19%"));
        entries.add(new StockItem("WMT", "Walmart Inc.", "142,00 US$", "0,27%"));
        StockListAdapter adapter = new StockListAdapter(entries);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);



        //SLIDE BAR
        ExpandableListView expandableListView = view.findViewById(R.id.slideBarExpandableView);
        prepareListData();
        SlideBarExpandableListAdapter listAdapter = new SlideBarExpandableListAdapter(requireContext(), listDataHeader, listDataChild);
        expandableListView.setAdapter(listAdapter);

        //IMAGE TO GO TO SLIDE BAR (3 LINES) //WIP\\
        ImageButton hamburgerButton = view.findViewById(R.id.hamburgerButton);
            hamburgerButton.setOnClickListener(view12 -> {
                if (openSlideBar) {
                    expandableListView.setVisibility(View.GONE);
                    openSlideBar = false;
                } else {
                    expandableListView.setVisibility(View.VISIBLE);
                    openSlideBar = true;
                }
            });
        return view;
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        listDataHeader.add("Favorite List");
        listDataHeader.add("Owned List");
        listDataHeader.add("Listed List");
        listDataHeader.add("Industry Portfolio");
        listDataHeader.add("Derivative List");
        listDataHeader.add("CW List");

        List<String> industryPortfolio = new ArrayList<>();
        industryPortfolio.add("Insurance");
        industryPortfolio.add("Media");
        industryPortfolio.add("Oil & Gas");
        industryPortfolio.add("Technology");
        industryPortfolio.add("Personal & Household Goods");
        industryPortfolio.add("Gas, Water & Multi-utilities");
        industryPortfolio.add("Food & Beverage");
        industryPortfolio.add("Banks");
        industryPortfolio.add("Real Estate");

        List<String> favoriteList = new ArrayList<>();
        favoriteList.add("Favorite Number 1");
        favoriteList.add("Favorite Number 2");

        List<String> listedList = new ArrayList<>();
        listedList.add("Listed List 1");
        listedList.add("Listed List 2");
        listedList.add("Listed List 3");

        listDataChild.put(listDataHeader.get(0), favoriteList);
        listDataChild.put(listDataHeader.get(2), listedList);
        listDataChild.put(listDataHeader.get(3), industryPortfolio);

    }
}
