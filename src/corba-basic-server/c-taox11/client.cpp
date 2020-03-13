#include <assert.h>
#include <string>
#include <fstream>
#include <iostream>

// Include CORBA headers and generated stub headers.
// The names of these files are not standardized,
// so porting to another CORBA implementation
// requires change here.
#include "exampleC.h"

int main (int iArgC, char *apArgV []) {
    try {

        // Initialize the CORBA library.
        //
        // This includes getting references to the ORB object,
        // which can convert string references into proxies.

        auto vORB = CORBA::ORB_init (iArgC, apArgV);

        // Create proxy for server object.
        //
        // String reference is expected in file.

        std::ifstream oReferenceFile ("ior");
        std::string oReferenceText;
        std::getline (oReferenceFile, oReferenceText);
        oReferenceFile.close ();

        auto vServiceBase = vORB->string_to_object (oReferenceText.c_str ());
        auto vService = IDL::traits<AnExampleService>::narrow (vServiceBase);

        // This is the actual remote call.

        vService->display ("Hello from TAOx11 client !");

        // Shut down the CORBA library.

        vORB->destroy ();

    } catch (const CORBA::SystemException &sEx) {
        std::cerr << sEx << std::endl;
    };
};
