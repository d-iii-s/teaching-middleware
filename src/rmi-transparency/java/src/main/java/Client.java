import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

public class Client {
    public static void main (String args []) {
        try {

            Registry registry = LocateRegistry.getRegistry ();
            Example object = (Example) registry.lookup (Shared.SERVER_NAME);

            // Try if two strings will become aliased.
            String stringOne = "string one";
            String stringTwo = "string two";
            object.twoStrings ("Sending one string twice ...", stringOne, stringOne);
            object.twoStrings ("Sending two different strings ...", stringOne, stringTwo);
            object.twoStrings ("Sending two equal string literals ...", "a string", "a string");

            System.out.println ("Press ENTER to continue.");
            System.in.read ();

            // Try if two aliased arguments will remain aliased.
            ArrayList<Integer> listOne = new ArrayList<Integer> ();
            listOne.add (1);
            listOne.add (2);
            listOne.add (3);
            ArrayList<Integer> listTwo = new ArrayList<Integer> ();
            listTwo.add (4);
            listTwo.add (5);
            listTwo.add (6);
            object.twoLists ("Sending one list twice ...", listOne, listOne);
            object.twoLists ("Sending two different lists ...", listOne, listTwo);

            System.out.println ("Press ENTER to continue.");
            System.in.read ();

            // Try if two overlapping arguments will remain overlapping.
            ArrayList<Integer> element = new ArrayList<Integer> ();
            element.add (8);
            ArrayList<Object> list = new ArrayList<Object> ();
            list.add (element);
            object.listAndElement ("Sending list and its element ...", list, element);

            System.out.println ("Press ENTER to continue.");
            System.in.read ();

            // See how two proxies of the same remote object will behave.
            Example proxyOne = (Example) registry.lookup (Shared.SERVER_NAME);
            Example proxyTwo = (Example) registry.lookup (Shared.SERVER_NAME);
            System.out.println ("Local proxies ...");
            System.out.println ("- reference equality says " + (proxyOne == proxyTwo));
            System.out.println ("- content equality says " + (proxyOne.equals (proxyTwo)));
            object.twoProxies ("Sending one proxy twice ...", proxyOne, proxyOne);
            object.twoProxies ("Sending two different proxies ...", proxyOne, proxyTwo);

            System.out.println ("Press ENTER to continue.");
            System.in.read ();

            // See how the proxy compares with the remote object.
            object.proxy ("This proxy ...", object);

        }
        catch (Exception e) {
            System.out.println ("Exception:");
            System.out.println (e);
            e.printStackTrace ();
        }
    }
}
