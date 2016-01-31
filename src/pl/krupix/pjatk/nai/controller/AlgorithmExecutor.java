package pl.krupix.pjatk.nai.controller;

import org.apache.log4j.Logger;
import org.graphstream.algorithm.generator.BananaTreeGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.PreferentialAttachmentGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
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

        for (int i = 0; i < 20; i++) {
            bt.nextEvents();
        }

        resetColors(graph);
        addLabels(graph);

        bt.end();
        graph.display();

    }


    public void executeAlgorithm() {

        HillClimbingAlgorithmImpl hillClimbing = new HillClimbingAlgorithmImpl();
        hillClimbing.init(graph);
        hillClimbing.compute();

    }


    private void resetColors(Graph g) {
        for (Node node : g) {
            log.info("added color");
            node.addAttribute("color", -1);
        }
    }

    private void addLabels(Graph g) {
        for (Node node : g) {
            node.addAttribute("ui.label", node.getAttribute("color") + " " + node.getId());
        }
    }
}
