package vn.edu.usth.stockdashboard;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class HeaderMyStockFragment extends Fragment {

    private ArrayList<StockItem> originalStockList;
    private ArrayList<StockItem> filteredStockList;
    private StockListAdapter adapter;
    public HeaderMyStockFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_header_my_stock, container,false);

        //Find the DateTextView
        TextView dateTextView = view.findViewById(R.id.dateTextViewHeader);
        //Get the current date to display
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM, yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        // Set the date to the text view
        dateTextView.setText(currentDate);
        // Inflate the layout for this fragment

        //Find the Search Text
        EditText searchHeaderText = view.findViewById(R.id.searchHeaderBar);

        //Setup a text change listener for the search bar
        searchHeaderText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                //Handle Text Changes in the search bar
                // Filter the list view
                String searchQuery = s.toString();
                filterListViewData(searchQuery);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    private void initData(){
        originalStockList = new ArrayList<>();
        filteredStockList = new ArrayList<>(originalStockList);
        adapter = new StockListAdapter(getContext(), filteredStockList);
        ListView listView = getView().findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
    private void filterData(String query){
        filteredStockList.clear();

        for (StockItem item: originalStockList){
            if (item.getName().toLowerCase().contains(query.toLowerCase())){
                filteredStockList.add(item);
            }
        }
        adapter.notifyDataSetChanged();
    }
    // Method to filter ListView data in MyStockFragment based on the search query
    private void filterListViewData(String query) {
        MyStockFragment myStockFragment = (MyStockFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.myStockFragment);

        if (myStockFragment != null){
            myStockFragment.filterData(query);
        }
    }
}