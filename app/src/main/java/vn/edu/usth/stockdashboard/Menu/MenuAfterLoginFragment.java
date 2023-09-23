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
        menuEntries.add(new StockItem("VNM", "VanEck VietNam ETF", "15,56 US$", "0,19%"));
        menuEntries.add(new StockItem("FB", "Facebook, Inc.", "331,26 US$", "0,19%"));
        menuEntries.add(new StockItem("GOOGL", "Alphabet Inc.", "2.431,38 US$", "0,27%"));
        menuEntries.add(new StockItem("MSFT", "Microsoft Corporation", "259,43 US$", "0,19%"));
        menuEntries.add(new StockItem("NVDA", "NVIDIA Corporation", "191,05 US$", "0,27%"));
        menuEntries.add(new StockItem("PYPL", "PayPal Holdings, Inc.", "279,50 US$", "0,19%"));
        menuEntries.add(new StockItem("TSM", "Taiwan Semiconductor Manufacturing Company Limited", "117,00 US$", "0,27%"));
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