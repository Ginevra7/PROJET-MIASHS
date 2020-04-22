package socket;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.locks.Lock;


public class ServeurMT extends Thread {
	String hostName = "127.0.0.1";
	int nbJoueurs; 
	private List<Socket> joueursConnectes=new ArrayList<>();
	private Dictionnaire dict;
	private String motBut;
	private Mot mot;
	final Scanner sc=new Scanner(System.in);
	
	public ServeurMT(Dictionnaire dict, Mot mot,String motBut){
		this.dict=dict;
		this.mot=mot;
		this.motBut=motBut;
	}
	public synchronized void run() {

		try {
			ServerSocket ss = new ServerSocket(2567);
			System.out.println("Serveur ecoute");
			while(true) {
				Socket s1=ss.accept();
				Socket s2=ss.accept();
				joueursConnectes.add(s1);
				nbJoueurs++;				//à chaque joueur en plus, le nombre des joueurs connectés augmente 
				joueursConnectes.add(s2);
				nbJoueurs++;
				String IP1 = s1.getRemoteSocketAddress().toString();
				System.out.println("Connexion du client numéro 1 reçue. IP= "+IP1);
				String IP2 = s2.getRemoteSocketAddress().toString();
				System.out.println("Connexion du client numéro 2 reçue. IP= "+IP2);
				new Conversation(s1,s2).start(); //On utilise un thread avec le code de la conversation
			
			}
			}catch (IOException e) {
				e.printStackTrace();
			}

	}
	
	class Conversation extends Thread{

		private Socket socket1;
		private Socket socket2;
		public Conversation(Socket socket1,Socket socket2){
			super();
			this.socket1=socket1;
			this.socket2=socket2;
		}
		

		public synchronized void run() {

			//code de la conversation entre deux joueurs: un thread pour l'envoie et un thread pour la récéption. Ces deux threads ont des threads correspondants dans les classes Joueur1 et Joueur2
			try{
				BufferedReader plec1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
				PrintWriter pred1 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket1.getOutputStream())),true);
				BufferedReader plec2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));
				PrintWriter pred2 = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket2.getOutputStream())),true);

				pred1.println("Bienvenue, vous êtes le joueur numéro 1! Nous allons vous donnez un mot but que vous devrez faire deviner au joueur 2 avec des phrases le décrivant d'une longueur maximale de 50 caractères. 30% des caractères seront automatiquement effacé ainsi que tous les synonymes de ce mot. Vous avez 5 essais.");
				pred1.println("Le mot but est : "+motBut);
				pred2.println("Bienvenue, vous êtes le joueur numéro 2! Vous devrez deviner le mot but décrit par le joueur 1. Vous pouvez écrire des phrases de 50 caractères maximum. Si la phrase contient le mot but vous avez gagné. Vous avez 5 essais.");
				pred2.println("S'il vous plait attendez");
				
				//On utilise ce thread pour pour lire les phrase écrites sur le clavier par le joueur. 
				Thread envoi= new Thread(new Runnable() {
			          String req1;
			          String req2;
			          public synchronized void run() {
			        	 
			        	  try{
		        		  		while(true){
		        		  			req1 = sc.nextLine();
		 			                pred1.println(req1);
		 			               
		 			                req2 = sc.nextLine();
		 			                pred2.println(req2);
		 			              }
				          	} catch (Exception e) {
				          		e.printStackTrace();
				          		}
			          }
			 });
			envoi.start();
			
			//On utilise ce thread pour l'échange des phrases entre les deux joueurs
			Thread recevoir= new Thread(new Runnable() {
				boolean trouve; //on utilise un boolean pour savoir si le joueur2 a trouvé le mot but (résultat en appelant la méthode isMot de la classe Dictionnaire)
				String req1,req2;
				int i =0;
	
		          public synchronized void run() {
		        	  try {
		            	 while(true){
		            		 synchronized (this){
		            	 pred1.println("Veuillez écrire votre phrase:"); //On demande au joueur1 d'écrire une phrase de moins de 50 caractères.
		                 req1 = plec1.readLine();
		                 pred1.println("S'il vous plait attendez la réponse du joueur2.");
		                 while(req1.length()>50){
		                	 pred1.println("Veuillez écrire une phrase de 50 caractère au maximum.");
		                	 req1 = plec1.readLine();
		                	 pred1.println("S'il vous plait attendez la réponse du joueur2.");
		                 	}
		            	 }
		                //Tant que le client est connecté
		            	 
		            	//Dans cette boucle on renvoie la phrase du joueur1 sans synonymes et sans 30% des caractères au joueur2 (on utilise les méthode isSyno et supprimer de la classe Dictionnaire).
		            	//On demande au joueur2 d'écrire sa phrase, on vérifie s'il a trouvé. S'il y a le mot but, on arrête le jeu, autrement on continue jusqu'au cinquième essai.
		            	 while(req1!=null){   
		                 req1=dict.isSyno(req1,mot);
		                 req1=dict.supprimer(req1);
		                 if(req1=="") //on prend en considération le cas où la phrase du joueur1 devient vide ou l'est déjà.
		                	   pred2.println("Suite à l'elimination des caractères, la phrase entière du joueur1 a été effacée. Pas d'indices. ");
		                 else
		                	   pred2.println(req1);
		                 synchronized (this){
			                   pred2.println("Veuillez écrire votre phrase:");
			                   req2=plec2.readLine();
			                  
			                   while(req2.length()>50){
			                	   pred2.println("Veuillez écrire une phrase de 50 caractères au maximum.");
			                	   req2 = plec2.readLine();
			                	   pred2.println("S'il vous plait attendez");
			                   }
		                   }
		                 if(req2!=null) {
		                	   boolean trouve=dict.isMot(req2,motBut);
		                	   pred1.println(req2);
		                	   if(trouve){  //cas où le joueur1 a deviné : on ferme le jeu.
		                		   pred1.println("Bravo vous avez gagné!");
		                	       pred2.println("Bravo vous avez gagné!");
		                	       pred1.close();
			   		               pred2.close();
			   		               socket1.close();
			   		               socket2.close();
		                	       
		                	   }
		                	  
		                   }
		                    i++;
		                    if(i==5){ //cas où les joueurs ont fait 5 essais : on ferme le jeu.
		                		   pred1.println("Oh non vous avez perdu!");
		                	       pred2.println("Oh non vous avez perdu!");
		                	       pred1.close();
			   		               pred2.close();
			   		               socket1.close();
			   		               socket2.close();
		                		 
		                	 
		                 }
		                    pred2.println("S'il vous plait attendez.");
		                    synchronized (this){
		                    	pred1.println("Veuillez écrire votre phrase:");
		                    	req1 = plec1.readLine();
		                    	pred1.println("S'il vous plait attendez la réponse du joueur2.");
		                    	 while(req1.length()>50){
				                	 pred1.println("Veuillez écrire une phrase de 50 caractères au maximum.");
				                	 req1 = plec1.readLine();
				                	 pred1.println("S'il vous plait attendez la réponse du joueur2.");
				                 	}
		                    }
		                }
		            	
		                //sortir de la boucle si le client s'est déconecté
		                System.out.println("Client déconnecté");
		            	 
		                //fermer le flux et la session socket
		                pred1.close();
		                pred2.close();
		                socket1.close();
		                socket2.close();
		            	 }}catch (IOException e) {
		                  e.printStackTrace();
		             }
		         }
		      });
		      recevoir.start();
			}catch(IOException e){
				e.printStackTrace();
			}
			} 
		}

	
				
	public static void main (String[] args) {
		//On a crée quelques mots, mais le dictionnaire peut etre modifié et aggrandi en ajoutant des nouveaux mots
		Mot mot1=new Mot("chaise");
		mot1.ajoutSyno("fauteuil");
		mot1.ajoutSyno("canapé");
		mot1.ajoutSyno("chauffause");
		mot1.ajoutSyno("siège");
		Mot mot2=new Mot("plancher");
		mot2.ajoutSyno("parquet");
		mot2.ajoutSyno("sol");
		mot2.ajoutSyno("plateforme");
		mot2.ajoutSyno("estrade");
		Mot mot3=new Mot("fleur");
		mot3.ajoutSyno("fleurette");
		mot3.ajoutSyno("gerbe");
		Mot mot4=new Mot("amitié");
		mot4.ajoutSyno("affection");
		mot4.ajoutSyno("camaraderie");
		mot4.ajoutSyno("entente");
		Mot mot5=new Mot("chaussure");
		mot5.ajoutSyno("soulier");
		mot5.ajoutSyno("godasse");
		mot5.ajoutSyno("escarpin");
		mot5.ajoutSyno("chausson");
		mot5.ajoutSyno("espadrille");
		mot5.ajoutSyno("sabot");
		Dictionnaire dict=new Dictionnaire();
		dict.ajoutMot(mot1);
		dict.ajoutMot(mot2);
		dict.ajoutMot(mot3);
		dict.ajoutMot(mot4);
		dict.ajoutMot(mot5);
		int i=(int)(Math.random()*(dict.getNbMots())); // ici on chosit au hasard le mot but du jeux.
		Mot mot=dict.getMOT(i);
		String motBut=mot.getMot();
		new ServeurMT(dict,mot,motBut).start();

	}


}
