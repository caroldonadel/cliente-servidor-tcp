package servidor;

import java.net.*;
import java.io.*;

public class Server {

    public static void main(String[] args) {

        int port = 0;

        if (args.length == 1) {
            port = Integer.parseInt(args[0]);

            if ((port >= 1) || (port <= 65535)) {
                runServer(port);
            } else {
                System.err.println("\nInvalid port value!\n\tRange: 1 - 65535");
                System.exit(1);
            }

        } else {
            System.err.print("\nInvalid Paramethers!\n\tUse Server <server-port>");
            System.exit(1);
        }
    }

    private static void runServer(int port) {
        System.out.println("\nServer running at port " + port);

        try {
        ServerSocket listenSocket = new ServerSocket(port,5);

        while (true) {
            System.out.println("\nWaiting for new connection...");

            Socket clientSocket = listenSocket.accept();
            System.out.println("\n\tConnected to " +
                    clientSocket.getInetAddress().toString() +
                    " at port +" +
                    clientSocket.getPort());

            Connection c = new Connection(clientSocket);
            c.start();
        }
        }catch (IOException e) {
            System.err.print("IO " + e.getMessage());
        }
    }
}
