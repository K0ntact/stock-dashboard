package vn.edu.usth.stockdashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CandleChartFragment firstFragment = new CandleChartFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.chartContainerView, firstFragment).commit();


        setContentView(R.layout.activity_main);
    }
}