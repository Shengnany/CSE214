package hw1;
/**
 * This class implements an abstract data type for a list of Applicants supporting some common operations on such lists.
 *
 * @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #1
 *  CSE 214, Rec 04
 */
public class HiringTable implements Cloneable{
    final static int MAX_SKILLS=3;
    final static int MAX_COMPANIES=3;
    final static int MAX_APPLICANTS=50;

    private Applicant[] data = new Applicant[0];

    /**
     * This is a method that returns the Applicants in the HiringTable obeject
     *
     * @return
     * Applicants
     */
    public Applicant[] getData(){
        return data;
    }

    /**
     * This is a Constructor that constructs an instance of the HiringTable with no Applicant objects in it.
     */
    public HiringTable(){
    }

    /**
     * This class determines the number of Applicant objects currently in the list.
     *
     * @return
     * The number of Applicants objects in this HiringTable.
     */
    public int size(){
       return data.length;
    }

    /**
     * This method adds the new Applicant at the end of the list.
     *
     * @param newApplicant
     * the new Applicant object to add to the list
     */
    public void addApplicant(Applicant newApplicant) {
        try {
            if(data.length<=MAX_APPLICANTS) {
                Applicant[] newData = new Applicant[data.length + 1];
                if (data.length > 0) {
                    for (int i = 0; i < data.length; i++) {
                        newData[i] = data[i];
                    }
                    newData[data.length]=newApplicant;
                }
                else
                    newData[0] =newApplicant;
                    data = newData;
                System.out.println("Appicant " + newApplicant.getApplicantName() + " has been successfully added to the hiring system.");
                }

            else throw new FullTableException();
        }
        catch (FullTableException ex){
            System.out.println("Table is full");
        }

    }

    /**
     * This method removes the Applicant with the name given from the list.
     *
     * @param name
     * the name of the applicant that must be removed
     *
     * @exception ApplicantNotFoundException
     *  indicates that the applicant is not found
     *
     */
    public void removeApplicant(String name){
        try {
           boolean exist=false;
           if(data.length==0){
               System.out.println("No applicant can be removed");
               return;
           }
            Applicant[] newData = new Applicant[data.length-1];
            for(int i=0; i<data.length;i++) {
                if(data[i].getApplicantName().equals(name)){
                    exist=true;
                    continue;
                }
            }
            if(exist) {
                data=newData;
                System.out.println("Applicant "+name+" has been successfully removed from the hiring system. ");
            }
            else
                throw new ApplicantNotFoundException();
        }
        catch (ApplicantNotFoundException ex){
            System.out.println("Applicant not found");
        }

    }

    /**
     * This method gives the applicant with the given name
     * @param name
     * name of the Applicant to retrieve.
     *
     * @return
     * The Applicant with the corresponding name.
     */
    public Applicant getApplicant(String name){
        try{ Applicant result= null;
            for(int i=0; i<data.length; i++){
        if(data[i].getApplicantName().equals(name)){
            result = data[i];
            }
        }
         if (result!= null) return result;
            else throw new ApplicantNotFoundException();
      }
        catch (ApplicantNotFoundException ex){
            System.out.println("Applicant not found");
            return null;
        }
    }

    /**
     * This method prints all the Applicant objects that match the requested criteria.
     *
     * @param table
     * The list of applicants to search in
     * @param company
     * The company must be in the Applicant's application
     * @param skill
     * The skill that must be in the Applicant's application
     * @param college
     * The college that must be in the Applicant's application
     * @param GPA
     * The minimum GPA that must be in the Applicant's application
     */
    public static void refineSearch(HiringTable table, String company, String skill, String college, double GPA) {
        Applicant[] target = table.getData();
        final String A = "Company Name";
        final String B = "Applicant";
        final String C = "GPA";
        final String D = "College";
        final String E = "Skills";
        System.out.printf("%-26s%-26s%-26s%-26s%-26s", A, B, C, D, E);
        System.out.println();
        System.out.println("------------------------------------------------------------------------"
                +"------------------------------------------");
        for (int i = 0; i < MAX_APPLICANTS; i++) {
            boolean no1 = true;
            boolean no2 = true;
            if (i < table.getData().length) {
                if (!company.equals("no")) {
                    for (int j = 0; j < target[i].getCompanyName().length; j++) {
                        if (target[i].getCompanyName()[j].equals(company))
                           break;
                        if(j == target[i].getCompanyName().length-1 &&
                                (!target[i].getCompanyName()[j].equals(company))){
                            no1 = false;
                        }
                    }
                    if (!no1) continue;
                }

                if (!skill.equals("no")) {
                    for (int j = 0; j < target[i].getApplicantSKills().length; j++) {
                        if (target[i].getApplicantSKills()[j].equals(skill))
                            break;
                        if(j == target[i].getApplicantSKills().length-1 &&
                                !(target[i].getApplicantSKills()[j].equals(skill))){
                            no2 = false;
                        }
                    }
                    if (!no2) continue;
                }

                if (!college.equals("no") && !(target[i].getApplicantCollege().equals(college)))
                    continue;

                if (GPA != 0) {
                    if (target[i].getApplicantGPA() <= GPA)
                        continue;
                }

                System.out.println(target[i].toString());

            }
            else break;
        }
    }

    /**
     * This method creates a copy of this HiringTable.
     *
     * @return
     * a copy of the Hiringtable
     */
    public Object clone(){
        try{
            HiringTable newTable = (HiringTable)super.clone();
            Applicant[] newMember = new Applicant[getData().length];

            for(int i=0; i<newTable.getData().length;i++) {
                 newMember[i]=(Applicant)(data[i].clone());
            }
            newTable.data = newMember;
            return newTable;
        }
        catch(CloneNotSupportedException ex){
            return null;
        }
    }

    /**
     * This method compares whether two HiringTable is equal
     *
     * @param obj
     * The HiringTable object that is being compared to
     *
     * @return
     * true if they are equal, otherwise false
     */
    public boolean equals(Object obj){
        if (obj instanceof HiringTable) {
            HiringTable compared = (HiringTable) obj;
            int a = compared.getData().length;
            int b = getData().length;
            if (a != b)
                return false;
            else {
                for (int i = 0; i < a; i++) {
                    if (!data[i].equals(compared.getData()[i]))
                        return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * This method prints a neatly formatted table of each item in the list as shown in the sample output
     */
    public void printApplicantTable(){
        System.out.printf("%-26s%-26s%-26s%-26s%-26s","Company Name","Applicant","GPA","College","Skills");
        System.out.println();
        System.out.println("------------------------------------------------------------------------"
                +"------------------------------------------");
        for(int i=0;i<data.length;i++){
            System.out.println(data[i].toString());
        }

    }



}


