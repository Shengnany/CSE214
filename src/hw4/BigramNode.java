/**
 * This class acts as a node representing a Bigram which allows Phrase to use a singly linked linked list to implement queue.
 *  @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #4
 *  CSE 214, Rec 04
 */package hw4;

public class BigramNode {
    private Bigram data;
    private BigramNode link;

    /**
     * This method is  a constructor used to construct a BigramNode
     * @param initialData
     * the data  in the node
     */
    public BigramNode(Bigram initialData){
        data = initialData;
        link  = null;
    }

    /**
     * This class is used to retrieve the data in a node
     * @return
     *  the data in a node
     */
    public Bigram getData(){
        return data;
    }

    /**
     * This method is used to get the link of a node
     * @return
     * the link of the node
     */
    public BigramNode getLink(){
        return link;
    }

    /**
     * This method is used to set data in a  node
     * @param newData
     * the data assigned to a node
     */
    public void setData(Bigram newData){
        data = newData;
    }

    /**
     * This method is used to set link in a node
     * @param newLink
     * the link assigned to a node
     */
    public void setLink(BigramNode newLink){
        link = newLink;
    }

}
