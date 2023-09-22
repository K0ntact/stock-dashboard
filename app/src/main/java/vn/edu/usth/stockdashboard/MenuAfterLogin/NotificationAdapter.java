package vn.edu.usth.stockdashboard.MenuAfterLogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

import vn.edu.usth.stockdashboard.R;

public class NotificationAdapter extends ArrayAdapter<NotificationItem> {
    private Context context;
    public NotificationAdapter(Context context, List<NotificationItem> notificationList){
        super(context, 0, notificationList);
        this.context = context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_notification,parent,false);
        }
        NotificationItem notificationItem = getItem(position);

        TextView NotificationTitle = convertView.findViewById(R.id.titleNotification);
        TextView DescriptionTitle = convertView.findViewById(R.id.descriptionNotification);
        NotificationTitle.setText(notificationItem.getTitle());
        DescriptionTitle.setText(notificationItem.getDescription());
        View separatorView = convertView.findViewById((R.id.separatorNotificationView));
        if (position == getCount() - 1){
            separatorView.setVisibility(View.GONE);
        } else {
            separatorView.setVisibility(View.VISIBLE);
        }
        return convertView;
    }
}
