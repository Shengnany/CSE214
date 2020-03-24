/**
 * This class represents a single node in the OrganisTree, and each node will represent a single species (either a plant or an animal).
 *  @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #5
 *  CSE 214, Rec 04
 */
package hw5;
public class OrganismNode {
    /**
     * This method is a constructor used to create a OrganismNode
     */
    public OrganismNode(){

    }

    public  OrganismNode(String name, boolean isPlant, boolean isHerbivore,  boolean isCarnivore){
        setName(name);
        setHerbivore(isHerbivore);
        setCarnivore(isCarnivore);
    }



    private String name;
    private boolean isPlant = false;
    private boolean isHerbivore = false;
    private boolean isCarnivore = false;
    private boolean isOmnivore =true;
    private OrganismNode left = null;
    private OrganismNode middle = null;
    private OrganismNode right = null;
    private OrganismNode parent = null;
    private int layer = 0;
    /**
     * This method adds a preyNode as prey to this node.
     * @param preyNode
     * The OrganismNode to be added as prey of this organism
     * @throws PositionNotAvailableException
     * @throws IsPlantException
     * @throws DietMismatchException
     */
    public void addPrey(OrganismNode preyNode) throws PositionNotAvailableException,IsPlantException,DietMismatchException,PreyAlreadyExistException {
            if( (getLeft()!=null && getLeft().getName().equals(preyNode.getName()))
                ||(getMiddle()!=null && getMiddle().getName().equals(preyNode.getName()))
                ||(getRight()!=null && getRight().getName().equals(preyNode.getName()))
            )
            throw new PreyAlreadyExistException();
            if(checkDiet(preyNode))
                throw new DietMismatchException();
            preyNode.setParent(this);
            if (this.getLeft() == null)
                left = preyNode;
            else if (this.getMiddle() == null) {
                setMiddle(preyNode);
            }
            else if (this.getRight() == null) {
                setRight(preyNode);
            }
            preyNode.setLayer(layer+1);

    }

    /**
     * This method sets the layer of the node
     * @param layer
     * the layer of the node
     */
    public void setLayer(int layer){
        this.layer = layer+1;
    }

    /**
     * This mehtod returs the layer of the node
     * @return
     * the layer of the node
     */
    public int getLayer(){
        return layer;
    }
    /**
     * This method sets the parent of the ndoe
     * @param parent
     */
    public void setParent(OrganismNode parent){
        this.parent = parent;
    }

    /**
     * This method returns the parent of the node;
     * @return
     * the parent node
     */
    public OrganismNode getParent(){
        return parent;
    }

    /**
     * This method returns a left node
     * @return
     * left node
     */
    public OrganismNode getLeft(){
        return left;
    }

    /**
     * This method returns a right node
     * @return
     * right node
     */
    public OrganismNode getRight(){
        return right;
    }

    /**
     * This method returns a middle node
     * @return
     * middle node
     */
    public OrganismNode getMiddle(){
        return middle;
    }

    /**
     * This method sets the right child of the node
     * @param right
     * the right child
     */
    public void setRight(OrganismNode right){
        this.right =  right;
    }

    /**
     * This method sets the middle child of the node
     * @param middle
     * the middle child
     */
    public void setMiddle(OrganismNode middle){
        this.middle =  middle;
    }

    /**
     * This method sets the left child of the node
     * @param left
     * the left child
     */
    public void setLeft(OrganismNode left){
        this.left =  left;
    }


    /**
     * This method returns the name of the String
     * @return
     */
    public String getName(){
        return name;
    }
    /**
     * This method checks whether this object is plant
     * @return
     * true if it is
     */
    public boolean isPlant(){
        return isPlant;
    }

    /**
     * This method checks whether this object is herbivore
     * @return
     * true if it is
     */
    public boolean isHerbivore(){
        return isHerbivore;
    }

    /**
     * This method checks whether this object is carnivore
     * @return
     * true if it is
     */
    public boolean isCarnivore(){
        return isCarnivore;
    }

    /**
     * This method sets isPlant
     * @param isPlant
     * whether node is  a plant
     */
    public void setPlant(boolean isPlant){
        this.isPlant = isPlant;
    }

    /**
     * This method sets isCarnivore
     * @param isCarnivore
     * whether node is  a c arnivore
     */
    public void setCarnivore(boolean isCarnivore){
        if(isCarnivore){
            this.isCarnivore = true;
            this.isOmnivore = false;
        }

    }

    /**
     * This method sets isherbivore
     * @param isHerbivore
     * whether node is a Herbivore
     */
    public void setHerbivore(boolean isHerbivore){
        if(isHerbivore){
            this.isHerbivore = true;
            this.isOmnivore = false;
        }
    }


    public void setName(String name){
        this.name = name;
    }
    /**
     * This is a method to check whether the preyNode matches the diet restriction
     * @param preyNode
     * the preyNode to be check
     * @return
     * true if it does not match
     */
    public boolean checkDiet(OrganismNode preyNode){
        if(isCarnivore && preyNode.isPlant())
            return true;
        else if(isHerbivore && !preyNode.isPlant())
            return true;
        else return false;
    }
}

