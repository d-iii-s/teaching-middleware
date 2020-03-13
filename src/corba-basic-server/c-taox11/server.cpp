#include <string>
#include <fstream>
#include <iostream>

// Include CORBA headers and generated stub headers.
// The names of these files are not standardized,
// so porting to another CORBA implementation
// requires change here.
#include "exampleS.h"


// Service implementation.
//
// The implementation inherits from a generated base class.

class AnExampleServiceServant : public CORBA::servant_traits<AnExampleService>::base_type {
public:
    void display (const std::string &sText) {
        std::cout << "TAOx11 server: " << sText << std::endl;
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

        auto vORB = CORBA::ORB_init (iArgC, apArgV);

        auto vRootPOABase = vORB->resolve_initial_references ("RootPOA");
        auto vRootPOA = IDL::traits<PortableServer::POA>::narrow (vRootPOABase);
        auto vRootPOAManager = vRootPOA->the_POAManager ();

        // Create one servant object.
        //
        // The servant object implements the service interface.
        // The object is registered with the root POA when
        // the remote reference is created.

        auto vServant = CORBA::make_reference <AnExampleServiceServant> ();
        auto vService = vRootPOA->servant_to_reference (vServant);

        // Export the remote reference for client use.

        std::ofstream oReferenceFile ("ior");
        oReferenceFile << vORB->object_to_string (vService) << std::endl;
        oReferenceFile.close ();

        // Start listening for incoming invocations.

        vRootPOAManager->activate ();
        vORB->run ();

    } catch (const CORBA::SystemException &sEx) {
        std::cerr << sEx << std::endl;
    };
};
