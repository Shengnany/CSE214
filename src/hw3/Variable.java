package hw3;
/**
 * This class represents a Variable having a name(String) and a initialValue(int).
 *
 * @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #3
 *  CSE 214, Rec 04
 */
public class Variable implements Cloneable {
    private String name;
    private int initialValue;

    /**
     * This mehtod is used to construct a Variable object
     */
    public Variable(){
    }

    /**
     * This mehtod is used to construct a Variable object with name v
     *
     * @param v
     * the name of the Variable
     */
    public Variable(String v){
        name = v;
    }

    /**
     * This mehtod is used to construct a Variable object with name v and value n
     * @param v
     *  name
     * @param n
     * value
     */
    public Variable(String v, int n){
        name = v;
        initialValue = n;
    }

    /**
     * This method is used to set the name of a Variable
     *
     * @param n
     * the name of the Variable
     */
    public void setName(String n){
        name = n;
    }

    /**
     * This method is used to set the initial value of a Variable
     * @param v
     *  initial value of a Variable
     */
    public void setInitialValue(int v){
        initialValue = v;
    }

    /**
     * This method is used to retrieve the name of a Variable
     * @return
     * the name of a Variable
     */
    public String getName(){
        return name;
    }

    /**
     * This method is used to retrieve the initial value of a Variable
     * @return
     * the initial value of a Variable
     */
    public int getInitialValue(){
        return initialValue;
    }

    /**
     * This method is used to clone a Variable object
     *
     * @return
     * a copy of the object
     */
    @Override
    public Object clone(){
        Variable x = new Variable();
        x.setName(name);
        x.setInitialValue(0);
        return x;
    }

    /**
     * This method is used to create a String representation  of the object
     * @return
     * a String representation  of the object
     */
    public String toString(){
        return String.format("%-20s%-20d", getName(), getInitialValue());
    }
}
