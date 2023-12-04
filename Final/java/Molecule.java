package Final.java;
import java.util.Arrays;

public class Molecule {
    private double pe;
    private double ke;
    private double opt;
    private int num_of_hits;
    private double[] structure;
    private int[] supersequence;

    // Constructor
    public Molecule(double[] structure, int[] supersequence) {
        this.pe = 0;
        this.ke = 0;
        this.opt = 9999999;
        this.num_of_hits = 0;
        this.structure = Arrays.copyOf(structure, structure.length);
        this.supersequence = Arrays.copyOf(supersequence, supersequence.length);
    }

    // Update method
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
    public int[] getSuper() {
        return supersequence;
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

    public void setSupersequence(int[] supersequence) {
        this.supersequence = Arrays.copyOf(supersequence, supersequence.length);
    }
}
