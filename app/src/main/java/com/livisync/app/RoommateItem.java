package com.livisync.app;

public class RoommateItem {
    private String uid;
    private String name;
    private String bio;
    private String city;
    private String sleep;
    private String budgetRange;
    private int score;

    public RoommateItem(String uid, String name, String bio, String city,
                        String sleep, String budgetRange, int score) {
        this.uid = uid;
        this.name = name;
        this.bio = bio;
        this.city = city;
        this.sleep = sleep;
        this.budgetRange = budgetRange;
        this.score = score;
    }

    public String getUid() { return uid; }
    public String getName() { return name; }
    public String getBio() { return bio; }
    public String getCity() { return city; }
    public String getSleep() { return sleep; }
    public String getBudgetRange() { return budgetRange; }
    public int getScore() { return score; }
}