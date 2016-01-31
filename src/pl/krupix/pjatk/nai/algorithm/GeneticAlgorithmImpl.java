package pl.krupix.pjatk.nai.algorithm;

import org.apache.log4j.Logger;
import org.apache.log4j.pattern.IntegerPatternConverter;
import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import pl.krupix.pjatk.nai.controller.AlgorithmExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by krupix on 29.01.2016.
 */
public class GeneticAlgorithmImpl implements Algorithm {

    static Logger log = Logger.getLogger(GeneticAlgorithmImpl.class);

    private static String ZERO_BITS = "00";

    private Graph graph;
    private ArrayList<Node> populationList;


    @Override
    public void init(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void compute() {

        showBinaryValues();
        populationList = generatePopulation();

        // mutate :3

        crossingOver(populationList);
        ratePopulation(populationList);
//        TODO
        clearRatingAndTmpColorsAttributes();

    }

    private void showBinaryValues() {

        for (Node node : graph.getEachNode()) {
            int color = node.getAttribute("color");
            log.debug(color + " in binary " + Integer.toBinaryString(color));
        }
    }

    private ArrayList<Node> generatePopulation() {

        ArrayList<Node> populationList = new ArrayList<Node>();

        for (Node node : graph.getEachNode()) {
            if (new Random().nextInt(2) == 1) {
                populationList.add(node);
//                TODO : remove form here mtutate
                mutate(node);
            }
        }

        log.debug("Generated population has: " + populationList.size() + " nodes");

        return populationList;
    }

    private void ratePopulation(ArrayList<Node> populationList) {


        int colorsSum = calculateColorSum();

        log.debug("Sum of neighbours same colors: " + colorsSum);

        calculateNodesRating(colorsSum);

        chooseRandomBestNodes(colorsSum);

    }

    private void chooseRandomBestNodes(int colorSum) {

        for (int i = 0; i < (colorSum / 2); i++) {

            int choosenNode = new Random().nextInt(colorSum);

            for (Node node : populationList) {
                String tmp_color = "" + node.getAttribute("tmp_color");
                if (choosenNode <= Integer.parseInt(tmp_color) ) {
                    node.setAttribute("color", tmp_color);
                }
            }

        }

    }

    private int calculateColorSum() {

        int colorsSum = 0;

        for (Node node : populationList) {

            int sameColorsNodes = 0;

            for (Edge edge : node.getEachEdge()) {

                Node n;

                if (node.getId().equals(edge.getNode0())) {
                    n = edge.getNode1();
                } else {
                    n = edge.getNode0();
                }

                if ((int) n.getAttribute("color") == node.getAttribute("color")) {
                    sameColorsNodes++;
                }


            }
            colorsSum += sameColorsNodes;
            log.debug("Node: " + node.getId() + ", (Color: " + node.getAttribute("color") + "), has: " + sameColorsNodes + " neighbours with same colors");
        }

        return colorsSum;

    }

    private void calculateNodesRating(int colorsSum) {
        for (Node node : populationList) {
            node.setAttribute("rating", getNodeRating(node, colorsSum));
        }
    }

    private int getNodeRating(Node n, int colorsSum) {

        AlgorithmExecutor.printAttriubtes("tmp_color", graph);

        log.debug("n.getAttribute(\"tmp_color\"): " + n.getAttribute("tmp_color"));
        String tmp_color  = "" + n.getAttribute("tmp_color");

        return (Integer.parseInt(tmp_color) * colorsSum) / 100;
    }

    private void crossingOver(ArrayList<Node> populationList) {

        log.info("CROSSING OVER");

        long seed = System.nanoTime();
        Collections.shuffle(populationList, new Random(seed));

        ArrayList<NodesPair> nodesPairsList = new ArrayList<NodesPair>();

        int pairsCount = populationList.size();
        log.debug("Paris count: " + pairsCount);

        if (pairsCount % 2 != 0) {
            pairsCount--;
        }

        for (int i = 0; i < pairsCount; i++) {
            Node node1 = populationList.get(i);
            i++;
            Node node2 = populationList.get(i);
            nodesPairsList.add(new NodesPair(node1, node2));
        }

        for (NodesPair pair : nodesPairsList) {
            pair.crossingOverBits();
        }


    }


    private void mutate(Node node) {

        int color = node.getAttribute("color");
        String binaryColor = colorIntToBytes(color);

        int changeBit = chooseBitToChange(binaryColor);

        log.debug("BEFORE: Color: dec: " + color + ", binary: " + binaryColor + ", length: " + binaryColor.length()
                + " - will change: " + changeBit);

        binaryColor = changeBitColor(binaryColor, changeBit);
        color = Integer.parseInt(binaryColor, 2);

        log.debug(" AFTER: Color: dec: " + color + ", binary: " + binaryColor + ", length: " + binaryColor.length());

        node.setAttribute("tmp_color", Integer.toBinaryString(color));


    }

    private String negateBit(String bit) {

        int b = Integer.parseInt(bit);

        if (b == 1) {
            return "0";
        } else {
            return "1";
        }
    }

    private String changeBitColor(String binaryColor, int changeBit) {

        String[] binaryColorArr = binaryColor.split("");

        if (changeBit != 0) {
            binaryColorArr[changeBit] = negateBit(binaryColorArr[changeBit]);
        }

        binaryColor = "";
        for (int i = 0; i < binaryColorArr.length; i++) {
            binaryColor += binaryColorArr[i];
        }

        return binaryColor;

    }

    private String colorIntToBytes(int color) {
        return ZERO_BITS + Integer.toBinaryString(color);
    }

    private int chooseBitToChange(String binaryColor) {
        return new Random().nextInt(binaryColor.length() + 1);
    }

    private void clearRatingAndTmpColorsAttributes() {
        for (Node n : graph.getEachNode()) {
            n.setAttribute("tmp_color", "");
            n.setAttribute("rating", "");
        }
    }


}
