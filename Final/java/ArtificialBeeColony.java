import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class MoleculeBee {
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

    public int calculateFitness() {
        int fitness = 0;
        for (Integer atom : structure) {
            fitness += frequencies.get(molecule.indexOf(atom));
        }
        return fitness;
    }

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
        this.maxCycles = maxCycles;
        this.mo =  Initialization.initialization(populationSize, initialPop);
        this.n = n;
        this.molecule  = molecule ; 
        this.molecules = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            MoleculeBee mol = new MoleculeBee( mo.get(i).getSupersequence(), frequencies) ;
            molecules.add(mol);
        }
    }

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

    private int calculateFitness(List<Integer> structure) {
        int fitness = 0;
        for (Integer atom : structure) {
            fitness += frequencies.get(molecule.indexOf(atom));
        }
        return fitness;
    }

    private double averageFitness() {
        double totalFitness = 0;
        for (MoleculeBee molecule : molecules) {
            totalFitness += molecule.calculateFitness();
        }
        return totalFitness / n;
    }

    // Assume initialization method, similar to the Python code
    // private List<Integer> initialization(int populationSize, List<Integer> initialPop) {
    //     // Implementation of the initialization method
    //     // ...

    //     return new ArrayList<>(); // Placeholder, replace with actual implementation
    // }

    private boolean checkAllElements(List<Integer> myMolecule) {
        List<Integer> elementsToCheck = List.of(0, 1, 2, 3);
        return myMolecule.containsAll(elementsToCheck);
    }
}
