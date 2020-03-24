package hw2;
/**
 * This class represents an exception whenever the playlist has no content.
 *
 * @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #2
 *  CSE 214, Rec 04
 */
public class NoSongInPlaylistException extends Exception {
    NoSongInPlaylistException(){
        super("NoSongInPlaylistException");
    }
}
