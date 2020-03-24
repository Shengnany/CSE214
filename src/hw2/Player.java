package hw2;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class has a main method runs a menu driven application that first creates an empty SongLinkedList object.
 *
 * @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #2
 *  CSE 214, Rec 04
 */
public class Player {
    public static void main(String[] args){
        SongLinkedList list =new SongLinkedList();
        Scanner input = new Scanner(System.in);
        String choice;
        do {
            System.out.print("Enter an option: ");
            choice = input.nextLine();
            String title;
            String artist;
            String album;
            if(choice.equals("A")||choice.equals("a")){
                while(true) {
                    try {
                        System.out.print("Enter song title: ");
                        title = input.nextLine();
                        System.out.print("Enter artist(s) of the song: ");
                        artist = input.nextLine();
                        System.out.print("Enter album: ");
                        album = input.nextLine();
                        System.out.print("Enter length (in seconds): ");
                        if (title.isEmpty()||artist.isEmpty()||album.isEmpty())
                            throw new EmptyInputException();
                        else break;
                    }
                    catch (EmptyInputException ex){
                        System.out.print("Don't enter empty content. Enter agian please: ");
                        input.nextLine();
                    }
                }
                int length;
                while(true){
                    try{
                        length=input.nextInt();
                        break;
                    }
                    catch (InputMismatchException ex){
                        System.out.print("Enter agian: ");
                        input.nextLine();
                    }
                }
                list.insertAfterCursor(new Song(artist,album,title,length));
                input.nextLine();
            }
            if(choice.equals("F")||choice.equals("f")){
                list.cursorForwards();
            }
            if(choice.equals("B")||choice.equals("b")){
                list.cursorBackwards();

            }
            if(choice.equals("R")||choice.equals("r")){
                list.removeCursor();
            }
            if(choice.equals("L")||choice.equals("l")){
                System.out.print("Enter name of song to play: ");
                String name= input.nextLine();
                list.play(name);
            }
            if(choice.equals("C")||choice.equals("c")){
                list.deleteAll();
            }
            if(choice.equals("S")||choice.equals("s")){
                list.shuffle();
            }
            if(choice.equals("Z")||choice.equals("z")){
                list.random();
            }
            if(choice.equals("P")||choice.equals("p")){
                list.printPlaylist();
            }
            if(choice.equals("T")||choice.equals("t")){
                if(list.getSize()==0)
                    System.out.println("Your playlist is empty.");
                else if(list.getSize()>1)
                    System.out.println("Your playlist contains "+list.getSize()+" songs. ");
                else if(list.getSize()==1)
                    System.out.println("Your playlist contains 1 song. ");
            }
            if(choice.equals("Q")||choice.equals("q")){
                System.out.println("Program terminated. ");
                System.exit(0);
            }
        } while (true);
    }
}
