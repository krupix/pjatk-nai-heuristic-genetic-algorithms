package pl.krupix.pjatk.nai.algorithm;

import org.apache.log4j.Logger;
import org.graphstream.graph.Node;

import java.util.Random;

/**
 * Created by krupix on 31.01.2016.
 */
public class NodesPair {

    static Logger log = Logger.getLogger(NodesPair.class);

    private Node node1, node2;

    public NodesPair(Node node1, Node node2) {
        this.node1 = node1;
        this.node2 = node2;
    }

    public void crossingOverBits() {

        int doCross = new Random().nextInt(4);

        log.debug("doCross? " + doCross);

// TODO: Only about 20-50% has to be crossing-over
        if (doCross > 0) {

            String node1Bits = "00" + node1.getAttribute("tmp_color");
            String node2Bits = "00" + node2.getAttribute("tmp_color");

            int lengthDiff = node1Bits.length() - node2Bits.length();

            log.debug("Length diff: " + lengthDiff + ", node1: " + node1Bits + ", node2: " + node2Bits );

            if (lengthDiff > 0) {
                node2Bits = generateZerosString(lengthDiff) + "" + node2Bits;
            } else if (lengthDiff < 0) {
                node1Bits = generateZerosString(lengthDiff) + "" + node1Bits;
            }

            int changeBitsPosition = new Random().nextInt(node1Bits.length());
            log.debug("Change bits position: " + changeBitsPosition);

            if (changeBitsPosition != 0) {
                changeBitsBetweenNodes(changeBitsPosition, node1Bits, node2Bits);
            }

            node1.setAttribute("tmp_color", Integer.parseInt(node1Bits,2));
            node2.setAttribute("tmp_color", Integer.parseInt(node2Bits,2));

        }

    }

    public void changeBitsBetweenNodes(int n, String node1bits, String node2bits) {

        String node1part1 = node1bits.substring(0, node1bits.length() / n);
        String node1part2 = node1bits.substring(node1bits.length() / n);

        String node2part1 = node2bits.substring(0, node2bits.length() / n);
        String node2part2 = node2bits.substring(node2bits.length() / n);

        log.debug("Node 1 before: " + node1bits + ", 1st part: " + node1part1 + ", secondpart = " + node1part2);
        log.debug("Node 2 before: " + node2bits + ", 1st part: " + node2part1 + ", secondpart = " + node2part2);

        node1bits = node1part1 + "" + node2part2;
        node2bits = node2part1 + "" + node1part2;
        log.debug("node 1 after: " + node1bits);
        log.debug("node 2 after: " + node2bits);

    }

    public String generateZerosString(int n) {
        String result = "";

        for (int i = 0; i < n; i++ ) {
            result += "0";
        }

        return result;
    }


}