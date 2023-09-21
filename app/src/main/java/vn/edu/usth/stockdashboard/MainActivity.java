package vn.edu.usth.stockdashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import vn.edu.usth.stockdashboard.ListStock.StockFragment;

public class MainActivity extends AppCompatActivity{
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if(id == R.id.listTab) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StockFragment()).commit();
                return true;
            } else if (id == R.id.viewListTab) {
                return true;
            }
            else return id == R.id.menuTab;
        });
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StockFragment()).commit();
    }
}