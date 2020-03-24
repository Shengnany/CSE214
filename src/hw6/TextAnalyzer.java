/**
 *  The user should be prompted to enter the directory of a folder on startup—
 *  this is the directory that the program will iterate through the contents of and,
 *  after determining the relations between each Passage object
 *  (whether or not they have the same author and by what percentage),
 *  print a table showing how similar each text is to others.
 * @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #6
 *  CSE 214, Rec 04
 */
package hw6;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextAnalyzer {
    public static void main(String[] args) {
        ArrayList<String> stopWords = new ArrayList<>();
        try {
            System.out.println("Enter the directory of a folder of text files: ");
            Scanner input = new Scanner(System.in);
            String directoryPath = input.nextLine();
            File[] directoryOfFiles = new File(directoryPath).listFiles();
            System.out.println("Reading texts...");
            System.out.printf("%-30s","Text (title)");
            System.out.println("| Similarities (%)");
            ArrayList<Passage> passages = new ArrayList<>();

            for (File i : directoryOfFiles) {
                if (i.getName().equals("StopWords.txt")) {
                    Scanner s = new Scanner(i);

                    while (s.hasNextLine()) {
                        String a= s.nextLine();
                        stopWords.add(a.trim().toLowerCase());
                    }
                }
            }

            for (File i : directoryOfFiles) {
                if (!i.getName().equals("StopWords.txt") && !i.getName().equals(".DS_Store")) {
                    Passage p = new Passage(i.getName());
                    p.setStopWords(stopWords);
                    p.parseFile(i);
                    passages.add(p);
                }

            }

            for(int j=0;j<passages.size();j++) {
                for (int k = 0; k < passages.size(); k++) {
                    if(k==j) continue;
                    double a =Passage.cosineSimilarity(passages.get(j), passages.get(k));
                    passages.get(j).getSimilarTitles().put(passages.get(k).getTitle(),a);
                }
                System.out.println("-------------------------------------------------------------------------------");
                System.out.println(passages.get(j).toString());
            }


            System.out.println("Suspected Texts With Same Authors ");
            System.out.println("-------------------------------------------------------------------------------");
            for(int j=0;j<passages.size();j++) {
                for (int k = j+1; k < passages.size(); k++) {
                    double a=Passage.cosineSimilarity(passages.get(j),passages.get(k));
                    if(a>0.57&&a<0.79)
                        System.out.println("'" + passages.get(j).getTitle() + "'" + " and " + "'" +passages.get(k).getTitle()  + "'" + " may have the same author " + "("
                                + a*100 + "%)");
                }
            }

            System.out.println("Program terminating...");

        }
        catch (FileNotFoundException ex){
            System.out.println("File not found.");
        }
        catch (NullPointerException | IOException ex){
            System.out.println("File path not found.");
        }

    }
}

