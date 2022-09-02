import SIMZ.Simz;
import user.User.User;

public class client {
    public static void main(String[] args){
        Simz simz = new Simz();

        User user = simz.createUser();
        user.introducingUserself();
    
    }
}
