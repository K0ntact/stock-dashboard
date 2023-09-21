package vn.edu.usth.stockdashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class SearchInputFragment extends Fragment {
    private EditText searchEditText;
    public SearchInputFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_input,container,false);
        searchEditText = view.findViewById(R.id.searchEditText);
        ListFragment listFragment = (ListFragment) getChildFragmentManager().findFragmentById(R.id.listFragmentContainer);
        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ListFragment listFragment = (ListFragment) getChildFragmentManager().findFragmentById(R.id.listFragmentContainer);
                if (listFragment != null){
                    listFragment.filterlist(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        return view;
    }
}