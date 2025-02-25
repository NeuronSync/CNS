package com.university.gui.admin;

import com.university.dao.AnalyticsDAO;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

public class AnalyticsReporting extends JFrame {
    private AnalyticsDAO analyticsDAO;
    private JTabbedPane tabbedPane;

    public AnalyticsReporting() {
        analyticsDAO = new AnalyticsDAO();
        initializeUI();
        loadData();
    }

    private void initializeUI() {
        setTitle("Analytics & Reporting");
        setSize(1200, 800);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        createEnrollmentTab();
        createGradeDistributionTab();
        createAverageGradesTab();

        add(tabbedPane);
    }

    private void createEnrollmentTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Table
        JTable table = createStyledTable(new String[]{"Course", "Total Enrollments"});
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(400, 0));

        // Chart
        ChartPanel chartPanel = new ChartPanel(createEnrollmentChart());
        chartPanel.setPreferredSize(new Dimension(600, 0));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScroll, chartPanel);
        splitPane.setResizeWeight(0.4);
        panel.add(splitPane, BorderLayout.CENTER);

        tabbedPane.addTab("Enrollments", panel);
    }

    private void createGradeDistributionTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Table
        JTable table = createStyledTable(new String[]{"Grade", "Student Count"});
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(400, 0));

        // Chart
        ChartPanel chartPanel = new ChartPanel(createGradeDistributionChart());
        chartPanel.setPreferredSize(new Dimension(600, 0));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScroll, chartPanel);
        splitPane.setResizeWeight(0.4);
        panel.add(splitPane, BorderLayout.CENTER);

        tabbedPane.addTab("Grade Distribution", panel);
    }

    private void createAverageGradesTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Table
        JTable table = createStyledTable(new String[]{"Course", "Average Grade"});
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(400, 0));

        // Chart
        ChartPanel chartPanel = new ChartPanel(createAverageGradeChart());
        chartPanel.setPreferredSize(new Dimension(600, 0));

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableScroll, chartPanel);
        splitPane.setResizeWeight(0.4);
        panel.add(splitPane, BorderLayout.CENTER);

        tabbedPane.addTab("Average Grades", panel);
    }

    private JTable createStyledTable(String[] columns) {
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        styleTable(table);
        return table;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 5));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, 
                        isSelected, hasFocus, row, column);
                
                if (isSelected) {
                    c.setBackground(new Color(220, 240, 255));
                } else {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 245, 245));
                }
                return c;
            }
        });
    }

    private JFreeChart createEnrollmentChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<String[]> data = analyticsDAO.getEnrollmentAnalytics();
        for (String[] row : data) {
            dataset.addValue(Double.parseDouble(row[1]), "Enrollments", row[0]);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Course Enrollments", 
                "Course", 
                "Number of Students", 
                dataset, 
                PlotOrientation.VERTICAL,
                true, true, false);

        chart.getPlot().setBackgroundPaint(Color.WHITE);
        return chart;
    }

    private JFreeChart createGradeDistributionChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        List<String[]> data = analyticsDAO.getGradeDistribution();
        for (String[] row : data) {
            dataset.setValue(row[0], Double.parseDouble(row[1]));
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Grade Distribution", 
                dataset, 
                true, true, false);

        chart.getPlot().setBackgroundPaint(Color.WHITE);
        return chart;
    }

    private JFreeChart createAverageGradeChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<String[]> data = analyticsDAO.getAverageGrades();
        for (String[] row : data) {
            dataset.addValue(Double.parseDouble(row[1]), "Average Grade", row[0]);
        }

        JFreeChart chart = ChartFactory.createLineChart(
                "Average Grades by Course", 
                "Course", 
                "Average Grade", 
                dataset, 
                PlotOrientation.VERTICAL,
                true, true, false);

        chart.getPlot().setBackgroundPaint(Color.WHITE);
        return chart;
    }

    private void loadData() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                loadTableData(0, analyticsDAO.getEnrollmentAnalytics());
                loadTableData(1, analyticsDAO.getGradeDistribution());
                loadTableData(2, analyticsDAO.getAverageGrades());
                return null;
            }
        };
        worker.execute();
    }

    private void loadTableData(int tabIndex, List<String[]> data) {
        JTable table = (JTable) ((JScrollPane) ((JSplitPane) tabbedPane.getComponentAt(tabIndex))
                .getLeftComponent()).getViewport().getView();
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        for (String[] row : data) {
            model.addRow(row);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new AnalyticsReporting().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
