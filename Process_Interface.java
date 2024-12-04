import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Process_Interface extends Remote {
    void requestCriticalSection() throws RemoteException, MalformedURLException, NotBoundException, InterruptedException;
    void releaseCriticalSection() throws RemoteException, MalformedURLException, NotBoundException;
    int getSequenceNumber() throws RemoteException;
    void receiveToken() throws RemoteException;
}