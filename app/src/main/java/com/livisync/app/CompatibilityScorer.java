package com.livisync.app;

public class CompatibilityScorer {

    public static int calculate(Preferences mine, Preferences theirs) {
        int score = 0;

        // Sleep schedule match — 25 points
        if (mine.getSleepSchedule() != null &&
                mine.getSleepSchedule().equals(theirs.getSleepSchedule())) {
            score += 25;
        }

        // Budget overlap — 25 points
        if (budgetOverlaps(mine, theirs)) {
            score += 25;
        }

        // City match — 20 points
        if (mine.getCity() != null &&
                mine.getCity().equalsIgnoreCase(theirs.getCity())) {
            score += 20;
        }

        // Cleanliness closeness — 15 points
        int cleanDiff = Math.abs(mine.getCleanliness() - theirs.getCleanliness());
        if (cleanDiff == 0) score += 15;
        else if (cleanDiff == 1) score += 10;
        else if (cleanDiff == 2) score += 5;

        // Smoking policy match — 5 points
        if (mine.isSmokingAllowed() == theirs.isSmokingAllowed()) score += 5;

        // Pets policy match — 5 points
        if (mine.isPetsAllowed() == theirs.isPetsAllowed()) score += 5;

        // Guests policy match — 5 points
        if (mine.isGuestsAllowed() == theirs.isGuestsAllowed()) score += 5;

        return score; // 0-100
    }

    private static boolean budgetOverlaps(Preferences a, Preferences b) {
        return a.getBudgetMin() <= b.getBudgetMax() &&
                b.getBudgetMin() <= a.getBudgetMax();
    }
}