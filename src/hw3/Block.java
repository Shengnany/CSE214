package hw3;
/**
 * This class implements a block node.
 *
 * @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #3
 *  CSE 214, Rec 04
 */
public class Block {
    public final int Capacity = 20;
    private Variable[] data;
    private Block link;
    private int count;

    /**
     * This is a constructor used to construct a block object
     */
    public Block() {
        data = new Variable[Capacity];
        link = null;
    }

    /**
     * This method assigns a value to the link
     *
     * @param next
     * The Block which comes after the block
     */
    public void setLink(Block next) {
        link = next;
    }

    /**
     * This is method used to get the link of the object
     *
     * @return
     * the link to next block
     */
    public Block getLink() {
        return link;
    }

    /**
     * This is a method used to get the Variable[]
     *
     * @return
     * the Variable[] data of the block
     */
    public Variable[] getData(){
        return data;
    }

    /**
     * This is a method used to get the actual size of the block's data
     *
     * @return
     * the actual size of the block'data Variable[]
     */
    public int getSize(){
        return count;
    }

    /**
     * This is a method used to add Variable to the block object
     *
     * @param x
     * the Variable added to the block object
     */
    public void addVariable(Variable x) {
        data[count] = x;
        count++;
    }

    /**
     * This is a method used add unknown Variable to the block object
     *
     * @param x
     * the unknown Variable added to the block object
     */
    public void addUnknownVariable(String x){
        data[count] = new Variable();
        data[count].setName(x);
        data[count].setInitialValue(0);
        count++;
    }

    /**
     * This is a method used to get the names of the Variable objects
     *
     * @return
     * array of Varaiables' names
     */
    public String[] getName(){
        String[] name = new String[getSize()];
        for(int i = 0; i<getSize(); i++){
            name[i] = data[i].getName();
        }
        return name;
    }

    /**
     * This method is used to find the value of a Variable given a name
     * @param s
     * the name of the Object
     * @return
     * the value of the Object with given name
     */

    public int findValue(String s){
        for(int i=0 ;i<getSize();i++){
            if(data[i].getName().equals(s))
                return data[i].getInitialValue();
        }
        return -1;
    }
}
