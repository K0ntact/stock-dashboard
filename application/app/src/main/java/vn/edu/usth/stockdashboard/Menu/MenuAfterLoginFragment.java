package vn.edu.usth.stockdashboard.Menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

import vn.edu.usth.stockdashboard.R;
import vn.edu.usth.stockdashboard.utils.*;

public class MenuAfterLoginFragment extends Fragment implements DataNotify {
    private boolean isUserIdVisible = false;
    private final String userID = "1A537GH6";

    private ClientEndpoint clientEndpoint;
    private final ArrayList<StockItem> entries;
    private final StockListAdapter adapter;

    public MenuAfterLoginFragment(){
        entries = new ArrayList<>();
        entries.add(new StockItem("META", "Meta Platforms, Inc."));
        entries.add(new StockItem("GOOGL", "Alphabet Inc."));
        entries.add(new StockItem("MSFT", "Microsoft Corporation"));
        entries.add(new StockItem("NVDA", "NVIDIA Corporation"));
        entries.add(new StockItem("PYPL", "PayPal Holdings, Inc."));
        entries.add(new StockItem("TSM", "Taiwan Semiconductor Manufacturing Company Limited"));
        adapter = new StockListAdapter(entries);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread thread = new Thread(() -> {
            try {
                String[] symbols = {"AAPL", "SBUX", "NKE", "TSLA", "AMZN", "META", "GOOGL", "MSFT", "NVDA", "PYPL", "TSM", "V", "WMT"};
//                String[] symbols = {"BINANCE:BTCUSDT"};
//                String[] symbols = {"BINANCE:BTCUSDT", "BINANCE:ETHUSDT", "BINANCE:BNBUSDT", "BINANCE:ADAUSDT", "BINANCE:DOTUSDT", "BINANCE:XRPUSDT"};
//                clientEndpoint = new ClientEndpoint(new URI("ws://146.190.83.69:8080/trade?uuid=bhhoang"), symbols);
                clientEndpoint = new ClientEndpoint(new URI("ws://192.168.1.2:8080/trade?uuid=bhhoang"), symbols);
                clientEndpoint.setInterval(3000);
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
        // Add more data points as needed
        View view = inflater.inflate(R.layout.fragment_menu_after_login, container, false);
        RecyclerView listView = view.findViewById(R.id.listMenuView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);

        // Set the eye icon to show, hide ID
        TextView userIdTextView = view.findViewById(R.id.IDHeaderMenu);
        ImageView eyeIconImageView = view.findViewById(R.id.eyeIconOpen);
        eyeIconImageView.setOnClickListener(view12 -> {
            if (isUserIdVisible){
                String bulletText = getBulletString(userID.length());
                userIdTextView.setText(bulletText);
                eyeIconImageView.setImageResource(R.drawable.eye_icon_close);
            } else {
                userIdTextView.setText(userID);
                eyeIconImageView.setImageResource(R.drawable.eye_icon_open);
            }
            isUserIdVisible = !isUserIdVisible;
        });
        String bulletText = getBulletString(userID.length());
        userIdTextView.setText(bulletText);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("On Destroy");
        clientEndpoint.close(1000, "Close from client");
    }

    private String getBulletString(int length) {
        StringBuilder bulletText = new StringBuilder();
        for (int i = 0; i < length; i++) {
            bulletText.append("•");
        }
        return bulletText.toString();
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