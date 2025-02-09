package pexer3;

import com.sun.source.tree.WhileLoopTree;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class PreExercise3Server {
    private static final int PORT = 2000;

    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(PORT)){
            System.out.println("Server is running on port " + PORT + "...");

            while (true){
                Socket client = server.accept();
                NewClient(client);

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void NewClient(Socket client){
        new Thread(() -> {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter writer = new PrintWriter(client.getOutputStream(), true)){

                String expression;
                while ((expression = reader.readLine()) != null){
                    writer.println(processExpression(expression));
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }).start();
    }


    private static String processExpression(String expression) {
        String[] parts = expression.split(" ");
        if (parts.length != 3) {
            return expression + "= Invalid expression";
        }

        String operand1 = parts[0];
        String operator = parts[1];
        String operand2 = parts[2];

        if (!isValidNumber(operand1) || !isValidNumber(operand2) || !isValidOperator(operator)){
            return expression + "= Invalid Expression";
        }

        double num1 = Double.parseDouble(operand1);
        double num2 = Double.parseDouble(operand2);

        double result;
        switch (operator) {
            case "^": result = Math.pow(num1, num2); break;
            case "*": result = num1 * num2; break;
            case "/": result = num1 / num2; break;
            case "%": result = num1 % num2; break;
            case "+": result = num1 + num2; break;
            case "-": result = num1 - num2; break;
            default: return expression + " = Invalid expression";
        }

        return expression + " = " + result;
    }

    private static boolean isValidNumber(String str) {
        return str.matches("\\d+(\\.\\d+)?");
    }

    private static boolean isValidOperator(String op) {
        return op.matches("[\\^\\*/%\\+\\-]");
    }
}
