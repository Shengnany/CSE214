/**
 * This class represents a bigram cotaining two char variables.
 *  @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #4
 *  CSE 214, Rec 04
 */
package hw4;
public class Bigram {
    private char first,second;

    /**
     * This method returns the first char
     * @return
     * the first char
     */
    public char getFirst(){
        return first;
    }

    /**
     * This method returns the second char
     * @return
     * the second char
     */
    public char getSecond(){
        return second;
    }

    /**
     * This method assigns the first char with a given character
     * @param f
     * tthe char to be assigned
     */
    public void setFirst(char f){
        first = f;
    }

    /**
     * This method assigns the second char with a given character
     * @param s
     * tthe char to be assigned
     */
    public void setSecond(char s){
        second  = s;
    }
    /**
     * This is a constructor used to create a bigram object
     */
    public Bigram(){

    }
    /**
     * This method is a constructor used to construct a Bigram object with the given chars
     * @param first
     * the first char in the object
     * @param second
     * the second char in the object
     */
    public Bigram(char first, char second){
        setFirst(first);
        setSecond(second);
    }

    /**
     * This method is used to create a string representation of the Bigram
     * @return
     * a string representation of the Bigram
     */
    public String toString(){
        return getFirst()+String.valueOf(getSecond());
    }
}
