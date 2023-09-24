package vn.edu.usth.stockdashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import vn.edu.usth.stockdashboard.DetailStock.StockDetailActivity;
import vn.edu.usth.stockdashboard.utils.StockItem;
import vn.edu.usth.stockdashboard.utils.StockListAdapter;

public class StockMarketFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock_market, container, false);
        ListView listView = view.findViewById(R.id.listView);

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
