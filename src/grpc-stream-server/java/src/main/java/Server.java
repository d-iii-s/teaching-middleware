import example.Example.AnExampleMessage;

import example.AnExampleServiceGrpc;


public class Server {

    // Service implementation.
    //
    // The implementation inherits from a generated base class.

    static class MyService extends AnExampleServiceGrpc.AnExampleServiceImplBase {

        @Override
        public io.grpc.stub.StreamObserver<AnExampleMessage> echoMessages (final io.grpc.stub.StreamObserver<AnExampleMessage> responseObserver) {
            System.out.println ("Call.");

            // Create and return an anonymous observer class that handles the request.
            return (new io.grpc.stub.StreamObserver<AnExampleMessage> () {

                @Override
                public void onNext (AnExampleMessage message) {
                    System.out.println ("Request:");
                    System.out.println (message.toString ());
                    try { Thread.sleep (666); } catch (InterruptedException e) { }
                    responseObserver.onNext (message);
                }

                @Override
                public void onError (Throwable t) {
                    System.out.println (t);
                }

                @Override
                public void onCompleted () {
                    System.out.println ("Done.");
                    responseObserver.onCompleted ();
                }
            });
        }
    }

    public static void main (String [] args) {

        // Create the server object.
        //
        // The server object represents the server runtime.
        // It needs to be told what service to provide
        // and what port to listen on.

        try {
            io.grpc.Server server = io.grpc.ServerBuilder
                .forPort (Shared.SERVER_PORT)
                .addService (new MyService ())
                .build ()
                .start ();

            // The server is never asked to terminate in this example,
            // it therefore waits here until interrupted from outside.

            server.awaitTermination ();
        }
        catch (Exception e) {
            System.out.println (e);
        }
    }
}
