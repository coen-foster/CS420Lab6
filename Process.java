import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Process extends UnicastRemoteObject implements Process_Interface {
    private static final long serialVersionUID = 1L;
	private int sequenceNumber = 0;
    private boolean hasToken = false;
    private final TokenManager_Interface tokenManager;
    private int processID;

    public Process(TokenManager_Interface tokenManager,int processID) throws RemoteException {
        super();
        this.tokenManager = tokenManager;
        this.processID = processID;
    }

    @Override
    public synchronized void requestCriticalSection() throws RemoteException, MalformedURLException, NotBoundException, InterruptedException {
        sequenceNumber++;
        System.out.println("Process " + getProcessId() + " Requesting critical section with sequence number: " + sequenceNumber);
        tokenManager.requestEntry(getProcessId(), sequenceNumber);
        
        while(true) {
	        if (!hasToken) {
	            System.out.println("Process " + getProcessId() + " Waiting for token...");
	        }
	        else {
	        	System.out.println("Process " + getProcessId() + " entering critical section.");
	        	break;
	        }
	        Thread.sleep(100);
        }
    }

    @Override
    public synchronized void releaseCriticalSection() throws RemoteException, MalformedURLException, NotBoundException {
        System.out.println("Process " + getProcessId() + " exiting critical section.");
        hasToken = false;
        tokenManager.releaseToken(getProcessId());
    }

    @Override
    public int getSequenceNumber() throws RemoteException {
        return sequenceNumber;
    }

    @Override
    public void receiveToken() throws RemoteException {
        System.out.println("Received token for process " + getProcessId());
        hasToken = true;
    }
    
    private int getProcessId() {
        return processID;
    }
}
