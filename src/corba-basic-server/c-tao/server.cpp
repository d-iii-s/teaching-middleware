#include <string>
#include <fstream>
#include <iostream>

// Include CORBA headers and generated stub headers.
// The names of these files are not standardized,
// so porting to another CORBA implementation
// requires change here.
#include <tao/corba.h>
#include "exampleS.h"


// Service implementation.
//
// The implementation inherits from a generated base class.

class AnExampleServiceServant : public POA_AnExampleService {
public:
    void display (const char *sText) {
        std::cout << "TAO server: " << sText << std::endl;
    };
};


int main (int iArgC, char *apArgV []) {
    try {

        // Initialize the CORBA library.
        //
        // This includes getting references to several important objects.
        // vORB refers to the ORB object, which launches the message dispatch loop.
        // vRootPOA refers to the root POA object, which registers servant objects.
        // vRootPOAManager refers to the manager of the root POA object, needed to activate POA.

        CORBA::ORB_var vORB = CORBA::ORB_init (iArgC, apArgV);

        CORBA::Object_var vRootPOABase = vORB->resolve_initial_references ("RootPOA");
        PortableServer::POA_var vRootPOA = PortableServer::POA::_narrow (vRootPOABase);
        PortableServer::POAManager_var vRootPOAManager = vRootPOA->the_POAManager ();

        // Create one servant object.
        //
        // The servant object implements the service interface.
        // The object is registered with the root POA when
        // the remote reference is created.

        AnExampleServiceServant *pServant = new AnExampleServiceServant ();
        CORBA::Object_var vServiceBase = vRootPOA->servant_to_reference (pServant);

        // Export the remote reference for client use.

        std::ofstream oReferenceFile ("ior");
        oReferenceFile << vORB->object_to_string (vServiceBase) << std::endl;
        oReferenceFile.close ();

        // Start listening for incoming invocations.

        vRootPOAManager->activate ();
        vORB->run ();

    } catch (const CORBA::SystemException &sEx) {
        // Exception printing is not standardized.
        std::cerr << sEx << std::endl;
    };
};
