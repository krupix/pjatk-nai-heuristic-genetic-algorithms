package pl.edu.pjatk.nai.controller;

import org.apache.log4j.Logger;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Piotr Osiadacz, s11562 on 31.01.2016.
 */
public class ReportGenerator {

    static Logger log = Logger.getLogger(ReportGenerator.class);

    private Graph graph;
    private int maxColorValue;
    private int uniqueColorsCount;
    private String neighboursStats;
    private ArrayList<ReportBean> raportList;

    public ReportGenerator(Graph graph) {
        raportList = new ArrayList<ReportBean>();
    }

    public void generateReport(Graph graph) {
        this.graph = graph;

        maxColorValue = getMaxColorValue();
        uniqueColorsCount = getUniqueColorsCount();
        neighboursStats = getNodesWithoutNeighbours();

        raportList.add(new ReportBean(maxColorValue, uniqueColorsCount, neighboursStats));
    }



    public int getMaxColorValue() {

        int maxColor = 0;

        for (Node node : graph.getEachNode()) {

            if ((int) node.getAttribute("color") > maxColor) {
                maxColor = (int) node.getAttribute("color");
            }
        }

        return maxColor;

    }

    public int getUniqueColorsCount() {

        HashMap colorHasMap = new HashMap();

        for (Node n : graph.getEachNode()) {
            colorHasMap.put(n.getAttribute("color"), 1);
        }

        return colorHasMap.size();

    }

    public String getNodesWithoutNeighbours() {

        int withSameNodes = 0;
        int wihtoutSameNodes = 0;

        for (Node n : graph.getEachNode()) {

            boolean hasSameNeighboursColor = false;

            for (Edge e : n.getEachEdge()) {

                Node neighbourNode;

                if (e.getNode0().getId().equals(n.getId())) {
                    neighbourNode = e.getNode1();
                } else {
                    neighbourNode = e.getNode0();
                }

                if (neighbourNode.getAttribute("color") == n.getAttribute("color")) {
                    hasSameNeighboursColor = true;
                }
            }

            if (hasSameNeighboursColor) {
                withSameNodes++;
            } else {
                wihtoutSameNodes++;
            }


        }

        return withSameNodes + "/" + wihtoutSameNodes;


    }



    public void printReporsList( ) {

        for (ReportBean report : raportList) {
            log.debug(report.getReportString());
        }
    }

    public void saveToFileCSVReport(String path) {

        try {
            PrintWriter out = new PrintWriter(path);
            out.println("maxColorValue;uniqueColorCount;withSameNodes;withoutSameNodes");
            for (ReportBean report : raportList) {
                out.println(report.getCSVReportString());
            }

            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



    }




}
