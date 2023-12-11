

import java.util.ArrayList;
import java.util.List;

public class TimeComparison {

    public static List<List<Double>> compareTime(ArrayList<String> initialPop, ArrayList<Integer> popSize, ArrayList<Integer> iteration) {
        List<List<Double>> result = new ArrayList<>();

        // IMCRO
        for (int i = 0; i < popSize.size(); i++) {
            List<Double> timeRun = new ArrayList<>();
            
            // IMCRO
            long startTime1 = System.currentTimeMillis() ; 
            ArrayList<Molecule> molecules = Initialization_IMCRO.initialization(popSize.get(i), initialPop);
            // start the timer
           
            IMCRO myimcro1 = new MYIMCRO(molecules, initialPop);
            myimcro1.run();
            long endTime1 = System.currentTimeMillis();
            double duration1 = (endTime1 - startTime1) / 1000.0; // Convert to seconds
            timeRun.add(duration1);

            // ACB
            long startTime2 = System.currentTimeMillis();
            ArrayList<String> encodedPopulation = Initialization_IMCRO.encodingPopulation(initialPop);
            List<Integer> molecule = Decoder.decodeToIntList(encodedPopulation);

            int n = initialPop.size();
            List<Integer> frequencies = FrequencyCalculator.frequency(molecule);
            // start the timer
        
            ArtificialBeeColony moleculeColony = new ArtificialBeeColony(initialPop, molecule, frequencies, popSize.get(i), n, iteration.get(i));
            List<Integer> bestStructure = moleculeColony.solve();
            long endTime2 = System.currentTimeMillis();
            double duration2 = (endTime2 - startTime2) / 1000.0; // Convert to seconds
            timeRun.add(duration2);

            // ACO
            //start the timer
            long startTime3 = System.currentTimeMillis();
            AntColonyOptimizer aco = new AntColonyOptimizer(initialPop, popSize.get(i), iteration.get(i));
            List<Integer> antCosts = aco.solve();
            long endTime3 = System.currentTimeMillis();
            double duration3 = (endTime3 - startTime3) / 1000.0; // Convert to seconds
            timeRun.add(duration3);

            result.add(timeRun);
        }

        return result;
    }

    // Define other methods as needed (e.g., initialization, MYIMCRO, encodingPopulation, decodeToIntList, frequency, ArtificialBeeColony, ACO)
    // ...

    public static void main(String[] args) {
        ArrayList<String> initialPop = new ArrayList<>(List.of("acg", "cat", "gtt", "tgc","tcc"));
        ArrayList<Integer> popSize = new ArrayList<>(List.of(20, 50, 100, 500,1000));
        ArrayList<Integer> iterations = new ArrayList<>(List.of(10, 100, 200, 500,1000));

        List<List<Double>> result = compareTime(initialPop, popSize, iterations);

        // Print the result if needed
        System.out.println("IMCRO , ABC , ACO");
        for (List<Double> timeRun : result) {
        
            System.out.println(timeRun);
        }

        System.out.println();
        // another test 
        System.out.println("IMCRO , ABC , ACO");
        initialPop = new ArrayList<>(List.of(  "acg", "cat", "gtt", "tgc","tcc"));
        popSize = new ArrayList<>(List.of(100, 500, 100, 500, 100, 500, 100, 500, 1000, 100, 500));

          // Second column converted to Java code
        iterations = new ArrayList<>(List.of(500, 500, 1000, 1000, 100, 100, 500, 500, 500, 100, 100));

        result = compareTime(initialPop, popSize, iterations);

        // Print the result if needed
        System.out.println("IMCRO , ABC , ACO");
        for (List<Double> timeRun : result) {
        
            System.out.println(timeRun);
        }
    }

}
