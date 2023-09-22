package vn.edu.usth.stockdashboard.Menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import vn.edu.usth.stockdashboard.R;

public class HeaderMenuFragment extends Fragment {
    private boolean isUserIdVisible = false;
    private final String userID = "00123A35";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu_after_login, container, false);
        TextView userIdTextView = view.findViewById(R.id.IDHeaderMenu);
        ImageView eyeIconImageView = view.findViewById(R.id.eyeIconOpen);
        eyeIconImageView.setOnClickListener(view1 -> {
            if (isUserIdVisible){
                String bulletText = getBulletString(userID.length());
                userIdTextView.setText(bulletText);
                eyeIconImageView.setImageResource(R.drawable.eye_icon_close);
            } else {
                userIdTextView.setText(userID);
                eyeIconImageView.setImageResource(R.drawable.eye_icon_open);
            }
            isUserIdVisible = !isUserIdVisible;
        });

        String bulletText = getBulletString(userID.length());
        userIdTextView.setText(bulletText);

        return view;
    }
    private String getBulletString(int length){
        StringBuilder bulletText = new StringBuilder();
        for (int i = 0; i < length; i++){
            bulletText.append("•");
        }
        return bulletText.toString();
    }
}