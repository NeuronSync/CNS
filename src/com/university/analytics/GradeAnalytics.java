package com.university.analytics;

public class GradeAnalytics implements Analytics {
    @Override
    public void generateChart() {
        ChartGenerator.showGradeDistribution();
    }
}

