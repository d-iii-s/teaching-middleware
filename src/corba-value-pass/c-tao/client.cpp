#include <assert.h>
#include <string>
#include <fstream>
#include <iostream>

// Include CORBA headers and generated stub headers.
// The names of these files are not standardized,
// so porting to another CORBA implementation
// requires change here.
#include <tao/corba.h>
#include <tao/Valuetype/ValueFactory.h>
#include "exampleC.h"
#include "bundle.h"

int main (int iArgC, char *apArgV []) {
    try {

        // Initialize the CORBA library.
        //
        // This includes getting references to the ORB object,
        // which can convert string references into proxies.

        CORBA::ORB_var vORB = CORBA::ORB_init (iArgC, apArgV);

        // Create proxy for server object.
        //
        // String reference is expected in file.

        std::ifstream oReferenceFile ("ior");
        std::string oReferenceText;
        std::getline (oReferenceFile, oReferenceText);
        oReferenceFile.close ();

        CORBA::Object_var vServiceBase = vORB->string_to_object (oReferenceText.c_str ());
        AnExampleService_var vService = AnExampleService::_narrow (vServiceBase);

        // Create bundle to be value passed.
        //
        // Note use of setters for filling individual attributes.

        AValueBundle_var vBundle = new AValueBundleImpl ();        
        vBundle->text (CORBA::string_dup ("Hello from TAO client !"));
        vBundle->number (12345);

        // This is a local call.

        vBundle->display ();

        // This is a remote call.

        vService->display (vBundle);

        // Shut down the CORBA library.

        vORB->destroy ();

    } catch (const CORBA::SystemException &sEx) {
        std::cerr << sEx << std::endl;
    };
};
