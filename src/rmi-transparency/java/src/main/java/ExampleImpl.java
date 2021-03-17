import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ExampleImpl extends UnicastRemoteObject implements Example {
    public ExampleImpl () throws RemoteException { }
    public void twoStrings (String message, String stringOne, String stringTwo) {
        System.out.println (message);
        System.out.println ("- reference equality says " + (stringOne == stringTwo));
        System.out.println ("- content equality says " + (stringOne.equals (stringTwo)));
    }
    public void twoLists (String message, List listOne, List listTwo) {
        System.out.println (message);
        System.out.println ("- reference equality says " + (listOne == listTwo));
        System.out.println ("- content equality says " + (listOne.equals (listTwo)));
    }
    public void listAndElement (String message, List list, Object element) {
        System.out.println (message);
        System.out.println ("- reference equality says " + (list.get (0) == element));
        System.out.println ("- content equality says " + (list.get (0).equals (element)));
    }
    public void twoProxies (String message, Example proxyOne, Example proxyTwo) {
        System.out.println (message);
        System.out.println ("- reference equality says " + (proxyOne == proxyTwo));
        System.out.println ("- content equality says " + (proxyOne.equals (proxyTwo)));
    }
    public void proxy (String message, Example proxy) {
        System.out.println (message);
        System.out.println ("- reference equality says " + (this == proxy));
        System.out.println ("- content equality says " + (this.equals (proxy)));
    }
}
