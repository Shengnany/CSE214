import java.io.Serializable;

/**
 * Create a User class that contains the name of the user (String), indexPos (int), and static variable userCount (int).
 * Provide appropriate getters and setters.
 * Every time a new User object is created (the constructor is invoked),
 * assign indexPos of the object to be the userCount, and then increment userCount.
 */
public class User implements Serializable {
    String user;
    int indexPos;
    static int userCount=0;
    int x;

    public User(String name,int pos){
        this.user=name;
        indexPos=pos;
       userCount++;
    }
    /**
     * This method sets indexPos
     * @param indexPos
     * indexPos to be set
     */
    public void setIndexPos(int indexPos) {
        this.indexPos = indexPos;
    }

    /**
     * This method sets user
     * @param user
     *  user to be set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * This method sets userCount
     * @param userCount
     * userCount to be set
     */
    public static void setUserCount(int userCount) {
        User.userCount = userCount;
    }

    /**
     * This method returns user
     * @return
     * user
     */
    public String getUser(){
        return user;
    }

    /**
     * This method returns indexPos
     * @return
     * indexPos
     */
    public int getIndexPos(){
        return indexPos;
    }

    /**
     * This method returns indexPos
     * @return
     * indexPos
     */
    public static int getUserCount(){
        return userCount;
    }

    @Override
    public String toString() {
        return getUser();
    }
}
