package hw2;
/**
 * This class represents a song in the playlist which has a name (String), artist (String), album (String), and a length (int).
 *
 * @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #2
 *  CSE 214, Rec 04
 */
public class Song {
    private String artist;
    private String album;
    private String name;
    private int length;

    /**
     * This is a constructor used to create a Song object
     */
    public Song(){
    }

    public Song(String artist, String album, String name, int length ){
        this.artist=artist;
        this.album=album;
        this.name=name;
        this.length=length;
    }

    /**
     * This method returns the artist name
     *
     * @return
     * the artist's name
     */
    public String getArtist() {
        return artist;
    }

    /**
     * This method returns the album name
     *
     * @return
     * the album name
     */
    public String getAlbum(){
        return album;
    }

    /**
     * This method returns the song's name
     *
     * @return
     * the song's name
     */
    public String getName(){
        return name;
    }

    /**
     * This method returns the The length of the song in seconds
     *
     * @return
     * The length of the song in seconds
     */
    public int getLength(){
        return length;
    }

    /**
     * This method assigns a value to the artist
     *
     * @param artist
     * artist's name
     */
   public void setArtist(String artist){
        this.artist=artist;
   }

    /**
     * This method assigns a value to the album
     *
     * @param album
     */
   public void setAlbum(String album){
        this.album=album;
   }

    /**
     * This method assigns a value to the name
     *
     * @param name
     */
    public void setName(String name){
        this.name=name;
    }

    /**
     * This method assigns a value to the length
     *
     * @param length
     */
    public void setLength(int length){
        this.length=length;
    }

    public String toString(){
        return String.format("%-25s%-25s%-25s%-25s", getName(), getArtist(), getAlbum(), getLength());
    }


}
