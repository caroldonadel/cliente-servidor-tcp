package cliente;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        InetAddress serverAddress = null;
        int port = 0;
        String outMsg, inMsg;
        Scanner typedInput = new Scanner(System.in);

        if (args.length == 2 ) {
            try {
                serverAddress = InetAddress.getByName(args[0]);
            }
            catch (UnknownHostException e){
                System.err.println("\nServer Adress: " + e.getMessage());
                System.exit(1);
            }

            port = Integer.parseInt(args[1]);

            if((port < 1) || (port > 65535)) {
                System.err.println("\nInvalid port value!\n\tRange: 1 - 65535");
                System.exit(1);
            }
        } else {
            System.err.println("\nInvalid paramethers\n\t Use Cliente <server_ip>"
                    + "<server_port>");
            System.exit(1);
        }

        try {
            System.out.print("\nStarting Client..."
                    + "\n\tConfigured to send data to " + serverAddress.toString() +
                    " at port " + port);
            Socket clientSocket = new Socket(serverAddress, port);
            System.out.println("\nOpened Socket");
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            while (true) {
                System.out.println("\n\tType in a message: ");
                outMsg = typedInput.nextLine();

                if("<exit>".equals(outMsg)){
                    clientSocket.close();
                    System.exit(1);
                }

                out.writeUTF(outMsg);

                String receivedData = in.readUTF();
                System.out.println("Server's response:" + receivedData);
            }
        }
        catch (IOException e){
            System.err.print("\n\tConnection: " + e.getMessage());
        }
    }
}
