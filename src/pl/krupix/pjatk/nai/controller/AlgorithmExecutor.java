package pl.krupix.pjatk.nai.controller;

import org.apache.log4j.Logger;
import org.graphstream.algorithm.generator.BananaTreeGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import pl.krupix.pjatk.nai.algorithm.GeneticAlgorithmImpl;
import pl.krupix.pjatk.nai.algorithm.HillClimbingAlgorithmImpl;
import pl.krupix.pjatk.nai.graph.GraphGenerator;

/**
 * Created by krupix on 30.01.2016.
 */
public class AlgorithmExecutor {


    static Logger log = Logger.getLogger(AlgorithmExecutor.class);

    private String styleSheet = "node {" + " fill-color: black;" + "}" + "node.marked {" + " fill-color: red;" + "}";
    private Graph graph;
    private ReportGenerator rg;

    private static int ALGORITHM_STEPS = 1000;


    public AlgorithmExecutor() {
        init();
    }

    private void init() {

        generateFromFile();
//        generateFromGenerators();

    }

    private void generateFromFile() {

        GraphGenerator graphGenerator = new GraphGenerator();
        graph = graphGenerator.loadFromFile("/Users/krupix/KruPiX/dev/git/pjatk-nai-project2/graphs/le450_5a.col");
//        graph = graphGenerator.loadFromFile("/Users/krupix/KruPiX/dev/git/pjatk-nai-project2/graphs/le450_5b.col");
//        graph = graphGenerator.loadFromFile("/Users/krupix/KruPiX/dev/git/pjatk-nai-project2/graphs/le450_5c.col");
//        graph = graphGenerator.loadFromFile("/Users/krupix/KruPiX/dev/git/pjatk-nai-project2/graphs/le450_5d.col");
//        graph = graphGenerator.loadFromFile("/Users/krupix/KruPiX/dev/git/pjatk-nai-project2/graphs/le450_15b.col");
//        graph = graphGenerator.loadFromFile("/Users/krupix/KruPiX/dev/git/pjatk-nai-project2/graphs/le450_15c.col");
//        graph = graphGenerator.loadFromFile("/Users/krupix/KruPiX/dev/git/pjatk-nai-project2/graphs/le450_15d.col");
//        graph = graphGenerator.loadFromFile("/Users/krupix/KruPiX/dev/git/pjatk-nai-project2/graphs/le450_25a.col");
//        graph = graphGenerator.loadFromFile("/Users/krupix/KruPiX/dev/git/pjatk-nai-project2/graphs/le450_25b.col");
//        graph = graphGenerator.loadFromFile("/Users/krupix/KruPiX/dev/git/pjatk-nai-project2/graphs/le450_25c.col");
//        graph = graphGenerator.loadFromFile("/Users/krupix/KruPiX/dev/git/pjatk-nai-project2/graphs/le450_25d.col");
//        graph = graphGenerator.loadFromFile("/Users/krupix/KruPiX/dev/git/pjatk-nai-project2/graphs/games120.col");

        graph.addAttribute("ui.stylesheet", styleSheet);
        graph.setAutoCreate(true);
        graph.setStrict(false);

        resetColors(graph);
        addLabels(graph);

//        graph.display();
    }

    private void generateFromGenerators() {
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
//        graph.display();



    }


    public void executeHillClimbingAlgorithm() {

        HillClimbingAlgorithmImpl hillClimbing = new HillClimbingAlgorithmImpl();
        hillClimbing.init(graph);

        rg = new ReportGenerator(graph);
        rg.generateReport(graph);

        for (int i = 0; i < ALGORITHM_STEPS; i++) {
            hillClimbing.compute();
            rg.generateReport(graph);
        }
        refreshLabels();

    }

    public void executeGeneticAlgorithm() {

        GeneticAlgorithmImpl geneticAlgorithm = new GeneticAlgorithmImpl();
        geneticAlgorithm.init(graph);

        rg = new ReportGenerator(graph);
        rg.generateReport(graph);

        for (int i = 0; i < ALGORITHM_STEPS; i++) {
            geneticAlgorithm.compute();
            rg.generateReport(graph);
        }


        printAttriubtes("color", graph);

        refreshLabels();

    }


    private void resetColors(Graph g) {
        for (Node node : g) {
//            log.info("added color");
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
            log.debug(n.getId() + " " + attribute + ": " + n.getAttribute(attribute));
        }

    }

    public void refreshLabels() {
        for (Node n : graph) {
            n.addAttribute("ui.label", n.getAttribute("color") + "  " + n.getId());
        }
    }


    public ReportGenerator getRg() {
        return rg;
    }
}
