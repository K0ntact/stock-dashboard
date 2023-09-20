package vn.edu.usth.stockdashboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.usth.stockdashboard.ListStock.StockFragment;

public class SignUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Button signUp = findViewById(R.id.buttonSignUp);
        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, StockFragment.class);
            startActivity(intent);
        });
    }
}
