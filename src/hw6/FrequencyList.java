package hw6;
/**
 * Individual words and their frequencies across texts will be stored in FrequencyList objects,
 * which extends a Collection of your choice (ArrayList, LinkedList, etc.).
 * This class will contain a Collection of FrequencyLists and a static method which builds each FrequencyList
 * from a list of Passage objects, as well as a method to access the frequency of a word in a given Passage.
 *
 * @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #6
 *  CSE 214, Rec 04
 */

import java.util.ArrayList;
import java.util.HashMap;

public class FrequencyList {
    int index=0;
    String word;
    ArrayList<Double> frequencies;
    HashMap<String,Integer>passageIndices;

    /**
     * A constructor used to create a FrequencyList representing a word and the frequency of this words in passages
     * @param word
     * the word that it’s keeping track of
     * @param passages
     * the passage used to find occurrences of its given word in each
     */
    public FrequencyList(String word, ArrayList<Passage> passages){
        this.word=word;
        for(Passage p: passages){
            frequencies.add(index,(double)p.get(word)/p.getWordCount());
            passageIndices.put(p.getTitle(),index);
            index++;
        }
    }

    /**
     *  A constructor used to create a FrequencyList representing a word
     * @param word
     * the word represented
     */
    public FrequencyList(String word){
        this.word=word;
    }

    /**
     * returns the word
     * @return
     * word
     */
    public String getWord(){
        return word;
    }

    /**
     * Adds a Passage to this FrequencyList
     * @param p
     */
    public void addPassage(Passage p){
        frequencies.add(index,(double)p.get(word)/p.getWordCount());
        passageIndices.put(p.getTitle(),index);
        index++;
    }
    /**
     *  returns the frequency of “word” in the given Passage
     * @param p
     * @return
     */
    public double getFrequencies(Passage p){
        if(passageIndices.containsKey(p.getTitle())) return frequencies.get(passageIndices.get(p.getTitle()));
        return 0;
    }



}
