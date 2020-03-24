package hw3;
/**
 * This class implements a stack using linked list.
 *
 * @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #3
 *  CSE 214, Rec 04
 */
public class Stack implements Cloneable{
    private Block top;

    /**
     * This is a method used to create a Stack object
     */
    public Stack(){
        top = null;
    }

    /**
     * This is a method used to add a block to the stack
     * @param block
     * the block added to the  stack
     */
    public void push(Block block){
        block.setLink(top);
        top = block;
    }

    /**
     * This is a method used to remove a block from the stack
     * @return
     * the block removed
     */
    public Block pop(){
        Block old = top;
        top = top.getLink();
        return old;
    }

    /**
     * This is a method used to get the top block of the stack
     *
     * @return
     * the top block of the stack
     */
    public Block peek(){
        return top;
    }

    /**
     * This is a method used to check if the stack is empty
     * @return
     */
    public Boolean isEmpty(){
        return  top==null;
    }

}
