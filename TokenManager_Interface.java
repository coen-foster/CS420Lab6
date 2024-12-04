import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TokenManager_Interface extends Remote {
    void requestEntry(int processId, int sequenceNumber) throws RemoteException, MalformedURLException, NotBoundException;
    void releaseToken(int processId) throws RemoteException, MalformedURLException, NotBoundException;
}
