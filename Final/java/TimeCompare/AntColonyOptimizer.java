import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class AntColonyOptimizer {

    private List<List<Integer>> molecules;
    private List<String> origin;
    private List<Integer> flattenedMolecules;
    private List<Integer> uniqueElements;
    private List<List<Integer>> distanceMatrix;
    private int n;
    private int iterationSize;

    public AntColonyOptimizer(List<String> initialPop, int popsize, int iteration) {
        molecules = new ArrayList<>();
        origin = initialization2.initialization(popsize, initialPop) ;
        
        for (String originalString : origin) {
            List<Integer> digitList = new ArrayList<>();
            for (char digit : originalString.toCharArray()) {
                digitList.add(Character.getNumericValue(digit));
            }
            molecules.add(digitList);
        }

        flattenedMolecules = new ArrayList<>();
        for (List<Integer> sublist : molecules) {
            flattenedMolecules.addAll(sublist);
        }

        Set<Integer> uniqueElementsSet = new HashSet<>(flattenedMolecules);
        uniqueElements = new ArrayList<>(uniqueElementsSet);
        uniqueElements.sort(Integer::compareTo);

        distanceMatrix = new ArrayList<>();
        for (int i = 0; i < molecules.get(0).size()-2; i++) {
            List<Integer> column = new ArrayList<>();
            for (List<Integer> molecule : molecules) {
               
                if (i < molecule.size()) {
                    column.add(molecule.get(i));
                } else {
                    column.add(0);
                }
            }
            distanceMatrix.add(column);
        }

        n = distanceMatrix.size();
        iterationSize = iteration * 120;
    }

    public double calculateDistance(List<Integer> antPath) {
        int currentIndex = antPath.get(0);
        double distance = 0;
        for (int nextIndex : antPath.subList(1, antPath.size())) {
            distance += distanceMatrix.get(currentIndex).get(nextIndex);
            currentIndex = nextIndex;
        }
        return distance;
    }

    public void swap(List<Integer> sequence, int i, int j) {
        int temp = sequence.get(i);
        sequence.set(i, sequence.get(j));
        sequence.set(j, temp);
    }

    public Entry<List<Integer>, Double> localPheromoneUpdate(Entry<List<Integer>, Double> antPath, int a, int b) {
        List<Integer> updatedAntPath = new ArrayList<>(antPath.getKey());
        swap(updatedAntPath, a, b);
        double updatedDistance = calculateDistance(updatedAntPath);
        return new AbstractMap.SimpleEntry<>(updatedAntPath, updatedDistance);
    }

    public Entry<List<Integer>, Double> globalPheromoneUpdate(Entry<List<Integer>, Double> antPath, int a, int b, int c) {
        List<Integer> updatedAntPath = new ArrayList<>(antPath.getKey());
        swap(updatedAntPath, a, b);
        swap(updatedAntPath, b, c);
        double updatedDistance = calculateDistance(updatedAntPath);
        return new AbstractMap.SimpleEntry<>(updatedAntPath, updatedDistance);
    }

    public List<Integer> solve() {
        Random random = new Random();

        while (true) {
            int numAnts = 10;
            int worstAnts = (int) (0.1 * numAnts);
            int bestAnts = (int) (0.9 * numAnts);
            double alpha = 2.0;
            double beta = 2.0;
            int passMax = 15;
            int passMin = 0;
            double transitionProbability = 0.5;
            double passMethod = alpha * 1.0 / n * beta * (passMax - passMin);
            List<Entry<List<Integer>, Double>> ants = new ArrayList<>();

            List<Integer> initialPath = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                initialPath.add(i);
            }

            for (int i = 0; i < numAnts; i++) {
                List<Integer> antPath = new ArrayList<>(initialPath);
                antPath = sample(antPath, antPath.size());
                ants.add(new AbstractMap.SimpleEntry<>(antPath, calculateDistance(antPath)));
            }

            ants.sort(Entry.comparingByValue());

            for (int iterationIndex = 0; iterationIndex < iterationSize; iterationIndex++) {
                Entry<List<Integer>, Double> goAnt = ants.get(random.nextInt(bestAnts));
                int randomAntIndex = random.nextInt((int) passMethod);

                if (random.nextDouble() < transitionProbability) {
                    Entry<List<Integer>, Double> morePowerfulAnt =
                            localPheromoneUpdate(goAnt, random.nextInt(n), random.nextInt(n));

                    if (ants.get(randomAntIndex).getValue() > morePowerfulAnt.getValue()) {
                        ants.set(randomAntIndex, morePowerfulAnt);
                    }
                } else {
                    for (int i = numAnts - worstAnts; i < numAnts; i++) {
                        Entry<List<Integer>, Double> updatedAnt =
                                localPheromoneUpdate(ants.get(i), random.nextInt(n), random.nextInt(n));
                        ants.set(i, updatedAnt);
                    }
                    ants.sort(Entry.comparingByValue());
                }

                Entry<List<Integer>, Double> lowCostAnt = ants.get(0);
                Entry<List<Integer>, Double> effectivelyGlobalAnt = globalPheromoneUpdate(
                        lowCostAnt, random.nextInt(n), random.nextInt(n), random.nextInt(n));

                if (ants.get(0).getValue() > effectivelyGlobalAnt.getValue()) {
                    ants.set(0, effectivelyGlobalAnt);
                }
                ants.sort(Entry.comparingByValue());
            }

            List<Integer> antCosts = new ArrayList<>();
            for (int i = 0; i < ants.get(0).getKey().size() - 1; i++) {
                int currentCity = ants.get(0).getKey().get(i);
                int nextCity = ants.get(0).getKey().get(i + 1);
                int cost = molecules.get(currentCity).get(nextCity);
                antCosts.add(cost);
            }

            Set<Integer> uniqueElementsSet = new HashSet<>(antCosts);

            if (uniqueElementsSet.containsAll(Set.of(0, 1, 2, 3))) {
               
                return antCosts; 
            }
        }
    }
     public static <T> List<T> sample(List<T> inputList, int k) {
        if (k > inputList.size()) {
            throw new IllegalArgumentException("Sample size is larger than the input list size.");
        }

        List<T> sampledList = new ArrayList<>(inputList);
        Collections.shuffle(sampledList);
        return sampledList.subList(0, k);
    }

    public static void main(String[] args) {
        List<String> initialPop = List.of("acg", "cat", "gtt", "tgc");
        int popsize = 100;
        int iteration = 100;
        AntColonyOptimizer acoInstance = new AntColonyOptimizer(initialPop, popsize, iteration);
        List<Integer>result = acoInstance.solve();
      
    }

   
   
}

