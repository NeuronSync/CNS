package com.university.analytics;

public class EnrollmentAnalytics implements Analytics {
    @Override
    public void generateChart() {
        ChartGenerator.showEnrollmentChart();
    }
}
