package pl.krupix.pjatk.nai.algorithm;

import org.apache.log4j.Logger;
import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by krupix on 29.01.2016.
 */
public class HillClimbingAlgorithmImpl implements Algorithm {

    static Logger log = Logger.getLogger(HillClimbingAlgorithmImpl.class);


    private Graph graph;

    @Override
    public void init(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void compute() {


        Node node = graph.getNode(0);

        for (int i = 0; i < 1000; i++) {
            log.info("****************");
            log.info("try for: " + node.getId());
            node.setAttribute("ui.class", "marked");


            color(node);
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (Node n : graph) {
                n.addAttribute("ui.label", n.getAttribute("color") + "  " + n.getId());
            }

            node.setAttribute("ui.class", "");
            node = chooseNext(node);


        }


    }

    private void color(Node node) {

        boolean isNotUse = true;
        int color = -1;

        log.debug("CHOOSE COLOR");

        while(true) {

            boolean used = false;
            color++;

            ArrayList<Node> neighbourList = generateRandomNeighboursList(node);

            for (Node n : neighbourList) {
                if (n.getAttribute("color") == color) {
                    used = true;
                }
            }

            if (!used) {
                break;
            }


        }

        node.setAttribute("color", color);
        log.info("For node id: " + node.getId() + " set color: " + color);
    }


    public Node chooseNext(Node n) {

        log.debug("CHOOSE NEXT");

        ArrayList<Node> neighbourList = generateRandomNeighboursList(n);

        for (Node node : neighbourList) {

            int neighbourNodeColor = node.getAttribute("color");

            if (neighbourNodeColor == -1 || neighbourNodeColor >= (int) node.getAttribute("color")) {
                return node;
            }
        }

        return n;

    }

    private ArrayList<Node> generateRandomNeighboursList(Node node) {

        ArrayList<Node> randomList = new ArrayList<Node>();

        for (Edge edge : node.getEachEdge()) {

            Node tmpNode;

            if (node.getId().equals(edge.getNode0().getId())) {
                randomList.add(edge.getNode1());
            } else {
                randomList.add(edge.getNode0());
            }
        }

        long seed = System.nanoTime();
        Collections.shuffle(randomList, new Random(seed));
        log.debug(randomList);
        return randomList;
    }

}
