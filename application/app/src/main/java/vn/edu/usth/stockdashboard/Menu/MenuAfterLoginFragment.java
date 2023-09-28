package vn.edu.usth.stockdashboard.Menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import vn.edu.usth.stockdashboard.R;
import vn.edu.usth.stockdashboard.utils.StockItem;
import vn.edu.usth.stockdashboard.utils.StockListAdapter;

public class MenuAfterLoginFragment extends Fragment {
    private boolean isUserIdVisible = false;
    private final String userID = "1A537GH6";
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
        RecyclerView listView = view.findViewById(R.id.listMenuView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        StockListAdapter adapter = new StockListAdapter(menuEntries);
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
    private String getBulletString(int length) {
        StringBuilder bulletText = new StringBuilder();
        for (int i = 0; i < length; i++) {
            bulletText.append("•");
        }
        return bulletText.toString();
    }
}