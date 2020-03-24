package hw1;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class represents a menu driven application that first creates an empty HiringTable object and then prompts the user for a command.
 * Once a command has been chosen, the program ask the user for additional information if necessary to perform the operation.
 *
 * @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #1
 *  CSE 214, Rec 04
 */
public class HiringSystem {
    public static void main(String[] args) {
        HiringTable table = new HiringTable();
        Scanner input = new Scanner(System.in);
        String choice;
        HiringTable backup = new HiringTable();
        do {
            System.out.print("Please enter a command: ");
            choice = input.nextLine();
            switch (choice) {
                case "A":

                    String[] company = new String[HiringTable.MAX_COMPANIES];
                    String[] skills = new String[HiringTable.MAX_SKILLS];
                    System.out.print("Enter Applicant Name: ");
                    String name = input.nextLine();
                    System.out.print("Enter Applicant GPA: ");
                    String gpas;
                    double GPA;

                     //prompts the user to enter a non-empty gpa
                    while (true) {
                        try {
                            gpas = input.nextLine();
                            if (gpas.isEmpty()) {
                                throw new InputMismatchException();
                            }
                            else{
                                GPA = Double.valueOf(gpas);
                                break;
                            }
                        } catch (InputMismatchException ex) {
                            System.out.print("Enter GPA again: ");
                        }
                          catch (NumberFormatException ex){
                              System.out.print("Enter GPA again: ");
                          }
                    }
                    System.out.print("Enter Applicant College: ");
                    String college = input.nextLine();
                    int companyNum = 0;
                    int skillNum = 0;

                    // prompts the user to enter the company names and then store them in an array
                    for (int i = 0; i < HiringTable.MAX_COMPANIES; i++) {
                        System.out.print("Enter up to " + (HiringTable.MAX_COMPANIES - i) + " Companies: ");
                        String x = input.nextLine();
                        if (!x.isEmpty()) {
                            company[i] = x;
                            companyNum = companyNum + 1;
                        } else

                            break;
                    }

                    // prompts the user to enter the skill names and then store them in an array
                    for (int i = 0; i < HiringTable.MAX_SKILLS; i++) {
                        System.out.print("Enter up to " + (HiringTable.MAX_SKILLS - i) + " Skills: ");
                        String x = input.nextLine();
                        if (!x.isEmpty()) {
                            skills[i] = x;
                            skillNum = skillNum + 1;
                        } else
                            break;
                    }

                    // trim the company name array to its actual size
                    if (company.length != companyNum) {
                        String[] trimArray1 = new String[companyNum];
                        for (int i = 0; i < companyNum; i++) {
                            trimArray1[i] = company[i];
                        }
                        company = trimArray1;
                    }

                    // trim the skill name array to its actual size
                    if (skills.length != skillNum) {
                        String[] trimArray2 = new String[skillNum];
                        for (int i = 0; i < skillNum; i++) {
                            trimArray2[i] = skills[i];
                        }
                        skills = trimArray2;
                    }


                    Applicant applicant = new Applicant(company, name, GPA, college, skills);
                    // determines whether or not to add the applicant
                    if(applicant.getCondition())
                        table.addApplicant(applicant);
                    break;

                case "R":
                    System.out.print("Enter applicant name: ");
                    name = input.nextLine();
                    table.removeApplicant(name);
                    break;

                case "G":
                    System.out.print("Enter Applicant Name: ");
                    name = input.nextLine();
                    Applicant aim = table.getApplicant(name);
                    if(aim != null){
                        System.out.println("Applicant Name: " + aim.getApplicantName());
                        System.out.println("Applicant Applying From: " + aim.getCompanyName()[aim.getCompanyName().length - 1]);
                        System.out.println("Applicant GPA:  " + aim.getApplicantGPA());
                        System.out.println("Applicant College: " + aim.getApplicantCollege());
                        String[] applicantSkills = aim.getApplicantSKills();
                        System.out.println("Applicant Skills: " + Arrays.toString(applicantSkills));
                    }
                    else
                        System.out.println("Applicant is not find");

                    break;

                case "P":
                    table.printApplicantTable();
                    break;

                case "RS":
                    double min = 0;
                    System.out.print("Enter a company to filter: ");
                    String companyName = input.nextLine();
                    System.out.print("Enter a skill to filter for: ");
                    String skillName = input.nextLine();
                    System.out.print("Enter a college to filter for: ");
                    String collegeName = input.nextLine();
                    System.out.print("Enter the minimum GPA to filter for:");
                    String minGPA = input.nextLine();
                    // handle the situation when the user enters nothing
                    if (minGPA.isEmpty())
                        min = 0;
                    else min = Double.valueOf(minGPA);
                    if (companyName.isEmpty())
                        companyName = "no";
                    if (skillName.isEmpty())
                        skillName = "no";
                    if (collegeName.isEmpty())
                        collegeName = "no";

                    table.refineSearch(table, companyName, skillName, collegeName, min);
                    break;

                case "S":
                    System.out.println("There are " + table.size() + "  applicants in the hiring system. ");
                    break;

                case "B":
                    backup = (HiringTable) (table.clone());
                    System.out.println("Successfully created backup.");
                    break;

                case "CB":
                    boolean compare = table.equals(backup);
                    if (compare)
                        System.out.println("Current list is the same as the backup copy. ");
                    else
                        System.out.println("Current list is not the same as the backup copy. ");
                    break;

                case "RB":
                    table = (HiringTable) backup.clone();
                    System.out.println("Successfully reverted to the backup.");
                    break;

                default:
                    break;

            }
        }
        while (!choice.equals("Q"));
        System.exit(0);
    }
}

