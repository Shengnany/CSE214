package hw6;
/**
 *This class will contain a Collection of FrequencyLists and a static method
 * which builds each FrequencyList from a list of Passage objects,
 * as well as a method to access the frequency of a word in a given Passage from a list of Passage objects,
 *
 * @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #6
 *  CSE 214, Rec 04
 */
import java.util.ArrayList;
import java.util.Iterator;

public class FrequencyTable extends ArrayList<FrequencyList> {
    /**
     * Iterates through passages and constructs FrequencyLists with each Passage’s appropriate word frequencies
     * @param passages
     * a collection containing the Passage objects to be parsed through
     * @return
     * The FrequencyTable constructed from each Passage in passages.
     */
    public static FrequencyTable buildTable (ArrayList<Passage> passages){
       FrequencyTable table1 =new FrequencyTable();
       int wordTotal= 0;
        for (Passage p:passages) {
            Iterator<String> it = p.getWords().iterator();
            boolean has=false;
            while(it.hasNext()){
                String x = it.next();
                for(FrequencyList l: table1) {
                    if(l.getWord().equals(x))
                    has=true;
                    break;
                }
                if(!has){
                    FrequencyList list = new FrequencyList(x,passages);
                    table1.add(list);
                }
            }

        }
        return  table1;
    }

    /**
     *Adds a Passage into the FrequencyTable and updates the FrequencyLists accordingly
     * @param p
     *  the Passage being iterated over and integrated into the table.
     * @throws IllegalArgumentException
     * If the given Passage is null or empty.
     */
    public void addPassage(Passage p) throws IllegalArgumentException{
        if(p==null||p.getWordCount()==0)
            throw new IllegalArgumentException();
        else {
            Iterator<String> it = p.getWords().iterator();
            boolean has = false;
            while (it.hasNext()) {
                String x = it.next();
                for (FrequencyList l : this) {
                    if (l.getWord().equals(x))
                        has = true;
                }
                if (!has) {
                    FrequencyList list = new FrequencyList(x);
                    list.addPassage(p);
                    this.add(list);
                }
            }
        }
    }

    /**
     *  returns the frequency of the given word in the given Passage.
     * @param word
     * @param p
     * @return
     */
    public double getFrequency(String word, Passage p){
        Iterator<FrequencyList> it = this.iterator();
        while(it.hasNext()){
            FrequencyList list = it.next();
            if(list.getWord().equals(word)){
                return list.getFrequencies(p);
            }
        }
        return -1;
    }
}

