import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class MoleculeBee {
    private List<Integer> molecule;
    private List<Integer> frequencies;
    private List<Integer> structure;

    /**
 * MoleculeBee Class:
 *
 * Input: Lists of molecule and frequencies
 * Output: MoleculeBee object
 * What it does: Initializes a MoleculeBee object with a random structure based on given molecule and frequencies.
 *
 * @param molecule     List of molecule symbols.
 * @param frequencies  List of frequencies corresponding to each molecule symbol.
 * @return MoleculeBee object.
 */
    // Constructor
    public MoleculeBee(List<Integer> molecule, List<Integer> frequencies) {
        this.molecule = molecule;
        this.frequencies = frequencies;
        this.structure = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < molecule.size(); i++) {
            structure.add(molecule.get(random.nextInt(molecule.size())));
        }
    }

/**
 * calculateFitness Method:
 *
 * Input: None
 * Output: Fitness value of the current structure
 * What it does: Calculates the fitness value of the current structure based on frequencies.
 *
 * @return Fitness value of the current structure.
 */
    public int calculateFitness() {
        int fitness = 0;
        for (Integer atom : structure) {
            fitness += frequencies.get(molecule.indexOf(atom));
        }
        return fitness;
    }

/**
 * generateNewStructure Method:
 *
 * Input: None
 * Output: List of integers representing a new random structure
 * What it does: Generates a new structure randomly based on frequencies.
 *
 * @return List of integers representing a new random structure.
 */
    public List<Integer> generateNewStructure() {
        List<Integer> newStructure = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < frequencies.size(); i++) {
            newStructure.add(frequencies.get(random.nextInt(frequencies.size())));
        }
        return newStructure;
    }

/**
 * Getter for molecule:
 *
 * Input: None
 * Output: List of molecule symbols
 * What it does: Retrieves the list of molecule symbols.
 *
 * @return List of molecule symbols.
 */
    // Getter for molecule
    public List<Integer> getMolecule() {
        return molecule;
    }
/**
 * Setter for molecule:
 *
 * Input: List of molecule symbols
 * Output: None
 * What it does: Sets the list of molecule symbols.
 *
 * @param molecule List of molecule symbols.
 */
    // Setter for molecule
    public void setMolecule(List<Integer> molecule) {
        this.molecule = molecule;
    }
/**
 * Getter for frequencies:
 *
 * Input: None
 * Output: List of frequencies
 * What it does: Retrieves the list of frequencies.
 *
 * @return List of frequencies.
 */
    // Getter for frequencies
    public List<Integer> getFrequencies() {
        return frequencies;
    }
/**
 * Setter for frequencies:
 *
 * Input: List of frequencies
 * Output: None
 * What it does: Sets the list of frequencies.
 *
 * @param frequencies List of frequencies.
 */
    // Setter for frequencies
    public void setFrequencies(List<Integer> frequencies) {
        this.frequencies = frequencies;
    }

    // Getter for structure
    public List<Integer> getStructure() {
        return structure;
    }

    // Setter for structure
    public void setStructure(List<Integer> structure) {
        this.structure = structure;
    }
}
/**
 * ArtificialBeeColony Class:
 *
 * Input: Initial population, frequencies, molecule, population size, max cycles, and n
 * Output: List of integers representing the best structure
 * What it does: Solves an optimization problem using the Artificial Bee Colony algorithm.
 *
 * @param initialPop      Initial population of strings.
 * @param frequencies     List of frequencies.
 * @param molecule        List of molecule symbols.
 * @param populationSize  Size of the population.
 * @param maxCycles       Maximum number of cycles.
 * @param n               Total number of elements in the molecule.
 * @return List of integers representing the best structure.
 */
public class ArtificialBeeColony {
    private List<MoleculeBee> molecules;
    private List<Integer> molecule;
    private List<Integer> frequencies;
    private int populationSize;
    private int maxCycles;
    private List<Molecule> mo;
    private int n;

    // Constructor
    public ArtificialBeeColony(List<String> initialPop, List<Integer> frequencies, List<Integer> molecule,
                                int populationSize, int maxCycles, int n) {
        this.frequencies = frequencies;
        this.populationSize = populationSize;
        this.maxCycles = maxCycles;
        this.mo = Initialization.initialization(populationSize, initialPop);
        this.n = n;
        this.molecule = molecule;
        this.molecules = new ArrayList<>();

        // Initialize MoleculeBee objects
        for (int i = 0; i < populationSize; i++) {
            MoleculeBee mol = new MoleculeBee(mo.get(i).getSupersequence(), frequencies);
            molecules.add(mol);
        }
    }

/**
 * solve Method:
 *
 * Input: None
 * Output: List of integers representing the best structure
 * What it does: Solves the optimization problem using the Artificial Bee Colony algorithm.
 *
 * @return List of integers representing the best structure.
 */
    public List<Integer> solve() {
        long startTime = System.currentTimeMillis(); // Start

        List<Integer> bestStructure = new ArrayList<>(molecules.get(0).getStructure());
        int bestFitness = calculateFitness(bestStructure);

        for (int cycle = 0; cycle < maxCycles; cycle++) {
            // Evaluate fitness and update the best structure
            for (MoleculeBee molecule : molecules) {
                int fitness = molecule.calculateFitness();
                // Update best structure if fitness is higher and all elements are present in the molecule
                if (fitness > bestFitness && checkAllElements(molecule.getMolecule())) {
                    bestStructure = new ArrayList<>(molecule.getStructure());
                    bestFitness = fitness;
                }
            }

            double averageFitness = averageFitness();
            // Employ artificial bee algorithm to generate new structures
            for (MoleculeBee molecule : molecules) {
                if (molecule.calculateFitness() < averageFitness) {
                    List<Integer> newStructure = molecule.generateNewStructure();
                    int newFitness = calculateFitness(newStructure);
                    // Accept the new structure if it improves fitness
                    if (newFitness > molecule.calculateFitness()) {
                        molecule.setStructure(newStructure);
                    }
                }
            }
        }

        long endTime = System.currentTimeMillis(); // End
        double elapsedTime = (endTime - startTime) / 1000.0;
        System.out.println("Elapsed Time: " + elapsedTime + " seconds");

        return bestStructure;
    }

   /**
 * calculateFitness Method:
 *
 * Input: List of integers representing a structure
 * Output: Fitness value of the structure
 * What it does: Calculates the fitness value of a structure based on frequencies.
 *
 * @param structure List of integers representing a structure.
 * @return Fitness value of the structure.
 */
    private int calculateFitness(List<Integer> structure) {
        int fitness = 0;
        for (Integer atom : structure) {
            fitness += frequencies.get(molecule.indexOf(atom));
        }
        return fitness;
    }
/**
 * averageFitness Method:
 *
 * Input: None
 * Output: Average fitness value of the population
 * What it does: Calculates the average fitness value of the population.
 *
 * @return Average fitness value of the population.
 */
    private double averageFitness() {
        double totalFitness = 0;
        for (MoleculeBee molecule : molecules) {
            totalFitness += molecule.calculateFitness();
        }
        return totalFitness / n;
    }


/**
 * checkAllElements Method:
 *
 * Input: List of integers representing a molecule
 * Output: Boolean indicating if all required elements are present
 * What it does: Checks if all required elements are present in a molecule.
 *
 * @param myMolecule List of integers representing a molecule.
 * @return Boolean indicating if all required elements are present.
 */
    private boolean checkAllElements(List<Integer> myMolecule) {
        List<Integer> elementsToCheck = List.of(0, 1, 2, 3);
        return myMolecule.containsAll(elementsToCheck);
    }
}
