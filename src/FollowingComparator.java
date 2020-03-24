import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class FollowingComparator implements Comparator, Serializable {
    ArrayList<Integer> users= new ArrayList<>();
    public FollowingComparator(boolean[][] connections){
        for(int i=0;i<connections.length;i++){
            for(int j=0;j<connections.length;j++){
                if(connections[i][j])
                    users.set(i,users.get(i));
            }
        }
    }
    public int compare(Object o1,Object o2){
        User u1 = (User)o1;
        User u2 = (User)o2;
        int a=users.get(u1.getIndexPos());
        int b=users.get(u2.getIndexPos());
        return Integer.compare(a, b);
    }
}
