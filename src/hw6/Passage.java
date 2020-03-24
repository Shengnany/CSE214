/**
 * This class has a hash table which maps a String (word) to an Integer value (occurences of that word).
 *
 * @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #6
 *  CSE 214, Rec 04
 */
package hw6;

import java.io.*;
import java.util.*;


public class Passage extends HashMap<String,Integer>{
    private String title;
    private int wordCount;
    private HashMap <String, Double> similarTitles;
    ArrayList<String> stopWords = new ArrayList<>();



    public  void setStopWords(ArrayList<String> stopWords){
        this.stopWords=stopWords;
    }
    /**
     * This method sets the title
     * @param title
     * the string assigned to tittle
     */
    public void setTittle(String title) {
        this.title = title;
    }

    /**
     * This method sets the wordCount
     * @param wordCount
     * the integer assigned to wordCount
     */
    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    /**
     * This method sets the similarTitles
     * @param similarTitles
     *  a collection which contains other Passages and the similarity percentage between this Passage and each other Passage
     */
    public void setSimilarTitles(HashMap<String, Double> similarTitles) {
        this.similarTitles = similarTitles;
    }

    /**
     * This method returns the distinct wordCount
     * @return
     * the wordCount
     */
    public int getWordCount() {
        return wordCount;
    }

    /**
     * This method returns title
     * @return
     * title
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method returns similarTitles
     * @return
     *  a collection which contains other Passages and the similarity percentage between this Passage and each other Passage
     */
    public HashMap<String, Double> getSimilarTitles() {
        return similarTitles;
    }


    /**
     * This constructor sets the title of the Passage and calls the parseFile() method
     * @param string
     * the tittle of the passage
     * the file to be parsed
     * @throws FileNotFoundException
     */
    public Passage(String string) throws FileNotFoundException,IOException {
        setTittle(string);

    }

    /**
     * This method Reads the given text file and counts each word’s occurrence, excluding stop words
     * and inserts them into the table
     * @param file
     * @throws FileNotFoundException
     */
    public void parseFile(File file) throws FileNotFoundException,IOException {
        similarTitles = new HashMap<String,Double>(300000,0.75f);
        Scanner b = new Scanner(file);
        while (b.hasNext()) {

            String word = b.next();
            word = word.replaceAll("[^a-zA-Z]", "");
            word = word.toLowerCase().trim();



                boolean w = true;
                for (int j = 0; j < stopWords.size(); j++) {
                    if (stopWords.get(j).equals(word)) {
                        w = false;
                    }
                }
                if (w && !word.isEmpty()) {
                    wordCount++;
                    if (containsKey(word)) {
                      replace(word, get(word)+ 1);
                    } else put(word, 1);

                }
            }

        }



    /**
     * This method returns a Set containing all of the words in this Passage
     * @return
     *  a Set containing all the words
     */
    public Set<String> getWords(){
        return this.keySet();
    }

    /**
     * This method returns the specified word's frequency in the text
     * @param word
     * @return
     */
    public double getWordFrequency(String word){
        return get(word)/getWordCount();
    }

    /**
     * Calculates the similarity between two Passage objects using the formula above and modifies their similarTitles accordingly
     * @param passage1
     * the first passage
     * @param passage2
     * the second passage
     * @return
     * the similarity between 2 passages
     */
    public static double cosineSimilarity(Passage passage1, Passage passage2){
        double cosa;
        double up=0;

        for (Map.Entry mapElement : passage1.entrySet()) {
            String key = (String)mapElement.getKey();
           if(passage2.containsKey(key)){
              up = up + passage1.get(key) *passage2.get(key);
           }
        }


        double down1 = 0;
        for(double u: passage1.values()){
            down1+= (u*u);
        }
        down1=Math.sqrt(down1);

        double down2 = 0;
        for(double u: passage2.values()){
            down2+= (u*u);

        }
        down2=Math.sqrt(down2);

        cosa=up/(down1*down2);

        return cosa;

    }

    /**
     * returns string of similar titles and their percentage similarity
     * @return
     * a string representation of the similar strings
     */
    public String toString() {
        String a="";
        a=a+String.format("%-30s",getTitle());
        int count=0;
        for (Map.Entry<String, Double> entry : getSimilarTitles().entrySet()) {
            String key = entry.getKey();
            Double value = entry.getValue();
            String thing;
            if (count % 2 == 0) {
                thing = "|" + key + "(" + value*100+ "%),";
                a=a+thing;
                count++;
            } else {
                thing = key + "(" + value*100  + "%)\n";
                thing =thing+ String.format("%-30s","");
                a=a+thing;
                count++;
            }
        }
            return a;
        }

}

