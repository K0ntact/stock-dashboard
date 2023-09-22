package vn.edu.usth.stockdashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import vn.edu.usth.stockdashboard.ListStock.StockFragment;
import vn.edu.usth.stockdashboard.Menu.MenuFragment;

public class MainActivity extends AppCompatActivity{
    BottomNavigationView bottomNavigationView;
    boolean isLogin = false;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getExtras() != null)
            isLogin = getIntent().getExtras().getBoolean("isLogin");

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.listTab);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.listTab:
                    if (getSupportFragmentManager().findFragmentByTag("stockList") != null && getSupportFragmentManager().findFragmentByTag("stockList") instanceof StockFragment) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("stockList"))).commit();
                        overridePendingTransition(0, 0);
                        return true;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StockFragment()).addToBackStack("stockList").commit();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.buystockTab:
                    if (getSupportFragmentManager().findFragmentByTag("stockBuy") != null && getSupportFragmentManager().findFragmentByTag("stockBuy") instanceof StockBuyFragment) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("stockBuy"))).commit();
                        overridePendingTransition(0, 0);
                        return true;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StockBuyFragment()).addToBackStack("stockBuy").commit();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.menuTab:
                    if (isLogin) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuFragment()).commit();
                        overridePendingTransition(0, 0);
                        return true;
                    }
                    if (getSupportFragmentManager().findFragmentByTag("menuNotLogin") != null && getSupportFragmentManager().findFragmentByTag("menuNotLogin") instanceof MenuBeforeLoginFragment) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Objects.requireNonNull(getSupportFragmentManager().findFragmentByTag("menuNotLogin"))).commit();
                        overridePendingTransition(0, 0);
                        return true;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MenuBeforeLoginFragment()).addToBackStack("menuNotLogin").commit();
                    overridePendingTransition(0, 0);
                    return true;
                default:
                    return false;
            }
        });
    }
}