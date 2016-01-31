package pl.edu.pjatk.nai.controller;

/**
 * Piotr Osiadacz, s11562 on 31.01.2016.
 */
public class ReportBean {


    private int maxColorValue;
    private int uniqueColorsCount;
    private String neighboursStats;


    public ReportBean(int maxColorValue, int uniqueColorsCount, String neighboursStats) {
        this.maxColorValue = maxColorValue;
        this.uniqueColorsCount = uniqueColorsCount;
        this.neighboursStats = neighboursStats;
    }

    public int getMaxColorValue() {
        return maxColorValue;
    }

    public void setMaxColorValue(int maxColorValue) {
        this.maxColorValue = maxColorValue;
    }

    public int getUniqueColorsCount() {
        return uniqueColorsCount;
    }

    public void setUniqueColorsCount(int uniqueColorsCount) {
        this.uniqueColorsCount = uniqueColorsCount;
    }

    public String getNeighboursStats() {
        return neighboursStats;
    }

    public void setNeighboursStats(String neighboursStats) {
        this.neighboursStats = neighboursStats;
    }

    public String getReportString() {
        return "Report[ maxColorValue = " + maxColorValue + ", uniqueColorsCount = " + uniqueColorsCount + ", neighboursStats = " + neighboursStats + " ]";
    }

    public String getCSVReportString() {
        String[] arr = neighboursStats.split("/");
        return maxColorValue + ";" + uniqueColorsCount + ";" + arr[0] + ";" + arr[1];
    }

}
