package vn.edu.usth.stockdashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Objects;

import vn.edu.usth.stockdashboard.Menu.MenuBeforeLoginFragment;
import vn.edu.usth.stockdashboard.Menu.MenuFragment;
import vn.edu.usth.stockdashboard.utils.DbQuery;

public class MainActivity extends AppCompatActivity{
    private BottomNavigationView bottomNavigationView;
    private boolean isLogin;
    private boolean doubleBackToExitPressedOnce;
    private final HashMap<String, Fragment> fragmentHashMap;
    private final DbQuery db;

    public MainActivity() {
        isLogin = false;
        doubleBackToExitPressedOnce = false;
        fragmentHashMap = new HashMap<>();
        db = DbQuery.getInstance();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getExtras() != null)
            isLogin = getIntent().getExtras().getBoolean("isLogin");

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.listTab);

        fragmentHashMap.put("stockList", new StockMarketFragment());
        fragmentHashMap.put("menuNotLogin", new MenuBeforeLoginFragment());
        Fragment menuFragment = new MenuFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isLogin", isLogin);
        menuFragment.setArguments(bundle);
        fragmentHashMap.put("menuLogin", menuFragment);

        Fragment stockBuyFragment = new StockBuyFragment();
        stockBuyFragment.setArguments(bundle);
        fragmentHashMap.put("stockBuy", stockBuyFragment);


        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            FragmentManager fragmentManager = getSupportFragmentManager();
            switch (id) {
                case R.id.listTab:
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    transaction.replace(R.id.fragment_container, Objects.requireNonNull(fragmentHashMap.get("stockList")));
                    transaction.commit();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.buystockTab:
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    transaction.replace(R.id.fragment_container, Objects.requireNonNull(fragmentHashMap.get("stockBuy")));
                    transaction.commit();
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.menuTab:
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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
    public void onBackPressed() {
        // If the bottom navigation is not at the first tab, go to the first tab else need to press back twice to exit and pop up a toast
        if (bottomNavigationView.getSelectedItemId() != R.id.listTab) {
            bottomNavigationView.setSelectedItemId(R.id.listTab);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, Objects.requireNonNull(fragmentHashMap.get("stockList"))).commit();
            return;
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);
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