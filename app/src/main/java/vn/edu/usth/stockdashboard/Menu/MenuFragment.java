package vn.edu.usth.stockdashboard.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import vn.edu.usth.stockdashboard.R;

public class MenuFragment extends Fragment {
    private boolean isLogin = false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_menu, container, false);
        savedInstanceState = getArguments();
        if (savedInstanceState != null)
            isLogin = savedInstanceState.getBoolean("isLogin");
        if (isLogin) {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.menuFragmentContainer, new MenuAfterLoginFragment()).commit();
        }

        Toolbar toolbar = view.findViewById(R.id.Menutoolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);

        //Add the Refresh Button
        ImageView actionRefresh = toolbar.findViewById(R.id.actionRefresh);
        actionRefresh.setOnClickListener(view1 -> Toast.makeText(MenuFragment.this.getActivity(), "Refreshing...", Toast.LENGTH_SHORT).show());
        ImageView actionSearch = toolbar.findViewById(R.id.actionSearch);
        actionSearch.setOnClickListener(view1 -> {
//            ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(
//                MenuFragment.this.getActivity(),
//                R.anim.zoom_in_enter,
//                R.anim.zoom_in_exit);
            Intent intent = new Intent(MenuFragment.this.getActivity(), MenuSearchActivity.class);
            startActivity(intent);
        });
        ImageView actionNotification = toolbar.findViewById(R.id.actionNotification);
        actionNotification.setOnClickListener(view1 -> {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(MenuFragment.this.requireActivity(), R.anim.zoom_in_enter, R.anim.zoom_in_exit);
            Intent intent = new Intent(MenuFragment.this.getActivity(), NotiMenuActivity.class);
            startActivity(intent, options.toBundle());
        });
        return view;
    }
}