package pl.edu.pjatk.nai.graph;

import org.apache.log4j.Logger;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Piotr Osiadacz, s11562 on 31.01.2016.
 */
public class GraphGenerator {

    static Logger log = Logger.getLogger(GraphGenerator.class);

    private Graph graph;

    public GraphGenerator() {

    }

    public Graph loadFromFile(String filePath) {

        BufferedReader br = null;
        graph = new SingleGraph(filePath);
        graph.setAutoCreate(true);
        graph.setStrict(false);

        try {

            String readedLine;

            br = new BufferedReader(new FileReader(filePath));

            while ((readedLine = br.readLine()) != null) {

                if (readedLine.charAt(0) == 'c') {

                    log.debug(readedLine);

                } else if (readedLine.charAt(0) == 'p') {

                    String[] readedLineArr = readedLine.split(" ");
                    log.debug(readedLine);

                    if (readedLineArr[1].equals("edge")) {
                        log.debug("CREATING BY EDGE");
                        createGraphByEdges(br, graph);
                        break;
                    } else {
                        createGraphByNodes(br, graph);
                        break;
                    }


                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return graph;

    }

    public void createGraphByEdges(BufferedReader br, Graph graph) throws IOException {

        String readedLine;

        while ((readedLine = br.readLine()) != null) {
            if (readedLine.charAt(0) == 'e') {
//                e 1 210
                String[] arr = readedLine.split(" ");
                log.debug("Creating " + arr[1] + "-" + arr[2] +  ", " + arr[1] + " " + arr[2] );
                graph.addEdge(arr[1] + "-" + arr[2] , arr[1], arr[2] );
            }
        }

    }

    public void createGraphByNodes(BufferedReader br, Graph graph) {

    }




}
