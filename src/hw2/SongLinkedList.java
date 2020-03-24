package hw2;
/**
 * This class implements a Doubly-Linked List ADT.
 *
 * @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #2
 *  CSE 214, Rec 04
 */
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;

public class SongLinkedList {
    private SongNode head;
    private SongNode tail;
    private SongNode cursor;
    private int size;

    Clip prevClip;

    /**
     * This is is a constructor used to create an instance of the SongLinkedList with no Song objects in it.
     */
    public SongLinkedList() {
        head = null;
        tail = null;
        cursor = null;
        size = 0;
    }

    /**
     * This method assigns a value to the head
     *
     * @param head
     */
    public void setHead(SongNode head) {
        this.head = head;
    }

    /**
     * This method assigns a value to the tail
     *
     * @param tail
     */
    public void setTail(SongNode tail) {
        this.tail = tail;
    }

    /**
     * This method assigns a value to the cursor
     *
     * @param cursor
     */
    public void setCursor(SongNode cursor) {
        this.cursor = cursor;
    }

    /**
     * This method assigns a value the size
     *
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * This method returns the head
     *
     * @return head
     */
    public SongNode getHead() {
        return head;
    }

    /**
     * This method returns tail
     *
     * @return tail
     */
    public SongNode getTail() {
        return tail;
    }

    /**
     * This method returns cursor
     *
     * @return cursor
     */
    public SongNode getCursor() {
        return cursor;
    }

    /**
     * This method returns size
     *
     * @return size
     */

    public void play(String name) throws IllegalArgumentException {
        String songPlay = name + ".wav";
        try {
            AudioInputStream AIS = AudioSystem.getAudioInputStream(new File(songPlay));
            Clip c =AudioSystem.getClip();
            if(prevClip!=null && prevClip.isRunning()){
                prevClip.stop();
            }
            c.open(AIS);
            c.start();
            prevClip = c;
            System.out.println(name+" is now playing. ");
        } catch (IllegalArgumentException ex) {
            System.out.println("File not found. ");
        } catch (javax.sound.sampled.UnsupportedAudioFileException ex) {
            System.out.println("File not found");
        } catch (java.io.IOException ex) {
            System.out.println("File not found");
        } catch (javax.sound.sampled.LineUnavailableException ex) {
            System.out.println("File not found");
        }
    }

    /**
     * Moves the cursor to point at the next SongNode.
     * <p>
     * Preconditions:
     * The list is not empty (cursor is not null).
     * <p>
     * Postconditions:
     * The cursor has been advanced to the next SongNode, or has remained at the tail of the list.
     */
    public void cursorForwards() {
        // if(cursor!=tail)
        if (cursor != null && cursor != tail) {
            setCursor(cursor.getNext());
            System.out.println("Cursor moved to next song.");
        }
        else
            System.out.println("Already at end of playlist.");

    }

    /**
     * Moves the cursor to point at the previous SongNode.
     */
    public void cursorBackwards() {
        if (cursor != null && cursor != head) {
                //cursor.setPrev(cursor.getPrev().getPrev());
            setCursor(cursor.getPrev());
            System.out.println("Cursor moved to the previous song.");
        }
        else
            System.out.println("Already at beginning of playlist.");
    }

    /**
     * Inserts a song into the playlist AFTER the cursor position.
     *
     * @param newSong
     * the Song to be inserted into the playlist.
     */
    public void insertAfterCursor(Song newSong) {
        SongNode listSong = new SongNode(newSong);
        if (cursor != null) {
            listSong.setNext(cursor.getNext());
            listSong.setPrev(cursor);
            cursor.setNext(listSong);
            if(cursor != tail)
                listSong.getNext().setPrev(listSong);
            else
                tail=listSong;
        } else {
            head = listSong;
            tail = listSong;
        }
        setCursor(listSong);
        size++;
        System.out.println("\'"+listSong.getData().getName()+"\'"+" by "
                +listSong.getData().getArtist()+" is added to your playlist.");
    }

    /**
     * This method Removes the SongNode referenced by the cursor and returns the Song contained within the node.
     * <p>
     * Precondition:
     * The cursor is not null
     * <p>
     * Postcondition:
     * The SongNode referenced by the cursor has been removed from the playlist.
     * The cursor now references the next node, or the previous node if no next node exists.
     *
     * @return The Song contained within the removed SongNode.
     */
    public Song removeCursor() {
        if (cursor != null) {
            Song theData;
            theData = cursor.getData();
            if (cursor != tail && cursor != head) {
                cursor.getPrev().setNext(cursor.getNext());
                cursor.getNext().setPrev(cursor.getPrev());
                setCursor(cursor.getNext());
            }
            else if(cursor == tail && cursor == head ) {
               tail = null;
               head = null;
               cursor = null;
            }
            else if(cursor == tail){
                tail = cursor.getPrev();
                setCursor(tail);
            }
            else {
                head = cursor.getNext();
                setCursor(head);
            }
            size--;
            System.out.println("\'"+theData.getName()+"\'"+" by "+theData.getArtist()+" was removed from the playlist. ");
            return theData;
        }
        else
            System.out.println("The song cannot be removed. ");
        return null;
    }

    /**
     * This method determines the number of Song objects currently in the playlist.
     *
     * @return The number of Song objects in the playlist.
     */
    public int getSize() {
        return size;
    }

    /**
     * This method selects one of the songs in the playlist and play it at random.
     *
     * @return The Song which was randomly selected.
     */
    public Song random() {
        try{
            SongNode randomSong = randomPlay();
            play(randomSong.getData().getName());
            return randomSong.getData();
        }
        catch (NullPointerException ex){
            System.out.println("No music in the playlist. ");
            return null;
        }
    }

    /**
     * This is a helper method for random().
     *
     * @return the song that is randomly chosen
     */
    public SongNode randomPlay() {
        try{
            if (getSize()==0)
                throw new NoSongInPlaylistException();
            int num = (int) (Math.random() * (getSize()+1));
            SongNode nodePtr = head;
            for (int i = 0; i < num-1; i++) {
                nodePtr = nodePtr.getNext();
            }
            return nodePtr;
        }
        catch (NoSongInPlaylistException ex){
            System.out.println("There is no song in the playlist.");
            return null;
        }
    }

    /**
     * This method randomly shuffles the order of the songs contained within the playlist.
     */
    public void shuffle() {
        SongLinkedList newList = new SongLinkedList();
        Song current = cursor.getData();
        int times = getSize();
        for(int i= 0;i<times;i++){
            SongNode r = randomPlay();
            if(r == null){
                System.out.println("The list is empty. ");
                break;
            }
            newList.insert(r);
            remove(r);
        }
        setHead(newList.getHead());
        setTail(newList.getTail());
        SongNode nodePtr = head;
        for(int i= 0;i<newList.getSize();i++){
            if(nodePtr.getData().equals(current)) {
                setCursor(nodePtr);
                break;
            }
        }
        size=newList.getSize();
        System.out.println("Playlist Shuffled. ");
    }


    /**
     * This is a helper method to shuffle(). This method inserts a new SongNode to new list.
     *
     * @param insertSong
     * The song being inserted
     */
    public void insert(SongNode insertSong) {
        SongNode listSong = new SongNode(insertSong.getData());
        if (cursor != null) {
            listSong.setNext(cursor.getNext());
            listSong.setPrev(cursor);
            cursor.setNext(listSong);
            if(cursor!=tail)
                listSong.getNext().setPrev(listSong);
            else
                tail=listSong;

        }
        else {
            setHead(listSong);
            setTail(listSong);
        }
        setCursor(listSong);
        size++;
    }


    /**
     * This is a helper method to shuffle(). This method removes a the SongNode from old list
     *
     * @param removeSong
     * The song being removed
     */
    public void remove(SongNode removeSong) {
        if(removeSong!= tail && removeSong!= head) {
            removeSong.getNext().setPrev(removeSong.getPrev());
            removeSong.getPrev().setNext(removeSong.getNext());
        }
        else if(removeSong == head && removeSong == tail){
            head=null;
            tail=null;
        }
        else if(removeSong == head){
            head=removeSong.getNext();
        }
        else if(removeSong == tail){
            tail=removeSong.getPrev();
        }

        size--;
    }

    /**
     * This method prints the playlist in a neatly-formatted table.
     */
    public void printPlaylist() {
        System.out.println(toString());
    }

    /**
     * This method returns a neatly formatted String representation of the playlist.
     *
     * @return
     *  Stringrepresentation of the playlist.
     */
    public String toString() {
        String a = "Playlist:\n";
        String b = String.format("%-25s%-25s%-25s%-25s\n", "Song", "| Artist", "| Album", "| Length(s)");
        String c ="";
        SongNode nodePtr = head;
        for (int i = 0; i < size; i++) {
            if (nodePtr == cursor) {
                c = c + nodePtr.getData().toString() + "  <-"+"\n";
            }
            else {
                c = c + nodePtr.getData().toString()+"\n";
                System.out.println();
            }
            nodePtr=nodePtr.getNext();
        }
        return a+b+c;
    }

    public void deleteAll(){
        head = null;
        tail = null;
        cursor = null;
        size = 0;
        System.out.println("Playlist cleared. ");
    }
}