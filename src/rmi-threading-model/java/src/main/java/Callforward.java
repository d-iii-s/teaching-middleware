import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Callforward extends Remote {
    void display (Callback callback, String message) throws RemoteException;
}
