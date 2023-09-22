<<<<<<<< HEAD:app/src/main/java/vn/edu/usth/stockdashboard/MenuAfterLogin/MyStockFragment.java
package vn.edu.usth.stockdashboard.MenuAfterLogin;
========
package vn.edu.usth.stockdashboard.ListStock;
>>>>>>>> 357e1cf60db4ff56fa8dab0e29234c500901921a:app/src/main/java/vn/edu/usth/stockdashboard/ListStock/ListStockFragment.java

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import vn.edu.usth.stockdashboard.R;
<<<<<<<< HEAD:app/src/main/java/vn/edu/usth/stockdashboard/MenuAfterLogin/MyStockFragment.java
import vn.edu.usth.stockdashboard.StockDetailActivity;
import vn.edu.usth.stockdashboard.StockItem;
import vn.edu.usth.stockdashboard.StockListAdapter;

========
import vn.edu.usth.stockdashboard.DetailStock.StockDetailActivity;
>>>>>>>> 357e1cf60db4ff56fa8dab0e29234c500901921a:app/src/main/java/vn/edu/usth/stockdashboard/ListStock/ListStockFragment.java


public class ListStockFragment extends Fragment implements AdapterView.OnItemClickListener {
    private final ArrayList<StockItem> entries = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {

        entries.add(new StockItem("VNM", "VanEck VietNam ETF", "15,56 US$", "+0,19%")); // Sample data point 1
        entries.add(new StockItem("Dow Jones", "Dow Jones Industrial Average", "34.576,59", "+0,22%")); // Sample data point 2
        entries.add(new StockItem("AAPL", "Apple Inc.", "178,18 US$", "+0,35%")); // Sample data point 3
        entries.add(new StockItem("SBUX", "Starbucks Corporation", "95,28 US$","+0,19%"));
        entries.add(new StockItem("NKE", "NIKE, Inc.", "97,67 US$", " -0,27%"));
        // Add more data points as needed
        View view = inflater.inflate(R.layout.fragment_list_stock, container, false);
        ListView listView = view.findViewById(R.id.listView);
        // Set the item click listener for the ListView
        listView.setOnItemClickListener(this);
        StockListAdapter adapter = new StockListAdapter(requireContext(), entries);
        listView.setAdapter(adapter);
        return view;
    }
    public ListStockFragment() {
        // Required empty public constructor
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //Start the MainActivity when an item is clicked
        Intent intent = new Intent(getActivity(), StockDetailActivity.class);
<<<<<<<< HEAD:app/src/main/java/vn/edu/usth/stockdashboard/MenuAfterLogin/MyStockFragment.java
========
        intent.putExtra("stockName", entries.get(i).getSymbol());
        intent.putExtra("companyName", entries.get(i).getName());
        intent.putExtra("money", entries.get(i).getMoney());
        intent.putExtra("percentage", entries.get(i).getPercentage());
>>>>>>>> 357e1cf60db4ff56fa8dab0e29234c500901921a:app/src/main/java/vn/edu/usth/stockdashboard/ListStock/ListStockFragment.java
        startActivity(intent);
    }
}