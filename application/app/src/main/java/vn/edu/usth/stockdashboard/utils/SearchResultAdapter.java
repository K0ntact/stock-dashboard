package vn.edu.usth.stockdashboard.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.usth.stockdashboard.DetailStock.StockDetailActivity;
import vn.edu.usth.stockdashboard.R;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {
    private ArrayList<StockItem> items;

    public SearchResultAdapter(ArrayList<StockItem> items, Context context) {
        this.items = items;
    }
    public void filterList(ArrayList<StockItem> filterlist) {
        items = filterlist;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_search, parent, false);
        view.setOnClickListener(v -> {
            TextView stockSymbol = view.findViewById(R.id.stockSymbol);
            Intent intent = new Intent(view.getContext(), StockDetailActivity.class);
            intent.putExtra("stockName", stockSymbol.getText());
            view.getContext().startActivity(intent);
        });

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultAdapter.ViewHolder holder, int position) {
        StockItem item = items.get(position);
        holder.companyName.setText(item.getName());
        holder.stockSymbol.setText(item.getSymbol());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView companyName;
        private final TextView stockSymbol;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            companyName = itemView.findViewById(R.id.companyName);
            stockSymbol = itemView.findViewById(R.id.stockSymbol);
        }
    }
}
