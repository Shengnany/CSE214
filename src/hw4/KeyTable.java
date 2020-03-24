/**
 * This class represents the key to a Playfair Cipher. It contains a two-dimensional array of char variables.
 *  @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #4
 *  CSE 214, Rec 04
 *
 */
package hw4;
import java.util.Arrays;
public class KeyTable {
    private char[][] key = new char[5][5];

    /**
     * This is a default constructor used to construct a KeyTable object.
     * @return
     */
    public KeyTable(){

    }

    /**
     * This method builds a new KeyTable object from the string and returns it
     * @param keyphrase
     * The string to use as the key
     * @return
     * The new KeyTable object
     */
    public static KeyTable buildFromString(String keyphrase){
        KeyTable table = new KeyTable();
        String s = keyphrase.toUpperCase();
        s = Phrase.removeDuplicates(s);
        s = s.replaceAll("J","I");
        s=s.replaceAll("[^A-Z]","");
        char[] a=new char[25];
        int i;
        for(i=0;i<s.length();i++){
            a[i]=s.charAt(i);
        }
        int index='A';
        int j=i;
        while(j<a.length){
            boolean found = false;
            while(!found){
                if(s.length()==0)
                    found = true;
                if(index=='J')
                    index++;
                for(int k =0; k<s.length();k++){
                    if((char)index == s.charAt(k)){
                        found = false;
                        index++;
                        break;
                    }
                    found = true;
                }
            }
            a[j]=(char)(index);
            index++;
            j++;
        }
        table.setKey(a);
        System.out.println("Generation success! ");
        return table;
    }

    /**
     * This mathod returns the key matrix
     * @return
     * The key matrix contained in this KeyTable
     */
    public char[][] getKey(){
        return key;
    }

    /**
     * This method is used to set the key
     * @param numbers
     * the chars stored in key
     */
    public void setKey(char[] numbers){
        key = new char[5][5];
        int index =0;
        for(int i=0;i<key.length;i++){
            for(int j=0;j<key[i].length;j++){
                key[i][j]=numbers[index];
                index++;
            }
        }
    }

    /**
     * This method returns the row in which c ouccurs
     * @param c
     * the character to locate within the key matrix
     * @return
     * the index of the row in which c occurs
     */
    public int findRow(char c){
        for(int i =0; i< key.length; i++){
            for(int j = 0;j<key[i].length;j++){
                if(key[i][j] == c) return i+1;
            }
        }
        return -1;
    }

    /**
     * This method returns the column in which c ouccurs
     * @param c
     * the character to locate within the key matrix
     * @return
     * the index of the column in which c occurs
     */
    public int fincol(char c){
        for(int i =0; i< key.length; i++){
            for(int j = 0; j<key[i].length; j++){
                if(key[i][j] == c) return j+1;
            }
        }
        return -1;
    }

    /**
     * This method is used to create a string representation of the keyTable
     * @return
     * the string representation of keytable
     */
    public String toString(){
        String s="";
        for(int i=0;i<getKey().length;i++){
            for(int j=0;j<getKey()[i].length;j++){
                s=s+getKey()[i][j];
            }
            s=s+"\n";
        }
        return s;
    }

}
