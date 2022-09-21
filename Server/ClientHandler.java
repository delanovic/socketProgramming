import java.util.*;
import java.net.*;
import java.io.*;
import java.nio.file.*;

public class ClientHandler extends Thread
{
    Socket s;
    BufferedReader inputFromUser;
    PrintWriter outputSentFromServer;
    Scanner sc=new Scanner(System.in);
    
    public ClientHandler(Socket s) {
        this.s = s;
        
        try {
            inputFromUser = new BufferedReader(new InputStreamReader(s.getInputStream()));
            outputSentFromServer = new PrintWriter(s.getOutputStream(),true);
            
        }catch(IOException e) {
            
        }
    }
    public void run() {
        
        try {
            while(true) {
                outputSentFromServer.println("1.List Files\t2.Create folder\t3.Kreiranje teksta\t4.Upis u Datoteku\t5.kopiranje\t6.Sadrzaj\t7.uporedi .txt");
                String received = inputFromUser.readLine();
                
                if(received.equals("END")) {
                    s.close();
                }
                
                switch(received) {
                    case "1": sortedList();break;
                    case "2": createFolder();break;
                    case "3": kreiranjeTxt();break;
                    case "4": upisUText();break;
                    case "5":kopiranje();break;
                    case "6":sadrzaj();break;
                    case "7":usporedi();break;
                    default:outputSentFromServer.println("Chose something else 1");
                }
                received = inputFromUser.readLine();
            }
        }catch(Exception e) {
            
        }
    }
    
    
    public void sortedList() {
        File f= new File("C:/Users/Comp/Desktop/FileSystem/Server");
        String returnData ="";
      
        File[] files = f.listFiles();
        try {
         
        for(File file: files) {
                returnData = returnData + FileHandler(file) + " " + file.getName() + "-";  
        }
        outputSentFromServer.println(returnData +"\t..Enter");
        }catch(Exception e) {}
    }
    
     public void specifiranaPretraga(){
        try{
            String returnData = "";
            outputSentFromServer.println("Unesite naziv foldera ciji sadrzaj zelite ispisati.");
            String nf = inputFromUser.readLine();
            boolean status = true;
            
            File rf = new File(nf);
            
            if(!rf.isDirectory()){
                returnData = "Folder ne postoji.";
                status = false;
            }
            
            if(status){
                File[] files = rf.listFiles();
                
                for(File file : files){
                    returnData = returnData + FileHandler(file) + " " + file.getName() + "  -  ";
                }
                
            }
            
            outputSentFromServer.println(returnData + "\tPritisnite enter za nastavak.");
            
        }catch(Exception e){}
    }
 
    
    public void createFolder() {
        try {
            outputSentFromServer.println("Type the name for new folder");
            String input = inputFromUser.readLine();
            String returnData = "";
            boolean status = true;
            
            File f=new File(input);
            if(f.isFile() || f.isDirectory()) {
                outputSentFromServer.println("File with this name is already existing");
                status = false;
            }
            
            if(status) {
                if(f.mkdir()) {
                    returnData="File" + input + "is created";
                }
            }
            
             outputSentFromServer.println(returnData +"\t..Enter");
        } catch(Exception e) {
            
        }
    }
    
      public void kreiranjeTxt(){
         try{
            String returnData = "";
            outputSentFromServer.println("Unesite ime tekstualne datoteke i slovo a ukoliko dodajete tekst a d ukoliko zelite prebrisati postojeci.");
            String nf = inputFromUser.readLine();
            String[] nfinput = nf.split(" ");
            boolean status = true;
            
            File rf = new File(nfinput[0]+".txt");
            
            if(rf.isFile()){
                returnData = "Fajl vec postoji.";
                status = false;
            }
            
            if(status){
                FileWriter dtext = new FileWriter(rf);
                FileWriter atext = new FileWriter(rf, true);
                
                /*if(nfinput[1].equals("a")){
                    
                }*/
                
                outputSentFromServer.println("Unesite zeljeni tekst");
                String zt = inputFromUser.readLine();
                
                if(nfinput[1].equals("a")){
                    try{
                        atext.write(zt);
                    }catch(Exception e){}
                }
                
                dtext.close();
                atext.close();
                
                
                returnData = "Fajl kreiran i promijenjen";
            }
            
            outputSentFromServer.println(returnData + "\tPritisnite enter za nastavak.");
            
        }catch(Exception e){}
    }
    
    public void upisUText() {
        try {
        outputSentFromServer.println("U koju datoteku zelite upis .txt");
        String odgovor = inputFromUser.readLine();
        FileWriter myWriter = new FileWriter(odgovor);
        outputSentFromServer.println("Sta zelite upisati");
        String odgovor1=inputFromUser.readLine();
        myWriter.write(odgovor1);
        myWriter.close();
        outputSentFromServer.println("Uspjesno Upisano");
        }catch(Exception e){}
        
        
    }
    
     public void kopiranje() throws IOException{
    outputSentFromServer.println("Unesite COPY 'ime datoteke ili direktorija koje zelite prekopirati' 'novo ime'");
    String ime = inputFromUser.readLine();
    String [] dijelovi = ime.split(" ");
    File stari = new File(dijelovi[1]);
    File novi = new File(dijelovi[2]);
    Path prvi = Paths.get(stari.getAbsolutePath());
    Path drugi = Paths.get(novi.getAbsolutePath());
    Files.copy(prvi, drugi);
    outputSentFromServer.println("Kopirano!");
  }
  
   public void sadrzaj() throws IOException {
       outputSentFromServer.println("Unesite datoteku ciji  sadrzaj zelite vidjeti");
       String odgovor = inputFromUser.readLine();
       File f=new File(odgovor);
       BufferedReader citamizdatoteke=new BufferedReader(new FileReader(f));
       String linija="",sadrzaj="";
       while(linija!=null){
           linija=citamizdatoteke.readLine();
           sadrzaj+=(linija+" ");
       }
       outputSentFromServer.println("Sadrzaj datoteke je" + sadrzaj);
       
       
   }
   
   public void usporedi() throws IOException{
    outputSentFromServer.println("Unesite ime prve datoteke (i nastavak)");
    String prva = inputFromUser.readLine();
    outputSentFromServer.println("Unesite ime druge datoteke (i nastavak)");
    String druga = inputFromUser.readLine();
    File prvi = new File(prva);
    File drugi = new File(druga);
    BufferedReader citajdatoteku = new BufferedReader(new FileReader(prvi));
    String sadrzaj1 = "";
    String linija = "a";
    while(linija!=null){
      linija = citajdatoteku.readLine();
      sadrzaj1+=linija;
    }
    citajdatoteku = new BufferedReader(new FileReader(drugi));
    String sadrzaj2 = "";
    linija = "a";
    while(linija!=null){
      linija = citajdatoteku.readLine();
      sadrzaj2+=linija;
    }
    citajdatoteku.close();
    

    if(sadrzaj1.equals(sadrzaj2)){
      outputSentFromServer.println("Datoteke su jednake");
    }
    else{
      outputSentFromServer.println("Datoteke nisu jednake");
    }
  }
    

          
    
    
       
    public String FileHandler(File f) {
        String r="";
        if(f.isFile()) {
            r="FILE";
        }
        if(f.isDirectory()) {
            r="DIR";
        }
        
        return r;
    }
    
    
}
     
    

