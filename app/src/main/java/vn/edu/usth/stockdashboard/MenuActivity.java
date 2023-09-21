package vn.edu.usth.stockdashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = findViewById(R.id.Menutoolbar);
        setSupportActionBar(toolbar);

        //Add the Refresh Button
        ImageView actionRefresh = toolbar.findViewById(R.id.actionRefresh);
        actionRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MenuActivity.this, "Refreshing...", Toast.LENGTH_SHORT).show();
            }
        });
        ImageView actionSearch = toolbar.findViewById(R.id.actionSearch);
        actionSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(
                    MenuActivity.this,
                    R.anim.zoom_in_enter,
                    R.anim.zoom_in_exit);
                Intent intent = new Intent(MenuActivity.this, MenuSearchActivity.class);
                startActivity(intent, options.toBundle());
            }
        });
        ImageView actionNotification = toolbar.findViewById(R.id.actionNotification);
        actionNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(MenuActivity.this, R.anim.zoom_in_enter, R.anim.zoom_in_exit);
                Intent intent = new Intent(MenuActivity.this, NotiMenuActivity.class);
                startActivity(intent, options.toBundle());
            }
        });
    }
}