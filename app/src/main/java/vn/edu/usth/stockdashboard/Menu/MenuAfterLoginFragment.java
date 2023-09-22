package vn.edu.usth.stockdashboard.Menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import vn.edu.usth.stockdashboard.DetailStock.StockDetailActivity;
import vn.edu.usth.stockdashboard.R;
import vn.edu.usth.stockdashboard.ListStock.StockItem;
import vn.edu.usth.stockdashboard.ListStock.StockListAdapter;

public class MenuAfterLoginFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<StockItem> menuEntries = new ArrayList<>();
        menuEntries.add(new StockItem("Test", "It's just a Text", "99,69 US$", "+0,19%")); // Sample data point 1
        menuEntries.add(new StockItem("TEST1", "It's still just a Text", "11.222,33", "+0,22%")); // Sample data point 2
        menuEntries.add(new StockItem("TEST2", "Just a Text.", "56,65 US$", "+0,35%")); // Sample data point 3
        menuEntries.add(new StockItem("TEST3", "UwU toi k phai Wibu", "69,96 US$","+0,19%"));
        menuEntries.add(new StockItem("TEST4", "HipHop neva dai", "33,33 US$", " -0,27%"));
        // Add more data points as needed
        View view = inflater.inflate(R.layout.fragment_menu_after_login, container, false);
        ListView listView = view.findViewById(R.id.listMenuView);
        // Set the item click listener for the ListView
        listView.setOnItemClickListener(
                (adapterView, view1, i, l) -> {
                    Intent intent = new Intent(getActivity(), StockDetailActivity.class);
                    intent.putExtra("stockName", menuEntries.get(i).getSymbol());
                    intent.putExtra("companyName", menuEntries.get(i).getName());
                    intent.putExtra("money", menuEntries.get(i).getMoney());
                    intent.putExtra("percentage", menuEntries.get(i).getPercentage());
                    startActivity(intent);
                });
        StockListAdapter adapter = new StockListAdapter(requireContext(), menuEntries);
        listView.setAdapter(adapter);
        return view;
    }

}