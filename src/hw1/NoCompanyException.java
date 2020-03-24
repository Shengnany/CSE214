package hw1;
/**
 * This class represents an exception that applicant has no company. .
 *
 * @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #1
 *  CSE 214, Rec 04
 */
public class NoCompanyException extends Exception{
    NoCompanyException(){
        super("No company. ");
    }
}

