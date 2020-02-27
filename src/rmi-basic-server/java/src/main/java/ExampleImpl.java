import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ExampleImpl extends UnicastRemoteObject implements Example {
    public ExampleImpl () throws RemoteException { }
    public void printString (String text) { System.out.println (text); }
}
