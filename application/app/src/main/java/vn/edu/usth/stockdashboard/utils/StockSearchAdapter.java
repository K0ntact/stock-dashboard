package vn.edu.usth.stockdashboard.utils;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.usth.stockdashboard.DetailStock.StockDetailActivity;
import vn.edu.usth.stockdashboard.MainActivity;
import vn.edu.usth.stockdashboard.R;

public class StockSearchAdapter extends RecyclerView.Adapter<StockSearchAdapter.ViewHolder> {
    private final ArrayList<StockItem> stockList;
    private final boolean buyStock;
    private final boolean isLogin;

    public StockSearchAdapter(ArrayList<StockItem> stockList, boolean buyStock, boolean isLogin) {
        this.stockList = stockList;
        this.buyStock = buyStock;
        this.isLogin = isLogin;
    }

    @NonNull
    @Override
    public StockSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_search, parent, false);
        return new ViewHolder(view, buyStock, isLogin);
    }

    @Override
    public void onBindViewHolder(@NonNull StockSearchAdapter.ViewHolder holder, int position) {
        holder.bindData(stockList.get(position));
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final boolean buyStock;
        private final boolean isLogin;

        public ViewHolder(@NonNull View itemView, boolean buyStock, boolean isLogin) {
            super(itemView);
            this.buyStock = buyStock;
            this.isLogin = isLogin;
        }

        public void bindData(StockItem stockItem) {
            TextView symbolTextView = itemView.findViewById(R.id.stockSymbol);
            TextView nameTextView = itemView.findViewById(R.id.companyName);

            symbolTextView.setText(stockItem.getSymbol());
            nameTextView.setText(stockItem.getName());
            if (!buyStock) {
                itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(v.getContext(), StockDetailActivity.class);
                    intent.putExtra("stockName", stockItem.getSymbol());
                    intent.putExtra("companyName", stockItem.getName());
                    v.getContext().startActivity(intent);
                });
            }

            if (buyStock) {
                itemView.setOnClickListener(v -> {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    intent.putExtra("stockName", stockItem.getSymbol());
                    intent.putExtra("companyName", stockItem.getName());
                    intent.putExtra("slide", 1);
                    intent.putExtra("isLogin", isLogin);
                    v.getContext().startActivity(intent);
                });
            }
        }
    }
}
