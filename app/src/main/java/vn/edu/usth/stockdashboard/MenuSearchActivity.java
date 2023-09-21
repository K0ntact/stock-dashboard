package vn.edu.usth.stockdashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MenuSearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_search);

        if (savedInstanceState == null){
            SearchInputFragment searchInputFragment = new SearchInputFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,searchInputFragment).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.listFragmentContainer,searchInputFragment).commit();
        }
    }
}