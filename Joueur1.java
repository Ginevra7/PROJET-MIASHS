package socket;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Créé par Martin Elisa Martini Solène Romagnoni Nadia

public class Joueur1 {
static final int port = 2567 ;
	public static void main(String[] args) throws Exception{
			Socket socket1 = new Socket ("127.0.0.1", port);
			System.out.println("SOCKET+" + socket1);
			
			BufferedReader plec = new BufferedReader( new InputStreamReader(socket1.getInputStream()));
			PrintWriter pred = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream())),
					true);
			
			Scanner scan = new Scanner (System.in);
			
		//Ces deux threads : envoyer et recevoir, ont des threads correspondants dans le ServeurMT
			Thread envoyer = new Thread(new Runnable() {
	            String msg;
	             @Override
	             public synchronized void run() {
	               while(true){
	                 msg = scan.nextLine();
	                 pred.println(msg);
	                 
	               }
	            }
	        });
			envoyer.start();
			  
		       Thread recevoir = new Thread(new Runnable() {
		           String msg;
		           @Override
		           public synchronized void run() {
		        	   try {
		            	  
		                msg = plec.readLine();
		                while(msg!=null){
		                   
		                   System.out.println("Joueur1 reçoit : "+msg);
		                 
		                   msg = plec.readLine();
		               }
		                System.out.println("Le jeu est fini. Joueur1 déconnecté");
		                pred.close();
		                socket1.close();
		                scan.close();
		              } catch (IOException e) {
		                  e.printStackTrace();
		              }
		           }
		       });
		       recevoir.start();
		}
	}
		
		
