package com.example.accessingdatamongodb.Controller;

import io.grpc.*;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    private static final Logger logger = Logger.getLogger(Client.class.getName());

    private final ManagedChannel originChannel;

    /**
     * A custom client.
     */
    private Client(String host, int port) {
        originChannel = Grpc
                .newChannelBuilderForAddress(host, port, InsecureChannelCredentials.create())
                .build();
        ClientInterceptor interceptor = new HeaderClientInterceptor();
        Channel channel = ClientInterceptors.intercept(originChannel, interceptor);
    }

    private void shutdown() throws InterruptedException {
        originChannel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * A simple client method that like {@link io.grpc.examples.helloworld.HelloWorldClient}.
     */

    /**
     * Main start the client from the command line.
     */
    public static void main(String[] args) throws Exception {
        // Access a service running on the local machine on port 50051
        Client client = new Client("localhost", 50051);
        try {
            String user = "world";
            // Use the arg as the name to greet if provided
            if (args.length > 0) {
                user = args[0];
            }
//            client.greet(user);
        } finally {
            client.shutdown();
        }
    }
}
