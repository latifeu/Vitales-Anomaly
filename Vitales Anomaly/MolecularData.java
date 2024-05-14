import java.util.*;

// Class representing molecular data
public class MolecularData {

    // Private fields
    private final List<Molecule> molecules; // List of molecules

    // Constructor
    public MolecularData(List<Molecule> molecules) {
        this.molecules = molecules;
    }

    // Getter for molecules
    public List<Molecule> getMolecules() {
        return molecules;
    }

    // Method to identify molecular structures
    // Return the list of different molecular structures identified from the input data
    public List<MolecularStructure> identifyMolecularStructures() {
        ArrayList<MolecularStructure> structures = new ArrayList<>();
        for (int i =0;i<molecules.size();i++){
            ArrayList<Molecule > find=find_bonds  (molecules.get(i).getBonds(),molecules);
            boolean check= false;
            back:
            for(MolecularStructure ms: structures){
                if(ms.hasMolecule(molecules.get(i).getId())){//if molecules bond is already in a structure
                    check=true;
                    for(Molecule m:find){
                        if(!ms.hasMolecule(m.getId())){
                            ms.addMolecule(m);
                        }
                    }
                    break back;
                }


            }
            if(!check){
                int counter=0;
                myBreakLabel:
                for(MolecularStructure ms: structures){
                    for(Molecule m:find){
                        if(ms.hasMolecule(m.getId())){
                            ms.addMolecule(molecules.get(i));
                            for(Molecule mol:find){
                                if(!ms.hasMolecule(mol.getId())){
                                    ms.addMolecule(mol);
                                }
                            }
                            counter+=1;
                            break myBreakLabel;
                        }
                    }
                }



                if(counter!=1){
                    MolecularStructure ms=new MolecularStructure();
                    ms.addMolecule(molecules.get(i));
                    for(Molecule mol:find){
                        ms.addMolecule(mol);
                    }
                    structures.add(ms);

                }
            }




        }

        /* YOUR CODE HERE */ 

        return structures;
    }

    public static ArrayList<Molecule> find_bonds(List<String> string_bond,List<Molecule> molecules){
        ArrayList<Molecule> finded_molecules=new ArrayList<>();
        for(int i=0;i<string_bond.size();i++){
            for(Molecule m:molecules){
                if(Objects.equals(m.getId(), string_bond.get(i))){
                    finded_molecules.add(m);
                }
            }
        }
        return finded_molecules;
    }




    // Method to print given molecular structures
    public void printMolecularStructures(List<MolecularStructure> molecularStructures, String species) {
        if (Objects.equals(species, "typical humans")) {
            System.out.println(molecularStructures.size()+" molecular structures have been discovered in typical humans.");

        }else{
            System.out.println(molecularStructures.size()+" molecular structures have been discovered in Vitales individuals.");
        }
        for(int i=0;i<molecularStructures.size();i++){
            System.out.print("Molecules in Molecular Structure "+ (i+1) +": ");
            System.out.println(molecularStructures.get(i));
        }


        /* YOUR CODE HERE */ 

    }

    // Method to identify anomalies given a source and target molecular structure
    // Returns a list of molecular structures unique to the targetStructure only
    public static ArrayList<MolecularStructure> getVitalesAnomaly(List<MolecularStructure> sourceStructures, List<MolecularStructure> targeStructures) {
        ArrayList<MolecularStructure> anomalyList = new ArrayList<>();

        for(MolecularStructure vitales:targeStructures){
            if(!sourceStructures.contains(vitales)) anomalyList.add(vitales);
        }
        
        /* YOUR CODE HERE */ 

        return anomalyList;
    }

    // Method to print Vitales anomalies
    public void printVitalesAnomaly(List<MolecularStructure> molecularStructures) {
        System.out.println("Molecular structures unique to Vitales individuals:");
        for (MolecularStructure m:molecularStructures){
            System.out.println(m);
        }

        /* YOUR CODE HERE */ 

    }
}
