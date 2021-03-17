import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Example extends Remote {
    void twoStrings (String message, String stringOne, String stringTwo) throws RemoteException;
    void twoLists (String message, List listOne, List listTwo) throws RemoteException;
    void listAndElement (String message, List list, Object element) throws RemoteException;
    void twoProxies (String message, Example proxyOne, Example proxyTwo) throws RemoteException;
}
