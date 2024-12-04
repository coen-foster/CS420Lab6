import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            TokenManager_Interface manager = new TokenManager();
            Naming.rebind("TokenManager", manager);
            System.out.println("TokenManager is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
