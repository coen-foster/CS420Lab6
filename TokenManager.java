import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.PriorityQueue;
import java.util.Comparator;

public class TokenManager extends UnicastRemoteObject implements TokenManager_Interface {
    private static final long serialVersionUID = 1L;
	private int tokenHolder = 1;
    private final PriorityQueue<Request> requestQueue;

    public TokenManager() throws RemoteException {
        super();
        requestQueue = new PriorityQueue<>(Comparator.comparingInt((Request r) -> r.sequenceNumber)
                .thenComparingInt(r -> r.processId));
    }

    @Override
    public synchronized void requestEntry(int processId, int sequenceNumber) throws RemoteException, MalformedURLException, NotBoundException {
        requestQueue.offer(new Request(processId, sequenceNumber));
        System.out.println("Request added: processId=" + processId + ", sequenceNumber=" + sequenceNumber);
        
        if (tokenHolder == -1 || tokenHolder == processId) {
            System.out.println("Granting token...");
            grantToken();
        }
    }

    @Override
    public synchronized void releaseToken(int processId) throws RemoteException, MalformedURLException, NotBoundException {
        if (tokenHolder != processId) return;

        tokenHolder = -1;

        if (!requestQueue.isEmpty()) {
            grantToken();
        }
    }

    private synchronized void grantToken() throws RemoteException, MalformedURLException, NotBoundException {
        Request nextRequest = requestQueue.poll();
        if (nextRequest != null) {
            tokenHolder = nextRequest.processId;
            System.out.println("Granting token to process " + tokenHolder);
            Process_Interface process = (Process_Interface) Naming.lookup("rmi://localhost/Process" + tokenHolder);
            process.receiveToken();
        }
    }

    private static class Request {
        int processId;
        int sequenceNumber;

        public Request(int processId, int sequenceNumber) {
            this.processId = processId;
            this.sequenceNumber = sequenceNumber;
        }
    }
}
