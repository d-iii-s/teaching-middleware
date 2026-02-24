# Unique schema identifier for tooling.
@0xfedcba9876543210;

struct AnExampleMessage {
    someInteger @0: Int16;
    anotherInteger @3: UInt32;
    someString @2: Text;
    someMoreStrings @1: List(Text);
}

struct MoreExampleMessages {
    messages @0: List(AnExampleMessage);
}
