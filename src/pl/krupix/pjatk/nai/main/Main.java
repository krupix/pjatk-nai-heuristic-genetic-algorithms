package pl.krupix.pjatk.nai.main;

import org.apache.log4j.PropertyConfigurator;
import pl.krupix.pjatk.nai.controller.AlgorithmExecutor;

/**
 * Created by krupix on 29.01.2016.
 */
public class Main {

    public static void main(String args[]) {

        String log4jConfPath = "log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);

        AlgorithmExecutor algorithmExecutor = new AlgorithmExecutor();

    }
}
