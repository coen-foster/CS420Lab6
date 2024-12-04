import java.rmi.Naming;

public class Client {
    public static void main(String[] args) {
        try {
            TokenManager_Interface manager = (TokenManager_Interface) Naming.lookup("rmi://localhost/TokenManager");
            Process_Interface process1 = new Process(manager, 1);
            Naming.rebind("Process1", process1);
            Process_Interface process2 = new Process(manager, 2);
            Naming.rebind("Process2", process2);

            process1.requestCriticalSection();
            Thread.sleep(1000);
            process1.releaseCriticalSection();

            process2.requestCriticalSection();
            Thread.sleep(1000);
            process2.releaseCriticalSection();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
