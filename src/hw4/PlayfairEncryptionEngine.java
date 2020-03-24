/**
 *  This class acts as the menu driver for the program.
 *  It first prompt user for input to generate the KeyTable Object. If no input is given, a default KeyTable is created.
 *  The dirver will allow the user to change the current key, print the current key, encrypt and decrypt key.
 *  @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #4
 *  CSE 214, Rec 04
 */
package hw4;
import java.util.Scanner;

public class PlayfairEncryptionEngine {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter key phrase: ");
        String x = input.nextLine();
        KeyTable key = KeyTable.buildFromString(x);
        String option="";
        while(!x.equals("Q")&&!x.equals("q")){
            System.out.print("Please select an option: ");
            x=input.nextLine();
            if(x.equals("CK")||x.equals("ck")){
                System.out.print("Enter key phrase: ");
                option = input.nextLine();
                key = KeyTable.buildFromString(option);
            }
            if(x.equals("PK")||x.equals("pk")){
                System.out.println(key.toString());
            }
            if(x.equals("EN")||x.equals("en")){
                System.out.print("Please enter a phrase to encrypt: ");
                option=input.nextLine();
                if(option.trim().isEmpty()){
                    System.out.println("Wrong! Empty phrase!");
                }
                else {
                    Phrase p = Phrase.buildPhraseFromStringforEnc(option);
                    System.out.println("Encrypted text is: ");
                    System.out.println((p.encrypt(key)).toString());
                }
            }
            if(x.equals("DE")||x.equals("de")){
                System.out.print("Please enter a phrase to decrypt: ");
                option = input.nextLine();
                if(option.trim().isEmpty()){
                    System.out.println("Wrong! Empty phrase!");
                }
                else{
                    Phrase p = Phrase.buildPhraseFromStringforEnc(option);
                    System.out.print("Decrypted text is: ");
                    System.out.println((p.decrypt(key)).toString());
                }
            }
            if (x.equals("Q")||x.equals("q")){
                System.exit(0);
            }
        }

    }
}
