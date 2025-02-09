package pexer3;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PreExercise3Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 2000;

    public static void main(String[] args) {
        List<String> expressions = readXML("res/exer3.xml");

        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            for (String expr : expressions) {
                out.println(expr);
                System.out.println(in.readLine());  // Print response from server
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static List<String> readXML(String filePath) {
        List<String> expressions = new ArrayList<>();
        try {
            File file = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();

            NodeList nodeList = document.getElementsByTagName("expression");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String operand1 = element.getElementsByTagName("operand1").item(0).getTextContent();
                    String operator = element.getElementsByTagName("operator").item(0).getTextContent();
                    String operand2 = element.getElementsByTagName("operand2").item(0).getTextContent();
                    expressions.add(operand1 + " " + operator + " " + operand2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expressions;
    }
}
