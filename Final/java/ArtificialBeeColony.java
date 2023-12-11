import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class MoleculeBee {
    /*
    OBject: Represents a molecule with a structure composed of atoms and associated frequencies.
        
    Attributes:
        molecule (list): List of atoms forming the molecule.
        frequencies (list): List of frequencies corresponding to each atom in the molecule.
        structure (list): Initial structure of the molecule, randomly chosen from the given atoms.
     */
    private List<Integer> molecule;
    private List<Integer> frequencies;
    private List<Integer> structure;

    public MoleculeBee(List<Integer> molecule, List<Integer> frequencies) {
        this.molecule = molecule;
        this.frequencies = frequencies;
        this.structure = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < molecule.size(); i++) {
            structure.add(molecule.get(random.nextInt(molecule.size())));
        }
    }

    /*
    OBject: Calculates the fitness of the current molecule structure based on frequencies.

    Output:
        The fitness value: int
    */    
    public int calculateFitness() {
        int fitness = 0;
        for (Integer atom : structure) {
            fitness += frequencies.get(molecule.indexOf(atom));
        }
        return fitness;
    }

    /*
    OBject: Generates a new random structure for the molecule based on frequencies.

    Output:
        A new structure (list) for the molecule composed of randomly chosen frequencies.
     */

    public List<Integer> generateNewStructure() {
        List<Integer> newStructure = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < frequencies.size(); i++) {
            newStructure.add(frequencies.get(random.nextInt(frequencies.size())));
        }
        return newStructure;
    }
     public List<Integer> getMolecule() {
        return molecule;
    }

    // Setter for molecule
    public void setMolecule(List<Integer> molecule) {
        this.molecule = molecule;
    }

    // Getter for frequencies
    public List<Integer> getFrequencies() {
        return frequencies;
    }

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

/*
OBject: Represents an Artificial Bee Colony optimization algorithm for molecule structure.
        
Attributes:
    initial_pop: Initial population for the optimization.
    molecule (list): List of atoms forming the molecule. (Note: molecule is not defined in this scope.)
    frequencies (list): List of frequencies corresponding to each atom in the molecule.
    population_size (int): Size of the population in the optimization.
    max_cycles (int): Maximum number of cycles or iterations.
    mo: Initialization (Assumed to be a function or data structure) result for the population.
    n (int): Number of cycles or iterations.
    molecules (list): List of Molecule_Bee instances representing the population of molecules.
 */

public class ArtificialBeeColony {
    private List<MoleculeBee> molecules;
    private List<Integer> molecule ; 
    private List<Integer> frequencies;
    private int populationSize;
    private int maxCycles;
    private List<Molecule> mo;
    private int n;

    public ArtificialBeeColony(List<String> initialPop,
                               List<Integer> frequencies , List<Integer> molecule, int populationSize, int maxCycles, int n) {
        this.frequencies = frequencies;
        this.populationSize = populationSize;
        this.maxCycles = maxCycles * 10;
        this.mo =  Initialization_IMCRO.initialization(populationSize, initialPop);
        this.n = n;
        this.molecule  = molecule ; 
        this.molecules = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            MoleculeBee mol = new MoleculeBee( mo.get(i).getSupersequence(), frequencies) ;
            molecules.add(mol);
        }
    }

    /*
    OBject: Solves the optimization problem using the Artificial Bee Colony algorithm.

    Output:
        The best structure(list<int>) found by the algorithm.
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
       

        return bestStructure;
    }

    /*
    OBject: Calculates the fitness of a given structure.

    Input:
        structure (list): The structure for which fitness needs to be calculated.

    Output:
        The fitness value of the given structure.
     */

    private int calculateFitness(List<Integer> structure) {
        int fitness = 0;
        for (Integer atom : structure) {
            fitness += frequencies.get(molecule.indexOf(atom));
        }
        return fitness;
    }

    /*
    OBject: Calculates the average fitness of all molecules in the population.

    Output:
        The average fitness(float) value of the population.
     */

    private double averageFitness() {
        double totalFitness = 0;
        for (MoleculeBee molecule : molecules) {
            totalFitness += molecule.calculateFitness();
        }
        return totalFitness / n;
    }

    /*
    OBject: Checks if all specified elements are present in the given list.

    Input:
        my_list (list): The list to be checked.

    Output:
        True if all elements are present, False otherwise.
     */
    private boolean checkAllElements(List<Integer> myMolecule) {
        List<Integer> elementsToCheck = List.of(0, 1, 2, 3);
        return myMolecule.containsAll(elementsToCheck);
    }
}
