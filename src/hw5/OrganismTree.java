package hw5;
/**
 * This class will implement the ternary tree of OrganismNode objects.
 * This class will contain two OrganismNode references: root and cursor.
 * @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #5
 *  CSE 214, Rec 04
 */
public class OrganismTree {
    /**
     * This class creates a new OrganismTree with apexPredator as the root.
     * The apex predator of the food pyramid.
     * @param apexPredator
     */
    public OrganismTree(OrganismNode apexPredator){
        setRoot(apexPredator);
        cursor = root;
    }
    private OrganismNode root = null;
    private OrganismNode cursor = null;

    public void setRoot(OrganismNode root){
        this.root = root;
    }
    /**
     * This method moves the cursor back to the root of the tree.
     */
    public void cursorReset(){
        cursor = root;
        System.out.println("Cursor successfully reset to root!");
    }

    public OrganismNode getCursor(){
        return this.cursor;
    }

    /**
     * This method moves cursor to one of cursor’s children.
     * @param name
     * The name of the node to be moved to.
     * @throws IllegalArgumentException
     */
    public void moveCursor(String name) throws IllegalArgumentException{
        if(cursor.getLeft() != null && cursor.getLeft().getName().equals(name) )
            cursor = cursor.getLeft();
        else if(cursor.getMiddle() != null && cursor.getMiddle().getName().equals(name) )
            cursor = cursor.getMiddle();
        else if(cursor.getRight() != null && cursor.getRight().getName().equals(name) )
            cursor = cursor.getRight();
        else throw new IllegalArgumentException();
        System.out.println("Cursor successfully moved to "+name+"! ");
    }

    /**
     * This method returns a String containing the name of the cursor, and all the cursor’s possible prey.
     * @return
     * Returns a String including the organism at cursor and all its possible prey
     * @throws IsPlantException
     */
    public String listPrey() throws IsPlantException{
        String x = cursor.getName()+" -> ";
        if (cursor.getLeft()!=null)
            x=x+cursor.getLeft().getName()+", ";
        if (cursor.getMiddle()!=null)
            x=x+cursor.getMiddle().getName()+", ";
        if (cursor.getRight()!=null)
            x=x+cursor.getRight().getName()+" ";
        return x;
    }

    public OrganismNode getRoot() {
        return root;
    }

    /**
     * This method returns a String including the organism at cursor and all its possible prey
     * @return
     * A String containing the name of the cursor, and all the cursor’s possible prey.
     */
    public String listFoodChain(){
        OrganismNode nodePtr = cursor;
        String x = cursor.getName();
        while(nodePtr!=root){
            nodePtr = nodePtr.getParent();
            x=nodePtr.getName()+" -> "+x;
        }
        System.out.println();
        return x;
    }

    /**
     * This method prints out a layered, indented tree by performing a preorder traversal starting at cursor.
     */
    public void printOrganismTree(){
        printTreeHelper(cursor,"");
    }

    /**
     * This method is a helper method to printOrganismTree()
     * @param node
     * the root of each subtree
     */
    public void printTreeHelper(OrganismNode node,String indent){
        System.out.println(indent+ "|- "+ node.getName());
        if(node.getLeft()==null)
            return;
        else {
            if (node.getLeft() != null)
                printTreeHelper(node.getLeft(),indent+"   ");
            if (node.getMiddle() != null)
                printTreeHelper(node.getMiddle(),indent+"   ");
            if (node.getRight() != null)
                printTreeHelper(node.getRight(),indent+"   ");
        }
    }

    /**
     * This method returns a list of all plants currently at cursor and beneath cursor in the food pyramid.
     * @return
     * String containing a list of all the plants in the food pyramid.
     */
    public String listAllPlants(){
        return plantsHelper(cursor,"");
    }

    /**
     * This is a helper method for listAllPlants
     * @param node
     * the node to be checked whether it is a leaf
     * @param x
     * the string to append leaf to it
     * @return
     * the string of all plants
     */
    public String plantsHelper(OrganismNode node, String x){
        if(node == null)
            return x;
        if (node.getLeft() == null) {
            if (node.isPlant())
                return plantsHelper(node.getLeft(), x + node.getName() + ", ");
            else return plantsHelper(node.getLeft(), x);
        }
        if(node.getLeft()!= null)
            x= plantsHelper(node.getLeft(), x);
        if(node.getMiddle()!= null)
            x= plantsHelper(node.getMiddle(),x);
        if(node.getRight() != null)
            x= plantsHelper(node.getRight(),x);
        return x;

    }
    /**
     * Creates a new animal node with a specific name and diet and adds it as a child of the cursor node.
     * @param name
     * The name of the child node.
     * @param isHerbivore
     * depending on whether the animal consumes plants.
     * @param isCarnivore
     *  depending on whether the animal consumes other animals.
     * @throws IllegalArgumentException
     * @throws PositionNotAvailableException
     */
    public void addAnimalChild(String name, boolean isHerbivore, boolean isCarnivore) throws PreyAlreadyExistException,IllegalArgumentException, PositionNotAvailableException,IsPlantException,DietMismatchException{
            OrganismNode prey = new OrganismNode();
            prey.setCarnivore(isCarnivore);
            prey.setHerbivore(isHerbivore);
            prey.setName(name);
            cursor.addPrey(prey);
            System.out.println(name+" has successfully been added as prey for the "+cursor.getName()+"\n");

    }

    /**
     * This method creates a new plant node with a specific name and adds it as a child of the cursor node.
     * @param name
     * The name of the child node.
     * @throws IllegalArgumentException
     * @throws PositionNotAvailableException
     */
    public void addPlantChild(String name) throws PreyAlreadyExistException,IllegalArgumentException, PositionNotAvailableException,DietMismatchException, IsPlantException{
            OrganismNode prey = new OrganismNode();
            prey.setPlant(true);
            prey.setName(name);
            cursor.addPrey(prey);
            System.out.println(name+" has successfully been added as prey for the "+cursor.getName()+"\n");


    }

    /**
     *  Removes the child node of cursor with name "name" , and properly shifts the deleted node’s other siblings if necessary
     *  If the deleted node has any descendants, those nodes are deleted as well.
     * @param name
     * the name of the node to be removed
     * @throws IllegalArgumentException
     */
    public void removeChild(String name) throws IllegalArgumentException{
        if(cursor.getLeft() !=null && cursor.getLeft().getName().equals(name)) {
            if(cursor.getMiddle()!= null)  cursor.setLeft(cursor.getMiddle());
            else cursor.setLeft(null);
            if(cursor.getRight() != null)   cursor.setMiddle(cursor.getRight());
            else cursor.setMiddle(null);
            cursor.setRight(null);
        }
        else if(cursor.getMiddle() != null && cursor.getMiddle().getName().equals(name)){
            if(cursor.getRight() != null)
                cursor.setMiddle(cursor.getRight());
            else cursor.setMiddle(null);
            cursor.setRight(null);

        }
        else if(cursor.getMiddle() != null && cursor.getRight().getName().equals(name)){
            cursor.setRight(null);
        }
        else throw new IllegalArgumentException();
        System.out.println("A(n) "+name+" has successfully been removed as prey for the "+cursor.getName());
    }





}
