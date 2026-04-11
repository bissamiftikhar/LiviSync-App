package com.livisync.app;

public class Preferences {
    private String sleepSchedule;
    private String city;
    private int cleanliness;
    private int budgetMin;
    private int budgetMax;
    private boolean smokingAllowed;
    private boolean petsAllowed;
    private boolean guestsAllowed;

    // Empty constructor required for Firestore
    public Preferences() {}

    public Preferences(String sleepSchedule, String city, int cleanliness,
                       int budgetMin, int budgetMax, boolean smokingAllowed,
                       boolean petsAllowed, boolean guestsAllowed) {
        this.sleepSchedule = sleepSchedule;
        this.city = city;
        this.cleanliness = cleanliness;
        this.budgetMin = budgetMin;
        this.budgetMax = budgetMax;
        this.smokingAllowed = smokingAllowed;
        this.petsAllowed = petsAllowed;
        this.guestsAllowed = guestsAllowed;
    }

    public String getSleepSchedule() { return sleepSchedule; }
    public String getCity() { return city; }
    public int getCleanliness() { return cleanliness; }
    public int getBudgetMin() { return budgetMin; }
    public int getBudgetMax() { return budgetMax; }
    public boolean isSmokingAllowed() { return smokingAllowed; }
    public boolean isPetsAllowed() { return petsAllowed; }
    public boolean isGuestsAllowed() { return guestsAllowed; }

    public void setSleepSchedule(String sleepSchedule) { this.sleepSchedule = sleepSchedule; }
    public void setCity(String city) { this.city = city; }
    public void setCleanliness(int cleanliness) { this.cleanliness = cleanliness; }
    public void setBudgetMin(int budgetMin) { this.budgetMin = budgetMin; }
    public void setBudgetMax(int budgetMax) { this.budgetMax = budgetMax; }
    public void setSmokingAllowed(boolean smokingAllowed) { this.smokingAllowed = smokingAllowed; }
    public void setPetsAllowed(boolean petsAllowed) { this.petsAllowed = petsAllowed; }
    public void setGuestsAllowed(boolean guestsAllowed) { this.guestsAllowed = guestsAllowed; }
}