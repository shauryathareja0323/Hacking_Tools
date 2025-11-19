import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class VPNServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            System.out.println("VPN Server is running on port 9999...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected!");

                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                String encryptedMessage = in.readLine();
                String decryptedMessage = AESCipher.decrypt(encryptedMessage);
                System.out.println("Received from client (decrypted): " + decryptedMessage);

                String reply = "Server received: " + decryptedMessage;
                String encryptedReply = AESCipher.encrypt(reply);
                out.write(encryptedReply + "\n");
                out.flush();

                clientSocket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
