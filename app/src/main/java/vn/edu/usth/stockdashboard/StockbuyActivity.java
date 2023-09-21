package vn.edu.usth.stockdashboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class StockbuyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AtomicInteger tradeState = new AtomicInteger(); // 0 mean buy, 1 mean sell
        setContentView(R.layout.activity_stockbuy);
        Button buybtn = (Button) findViewById(R.id.buybtn);
        Button sellbtn = (Button) findViewById(R.id.sellbtn);
        Button placeorderbtn = (Button) findViewById(R.id.placeorder);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(StockbuyActivity.this);
            builder.setTitle(HtmlCompat.fromHtml("<font color='#FFFFFF'>Order Confirm</font>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            String tradeType = tradeState.get() == 0 ? "buy" : "sell";
            builder.setMessage(HtmlCompat.fromHtml("<font color='#FFFFFF'>Are you sure you want to " + tradeType + " this stock</font>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    AlertDialog alertDialog = new AlertDialog.Builder(StockbuyActivity.this).create();
                    alertDialog.setTitle(HtmlCompat.fromHtml("<font color='#FFFFFF'>Order Placed</font>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    alertDialog.setMessage(HtmlCompat.fromHtml("<font color='#FFFFFF'>Your order has been placed</font>", HtmlCompat.FROM_HTML_MODE_LEGACY));
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            (dialog, which) -> dialog.dismiss());
                    Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawableResource(android.R.color.background_dark);
                    alertDialog.show();
                }
            });
            builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
            builder.setCancelable(true);
            AlertDialog d = builder.create();
            Objects.requireNonNull(d.getWindow()).setBackgroundDrawableResource(android.R.color.background_dark);
            d.show();
        });
    }
}