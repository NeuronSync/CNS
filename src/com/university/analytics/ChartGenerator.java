package com.university.analytics;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartGenerator {
    public static void showEnrollmentChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // Add data from database (example)
        dataset.addValue(30, "Students", "Java Programming");
        dataset.addValue(25, "Students", "Network Security");

        JFreeChart chart = ChartFactory.createBarChart(
            "Enrollments", "Course", "Students", dataset
        );
        ChartFrame frame = new ChartFrame("Analytics", chart);
        frame.pack();
        frame.setVisible(true);
    }
}
