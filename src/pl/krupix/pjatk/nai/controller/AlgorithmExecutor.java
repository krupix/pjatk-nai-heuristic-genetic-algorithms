package pl.krupix.pjatk.nai.controller;

import org.apache.log4j.Logger;
import org.graphstream.algorithm.generator.BananaTreeGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import pl.krupix.pjatk.nai.algorithm.GeneticAlgorithmImpl;
import pl.krupix.pjatk.nai.algorithm.HillClimbingAlgorithmImpl;

/**
 * Created by krupix on 30.01.2016.
 */
public class AlgorithmExecutor {


    static Logger log = Logger.getLogger(AlgorithmExecutor.class);

    private String styleSheet = "node {" + " fill-color: black;" + "}" + "node.marked {" + " fill-color: red;" + "}";
    private Graph graph;


    public AlgorithmExecutor() {
        init();
    }

    private void init() {

        graph = new SingleGraph("nai");
        graph.addAttribute("ui.stylesheet", styleSheet);
        graph.setAutoCreate(true);
        graph.setStrict(false);


        Generator bt = new BananaTreeGenerator();

        bt.addSink(graph);
        bt.begin();

        for (int i = 0; i < 4; i++) {
            bt.nextEvents();
        }

        resetColors(graph);
        addLabels(graph);

        bt.end();
        graph.display();

    }


    public void executeHillClimbingAlgorithm() {

        HillClimbingAlgorithmImpl hillClimbing = new HillClimbingAlgorithmImpl();
        hillClimbing.init(graph);
        hillClimbing.compute();


        refreshLabels();

    }

    public void executeGeneticAlgorithm() {

        GeneticAlgorithmImpl geneticAlgorithm = new GeneticAlgorithmImpl();
        geneticAlgorithm.init(graph);
        geneticAlgorithm.compute();

        printAttriubtes("color", graph);
        printAttriubtes("tmp_color", graph);

        refreshLabels();

    }


    private void resetColors(Graph g) {
        for (Node node : g) {
            log.info("added color");
            node.addAttribute("color", 0);
        }
    }

    private void addLabels(Graph g) {
        for (Node node : g) {
            node.addAttribute("ui.label", node.getAttribute("color") + " " + node.getId());
        }
    }

    public static void printAttriubtes(String attribute, Graph graph) {

        log.debug("** GRAPH COLORS **");

        for (Node n : graph.getEachNode()) {
            log.debug(n.getId() + " "+ attribute + ": " + n.getAttribute(attribute));
        }

    }

    public void refreshLabels() {
        for (Node n : graph) {
            n.addAttribute("ui.label", n.getAttribute("color") + "  " + n.getId());
        }
    }
}
