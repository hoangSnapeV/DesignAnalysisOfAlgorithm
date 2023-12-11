

import java.util.ArrayList;
import java.util.Random;

public class MYIMCRO extends IMCRO {

    public MYIMCRO(ArrayList<Molecule> pop, ArrayList<String> checkSequences) {
        super(pop, checkSequences);
        //TODO Auto-generated constructor stub
    }

    @Override
    protected ArrayList<Molecule> dec(Molecule molecule) {
        Molecule newMolecule1 = new Molecule(molecule);
        Molecule newMolecule2 = new Molecule(molecule);
        ArrayList<ArrayList<Integer>> newSupersequences = this.ops.decomposition(molecule.getSupersequence());
        newMolecule1.setSupersequence(newSupersequences.get(0));
        newMolecule2.setSupersequence(newSupersequences.get(1));

        ArrayList<Molecule> result = new ArrayList<>();
        result.add(newMolecule1);
        result.add(newMolecule2);
        return result;
    }
    /*
    Fit_func function for deciding PE base on the struct attribute
     */
    @Override
    protected double fit_func(Molecule molecule) {
        // TODO Auto-generated method stub
        return Math.sin(molecule.getStructure()[0]) + Math.cos(molecule.getStructure()[1]);
    }

    @Override
    protected ArrayList<Molecule> inter(Molecule molecule1, Molecule molecule2) {
        Molecule newMolecule1 = new Molecule(molecule1);
        Molecule newMolecule2 = new Molecule(molecule2);

        ArrayList<ArrayList<Integer>> newSupersequences = this.ops.intermolecular(molecule1.getSupersequence(),molecule2.getSupersequence());
        newMolecule1.setSupersequence(newSupersequences.get(0));
        newMolecule2.setSupersequence(newSupersequences.get(1));

        // Copy structure elements
        double[] newStructure1 = newMolecule1.getStructure().clone();
        double[] newStructure2 = newMolecule2.getStructure().clone();
        newStructure1[0] = newMolecule2.getStructure()[0];
        newStructure1[1] = newMolecule1.getStructure()[1];
        newStructure2[0] = newMolecule1.getStructure()[0];
        newStructure2[1] = newMolecule2.getStructure()[1];

        newMolecule1.setStructure(newStructure1);
        newMolecule2.setStructure(newStructure2);

        ArrayList<Molecule> result = new ArrayList<>();
        result.add(newMolecule1);
        result.add(newMolecule2);
        return result;
    }

    @Override
    protected Molecule syn(Molecule molecule1, Molecule molecule2) {
         // Assuming Molecule class has a copy constructor
    Molecule newMolecule = new Molecule(molecule1);

    // Assuming ops has the Synthesis method
    ArrayList<Integer> newSupersequence = ops.synthesis(molecule1.getSupersequence(), molecule2.getSupersequence());
    newMolecule.setSupersequence(newSupersequence);

    Random rand = new Random();
    if (rand.nextDouble() < 0.5) {
        newMolecule.getStructure()[0] = molecule2.getStructure()[0];
    } else {
        newMolecule.getStructure()[1] = molecule2.getStructure()[1];
    }

    return newMolecule;
    }

    @Override
    protected Molecule wall(Molecule molecule) {
        Molecule newMolecule = new Molecule(molecule);
        // Swap structure elements
        double[] structure = newMolecule.getStructure().clone();
        double temp = structure[0];
        structure[0] = structure[1];
        structure[1] = temp;
        newMolecule.setStructure(structure);

        // Assuming ops has the OnWall method
        ArrayList<Integer> newSupersequence = ops.onWall(molecule.getSupersequence());
        newMolecule.setSupersequence(newSupersequence);

        return newMolecule;
    }
    
}
