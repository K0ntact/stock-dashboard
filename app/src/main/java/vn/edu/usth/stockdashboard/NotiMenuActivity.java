package vn.edu.usth.stockdashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class NotiMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_menu);

        List<NotificationItem> notificationList = new ArrayList<>();
        notificationList.add(new NotificationItem("Notification 1","Description 1"));
        notificationList.add(new NotificationItem("Notification 2","Description 2"));
        notificationList.add(new NotificationItem("Notification 3","Description 3"));
        notificationList.add(new NotificationItem("Notification 4","Description 4"));
        notificationList.add(new NotificationItem("Notification 5","Description 5"));

        RecyclerView recyclerView = findViewById(R.id.notificationRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        NotificationAdapter adapter = new NotificationAdapter(notificationList);
        recyclerView.setAdapter(adapter);
    }
}