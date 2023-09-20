package vn.edu.usth.stockdashboard;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class StockActivity extends AppCompatActivity {

    MyStockFragment myStockFragment = new MyStockFragment();
    HeaderMyStockFragment headerMyStockFragment = new HeaderMyStockFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
    }
}
