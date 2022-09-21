import java.util.*;
import java.net.*;

public class Server
{
   public static void main(String[] args) throws Exception {
       ServerSocket ss= new ServerSocket(1242);
       Socket socket = new Socket();
       
       try {
           while(true) {
               socket =ss.accept();
               System.out.println("Client" + socket + " is connected.");
               
               Thread t = new ClientHandler(socket);
               t.start();
           }
       } catch(Exception e) {
           
       }
   }
   
}
