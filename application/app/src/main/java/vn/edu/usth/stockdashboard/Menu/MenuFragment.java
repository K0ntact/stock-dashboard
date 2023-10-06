package vn.edu.usth.stockdashboard.Menu;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import vn.edu.usth.stockdashboard.MainActivity;
import vn.edu.usth.stockdashboard.R;

public class MenuFragment extends Fragment {
    private boolean isLogin = false;
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        savedInstanceState = getArguments();
        if (savedInstanceState != null)
            isLogin = savedInstanceState.getBoolean("isLogin");

        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.menuFragmentContainer, new MenuBeforeLoginFragment()).commit();
        if (isLogin) {
            String userdata = savedInstanceState.getString("userdata");

            MenuAfterLoginFragment menuAfterLoginFragment = new MenuAfterLoginFragment();
            if (userdata != null) {
                Bundle bundle = new Bundle();
                bundle.putString("userdata", userdata);
                menuAfterLoginFragment.setArguments(bundle);
            }
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.menuFragmentContainer, menuAfterLoginFragment);
            requireActivity().getSupportFragmentManager().popBackStack();
            transaction.commit();
        }

        TextView logout = view.findViewById(R.id.logoutView);
        logout.setOnClickListener(view1 -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(MenuFragment.this.requireContext());

            View s = getLayoutInflater().inflate(R.layout.dialog_style, null);
            TextView title = s.findViewById(R.id.titleAlert);
            title.setText("Logout");
            TextView content = s.findViewById(R.id.confirmMessage);
            content.setText("Are you sure to logout?");
            Button confirm = s.findViewById(R.id.cfbtn);
            confirm.setText(HtmlCompat.fromHtml("<b>Logout</b>", HtmlCompat.FROM_HTML_MODE_COMPACT));
            Button cancel = s.findViewById(R.id.cancelbtn);
            cancel.setText("Skip");
            builder.setView(s);
            AlertDialog d = builder.create();
            d.show();
             confirm.setOnClickListener(v -> {
                 d.dismiss();
                 Intent intent = new Intent(MenuFragment.this.getActivity(), MainActivity.class);
                 intent.putExtra("isLogin", false);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                 startActivity(intent);
                 Toast.makeText(MenuFragment.this.requireContext(), "Logged out!", Toast.LENGTH_SHORT).show();
             });
             cancel.setOnClickListener(v -> d.dismiss());
        });

        //Add the Refresh Button
        ImageView actionRefresh = view.findViewById(R.id.actionRefresh);
        actionRefresh.setOnClickListener(view1 -> Toast.makeText(MenuFragment.this.getActivity(), "Refreshing...", Toast.LENGTH_SHORT).show());
        ImageView actionSearch = view.findViewById(R.id.actionSearch);
        actionSearch.setOnClickListener(view1 -> {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(
                        MenuFragment.this.requireActivity(),
                        R.anim.zoom_in_enter,
                        R.anim.zoom_in_exit);
            Intent intent = new Intent(MenuFragment.this.getActivity(), MenuSearchActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent, options.toBundle());
        });
        ImageView actionNotification = view.findViewById(R.id.actionNotification);
        actionNotification.setOnClickListener(view1 -> {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(MenuFragment.this.requireActivity(), R.anim.zoom_in_enter, R.anim.zoom_in_exit);
            Intent intent = new Intent(MenuFragment.this.getActivity(), NotiMenuActivity.class);
            // Clear backstack
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent, options.toBundle());
        });

        return view;
    }
}