package pl.krupix.pjatk.nai.test;

import org.graphstream.algorithm.generator.BananaTreeGenerator;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import java.util.Iterator;

/**
 * Created by krupix on 25.01.2016.
 */
public class GraphExplore {

    public static void main(String args[]) {
        new GraphExplore();
    }

    public GraphExplore() {
        Graph graph = new SingleGraph("tutorial 1");

        graph.addAttribute("ui.stylesheet", styleSheet);

        BananaTreeGenerator bt = new BananaTreeGenerator();
        Graph g = new SingleGraph("lol");
        bt.addSink(g);
        bt.begin();
        for (int i = 0; i < 20; i++) {
            System.out.println("i:" + i);
            bt.nextEvents();
        }

        for (Node node : g) {
            node.addAttribute("color", "0");
        }

        bt.end();


        g.display();

        for (Node node : g) {
            node.addAttribute("ui.label", node.getAttribute("color"));
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

        System.out.println(g.getNodeCount());
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
