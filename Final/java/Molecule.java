import java.util.ArrayList;
import java.util.Arrays;

public class Molecule {
    /*
    PE : Potential Energy (This is the number we focus on during the algorithm)
    ke: Kinetic energy
    numHit: Number of collisions by a molecule
    optimal : This attribute will hold the Molecule Instance with the lowest PE
    structure : Molecule Structe store as 1d array with 2 elements generated randomly (For example: [0.3860863116352774 0.4017794415965995])
     */
    private double pe;
    private double ke;
    private double opt;
    private int num_of_hits;
    private double[] structure;
    private ArrayList<Integer> supersequence;

    // Constructor
    public Molecule(double[] structure, ArrayList<Integer> supersequence) {
        this.pe = 0;
        this.ke = 0;
        this.opt = 9999999;
        this.num_of_hits = 0;
        this.structure = Arrays.copyOf(structure, structure.length);
        this.supersequence = new ArrayList<>(supersequence);
    }
    public Molecule(Molecule another) {
        this.pe = another.pe;
        this.ke = another.ke;
        this.opt = another.opt;
        this.num_of_hits = another.num_of_hits;
        this.structure = Arrays.copyOf(another.structure, another.structure.length);
        this.supersequence = new ArrayList<>(another.getSupersequence());
    }

    // Update method
    /*
    This is called whenever a Operator is performed
    If this molecule has a lower energy, reset num_of_hits.
     */
    public void update() {
        if (this.pe < this.opt) {
            this.opt = this.pe;
            this.num_of_hits = 0;
        }
    }

    // toString method
    @Override
    public String toString() {
        StringBuilder reVal = new StringBuilder("Structure[ ");
        for (double v : this.structure) {
            reVal.append(v).append(" ");
        }
        reVal.append("]  Supersequence[ ");
        for (int j : this.supersequence) {
            reVal.append(j).append(" ");
        }
        reVal.append("]");
        return reVal.toString();
    }

    // Getter for supersequence
    public ArrayList<Integer> getSupersequence() {
        return this.supersequence;
    }

    // Additional Getters and Setters as needed

    public double getPe() {
        return pe;
    }

    public void setPe(double pe) {
        this.pe = pe;
    }

    public double getKe() {
        return ke;
    }

    public void setKe(double ke) {
        this.ke = ke;
    }

    public double getOpt() {
        return opt;
    }

    public void setOpt(double opt) {
        this.opt = opt;
    }

    public int getNumOfHits() {
        return num_of_hits;
    }

    public void setNumOfHits(int num_of_hits) {
        this.num_of_hits = num_of_hits;
    }

    public double[] getStructure() {
        return structure;
    }

    public void setStructure(double[] structure) {
        this.structure = Arrays.copyOf(structure, structure.length);
    }

    public void setSupersequence(ArrayList<Integer> supersequence) {
        this.supersequence = new ArrayList<>(supersequence);
    }
}
