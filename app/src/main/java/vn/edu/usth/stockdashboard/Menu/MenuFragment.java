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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_menu, container, false);

        Toolbar toolbar = view.findViewById(R.id.Menutoolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

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
            ActivityOptionsCompat options = ActivityOptionsCompat.makeCustomAnimation(MenuFragment.this.getActivity(), R.anim.zoom_in_enter, R.anim.zoom_in_exit);
            Intent intent = new Intent(MenuFragment.this.getActivity(), NotiMenuActivity.class);
            startActivity(intent, options.toBundle());
        });
        return view;
    }
}