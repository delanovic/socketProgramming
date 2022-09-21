import java.util.*;
import java.net.*;
import java.io.*;
/**
 * Write a description of class Client here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Client
{
  
    public static void main(String[] args) throws Exception {
        try {
               Socket s =new Socket("localhost",1242);
        
        BufferedReader inputFromServer=new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter outputSentFromUser = new PrintWriter(s.getOutputStream(),true);
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println(inputFromServer.readLine());
            String request = sc.nextLine();
            outputSentFromUser.println(request);
            
            if(request.equals("END")) {
                s.close();
            }
        }
        }catch(Exception e) {
        }
     
        
    }
}
