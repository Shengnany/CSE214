package hw5;
import java.util.Scanner;

/**
 * This class hosts your main method
 *
 * @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #5
 *  CSE 214, Rec 04
 */
public class FoodPyramid {
    /**
     *   Default constructor.
     */
    public FoodPyramid(){
    }
    private OrganismTree tree;

    /**
     * This method retrieves the pyramid's tree
     * @return
     * the pyramid's tree
     */
    public OrganismTree getTree(){
        return tree;
    }

    /**
     * This method sets the FoodPyramid's tree
     * @param tree
     * the FoodPyramid's tree
     */
    public void setTree(OrganismTree tree){
        this.tree = tree;
    }


    public static void main(String[] args) {
        FoodPyramid pyramid = new FoodPyramid();
        OrganismTree tree1;
        OrganismTree myTree;
        Scanner x = new Scanner(System.in);
        try {
            OrganismNode first;
            System.out.print("What is the name of the apex predator?: ");
            String name = x.nextLine();
            System.out.print("Is the organism an herbivore / a carnivore / an omnivore? (H / C / O): ");
            String type = x.nextLine();
            type = type.toUpperCase();
            if (type.equals("H"))
                first = new OrganismNode(name, false, true, false);
            else if (type.equals("C"))
                first = new OrganismNode(name, false, false, true);
            else
                first = new OrganismNode(name, false, false, false);
            System.out.println("Constructing food pyramid. . .");
            tree1 = new OrganismTree(first);
            pyramid.setTree(tree1);
        } catch (IllegalStateException ex) {
            System.out.println("Cannot find the child");
        }
        myTree = pyramid.getTree();
        String choice ="";
        do {
            try {
                System.out.println("\nMenu: ");
                System.out.println("(PC) - Create New Plant Child\n" +
                        "(AC) - Create New Animal Child\n" +
                        "(RC) - Remove Child\n" +
                        "(P) - Print Out Cursor’s Prey\n" +
                        "(C) - Print Out Food Chain\n" +
                        "(F) - Print Out Food Pyramid at Cursor\n" +
                        "(LP) - List All Plants Supporting Cursor\n" +
                        "(R) - Reset Cursor to Root\n" +
                        "(M) - Move Cursor to Child\n" +
                        "(Q) - Quit\n");
                System.out.print("Please select an option: ");
                choice = x.nextLine();
                choice = choice.toUpperCase();
                String d;
                if (choice.equals("PC")) {
                    if(myTree.getCursor().getLeft()!=null && myTree.getCursor().getMiddle()!=null && myTree.getCursor().getRight()!=null)
                         throw new PositionNotAvailableException();
                    if(myTree.getCursor().isPlant())
                        throw new IsPlantException();
                    System.out.print("What is the name of the organism?: ");
                    d = x.nextLine();
                    d= d.trim();
                    myTree.addPlantChild(d);
                }
                if (choice.equals("AC")) {
                    if(myTree.getCursor().getLeft()!=null && myTree.getCursor().getMiddle()!=null && myTree.getCursor().getRight()!=null)
                        throw new PositionNotAvailableException();
                    if(myTree.getCursor().isPlant())
                            throw new IsPlantException();
                    System.out.print("What is the name of the organism?: ");
                    d = x.nextLine();
                    d= d.trim();
                    System.out.print("Is the organism an herbivore / a carnivore / an omnivore? (H / C / O): ");
                    String t = x.nextLine();
                    t = t.toUpperCase();
                    if (t.equals("H"))
                        myTree.addAnimalChild(d, true, false);
                    else if (t.equals("C"))
                        myTree.addAnimalChild(d, false, true);
                    else
                        myTree.addAnimalChild(d, false, false);
                }
                if (choice.equals("RC")) {
                    System.out.print("What is the name of the organism to be removed?: ");
                    d = x.nextLine();
                    myTree.removeChild(d);
                }
                if (choice.equals("P")) {
                    System.out.println(myTree.listPrey());
                    System.out.println();
                }
                if (choice.equals("C")) {
                    System.out.println(myTree.listFoodChain());
                    System.out.println();
                }
                if (choice.equals("F")) {
                    myTree.printOrganismTree();
                    System.out.println();
                }
                if (choice.equals("LP")) {
                    System.out.println(myTree.listAllPlants());
                    System.out.println();
                }
                if (choice.equals("R")) {
                    myTree.cursorReset();
                }
                if (choice.equals("M")) {
                    System.out.print("Move to?:");
                    d = x.nextLine();
                    myTree.moveCursor(d);

                }
                if (choice.equals("Q"))
                    System.exit(0);

            }

        catch(IsPlantException ex){
            System.out.println("ERROR: The cursor is at a plant node. Plants cannot be predators.\n");
        }
        catch(PositionNotAvailableException ex){
            System.out.println("ERROR: There is no more room for more prey for this predator.\n");
        }
        catch(DietMismatchException ex){
            System.out.println("ERROR: This prey cannot be added as it does not match the diet of the predator\n");
        }
        catch(IllegalArgumentException ex){
            System.out.println("ERROR: This prey does not exist for this predator.\n");
        }
        catch(PreyAlreadyExistException ex){
            System.out.println("ERROR: This prey already exists for this predator.\n");
        }
    }while(!choice.equals("Q"));

    }


}

// 反馈