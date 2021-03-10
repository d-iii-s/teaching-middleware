import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;

public class Client {
    public static void main (String args []) {
        try {
            final Callback callback = new CallbackImpl ();
            final Registry registry = LocateRegistry.getRegistry ();
            final Callforward object = (Callforward) registry.lookup (Shared.SERVER_NAME);

            // Calls to the object without synchronization.
            System.out.println ("Calls without synchronization ...");

            System.out.println ("- About to invoke the callback locally.");
            callback.display ("Callback invoked locally.");
            System.out.println ("- About to invoke the callback remotely.");
            object.display (callback, "Callback invoked remotely.");

            System.out.println ("Press ENTER to continue.");
            System.in.read ();

            // Calls to the object from multiple threads.
            System.out.println ("Calls from multiple threads ...");

            final int threadCount = 10;
            Thread [] threadArray = new Thread [threadCount];

            class SimpleThread extends Thread {
                public void run () {
                    try {
                        object.display (callback, "Multithreaded callback invoked remotely.");
                    }
                    catch (RemoteException e) {
                    }
                }
            };

            for (int threadIndex = 0 ; threadIndex < threadCount ; threadIndex ++) {
                threadArray [threadIndex] = new SimpleThread ();
                threadArray [threadIndex].start ();
            }

            for (int threadIndex = 0 ; threadIndex < threadCount ; threadIndex ++) {
                threadArray [threadIndex].join ();
            }

            System.out.println ("Press ENTER to continue.");
            System.in.read ();

            // Calls to the object with synchronization.
            System.out.println ("Calls with synchronization ...");

            synchronized (callback) {
                System.out.println ("- About to invoke the callback locally.");
                callback.display ("Synchronized callback invoked locally.");
                System.out.println ("- About to invoke the callback remotely.");
                object.display (callback, "Synchronized callback invoked remotely.");
            }
        }
        catch (Exception e) {
            System.out.println ("Client Exception: " + e.getMessage ());
            e.printStackTrace ();
        }
    }
}
