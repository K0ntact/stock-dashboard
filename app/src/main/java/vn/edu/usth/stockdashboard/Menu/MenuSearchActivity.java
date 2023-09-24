package vn.edu.usth.stockdashboard.Menu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.usth.stockdashboard.R;

public class MenuSearchActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapter;
    private OnFilterListener filterListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_search);

        EditText searchEditText = findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (filterListener != null){
//                    filterListener.onFilter(charSequence.toString());
//                }
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

        ListView listView = findViewById(R.id.listSearchView);
        List<String> originalList = new ArrayList<>();
        originalList.add("Item 1");
        originalList.add("Item 2");
        originalList.add("Item 3");
        originalList.add("Item 4");
        originalList.add("Item 5");
        adapter = new ArrayAdapter<>(this,R.layout.custom_list_search, originalList);
        listView.setAdapter(adapter);
    }

    private void performSearch() {

    }

//    public void onFilter(String query) {
//        List<String> filteredList = new ArrayList<>();
//        for (String item: originalList){
//            if(item.toLowerCase().contains(query.toLowerCase())){
//                filteredList.add(item);
//            }
//        }
//        adapter.clear();
//        adapter.addAll(filteredList);
//        adapter.notifyDataSetChanged();
//    }
}