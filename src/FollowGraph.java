import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;

/**
 *This FollowGraph class that contains an adjacency matrix for the users. It represents a follow relationship betweenu sers
 * @author Shengnan You
 *  e-mail: shengnan.you@stonybrook.edu
 *  SBU ID:112361646
 *  HW #7
 *  CSE 214, Rec 04
 */

public class FollowGraph implements Serializable {

    private ArrayList<User> users = new ArrayList<>();
    private static final int MAX_USERS=100;
    private boolean[][] connections=new boolean[0][0];
    int y;
    /**
     * Constructor for FollowGraph object.Initializes all declared variables.
     */
    public FollowGraph(){

    }


    /**
     * This method sets the users
     * @param users
     * users to be set
     */
    public void setUsers(ArrayList<User> users){
        this.users = users;
    }

    /**
     * This method sets the users
     * @param connections
     * connections to be set
     */
    public void setConnections(boolean[][] connections){
        this.connections=connections;
    }
    /**
     * This method returns users
     * @return
     * users
     */
    public ArrayList<User> getUsers(){
        return this.users;
    }

    /**
     * This method returns connections
     * @return
     * connections
     */
    public boolean[][] getConnections(){
        return this.connections;
    }

    /**
     * adds a new user if not already exist
     * @param userName
     * the user name to be added
     */
    public void addUser(String userName){
        if(userPos(userName)!=-1){
            return;}
        User u = new User(userName,users.size());
        User.userCount=y;
        users.add(u);
        y++;
        boolean[][] newCon =new boolean[users.size()][users.size()]  ;
        for(int i=0;i<connections.length;i++){
            for(int j=0;j<connections.length;j++)
            newCon[i][j] = connections[i][j];
        }
        connections = newCon;
    }

    /**
     * add a connection if appropriate users are given.
     * @param userFrom
     * follower
     * @param userTo
     * followee
     */
    public void addConnection(String userFrom, String userTo){
        if(connections.length-1>=userPos(userFrom)&&connections.length-1>=userPos(userTo)) {
            int a = userPos(userFrom);
            int b = userPos(userTo);
            if (a == -1 || b == -1)
                return;
            else connections[a][b] = true;
        }

    }

    /**
     * remove a connection if appropriate users are given.
     * @param userFrom
     * follower
     * @param userTo
     * followee
     */
    public void removeConnections(String userFrom, String userTo ){
        if(connections.length>userPos(userFrom)&&connections.length>userPos(userTo)){
        int a=userPos(userFrom);
        int b=userPos(userTo);
        connections[a][b]=false;
        }
    }

    /**
     * This is a method used to remove a user
     * @param userName
     * the user to be removed
     */
    public void removeUser(String userName){
        if(userPos(userName)<users.size()&&userPos(userName)>-1) {
            int delete = userPos(userName);
            users.remove(delete);

            User.userCount--;
            y--;
            for(int i=0;i<users.size();i++){
                users.get(i).setIndexPos(i);
            }
            boolean[][] newConnections = new boolean[users.size()][users.size()];
            int i2=0;
            for (int i = 0; i < connections.length; i++) {
                if (i == delete)
                    continue;
                int j2=0;
                for (int j = 0; j < connections.length; j++) {
                    if (j == delete)
                        continue;
                    newConnections[i2][j2]=connections[i][j] ;
                    j2++;
                }
                i2++;
            }
            connections=newConnections;
        }
        else
            System.out.println("User does not exist");
    }

    /**
     *  if appropriate users are given, find the shortest path between the users
     *  and return a String representation of the path
     *
     * @param userFrom
     * follower
     * @param userTo
     * followee
     * @return
     */
    public String shortestPath(String userFrom, String userTo){
        if(userPos(userFrom)==-1||userPos(userTo)==-1) {
            System.out.println("The user names are wrong");
            return "";
        }
        // initialize
        int l =users.size();
        double[][] dist= new double[l][l];
        for(int i=0;i<dist.length;i++){
            for(int j=0;j<dist.length;j++){
                dist[i][j]= Double.POSITIVE_INFINITY;
            }
        }
        int[][] next =new int[l][l];
        for(int i =0;i<connections.length;i++){
            for(int j=0;j<connections[i].length;j++){
                dist[i][j] = 1;
                next[i][j] = j;
            }
        }
       for(int i=0;i<l;i++){
            dist[i][i] = 0;
            next[i][i] = i;
       }
       // implementation
       for(int k = 0;k<l;k++){
           for(int i=0;i<l;i++){
               for(int j=0;j<l;j++){
                   if(dist[i][j]>dist[i][k]+dist[k][j]) {
                       dist[i][j] = dist[i][k] + dist[k][j];
                       next[i][j] = next[i][k];
                   }
               }
           }
       }
       String path =userFrom;
       if(next[userPos(userFrom)][userPos(userTo)]==0)
           return path;
       int a =userPos(userFrom);
       int b = userPos(userTo);
       while(a!=b){
           a = next[a][b];
           path=path+" -> "+users.get(a);
       }
       return path;
    }

    /**
     * This is a method used to find all paths in a graph given the source and destination
     * @param userFrom
     * the source
     * @param userTo
     * the destination
     * @return
     * An ArrayList<String> containing all the paths
     */
    public ArrayList<String> allPaths(String userFrom,String userTo){
        ArrayList<String> path = new ArrayList<>();
        if(userPos(userFrom)==-1||userPos(userTo)==-1) {
            System.out.println("There  is no path");
            return path;
        }
        boolean[] marked = new boolean[users.size()];
        path=pathHelper(userFrom,userTo,marked,path,userFrom);
        if(path.size()==0) System.out.println("There  is no path");
        return path;
    }

    /**
     * This is a helper method to find all paths
     * @param userFrom
     * the source user in each search
     * @param userTo
     * the target user in each search
     * @param marked
     * a boolean array used to make record of the searching
     * @param pathList
     * an ArrayList which contains all the path
     * @param path
     * a record for each path
     * @return
     * an ArrayList which contains all the path
     */
    public  ArrayList<String>  pathHelper(String userFrom, String userTo, boolean[]marked, ArrayList<String>pathList,String path){
        marked[userPos(userFrom)]=true;
        if (userFrom.equals(userTo))
        {
            pathList.add(path);
            marked[userPos(userFrom)]=false;
            return pathList;
        }
        for (Integer i : getNeightbours(userPos(userFrom)))
        {
            if (!marked[i])
            {
                String temp =path;
                path=path+" -> "+users.get(i).getUser();
                pathList = pathHelper(users.get(i).getUser(),userTo, marked, pathList,path);
                path=temp;
            }

        }
        marked[userPos(userFrom)]=false;

        return pathList;
    }

    /**
     * This is a method used to get all followings of a particular user
     * @param i
     * the index of the user
     * @return
     * all neighbours of a particular user
     */
    public ArrayList<Integer> getNeightbours(int i){
        ArrayList<Integer> neighbours=new ArrayList<>();
        for(int j=0;j<connections[i].length;j++){
            if(connections[i][j])
                neighbours.add(j);
        }
        return neighbours;
    }

    /**
     * This is a method used to print all users in the order of a given comparator
     * @param com
     * used to compare all the users
     */
    public void printAllUsers(Comparator com) {
        ArrayList<User> compare = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            compare.add(users.get(i));
        }
        for (int i = 0; i < compare.size() - 1; i++)
            for (int j = 0; j < compare.size() - i - 1; j++)
                if (com.compare(compare.get(j), compare.get(j + 1)) < 0) {
                    User temp = compare.get(j);
                    compare.set(j, compare.get(j + 1));
                    compare.set(j + 1, temp);
                }
        System.out.println("Users:");
        System.out.println("User Name               Number of Followers      Number Following");
        for (int i = 0; i < compare.size(); i++) {
            System.out.printf("%-30s%-25s%-35s", compare.get(i).getUser(), numOfFollowers(compare.get(i).getUser()), numOfFollowings(compare.get(i).getUser()));
            System.out.println();
        }

    }

    /**
     * This is a method used to print all the followers
     * @param userName
     * the user's name
     */
    public void printAllFollowers(String userName){
        String followers = "";
        int j=userPos(userName);
        for(int i=0;i<connections.length;i++){
            if(connections[i][j])
                followers+=users.get(i)+",";
        }
        System.out.println(followers);
    }

    /**
     * This is a method used to get the number of followers
     * @param userName
     * user's name
     * @return
     * the number of followers
     */
    public int numOfFollowers(String userName){
        int j=userPos(userName);
        int num=0;
        for(int i=0;i<connections.length;i++){
            if(connections[i][j])
                num++;
        }
        return num;
    }

    /**
     * This is a method used to print the number of followings
     * @param userName
     * the user's name
     */
    public void printAllFollowings(String userName){
        String followees = "";
        int i=userPos(userName);
        for(int j=0;j<connections.length;j++){
            if(connections[i][j])
                followees+=users.get(j)+",";
        }
        System.out.println(followees);
    }

    /**
     * This is a method used to get the number of followers
     * @param userName
     * the user's name
     * @return
     *the number of followings
     */
    public int numOfFollowings(String userName){
        int i=userPos(userName);
        int num=0;
        for(int j=0;j<connections.length;j++){
            if(connections[i][j])
                num++;
        }
        return num;
    }

    /**
     * This is a method used to find all loops
     * @return
     * an ArrayList<String> containing all the loops
     */
    public ArrayList<String> findAllLoops(){
            // do DFS search for each node
            Set<ArrayList<Integer>> cycles = new HashSet<>();
            for (int i = 0; i < users.size(); i++) {
                boolean[] marked = new boolean[users.size()];
                ArrayList<Integer> path = new ArrayList<>();
                loopHelper(users.get(i).getUser(), users.get(i).getUser(), marked, cycles, path);
            }
            if (cycles.size() == 0) {
                System.out.println("There is no loop");
            }

            //turn cycles into ArrayList and change to string
            ArrayList<ArrayList<Integer>> pathList = new ArrayList<>(cycles);
            ArrayList<String> loops = new ArrayList<>();
            for (int i = 0; i < pathList.size(); i++) {
                String x = "";
                for (int j = 0; j < pathList.get(i).size(); j++) {
                    if(j==0) x = x + "" + users.get(pathList.get(i).get(j)).getUser();
                    else x=x + " -> " + users.get(pathList.get(i).get(j)).getUser();

                }
                x = x + " -> " + users.get(pathList.get(i).get(0)).getUser();
                loops.add(x);
            }
            return loops;


    }

    /**
     * This is a helper method to findAllLoops()
     * @param userFrom
     * the user to be searched next
     * @param start
     * the user started at each search
     * @param marked
     * a boolean array used to record user visited
     * @param cycles
     * a HashSet containing all the loops
     * @param path
     * a list keeping track of the search
     * @return
     * a set containg loops writing in their index
     */
    public  Set<ArrayList<Integer>> loopHelper(String userFrom,String start,boolean[]marked,Set<ArrayList<Integer>> cycles,ArrayList<Integer> path){
        // if next vertice is start and it has been visited,than loop
        //dfs
        marked[userPos(userFrom)]=true;
        for (Integer i : getNeightbours(userPos(userFrom)))
        {
            if (!marked[i])
            {
                ArrayList<Integer> temp=new ArrayList<>(path);
                path.add(i);
                loopHelper(users.get(i).getUser(),start, marked, cycles, path);
                path=temp;
            }
            else if((i==(userPos(start))&&marked[i]))
            {
                path.add(i);
            ArrayList<Integer> normalized = new ArrayList<>(path);
            changeOrder(normalized);
            cycles.add(normalized);
            return  cycles;
            }
        }
        // clear record after a search reach its deepest(no neighbours)
        marked[userPos(userFrom)]=false;

        return cycles;
    }

    /**
     * This is a method used to set the loop in a order such that each loop has the smallest index in the front and remains unchanged
     * @param old
     * the former loop
     */
    public void changeOrder(ArrayList<Integer> old){
        int min=old.get(0);
        for(int i=0;i<old.size();i++){
            if(min>old.get(i)) min=old.get(i);
        }
        int c =  old.size()-1;
        while (old.get(0)!=min){
            old.add(0, old.get(old.size()-1));
            old.remove(old.size()-1);
        }

    }


    /**
     * This is a method used to parse a file and load all users
     * @param fileName
     * the file'name
     * @throws FileNotFoundException
     */
    public void loadAllUsers(String fileName) throws FileNotFoundException {
        File users = new File(fileName);
        Scanner u = new Scanner(users);
        while(u.hasNextLine()){
            String x=u.nextLine().trim();
            if(x.isEmpty()) continue;
            addUser(x);
        }
    }

    /**
     * This is a method used to parse a file and load all connections
     * @param fileName
     * the file'name
     * @throws FileNotFoundException
     */
    public void loadAllConnections(String fileName) throws FileNotFoundException {
        File users = new File(fileName);
        Scanner u = new Scanner(users);
        while(u.hasNext()){
            String x=u.nextLine();
            if(x.isEmpty())
                continue;
            String[] name = x.trim().split(",");
            for(int i=0;i<name.length;i++){
                name[i]=name[i].trim();
            }
            if(userPos(name[0])==-1||userPos(name[1])==-1) {
                continue;
            }
            addConnection(name[0],name[1]);
        }
    }

    /**
     * This method returns the postion of the user
     * @param userName
     * the user's name
     * @return
     * user's position
     */
    public int userPos(String userName){
        for(int i=0;i<users.size();i++){
            if(users.get(i).getUser().equals(userName))
                return i;
        }
        return -1;
    }


}
