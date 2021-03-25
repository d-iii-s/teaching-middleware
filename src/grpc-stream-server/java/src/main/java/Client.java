import java.util.concurrent.Semaphore;

import example.Example.AnExampleMessage;

import example.AnExampleServiceGrpc;


public class Client {

    public static void main (String [] args) {

        // Create the channel used to connect to the server.
        io.grpc.ManagedChannel channel = io.grpc.ManagedChannelBuilder
            .forAddress (Shared.SERVER_ADDR, Shared.SERVER_PORT)
            .usePlaintext ()
            .build ();

        // Create a stub object that provides the service interface.
        AnExampleServiceGrpc.AnExampleServiceStub stub = AnExampleServiceGrpc.newStub (channel);

        AnExampleMessage message = AnExampleMessage.newBuilder ()
            .setSomeInteger (12345)
            .setSomeString ("Hello gRPC !")
            .build ();
        System.out.println ("Message:");
        System.out.println (message.toString ());

        Semaphore finishSemaphore = new Semaphore (0);
        Semaphore continueSemaphore = new Semaphore (0);
        try {
            // Call the service through the stub object with an anonymous observer class that handles the response.
            io.grpc.stub.StreamObserver<AnExampleMessage> requestObserver =
                stub.echoMessages (new io.grpc.stub.StreamObserver<AnExampleMessage> () {

                @Override
                public void onNext (AnExampleMessage message) {
                    System.out.println ("Response:");
                    System.out.println (message.toString ());
                    continueSemaphore.release ();
                }

                @Override
                public void onError (Throwable t) {
                    System.out.println (t);
                    finishSemaphore.release ();
                }

                @Override
                public void onCompleted () {
                    finishSemaphore.release ();
                }
            });

            // Send messages through the stream.
            for (int i = 0 ; i < 8 ; i ++) {
                try { Thread.sleep (666); } catch (InterruptedException e) { }
                requestObserver.onNext (message);
                continueSemaphore.acquire ();
            }

            // Notify about the end of the stream.
            requestObserver.onCompleted ();

            // Wait for the call to complete.
            finishSemaphore.acquire ();
        }
        catch (Exception e) {
            System.out.println (e);
        }
    }
}
