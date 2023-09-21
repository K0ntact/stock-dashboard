package vn.edu.usth.stockdashboard;

public class NotificationItem {
    private String title;
    private String description;

    public NotificationItem(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}