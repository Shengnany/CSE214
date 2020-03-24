package hw4;
import java.util.Arrays;

/**
 * This class acts as a wrapper for a queue to retain the order of the phrase provided.
 *  @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #4
 *  CSE 214, Rec 04
 */
public class Phrase  {
    BigramNode front = null;
    BigramNode rear = null;
    private int size;

    /**
     * This method adds a new Bigram object at the end of the Phrase
     * @param b
     * the Bigram object to be added
     */
    public void enqueue(Bigram b){
        BigramNode newNode = new BigramNode(b);
        if(front == null){
            front = newNode;
            rear = front;
        }
        else{
            rear.setLink(newNode);
            rear = newNode;
        }
        size++;
    }

    /**
     * This method removes and returns the first Bigram object in the Phrase
     * @return
     * the first Bigram object in the Phrase
     */
    public Bigram dequeue(){
        try {
            Bigram pair;
            if (front == null)
                throw new EmptyQueueException();
            else {
                pair = front.getData();
                front=front.getLink();
                if(front == null)
                    rear = null;
                size--;
                return pair;
            }
        }
        catch (EmptyQueueException ex){
            return null;
        }
    }

    /**
     * This method is used to return the first Bigram object in a Phrase
     * @return
     * the first object in a Phrase
     */
    public Bigram peek(){
        return front.getData();
    }

    /**
     * This method returns the number of Bigram objects in the Phrase
     * @return
     * the size of the Phrase
     */
    public int size(){
        return size;
    }

    /**
     * This is a method used to check whether a Phrase is empty
     * @return
     * true if the Phrase object is empty
     */
    public boolean isEmpty(){
        return front==null;
    }

    /**
     * This is a constructor used to create a Phrase object
     */
    public Phrase(){
    }

    /**
     * This metthod is used to build and return a Phrase object, which is a queue of Bigram objects representing s
     * @param s
     * the String to represent as a Bigram queue
     * @return
     * the new Phrase object which contains a queue of Bigram objects representing S
     */
    public static Phrase buildPhraseFromStringforEnc(String s){
        s = s.toUpperCase();
        s = s.replaceAll("J","I");
        s = s.replaceAll("[^A-Z]","");
        Phrase p = new Phrase();
        int position = 0;
        char first ='a' ;
        char second='a';
        for(int i =0; i<s.length(); i++) {
            char c = s.charAt(i);
            if (position % 2 == 0) {
                first = c;
                position++;
            }
            else if (position % 2 == 1) {
                if (first == c) {
                    Bigram b = new Bigram();
                    b.setFirst(first);
                    b.setSecond('X');
                    p.enqueue(b);
                    first = c;
//                    Bigram g = new Bigram();
//                    g.setFirst(c);
//                    g.setSecond('X');
//                    p.enqueue(g);
//                    position++;
                }
                else {
                    Bigram b = new Bigram();
                    second = c;
                    b.setFirst(first);
                    b.setSecond(second);
                    position++;
                    p.enqueue(b);
                }
            }
        }
        if(position%2 == 1){
                Bigram b = new Bigram();
                b.setFirst(first);
                second = 'X';
                b.setSecond(second);
                p.enqueue(b);
            }
        return p;

    }

    /**
     * This method encrypts this Phrase and store the answer in another Phrase and return it
     * @param key
     * the KeyTable used to encrypt this Phrase
     * @return
     * the encrypted Phrase
     */
    public Phrase encrypt(KeyTable key) throws IllegalArgumentException {
        if(front == null)
            throw new IllegalArgumentException();
        Phrase q = new Phrase();
        char first,second;
        int rowFirst,columnFirst,rowSecond,columnSecond;
        while(front != null){
            Bigram b = dequeue();
            first = b.getFirst();
            second = b.getSecond();
            rowFirst = key.findRow(first);
            columnFirst = key.fincol(first);
            rowSecond = key.findRow(second);
            columnSecond = key.fincol(second);
            int [] pos =encryptHelper(rowFirst,rowSecond,columnFirst,columnSecond);
            Bigram c= new Bigram(first,second);
            c.setFirst(key.getKey()[pos[0]][pos[1]]);
            c.setSecond(key.getKey()[pos[2]][pos[3]]);
            q.enqueue(c);
        }
        return q;
    }

    /**
     * This method is a helper method for encryption.
     * @param rowFirst
     * the first word's row
     * @param rowSecond
     * the first word's column
     * @param columnFirst
     * the second word's row
     * @param columnSecond
     * the second word's column
     * @return
     * an array that stores position of the new words' rows and columns
     */
    public int[] encryptHelper(int rowFirst,int rowSecond,int columnFirst, int columnSecond){
        int firRowAns,secRowAns,firColAns,secColAns;
        if(rowFirst == rowSecond ){
            if(columnFirst==5||columnSecond==5){
                if(columnFirst==5){
                    firColAns=1;
                    secColAns=columnSecond+1;
                }
                else{
                    secColAns=1;
                    firColAns=columnFirst+1;
                }
            }
            else {
                firColAns = columnFirst + 1;
                secColAns = columnSecond + 1;
            }
            firRowAns=rowFirst;
            secRowAns=rowSecond;
        }

       else if(columnFirst == columnSecond ){
           if(rowFirst==5||rowSecond==5){
               if(rowFirst==5){
                   firRowAns=1;
                   secRowAns=rowSecond+1;
               }
               else{
                   secRowAns=1;
                   firRowAns=rowSecond+1;
               }
           }
           else {
               firRowAns = rowFirst + 1;
               secRowAns = rowSecond + 1;
           }
           firColAns=columnFirst;
           secColAns=columnSecond;
       }

       else{
               firRowAns=rowFirst;
               secRowAns=rowSecond;
               firColAns=columnSecond;
               secColAns=columnFirst;
       }

       int [] pos = new int[4];
        pos[0]=firRowAns-1;
        pos[1]=firColAns-1;
        pos[2]=secRowAns-1;
        pos[3]=secColAns-1;
       return pos;
    }

    /**
     * This method decrypts this Phrase, sttoring the decrypted bigrams in a new Phase queue object and return it
     * @param key
     * the KeyTable used to decrypt this Phrase
     * @return
     * the new Phrase object which contains a queue of Bigram objects
     */
    public Phrase decrypt(KeyTable key) throws IllegalArgumentException {
        if(front == null)
            throw new IllegalArgumentException();
        Phrase q = new Phrase();
        char first,second;
        int rowFirst,columnFirst,rowSecond,columnSecond;
        while(front != null){
            Bigram b = dequeue();
            first = b.getFirst();
            second = b.getSecond();
            rowFirst = key.findRow(first);
            columnFirst = key.fincol(first);
            rowSecond = key.findRow(second);
            columnSecond = key.fincol(second);
            int [] pos =decryptHelper(rowFirst,rowSecond,columnFirst,columnSecond);
            Bigram c= new Bigram(first,second);
            c.setFirst((key.getKey())[pos[0]][pos[1]]);
            c.setSecond((key.getKey())[pos[2]][pos[3]]);
            q.enqueue(c);
        }
        return q;
    }

    /**
     * This method is a helper method for decryption.
     * @param rowFirst
     * the first word's row
     * @param rowSecond
     * the first word's column
     * @param columnFirst
     * the second word's row
     * @param columnSecond
     * the second word's column
     * @return
     * an array that stores position of the new words' rows and columns
     */
    public int[] decryptHelper(int rowFirst,int rowSecond,int columnFirst, int columnSecond){
        int firRowAns,secRowAns,firColAns,secColAns;
        if(rowFirst == rowSecond ){
            if(columnFirst==1||columnSecond==1){
                if(columnFirst==1){
                    firColAns=5;
                    secColAns=columnSecond-1;
                }
                else{
                    secColAns=5;
                    firColAns=columnFirst-1;
                }
            }
            else {
                firColAns = columnFirst - 1;
                secColAns = columnSecond - 1;
            }
            firRowAns=rowFirst;
            secRowAns=rowSecond;
        }

        else if(columnFirst == columnSecond ){
            if(rowFirst==1||rowSecond==1){
                if(rowFirst==1){
                    firRowAns=5;
                    secRowAns=rowSecond-1;
                }
                else{
                    secRowAns=5;
                    firRowAns=rowSecond-1;
                }
            }
            else {
                firRowAns = rowFirst - 1;
                secRowAns = rowSecond - 1;
            }
            firColAns=columnFirst;
            secColAns=columnSecond;
        }

        else{
            firRowAns=rowFirst;
            secRowAns=rowSecond;
            firColAns=columnSecond;
            secColAns=columnFirst;
        }

        int [] pos = new int[4];
        pos[0]=firRowAns-1;
        pos[1]=firColAns-1;
        pos[2]=secRowAns-1;
        pos[3]=secColAns-1;
        return pos;
    }

    /**
     * This method prints out and return a String representation of the Phrase
     * @return
     * a string representation of the Phrase
     */
    public String toString(){
        String s="";
        int count=0;
        while (count<size) {
            Bigram p = dequeue();
            s=s+p.getFirst();
            s=s+p.getSecond();
            enqueue(p);
            count++;
        }
        return s;
    }

    /**
     * This method is used to remove duplicate characters in a string
     * @param s
     * @return
     */
    public static String removeDuplicates(String s){
        if(s.length()==0)
            return "";
        char[] c = s.toCharArray();
        int index=0;
        for(int i = 0;i<c.length;i++){
            int j;
            for (j = 0; j < i; j++) {
                if(c[i]==c[j])
                    break;;
            }
            if(j == i){
                c[index++]=c[i];
            }
        }
        return String.valueOf(Arrays.copyOf(c,index));
    }

}
