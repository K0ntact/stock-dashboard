package vn.edu.usth.stockdashboard;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class StockBuyFragment extends Fragment {
    boolean isLogin = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            isLogin = getArguments().getBoolean("isLogin");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        AtomicInteger tradeState = new AtomicInteger(); // 0 mean buy, 1 mean sell
        View view = inflater.inflate(R.layout.fragment_stockbuy, container, false);
        Button buybtn = view.findViewById(R.id.buybtn);
        Button sellbtn = view.findViewById(R.id.sellbtn);
        Button placeorderbtn = view.findViewById(R.id.placeorder);
        buybtn.setOnClickListener(v -> {
            // Get background tint color
            int color = Objects.requireNonNull(buybtn.getBackgroundTintList()).getDefaultColor();
            // If color is #161b21 then change to #08CA98
            if (color == Color.parseColor("#161b21")) {
                buybtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#08CA98")));
                sellbtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#161b21")));
                placeorderbtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#08CA98")));
                tradeState.set(0);
            }
        });
        sellbtn.setOnClickListener(v -> {
            // Get background tint color
            int color = Objects.requireNonNull(sellbtn.getBackgroundTintList()).getDefaultColor();
            // If color is #161b21 then change to #08CA98
            if (color == Color.parseColor("#161b21")) {
                sellbtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f04064")));
                buybtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#161b21")));
                placeorderbtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f04064")));
                tradeState.set(1);
            }
        });

        placeorderbtn.setOnClickListener(v ->{
            if (!isLogin) {
                Toast.makeText(StockBuyFragment.this.requireContext(), "Please login to place order", Toast.LENGTH_SHORT).show();
                return;
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(StockBuyFragment.this.requireContext());
            View s = getLayoutInflater().inflate(R.layout.dialog_style, null);
            TextView content = s.findViewById(R.id.confirmMessage);
            Button confirm = s.findViewById(R.id.cfbtn);
            Button cancel = s.findViewById(R.id.cancelbtn);
            content.setText("Are you sure you want to " + (tradeState.get() == 0 ? "buy" : "sell") + " this stock");
            builder.setView(s);
            AlertDialog d = builder.create();
            Objects.requireNonNull(d.getWindow()).setBackgroundDrawableResource(android.R.color.background_dark);
            d.show();
            confirm.setOnClickListener(v1 -> {
                d.dismiss();
                AlertDialog alertDialog = new AlertDialog.Builder(StockBuyFragment.this.requireContext()).create();
                View s1 = getLayoutInflater().inflate(R.layout.dialog_style, null);
                TextView title = s1.findViewById(R.id.titleAlert);
                TextView content1 = s1.findViewById(R.id.confirmMessage);
                Button confirm1 = s1.findViewById(R.id.cfbtn);
                confirm1.setText("OK");
                confirm1.setTextColor(Color.parseColor("#7186bc"));
                confirm1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1f232c")));
                Button cancel1 = s1.findViewById(R.id.cancelbtn);
                cancel1.setVisibility(View.GONE);
                title.setText("Order Placed");
                content1.setText("Your order has been placed");
                confirm1.setOnClickListener(v11 -> alertDialog.dismiss());
                alertDialog.setView(s1);
                Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.background_dark);
                alertDialog.show();
            });

            cancel.setOnClickListener(v12 -> d.dismiss());
        });
        return view;
    }
}