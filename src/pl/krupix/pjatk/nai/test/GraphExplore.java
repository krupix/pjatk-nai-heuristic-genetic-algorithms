package pl.krupix.pjatk.nai.test;

import org.apache.log4j.PropertyConfigurator;
import org.graphstream.algorithm.generator.*;
import org.graphstream.algorithm.generator.lcf.GrayGraphGenerator;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.view.Viewer;
import pl.krupix.pjatk.nai.algorithm.HillClimbingAlgorithmImpl;

import java.util.Iterator;

/**
 * Created by krupix on 25.01.2016.
 */
public class GraphExplore {


    public static void main(String args[]) {

        String log4jConfPath = "log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);


        new GraphExplore();
    }


    public GraphExplore() {

        Graph graph = new SingleGraph("tutorial 1");

        graph.addAttribute("ui.stylesheet", styleSheet);

        Generator bt = new PreferentialAttachmentGenerator();
        Graph g = new MultiGraph("lol");
        bt.addSink(g);
        bt.begin();

        for (int i = 0; i < 20; i++) {
            bt.nextEvents();
        }

        g.setAutoCreate(true);
        g.setStrict(false);
//        g.addEdge("AB", "A", "B");
//        g.addEdge("BC", "B", "C");
//        g.addEdge("CA", "C", "A");
//        g.addEdge("AD", "A", "D");
//        g.addEdge("DE", "D", "E");
//        g.addEdge("DF", "D", "F");
//        g.addEdge("EF", "E", "F");


        g.addAttribute("ui.stylesheet", styleSheet);

        for (Node node : g) {
            node.addAttribute("color", -1);
        }

//        bt.end();


//        g.display();


        g.setAutoCreate(true);
        g.setStrict(false);
        g.display();


        HillClimbingAlgorithmImpl hillClimbing = new HillClimbingAlgorithmImpl();
        hillClimbing.init(g);
        hillClimbing.compute();


    }

    public void explore(Node source) {

        Iterator<? extends Node> k = source.getBreadthFirstIterator();

        while (k.hasNext()) {
            Node next = k.next();
            next.setAttribute("ui.class", "marked");
            sleep();
        }
    }

    protected void sleep() {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
    }

    protected String styleSheet =
            "node {" +
                    "	fill-color: black;" +
                    "}" +
                    "node.marked {" +
                    "	fill-color: red;" +
                    "}";
}


//        graph.setAutoCreate(true);
//        graph.setStrict(false);
//        graph.display();
//
//        graph.addEdge("AB", "A", "B");
//        graph.addEdge("BC", "B", "C");
//        graph.addEdge("CA", "C", "A");
//        graph.addEdge("AD", "A", "D");
//        graph.addEdge("DE", "D", "E");
//        graph.addEdge("DF", "D", "F");
//        graph.addEdge("EF", "E", "F");
//
//        for (Node node : graph) {
//            node.addAttribute("ui.label", node.getId());
//        }
//
//        explore(graph.getNode("A"));