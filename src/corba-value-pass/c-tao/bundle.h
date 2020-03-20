// Bundle implementation.
//
// The implementation inherits from a generated base class and a reference counting mixin.

class AValueBundleImpl : public virtual OBV_AValueBundle, public virtual CORBA::DefaultValueRefCountBase {
public:
    void display () {
        std::cout << "AValueBundleImpl::display ()" << std::endl;
        std::cout << "- number: " << number () << std::endl;
        std::cout << "- text: " << text () << std::endl;
    };
    virtual CORBA::ValueBase *_copy_value (void) {
        // A helper function not used in this example.
        assert (false);
    };
};


// Bundle factory implementation.
//
// Used to create empty bundle instance when bundle argument arrives.

class AValueBundleFactory : public CORBA::ValueFactoryBase {
private:
    AValueBundle *create_for_unmarshal () {
        std::cout << "AValueBundleFactory::create_for_unmarshal ()" << std::endl;
        return (new AValueBundleImpl ());
    };
};
