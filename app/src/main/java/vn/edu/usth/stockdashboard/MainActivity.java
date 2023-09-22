package vn.edu.usth.stockdashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Objects;

import vn.edu.usth.stockdashboard.ListStock.StockFragment;
import vn.edu.usth.stockdashboard.Menu.MenuBeforeLoginFragment;
import vn.edu.usth.stockdashboard.Menu.MenuFragment;

public class MainActivity extends AppCompatActivity{
    BottomNavigationView bottomNavigationView;

    boolean isLogin = false;
    boolean doubleBackToExitPressedOnce = false;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getExtras() != null)
            isLogin = getIntent().getExtras().getBoolean("isLogin");

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.listTab);


        HashMap<String, Fragment> fragmentHashMap = new HashMap<>();
        fragmentHashMap.put("stockList", new StockFragment());
        fragmentHashMap.put("menuNotLogin", new MenuBeforeLoginFragment());
        Fragment menuFragment = new MenuFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isLogin", isLogin);
        menuFragment.setArguments(bundle);
        fragmentHashMap.put("menuLogin", menuFragment);
        fragmentHashMap.put("stockBuy", new StockBuyFragment());


        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (id) {
                case R.id.listTab:
                    transaction.replace(R.id.fragment_container, Objects.requireNonNull(fragmentHashMap.get("stockList")));
                    transaction.commit();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.buystockTab:
                    transaction.replace(R.id.fragment_container, Objects.requireNonNull(fragmentHashMap.get("stockBuy")));
                    transaction.commit();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.menuTab:
                    if (isLogin) {
                        transaction.replace(R.id.fragment_container, Objects.requireNonNull(fragmentHashMap.get("menuLogin")));
                    } else {
                        transaction.replace(R.id.fragment_container, Objects.requireNonNull(fragmentHashMap.get("menuNotLogin")));
                    }
                    transaction.commit();
                    overridePendingTransition(0, 0);
                    return true;
                default:
                    return false;
            }
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Objects.requireNonNull(fragmentHashMap.get("stockList"))).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getIntent().getExtras() != null)
            isLogin = getIntent().getExtras().getBoolean("isLogin");
        if (isLogin) {
            bottomNavigationView.getMenu().findItem(R.id.menuTab).setTitle("Menu");
        } else {
            bottomNavigationView.getMenu().findItem(R.id.menuTab).setTitle("Log In");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}