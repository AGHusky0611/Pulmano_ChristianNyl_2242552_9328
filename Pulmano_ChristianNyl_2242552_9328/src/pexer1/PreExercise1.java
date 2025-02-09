package pexer1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class PreExercise1 {
    public static void main(String[] args) {
        boolean search = true;

        try(
                ServerSocket server = new ServerSocket(2024);
                Socket client = server.accept();
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter streamWriter = new PrintWriter(client.getOutputStream(), true);
        ){
            streamWriter.println("succesfully connected");

            while (search){
                streamWriter.println("Host 1 - Type IP address/Hostname: ");
                String hostAdress = streamReader.readLine();
                InetAddress[] addressess = InetAddress.getAllByName(hostAdress);

                streamWriter.println("Numbe of Hosts/IPs " + addressess.length);
                streamWriter.printf("%-20s %-20s%n", "Hostname", "Ip Address");
                for(InetAddress address : addressess){
                    streamWriter.printf("%-20s %-20s%n", address.getHostName(), address.getHostAddress());
                }

                streamWriter.println("Search Another[y/n]: ");
                String response = streamReader.readLine();
                search = response.equalsIgnoreCase("y");
            }

            streamWriter.println("Program Terminated...");


        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
