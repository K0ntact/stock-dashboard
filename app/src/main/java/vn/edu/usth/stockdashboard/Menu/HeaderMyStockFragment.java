package vn.edu.usth.stockdashboard.Menu;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import vn.edu.usth.stockdashboard.R;


public class HeaderMyStockFragment extends Fragment {

        public HeaderMyStockFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_header_my_stock, container,false);

        //Find the DateTextView
        TextView dateTextView = view.findViewById(R.id.dateTextViewHeader);
        //Get the current date to display
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM, yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(new Date());
        // Set the date to the text view
        dateTextView.setText(currentDate);
        return view;

    }
}