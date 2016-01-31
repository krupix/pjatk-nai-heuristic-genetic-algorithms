package pl.krupix.pjatk.nai.main;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import pl.krupix.pjatk.nai.controller.AlgorithmExecutor;
import pl.krupix.pjatk.nai.controller.ReportGenerator;

/**
 * Created by krupix on 29.01.2016.
 */
public class Main {

    static Logger log = Logger.getLogger(Main.class);

    public static void main(String args[]) {

        String log4jConfPath = "log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);



        AlgorithmExecutor algorithmExecutor = new AlgorithmExecutor();
        algorithmExecutor.executeHillClimbingAlgorithm();

        ReportGenerator hc = algorithmExecutor.getRg();



        algorithmExecutor = new AlgorithmExecutor();
        algorithmExecutor.executeGeneticAlgorithm();


        ReportGenerator rg = algorithmExecutor.getRg();

        log.info("HC REPORT");
        hc.printReporsList();
        log.info("GA REPORT");
        rg.printReporsList();

        log.warn("FINISHED");


    }
}
