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
    private EditText searchMenuText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_search);

        searchMenuText = findViewById(R.id.searchMenu);
        searchMenuText.requestFocus();
        searchMenuText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE){
                    String query = searchMenuText.getText().toString();
                    Toast.makeText(MenuSearchActivity.this, "Search Query: " + query, Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }
}