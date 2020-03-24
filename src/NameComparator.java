/**
 * This class create a comparator used to sort the users
 */

import java.io.Serializable;
import java.util.Comparator;

public class NameComparator implements Comparator, Serializable {
    public int compare(Object o1,Object o2){
        User u1 = (User)o1;
        User u2 = (User)o2;
        return (u1.getUser().trim().compareTo(u2.getUser().trim()));
    }
}
