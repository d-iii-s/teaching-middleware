import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CallforwardImpl extends UnicastRemoteObject implements Callforward {
    public CallforwardImpl () throws RemoteException { }

    public void display (Callback callback, String message) throws RemoteException {
        System.out.println ("- Server method on thread " + Thread.currentThread ().getId () + " (" + Thread.currentThread ().getName () + ") starting.");
        callback.display (message);
        try { Thread.sleep (1000); } catch (InterruptedException e) { };
        System.out.println ("- Server method on thread " + Thread.currentThread ().getId () + " (" + Thread.currentThread ().getName () + ") finishing.");
    }
}
