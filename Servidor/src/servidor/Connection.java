package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Connection extends Thread {

    DataInputStream in;
    DataOutputStream out;
    Socket clientSocket;

    public Connection(Socket clientSocket){
        try {
            this.clientSocket = clientSocket;
            in = new DataInputStream(this.clientSocket.getInputStream());
            out = new DataOutputStream(this.clientSocket.getOutputStream());
        }
        catch (IOException e) {
            System.err.print("Connection: " + e.getMessage());
        }
    }

    public void run(){
        try {
            while (true) {

                String data = in.readUTF();
                String response;
                System.out.println("\nMessage received from: ");
                System.out.println("\n\t" + clientSocket.getInetAddress().toString()
                + ":" + clientSocket.getPort());

                System.out.println("Payload: " + data);

                switch (data) {
                    case "get_date":
                        LocalDate currentDate = LocalDate.now();
                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String formattedDate = currentDate.format(dateTimeFormatter);

                        response = String.format("{\"date\": \"%s\"}", formattedDate);
                        break;
                    case "get_time":
                        LocalTime currentTime = LocalTime.now();
                        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                        String formattedTime = currentTime.format(timeFormatter);

                        response = String.format("{\"time\": \"%s\"}", formattedTime);
                        break;
                    default:
                        response = "Invalid Command!";
                }

                out.writeUTF(response);
            }
        }
        catch (Exception e) {
            System.out.println("The connection with one client program was interrupted");
        }
    }
}
