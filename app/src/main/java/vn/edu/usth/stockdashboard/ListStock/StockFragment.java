package vn.edu.usth.stockdashboard.ListStock;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import vn.edu.usth.stockdashboard.DetailStock.StockDetailActivity;
import vn.edu.usth.stockdashboard.R;

public class StockFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock, container, false);
        ListView listView = view.findViewById(R.id.listView);

        ArrayList<StockItem> entries = new ArrayList<>();
        entries.add(new StockItem("VNM", "VanEck VietNam ETF", "15,56 US$", "0,19%")); // Sample data point 1
        entries.add(new StockItem("Dow Jones", "Dow Jones Industrial Average", "34.576,59", "0,22%")); // Sample data point 2
        entries.add(new StockItem("AAPL", "Apple Inc.", "178,18 US$", "0,35%")); // Sample data point 3
        entries.add(new StockItem("SBUX", "Starbucks Corporation", "95,28 US$","0,19%"));
        entries.add(new StockItem("NKE", "NIKE, Inc.", "97,67 US$", " 0,27%"));
        // Set the item click listener for the ListView
        listView.setOnItemClickListener(
                (adapterView, view1, i, l) -> {
                    Intent intent = new Intent(getActivity(), StockDetailActivity.class);
                    intent.putExtra("stockName", entries.get(i).getSymbol());
                    intent.putExtra("companyName", entries.get(i).getName());
                    intent.putExtra("money", entries.get(i).getMoney());
                    intent.putExtra("percentage", entries.get(i).getPercentage());
                    startActivity(intent);
                });
        StockListAdapter adapter = new StockListAdapter(requireContext(), entries);
        listView.setAdapter(adapter);
        return view;
    }
}
