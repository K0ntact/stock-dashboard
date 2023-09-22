package vn.edu.usth.stockdashboard.Menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import vn.edu.usth.stockdashboard.R;

public class SearchInputFragment extends Fragment {
    private EditText searchEditText;
    private OnFilterListener filterListener;
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
                if (filterListener != null){
                    filterListener.onFilter(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        searchEditText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH){
                performSearch();
                return true;
            }
            return false;
        });
        return view;
    }
    private void performSearch() {

    }
}