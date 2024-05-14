import java.util.*;

class bond_comparator implements Comparator{
    public int compare(Object o1,Object o2){
        Bond s1=(Bond) o1;
        Bond s2=(Bond) o2;

        if(Objects.equals(s1.getWeight(), s2.getWeight()))
            return 0;
        else if(s1.getWeight()>s2.getWeight())
            return 1;
        else
            return -1;
    }
}

// Class representing the Mission Synthesis
public class MissionSynthesis {

    // Private fields
    private final List<MolecularStructure> humanStructures; // Molecular structures for humans
    private final ArrayList<MolecularStructure> diffStructures; // Anomalies in Vitales structures compared to humans
    public MolecularStructure selected_human =new MolecularStructure();
    public MolecularStructure selected_diff=new MolecularStructure();



    // Constructor
    public MissionSynthesis(List<MolecularStructure> humanStructures, ArrayList<MolecularStructure> diffStructures) {
        this.humanStructures = humanStructures;
        this.diffStructures = diffStructures;
    }

    // Method to synthesize bonds for the serum
    public List<Bond> synthesizeSerum() {
        List<Bond> serum = new ArrayList<>();
        List<Molecule> mins=new ArrayList<>();
        List<Bond> possible_bond=new ArrayList<>();
        List<Bond> sorted_bond=new ArrayList<>();
        List<Double> possible_bond_lengths=new ArrayList<>();


        //add min value of human structures
        for(MolecularStructure structure:humanStructures){
            mins.add(structure.getMoleculeWithWeakestBondStrength());
            selected_human.addMolecule(structure.getMoleculeWithWeakestBondStrength());
        }
        //add min value of diff structures
        for(MolecularStructure structure:diffStructures){
            mins.add(structure.getMoleculeWithWeakestBondStrength());
            selected_diff.addMolecule(structure.getMoleculeWithWeakestBondStrength());
        }
        //finds all possible value
        for(int i =0;i<mins.size();i++){
            for(int j=i+1;j<mins.size();j++){
                int a =mins.get(i).getBondStrength();
                int b =mins.get(j).getBondStrength();
                Double weight=(a+b)/(2.0);
                Bond temp=new Bond(mins.get(j),mins.get(i),weight);
                possible_bond.add(temp);

            }
        }
        Collections.sort(possible_bond,new bond_comparator());

        ArrayList<Molecule> inserum= new ArrayList<>();

        for( Bond bond:possible_bond){

            if( !inserum.contains(bond.getFrom() )&& !(inserum.contains(bond.getTo())) ){
                serum.add(bond);
                inserum.add(bond.getTo());
                inserum.add(bond.getFrom());

            }
            else if(inserum.contains(bond.getTo()) && !inserum.contains(bond.getFrom())){
                serum.add(bond);
                inserum.add(bond.getFrom());
            }
            else if (!inserum.contains(bond.getTo()) && inserum.contains(bond.getFrom())) {
                serum.add(bond);
                inserum.add(bond.getTo());

            }
            if(inserum.size()==mins.size()){
                break;
            }
        }


        /* YOUR CODE HERE */
        return serum;
    }




    // Method to print the synthesized bonds
    public void printSynthesis(List<Bond> serum) {
        System.out.println("Typical human molecules selected for synthesis: "+selected_human.getMolecules());
        System.out.println("Vitales molecules selected for synthesis: "+selected_diff.getMolecules());
        System.out.println("Synthesizing the serum...");
        double total=0;
        for (Bond bond:serum){
            System.out.print("Forming a bond between ");
            String a=bond.getFrom().getId();
            a=a.replace("M","");
            String b=bond.getTo().getId();
            b=b.replace("M","");

            if(Integer.valueOf(a)<Integer.valueOf(b)){
                System.out.print(bond.getFrom()+ " - " +bond.getTo()+" with strength ");
                System.out.printf("%.2f", bond.getWeight());
                System.out.println();
            }
            else{
                System.out.print(bond.getTo()+ " - " +bond.getFrom()+" with strength ");
                System.out.printf("%.2f", bond.getWeight());
                System.out.println();
            }

            total+=bond.getWeight();

        }
        System.out.print("The total serum bond strength is ");
        System.out.printf("%.2f", total);
        System.out.println();










        /* YOUR CODE HERE */ 

    }
}
