package hw2;
/**
 * The class represents a node in the linked list and contains SongNode references to the previous node and the next node.
 * The class also contains a Song variable that represents the data being stored in each node (a song).
 *
 * @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #2
 *  CSE 214, Rec 04
 */
public class SongNode {
    private SongNode prev;
    private SongNode next;
    private Song data;

    /**
     * This class is used to create a songNode object
     */
    public SongNode(){
    }

    /**
     * This class is used to create a songNode object using a given song
     *
     * @param song
     * the data used to create a songNode
     */
    public SongNode(Song song){
        data=song;
    }
    /**
     * This method assigns a value to the prev
     *
     * @param prev
     * The SongNode which comes before this one in the playlist
     */
    public void setPrev(SongNode prev){
        this.prev=prev;
    }

    /**
     * This method assigns a value to the next
     *
     * @param nextNode
     * The SongNode which comes after this one in the playlist
     */
    public void setNext(SongNode nextNode){
        this.next=nextNode;
    }

    /**
     * This method assigns a value to the data
     *
     * @param data
     */
    public void setData(Song data){
        this.data=data;
    }

    /**
     * This method returns prev
     *
     * @return
     * prev
     */
    public SongNode getPrev(){
        return this.prev;
    }

    /**
     * This method returns next
     *
     * @return
     * next
     */
    public SongNode getNext(){
        return next;
    }

    /**
     * This method returns data
     *
     * @return
     * data
     */
    public  Song getData(){
        return data;
    }

    /**
     * This method returns a string representation of Songnode
     *
     * @return
     * String representation of Songnode
     */
    public String toString(){
        return getData().getName() +" by " + getData().getArtist();
    }

    /**
     * This method returns a boolean whether 2 Songnodes are equal
     * @param a
     * @return
     */
    public boolean equals(SongNode a){
        return (getData().getName().equals(a.getData().getName()));
    }

}
