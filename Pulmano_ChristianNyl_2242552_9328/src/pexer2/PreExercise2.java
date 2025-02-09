package pexer2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PreExercise2 {
    private static final int PORT = 2000;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)){
            System.out.println("Server is up and running at port : " + PORT + "......");

            while (true){
                Socket client = server.accept();
                NewClient(client);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void NewClient(Socket client){
        new Thread(()-> {
            try (
                    BufferedReader streamRdr = new BufferedReader(new InputStreamReader( client.getInputStream()));
                    PrintWriter streamWtr = new PrintWriter(client.getOutputStream(), true)
            ) {
                // Server asks for client's name
                streamWtr.println("What is your name? ");
                String name = streamRdr.readLine();

                // Server asks for client's age
                int age;
                while (true) {
                    streamWtr.println("What is your age? ");
                    try {
                        age = Integer.parseInt(streamRdr.readLine());
                        if (age <= 0) throw new NumberFormatException();
                        break;
                    } catch (NumberFormatException e) {
                        streamWtr.println("Please enter a valid age.");
                    }
                }

                // Determine voting eligibility
                if (age >= 18) {
                    streamWtr.println(name + ", you may exercise your right to vote!");
                } else {
                    streamWtr.println(name + ", you are still too young to vote!");
                }

                streamWtr.println("Thank you and good day.");
            } catch (IOException e) {
                System.out.println("Client disconnected.");
            } finally {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
