// Class representing the mission of Genesis

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MissionGenesis {

    // Private fields
    private MolecularData molecularDataHuman; // Molecular data for humans
    private MolecularData molecularDataVitales; // Molecular data for Vitales

    // Getter for human molecular data
    public MolecularData getMolecularDataHuman() {
        return molecularDataHuman;
    }

    // Getter for Vitales molecular data
    public MolecularData getMolecularDataVitales() {
        return molecularDataVitales;
    }

    // Method to read XML data from the specified filename
    // This method should populate molecularDataHuman and molecularDataVitales fields once called
    public void readXML(String filename) {
        try
        {
            List<Molecule> human_molecule_list= new ArrayList<>();
            List<Molecule> vitales_molecule_list= new ArrayList<>();


            File xml_file = new File(filename);
            DocumentBuilderFactory d_factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder d_builder = d_factory.newDocumentBuilder();
            Document doc = d_builder.parse(xml_file);
            doc.getDocumentElement().normalize();
            NodeList human_moleculeList = doc.getElementsByTagName("HumanMolecularData");

            find_list(human_molecule_list, human_moleculeList);

            NodeList vitales_moleculeList = doc.getElementsByTagName("VitalesMolecularData");

            find_list(vitales_molecule_list, vitales_moleculeList);

            MolecularData human= new MolecularData(human_molecule_list);
            MolecularData vitale=new MolecularData(vitales_molecule_list);

            molecularDataHuman=human;
            molecularDataVitales=vitale;


        }





        catch (Exception e){
        e.printStackTrace();
        }






        /* YOUR CODE HERE */
        
    }

    private void find_list(List<Molecule> vitales_molecule_list, NodeList vitales_moleculeList) {
        for(int k=0;k<vitales_moleculeList.getLength();k++){
            NodeList molecule_list =  ((Element) vitales_moleculeList.item(k)).getElementsByTagName("Molecule");
            for (int i = 0; i < molecule_list.getLength(); i++) {
                Node element = molecule_list.item(i);
                if (element.getNodeType() == Node.ELEMENT_NODE) {

                    Element molecule = (Element) element ;

                    String id=  molecule.getElementsByTagName("ID").item(0).getTextContent();

                    String strength=molecule.getElementsByTagName("BondStrength").item(0).getTextContent();

                    NodeList bonds=molecule.getElementsByTagName("Bonds");
                    String bond1=bonds.item(0).getTextContent();
                    bond1=bond1.trim();
                    String[] bond_array = bond1.split("\\s+");
                    List<String> bond_list= new ArrayList<>();
                    bond_list.addAll(Arrays.asList(bond_array));

                    Molecule new_molecule= new Molecule(id,Integer.parseInt(strength),bond_list);
                    vitales_molecule_list.add(new_molecule);

                }

            }
        }
    }
}
