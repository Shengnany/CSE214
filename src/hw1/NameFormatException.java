package hw1;
/**
 * This class represents an exception that name(college or the applicant's name) format is wrong.
 *
 * @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #1
 *  CSE 214, Rec 04
 */
public class NameFormatException extends Exception {
    NameFormatException(){
        super("College or Name format is wrong");
    }
}
