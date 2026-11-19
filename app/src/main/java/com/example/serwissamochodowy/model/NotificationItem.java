package com.example.serwissamochodowy.model;



public class NotificationItem {

    private final String title;
    private final String description;
    private final String date;
    private final int iconRes;
    private boolean read;

    public NotificationItem(String title, String description,
                            String date, int iconRes, boolean read) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.iconRes = iconRes;
        this.read = read;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public int getIconRes() { return iconRes; }
    public boolean isRead() { return read; }

    public void setRead(boolean read) {
        this.read = read;
    }
}

