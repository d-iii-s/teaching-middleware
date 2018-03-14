import java.io.PrintWriter;

public class Server {
    public static void main (String asArgs []) {
        try {

            // Initialize the CORBA library.
            //
            // This includes getting references to several important objects.
            // vORB refers to the ORB object, which launches the message dispatch loop.
            // vRootPOA refers to the root POA object, which registers servant objects.
            // vRootPOAManager refers to the manager of the root POA object, needed to activate POA.

            org.omg.CORBA.ORB oORB = org.omg.CORBA.ORB.init (asArgs, null);

            org.omg.CORBA.Object oPOABase = oORB.resolve_initial_references ("RootPOA");
            org.omg.PortableServer.POA oPOA = org.omg.PortableServer.POAHelper.narrow (oPOABase);
            org.omg.PortableServer.POAManager oPOAManager = oPOA.the_POAManager ();

            // Create one servant object.
            //
            // The servant object implements the service interface.
            // The object is registered with the root POA when
            // the remote reference is created.

            Servant oServant = new Servant ();
            org.omg.CORBA.Object oServiceBase = oPOA.servant_to_reference (oServant);

            // Export the remote reference for client use.

            try (PrintWriter oReferenceFile = new PrintWriter ("ior")) {
                oReferenceFile.println (oORB.object_to_string (oServiceBase));
            }

            // Start listening for incoming invocations.

            oPOAManager.activate ();
            oORB.run ();

        } catch ( java.lang.Exception oEx ) {
            oEx.printStackTrace ();
        }
    }
}
