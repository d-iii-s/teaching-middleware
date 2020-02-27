import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Example extends Remote {
    void printString (String text) throws RemoteException;
}
