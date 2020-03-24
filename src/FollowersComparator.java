import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class FollowersComparator implements Comparator, Serializable {
    ArrayList<Integer> users= new ArrayList<>();
    public FollowersComparator(boolean[][] connections){
        for(int j=0;j<connections.length;j++){
            for(int i=0;i<connections.length;i++){
                if(connections[i][j])
                   users.set(j,users.get(j));
            }
        }
    }
    public int compare(Object o1,Object o2){
        User u1 = (User)o1;
        User u2 = (User)o2;
        int a=users.get(u1.getIndexPos());
        int b=users.get(u2.getIndexPos());
        if(a>b) return 1;
        else if(a<b) return -1;
        else return 0;
    }
}

