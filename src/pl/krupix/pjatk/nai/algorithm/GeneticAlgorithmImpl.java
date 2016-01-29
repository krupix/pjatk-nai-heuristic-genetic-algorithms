package pl.krupix.pjatk.nai.algorithm;

import org.graphstream.algorithm.Algorithm;
import org.graphstream.graph.Graph;

/**
 * Created by krupix on 29.01.2016.
 */
public class GeneticAlgorithmImpl implements Algorithm {

    Graph graph;


    @Override
    public void init(Graph graph) {
        this.graph = graph;
    }

    @Override
    public void compute() {

    }
}
