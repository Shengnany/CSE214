package hw1;
import java.util.Arrays;

/**
 * This class represents a applicant with company name, applicant name, GPA, college, and skills
 *
 * @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #1
 *  CSE 214, Rec 04
 */
public class Applicant implements Cloneable{

    private String[] companyName;
    private String applicantName;
    private double applicantGPA;
    private String applicantCollege;
    private String[] applicantSkills;
    private boolean condition = true;

    /**
     * This method assigns Strings to the companyName array
     *
     * @param companyName
     * the array that stores the company names
     *
     * @exception NoCompanyException
     * indicates that the company's name is empty
     */
    public void setCompanyName(String[] companyName) {
        try{
            if(companyName.length==0)
                throw new NoCompanyException();
            this.companyName=companyName;
        }
        catch (NoCompanyException ex){
            System.out.println("The applicant has no company. ");
            condition = false;
        }

    }

    /**
     * This method assigns Strings to the skillsName array
     *
     * @param applicantSkills
     * the array that stores the skills names
     *
     * @exception NoSkillsException
     * indicates that the applicant's skill is empty
     */
    public void setApplicantSkills(String[] applicantSkills) {
        try{
            if(applicantSkills.length==0)
                throw new NoSkillsException();
            this.applicantSkills=applicantSkills;
        }
        catch (NoSkillsException ex){
            System.out.println("The applicant has no skill. ");
            condition = false;
        }

    }

    /**
     * This method sets the applicant's GPA
     *
     * @param applicantGPA
     * the double that stores applicant's GPA
     *
     * @exception NegativeGPAException
     * indicates that the applicant's GPA is wrong
     */
    public void setApplicantGPA(double applicantGPA) {
       try{
           if(applicantGPA>=0 && applicantGPA<=4)
               this.applicantGPA=applicantGPA;
           else throw new NegativeGPAException();
       }
       catch (NegativeGPAException ex){
           System.out.println("The applicant has a wrong GPA. ");
           condition = false;
       }
    }

    /**
     * This method sets the applicant's college
     *
     * @param applicantCollege
     * the String that stores the applicant's college name
     */
    public void setApplicantCollege(String applicantCollege) {
        try{
            if(applicantCollege.trim().isEmpty()||applicantCollege.matches(".*$%^&.*"))
                throw new NameFormatException();
            this.applicantCollege=applicantCollege;
        }
        catch(NameFormatException ex){
            System.out.println("College Name format is  wrong. ");
            condition=false;
        }
    }

    /**
     * This method sets the applicant's name
     *
     * @param applicantName
     */
    public void setApplicantName(String applicantName){
        try{
            if(applicantName.trim().isEmpty()||applicantName.matches(".*$%^&.*"))
                throw new NameFormatException();
            this.applicantName=applicantName;
        }
        catch(NameFormatException ex){
            System.out.println("Applicant Name format is  wrong. ");
            condition=false;
        }
    }
    /**
     * This method returns the company name
     *
     * @return
     * the array of company name
     */
    public String[] getCompanyName() {
        return companyName;
    }

    /**
     * This method returns the applicant's name
     *
     * @return
     * the applicant's name
     */
    public String getApplicantName() {
        return applicantName;
    }

    /**
     * This method returns the applicant's name
     *
     * @return
     * the applicant's name
     */
    public double getApplicantGPA() {
        return applicantGPA;
    }

    /**
     * This method returns the applicant's college
     *
     * @return
     * the applicant's college
     */
    public String getApplicantCollege() {
        return applicantCollege;
    }

    /**
     * This method returns the applicant's skills
     *
     * @return
     * the applicant's skills
     */
    public String[] getApplicantSKills() {
        return applicantSkills;
    }

    /**
     * This method determines whether or not an applicant could be instantiated
     *
     * @return
     *  true if all the requirements are met, otherwise false
     */
    public boolean getCondition(){
        return condition;
    }

    /**
     * This is a Constructor used to create a new Applicant object
     */
    public Applicant(){
    }

    /**
     * This is a Constructor used to create a new Applicant object
     *
     * @param companyName
     *  the name of the companies
     * @param applicantName
     *  the name of the applicant
     * @param applicantGPA
     *  the GPA of the applicantt
     * @param applicantCollege
     *  the college of the applicant
     * @param applicantSkills
     *  the name of the applicant's skills
     */
    public Applicant(String[] companyName,String applicantName,double applicantGPA,String applicantCollege,String[] applicantSkills){
        try{
            setCompanyName(companyName);
            setApplicantCollege(applicantCollege);
            setApplicantGPA(applicantGPA);
            setApplicantSkills(applicantSkills);
            setApplicantName(applicantName);
            if(!condition)
                throw new ApplicantNotAddedException();
        }
        catch(ApplicantNotAddedException ex){
            System.out.println("Applicant cannot be added. ");
        }

    }

    /**
     * This method returns a String representation of the Applicant object.
     *
     * @return
     * a string representation of the applicant
     */
    public String toString() {
        return String.format("%-26s%-26s%-26f%-26s%-26s", Arrays.toString(companyName),applicantName,applicantGPA,applicantCollege,Arrays.toString(applicantSkills));
    }

    /**
     *This method returns a copy of this Applicant object.
     *
     * @return
     * a copy of this Applicant object.
     *
     */
    public Object clone(){
        Applicant applicant;
        try{
            applicant = (Applicant)super.clone();
            applicant.companyName = companyName.clone();
            applicant.applicantSkills = applicantSkills.clone();
        }
        catch(CloneNotSupportedException ex){
            applicant=null;
        }
        return applicant;
    }

    /**
     * This method compares whether two applicant is equal
     *
     * @param obj
     * the Applicant that is being compared to
     *
     * @return
     * true if they are equal, otherwise false
     */
    public boolean equals(Object obj){

        boolean a=false;
        boolean b=false;
        boolean c=false;
        boolean d=true;
        boolean e=true;
        if(obj instanceof Applicant){
            Applicant applicant=(Applicant)obj;
         a=(this.getApplicantGPA()==applicant.getApplicantGPA());
         b=(this.getApplicantCollege().equals(applicant.getApplicantCollege()));
         c=(this.getApplicantName().equals(applicant.getApplicantName()));
            for (int i=0;i<companyName.length;i++) {
                if (!applicant.companyName[i].equals(companyName[i])) {
                    d=false;
                    break;
                }
            }
            for (int i=0;i<applicantSkills.length;i++) {
                if (!applicant.applicantSkills[i].equals(applicantSkills[i])) {
                    e=false;
                    break;
                }
            }

        }

           if(a&&b&&c&&d&&e)
               return true;
           else
               return false;
        }

}


