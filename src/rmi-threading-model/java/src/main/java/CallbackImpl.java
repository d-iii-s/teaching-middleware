import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CallbackImpl extends UnicastRemoteObject implements Callback {
    public CallbackImpl () throws RemoteException { }

    public synchronized void display (String message) throws RemoteException {
        System.out.println ("- Client callback on thread " + Thread.currentThread ().getId () + " (" + Thread.currentThread ().getName () + ") starting.");
        System.out.println (message);
        try { Thread.sleep (1000); } catch (InterruptedException e) { };
        System.out.println ("- Client callback on thread " + Thread.currentThread ().getId () + " (" + Thread.currentThread ().getName () + ") finishing.");
    }
}
