import java.rmi.Naming;

public class Client {
    public static void main(String[] args) {
        try {
            TokenManager_Interface manager = (TokenManager_Interface) Naming.lookup("rmi://localhost/TokenManager");
            Process_Interface process = new Process(manager, 1);
            Naming.rebind("Process1", process);

            process.requestCriticalSection();
            Thread.sleep(1000);
            process.releaseCriticalSection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
