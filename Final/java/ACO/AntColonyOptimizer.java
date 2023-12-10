import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;
/* 
The Ant Colony Optimization (ACO) algorithm simulates the foraging behavior of ants to find an optimal path.
In this algorithm, a predefined number of ants explore possible paths, leaving behind a pheromone trail.
The intensity of the pheromone trail is influenced by the number of ants that traverse a particular path.
Paths with a higher concentration of pheromones become more attractive to subsequent ants, increasing the likelihood of those paths being chosen as optimal routes.
For instance, if multiple ants traverse a path with different distances, the algorithm calculates the total
pheromone level on that path, reflecting the cumulative choices of all ants that have moved along it. This collective information guides the algorithm in identifying promising paths for the final solution.
Approach to Solve SCS Problem using ACO Algorithm:

Step 1: Encode Subsequences
- Encode each subsequence as a number (0 to 3), representing different elements.

Step 2: Create 2D List
- Form a 2D list where each row corresponds to the string value of an encoded subsequence.

Step 3: Apply ACO Algorithm
- Utilize the Ant Colony Optimization algorithm on the 2D list, treating each row as a node.
- Find the most optimal path through these nodes, representing a solution to the SCS problem.

Step 4: Translate Optimal Path
- Translate the obtained optimal path back into the original sequence of 0 to 3.

Step 5: Output Optimal SCS
- Construct the most optimal Shortest Common Supersequence (SCS) using the translated path.
*/
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
        origin = initialization2.initialization(popsize, initialPop);

        // Convert the input strings to a list of lists of integers (molecules).
        for (String originalString : origin) {
            List<Integer> digitList = new ArrayList<>();
            for (char digit : originalString.toCharArray()) {
                digitList.add(Character.getNumericValue(digit));
            }
            molecules.add(digitList);
        }

        // Flatten the list of molecules.
        flattenedMolecules = new ArrayList<>();
        for (List<Integer> sublist : molecules) {
            flattenedMolecules.addAll(sublist);
        }

        // Create a set of unique elements from the flattened list.
        Set<Integer> uniqueElementsSet = new HashSet<>(flattenedMolecules);
        uniqueElements = new ArrayList<>(uniqueElementsSet);
        uniqueElements.sort(Integer::compareTo);

        // Create a distance matrix based on the molecules.
        distanceMatrix = new ArrayList<>();
        for (int i = 0; i < molecules.get(0).size() - 2; i++) {
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

    // Function to calculate the distance of an ant's path.
    public double calculateDistance(List<Integer> antPath) {
        int currentIndex = antPath.get(0);
        double distance = 0;
        for (int nextIndex : antPath.subList(1, antPath.size())) {
            distance += distanceMatrix.get(currentIndex).get(nextIndex);
            currentIndex = nextIndex;
        }
        return distance;
    }

    // Function to swap elements in a sequence.
    public void swap(List<Integer> sequence, int i, int j) {
        int temp = sequence.get(i);
        sequence.set(i, sequence.get(j));
        sequence.set(j, temp);
    }

    // Function to update a single path with local pheromone update.
    public Entry<List<Integer>, Double> localPheromoneUpdate(Entry<List<Integer>, Double> antPath, int a, int b) {
        List<Integer> updatedAntPath = new ArrayList<>(antPath.getKey());
        swap(updatedAntPath, a, b);
        double updatedDistance = calculateDistance(updatedAntPath);
        return new AbstractMap.SimpleEntry<>(updatedAntPath, updatedDistance);
    }

    // Function to update a single path with global pheromone update.
    public Entry<List<Integer>, Double> globalPheromoneUpdate(Entry<List<Integer>, Double> antPath, int a, int b,
            int c) {
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

            // Initialize the initial path.
            List<Integer> initialPath = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                initialPath.add(i);
            }

            // Generate initial paths for ants.
            for (int i = 0; i < numAnts; i++) {
                List<Integer> antPath = new ArrayList<>(initialPath);
                antPath = sample(antPath, antPath.size());
                ants.add(new AbstractMap.SimpleEntry<>(antPath, calculateDistance(antPath)));
            }

            // Sort ants based on the distance they will travel.
            ants.sort(Entry.comparingByValue());



            for (int iterationIndex = 0; iterationIndex < iterationSize; iterationIndex++) {
                Entry<List<Integer>, Double> goAnt = ants.get(random.nextInt(bestAnts));
                int randomAntIndex = random.nextInt((int) passMethod);
            // Transition method based on the probability.
            if (random.nextDouble() < transitionProbability) {
                Entry<List<Integer>, Double> morePowerfulAnt = localPheromoneUpdate(goAnt, random.nextInt(n),
                        random.nextInt(n));

                // Update ant if the new path is better.
                if (ants.get(randomAntIndex).getValue() > morePowerfulAnt.getValue()) {
                    ants.set(randomAntIndex, morePowerfulAnt);
                }
            } else {
                // Local pheromone update for the worst ants.
                for (int i = numAnts - worstAnts; i < numAnts; i++) {
                    Entry<List<Integer>, Double> updatedAnt = localPheromoneUpdate(ants.get(i), random.nextInt(n),
                            random.nextInt(n));
                    ants.set(i, updatedAnt);
                }
                ants.sort(Entry.comparingByValue());
            }

            // Global pheromone update for the best ant.
            Entry<List<Integer>, Double> lowCostAnt = ants.get(0);
            Entry<List<Integer>, Double> effectivelyGlobalAnt = globalPheromoneUpdate(
                    lowCostAnt, random.nextInt(n), random.nextInt(n), random.nextInt(n));

            // Update ant if the global path is better.
            if (ants.get(0).getValue() > effectivelyGlobalAnt.getValue()) {
                ants.set(0, effectivelyGlobalAnt);
            }
            ants.sort(Entry.comparingByValue());
        }

        // Calculate costs for the best ant's path.
        List<Integer> antCosts = new ArrayList<>();
        for (int i = 0; i < ants.get(0).getKey().size() - 1; i++) {
            int currentCity = ants.get(0).getKey().get(i);
            int nextCity = ants.get(0).getKey().get(i + 1);
            int cost = molecules.get(currentCity).get(nextCity);
            antCosts.add(cost);
        }

        // Check if the ant has taken a path with all encoded variable words.
        Set<Integer> uniqueElementsSet = new HashSet<>(antCosts);

        if (uniqueElementsSet.containsAll(Set.of(0, 1, 2, 3))) {
            return antCosts;
        }
    }
}

// Function to randomly sample k elements from a list.
public static <T> List<T> sample(List<T> inputList, int k) {
    if (k > inputList.size()) {
        throw new IllegalArgumentException("Sample size is larger than the input list size.");
    }

    List<T> sampledList = new ArrayList<>(inputList);
    Collections.shuffle(sampledList);
    return sampledList.subList(0, k);
}

public static void main(String[] args) {
    // Example usage of the AntColonyOptimizer class.
    List<String> initialPop = List.of("acg", "cat", "gtt", "tgc");
    int popsize = 10000;
    int iteration = 10000;
    AntColonyOptimizer acoInstance = new AntColonyOptimizer(initialPop, popsize, iteration);
    List<Integer> result = acoInstance.solve();
    System.out.println(result);
}
}