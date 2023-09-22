package vn.edu.usth.stockdashboard.MenuAfterLogin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.stockdashboard.R;


public class ListFragment extends Fragment implements OnFilterListener {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> originalList;

    public ListFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_search, container, false);
        listView = view.findViewById(R.id.listSearchView);

        originalList = new ArrayList<>();
        originalList.add("Notification");
        originalList.add("Get SmartOTP");
        originalList.add("QR Code");
        originalList.add("Alert");
        originalList.add("Help Center");
        originalList.add("Setting");
        originalList.add("Change Status Of Stock");
        originalList.add("Order History");
        originalList.add("Odd Sell");
        originalList.add("Stock Transfer");
        originalList.add("Cash Transfer");
        originalList.add("Advance");
        originalList.add("Investment PortFolio");
        originalList.add("Property Report");
        originalList.add("Invest Effects");
        originalList.add("Buy bonds");
        originalList.add("Transaction History");
        originalList.add("Ownership Category");
        originalList.add("Guide");
        originalList.add("Transactions eM24");
        originalList.add("Quote Limit Change");
        originalList.add("Loan Extension");
        originalList.add("Confirm Order Online");
        originalList.add("Register Buy Rights");
        originalList.add("Change Services");
        originalList.add("Register A Service Account");
        originalList.add("Register Professional Investors");
        originalList.add("Register For Warrants");
        originalList.add("Refer Friends");
        originalList.add("Instructions to transfer money to a securities account");

        adapter = new ArrayAdapter<>(requireContext(),R.layout.custom_list_search,originalList);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onFilter(String query) {
        List<String> filteredList = new ArrayList<>();
        for (String item: originalList){
            if(item.toLowerCase().contains(query.toLowerCase())){
                filteredList.add(item);
            }
        }
        adapter.clear();
        adapter.addAll(filteredList);
        adapter.notifyDataSetChanged();
    }
}