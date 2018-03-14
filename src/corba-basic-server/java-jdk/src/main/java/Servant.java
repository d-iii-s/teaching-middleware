// Service implementation.
//
// The implementation inherits from a generated base class.

class Servant extends AnExampleServicePOA {
    public void display (String sText) {
        System.out.println ("Plain Java server: " + sText);
    }
}
