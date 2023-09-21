package vn.edu.usth.stockdashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ListFragment extends Fragment implements OnFilterListener {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private List<String> originalList;

    public ListFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_search, container, false);
        listView = view.findViewById(R.id.listSearchView);

        originalList = new ArrayList<>();
        originalList.add("Item 1");
        originalList.add("Item 2");
        originalList.add("Item 3");
        originalList.add("Item 4");
        originalList.add("Item 5");

        adapter = new ArrayAdapter<>(requireContext(),R.layout.custom_list_search,originalList);
        listView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onFilter(String query) {
        List<String> filteredList = new ArrayList<>();
        for (String item: originalList){
            if(item.toLowerCase().contains(query.toLowerCase())){
                filteredList.add(item);
            }
        }
        adapter.clear();
        adapter.addAll(filteredList);
        adapter.notifyDataSetChanged();
    }
}