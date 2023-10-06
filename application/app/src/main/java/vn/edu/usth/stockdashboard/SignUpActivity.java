package vn.edu.usth.stockdashboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import vn.edu.usth.stockdashboard.utils.DbQuery;
import vn.edu.usth.stockdashboard.utils.RestDataNotify;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class SignUpActivity extends AppCompatActivity implements RestDataNotify {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText firstName = findViewById(R.id.first_name);
        EditText lastName = findViewById(R.id.last_name);
        EditText username = findViewById(R.id.usernameEditText);
        EditText password = findViewById(R.id.passwordInput);
        EditText confirmPassword = findViewById(R.id.cfInput);

        Button signUp = findViewById(R.id.buttonSignUp);
        signUp.setOnClickListener(v -> {
            String confirmPasswordString = confirmPassword.getText().toString();
            String passwordString = password.getText().toString();
            if (!confirmPasswordString.equals(passwordString)) {
                Toast.makeText(this, "Password and confirm password are not the same", Toast.LENGTH_SHORT).show();
                return;
            }

            Thread thread = new Thread(() -> {
                try {
                    DbQuery dbQuery = DbQuery.getInstance();
                    dbQuery.addDataNotify(this);
                    dbQuery.register(
                            firstName.getText().toString(),
                            lastName.getText().toString(),
                            username.getText().toString(),
                            password.getText().toString(),
                            Float.toString(20000)
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        });
    }

    @Override
    public void onRestDataReceived(String response) {
        runOnUiThread(() -> {
            if (response.equals("ERROR")) {
                Toast.makeText(this, "Sign up failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LogInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
