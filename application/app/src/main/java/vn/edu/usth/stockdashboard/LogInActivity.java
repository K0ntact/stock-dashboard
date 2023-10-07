package vn.edu.usth.stockdashboard;

import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import vn.edu.usth.stockdashboard.utils.DbQuery;
import vn.edu.usth.stockdashboard.utils.RestDataNotify;

import java.util.concurrent.CountDownLatch;

public class LogInActivity extends AppCompatActivity implements RestDataNotify {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        EditText username = findViewById(R.id.editTextUsername);
        EditText password = findViewById(R.id.editTextPassword);

        Button buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(v -> {
            Thread thread = new Thread(() -> {
                try {
                    DbQuery dbQuery = DbQuery.getInstance();
                    dbQuery.addDataNotify(this);
                    dbQuery.login(
                            username.getText().toString(),
                            password.getText().toString()
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        });

        Button buttonSignUp = findViewById(R.id.buttonSignup);
        buttonSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

    @Override
    public void onRestDataReceived(String response) {
        runOnUiThread(() -> {
            System.out.println("THIS IS THE" + response);
            if (response.equals("ERROR") || response.equals("[]d")) {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                intent.putExtra("isLogin", true);
                intent.putExtra("userdata", response);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}