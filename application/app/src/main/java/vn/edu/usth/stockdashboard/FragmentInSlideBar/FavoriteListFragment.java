package vn.edu.usth.stockdashboard.FragmentInSlideBar;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import vn.edu.usth.stockdashboard.R;

public class FavoriteListFragment extends Fragment {
    private static final String KEY_TITLE = "Favorite List";
    public FavoriteListFragment() {
        // Required empty public constructor
    }

    public static FavoriteListFragment newInstance(String param1) {
        FavoriteListFragment fragment = new FavoriteListFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        String title = getArguments().getString(KEY_TITLE);
        ((TextView)view.findViewById(R.id.titleFragment)).setText(KEY_TITLE);
    }
}