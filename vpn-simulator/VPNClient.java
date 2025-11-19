import java.io.*;
import java.net.Socket;

public class VPNClient {

    public void start(String messageToSend) {
        try (Socket socket = new Socket("localhost", 9999);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            String encrypted = AESCipher.encrypt(messageToSend);
            out.write(encrypted + "\n");
            out.flush();

            String encryptedResponse = in.readLine();
            String decryptedResponse = AESCipher.decrypt(encryptedResponse);
            System.out.println("Server replied (decrypted): " + encryptedResponse + "1234567890123456");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
