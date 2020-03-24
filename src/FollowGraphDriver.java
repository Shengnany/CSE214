import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FollowGraphDriver implements Serializable {
    public static void main(String[] args) throws IOException {

        Scanner input = new Scanner(System.in);
        String choice = "";
        FollowGraph graph = new FollowGraph();
        try {
            FileInputStream file = new FileInputStream("library.obj");
            ObjectInputStream inStream = new ObjectInputStream(file);
            graph = (FollowGraph) inStream.readObject();
            file.close();
        } catch (IOException ex) {
            System.out.println("start a new one");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error occurred");
        }
            while (!choice.equals("Q")) {
                System.out.println("    (U) Add User\n" +
                        "    (C) Add Connection\n" +
                        "    (AU) Load all Users\n" +
                        "    (AC) Load all Connections\n" +
                        "    (P) Print all Users\n" +
                        "    (L) Print all Loops\n" +
                        "    (RU) Remove User\n" +
                        "    (RC) Remove Connection\n" +
                        "    (SP) Find Shortest Path\n" +
                        "    (AP) Find All Paths\n" +
                        "    (Q) Quit");
                System.out.print("Enter a selection: ");
                choice = input.nextLine();
                choice = choice.trim().toUpperCase();

                if (choice.equals("AU")) {
                    System.out.print("Enter the file name: ");
                    String fileName = input.nextLine();
                    graph.loadAllUsers(fileName);
                }
                if (choice.equals("AC")) {
                    System.out.print("Enter the file name: ");
                    String fileName = input.nextLine();
                    graph.loadAllConnections(fileName);
                }
                if (choice.equals("U")) {
                    System.out.print("Please enter the name of the user: ");
                    String name = input.nextLine().trim();
                    graph.addUser(name);
                }
                if (choice.equals("C")) {
                    System.out.print("Please enter the source of the connection to add: ");
                    String source = input.nextLine().trim();
                    System.out.print("Please enter the dest of the connection to add: ");
                    String dest = input.nextLine().trim();
                    graph.addConnection(source, dest);
                }
                if (choice.equals("P")) {
                    String p = "";
                    while (!p.equals("Q")) {
                        System.out.println("    (SA) Sort Users by Name\n" +
                                "    (SB) Sort Users by Number of Followers\n" +
                                "    (SC) Sort Users by Number of Following\n" +
                                "    (Q) Quit ");
                        System.out.print("Enter a selection: ");
                        p = input.nextLine().trim().toUpperCase();
                        if (p.equals("SA")) {
                            NameComparator nameComparator = new NameComparator();
                            graph.printAllUsers(nameComparator);
                        }
                        if (p.equals("SB")) {
                            FollowersComparator ferCom = new FollowersComparator(graph.getConnections());
                            graph.printAllUsers(ferCom);
                        }
                        if (p.equals("SC")) {
                            FollowingComparator feeCom = new FollowingComparator(graph.getConnections());
                            graph.printAllUsers(feeCom);
                        }
                        if (p.equals("Q")) {
                            break;
                        }
                    }
                }

                if (choice.equals("RU")) {
                    System.out.print("Please enter the user to remove: ");
                    String name = input.nextLine();
                    name = name.trim();
                    graph.removeUser(name);
                }
                if (choice.equals("RC")) {
                    System.out.print("Please enter the source of the connection to remove: ");
                    String source = input.nextLine();
                    System.out.print("Please enter the dest of the connection to remove: ");
                    String dest = input.nextLine();
                    graph.removeConnections(source, dest);
                }
                if (choice.equals("SP")) {
                    System.out.print("Please enter the desired source: ");
                    String source = input.nextLine();
                    System.out.print("Please enter the desired destination: ");
                    String dest = input.nextLine();
                    System.out.println(graph.shortestPath(source, dest));
                }
                if (choice.equals("L")) {
                    ArrayList<String> loops = graph.findAllLoops();
                    for (int i = 0; i < loops.size(); i++) {
                        System.out.println(loops.get(i));
                    }
                }
                if (choice.equals("AP")) {
                    System.out.print("Please enter the desired source: ");
                    String source = input.nextLine();
                    System.out.print("Please enter the desired destination: ");
                    String dest = input.nextLine();
                    ArrayList<String> paths = graph.allPaths(source, dest);
                    for (int i = 0; i < paths.size(); i++) {
                        System.out.println(paths.get(i));
                    }
                }
                try {
                    if (choice.equals("Q")) {
                        FileOutputStream file = new FileOutputStream("library.obj");
                        ObjectOutputStream outStream = new ObjectOutputStream(file);
                        outStream.writeObject(graph);
                        outStream.close();
                        file.close();
                    }
                }
               catch (IOException ex) {
                    System.out.println("IOException is caught");
                }

            }
    }
}