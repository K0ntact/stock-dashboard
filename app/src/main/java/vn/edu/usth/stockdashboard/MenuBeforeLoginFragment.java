package vn.edu.usth.stockdashboard;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MenuBeforeLoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_menu_before_login, container, false);

        Button buttonLogin = view.findViewById(R.id.loginButton);
        buttonLogin.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LogInActivity.class);
            startActivity(intent);
        });

        Button buttonSignUp = view.findViewById(R.id.signUpButton);
        buttonSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SignUpActivity.class);
            startActivity(intent);
        });

        return view;
    }
}