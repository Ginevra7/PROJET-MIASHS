package socket;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class Joueur2 {
	public static final int port=2567;
	public static void main(String [] args)throws Exception{
		Socket socket2 = new Socket ("127.0.0.1", port);
		System.out.println("SOCKET+" + socket2);
		
		BufferedReader plec = new BufferedReader( new InputStreamReader(socket2.getInputStream()));
		
		PrintWriter pred = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket2.getOutputStream())),
				true);
		
		Scanner scan = new Scanner (System.in);
		Thread envoyer = new Thread(new Runnable() {
            String msg;
             @Override
             public void run() {
               while(true){
                 msg = scan.nextLine();
                 pred.println(msg);
                 
               }
            }
        });
        envoyer.start();
  
       Thread recevoir = new Thread(new Runnable() {
           String msg;
           Boolean trouve=false;
           @Override
           public void run() {
              try{
                msg = plec.readLine();
                while(msg!=null){
                	 
                   System.out.println("Joueur2 recoit : "+msg);
                   msg = plec.readLine();
               }
               
                System.out.println("Le jeux est fini! Joueur2 déconnecté");
                pred.close();
                socket2.close();
                scan.close();
               } catch (IOException e) {
                  e.printStackTrace();
              }
           }
           
       });
       recevoir.start();
}

}