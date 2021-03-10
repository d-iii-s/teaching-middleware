import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Callback extends Remote {
    void display (String message) throws RemoteException;
}
