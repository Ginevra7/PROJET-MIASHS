package socket;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
public class ServeurMT extends Thread{
	ServerSocket ss=null;
	public static final int port=1180;
	int nbJoueurs;
	private List<Socket> joueursConnectes=new ArrayList<>();
	private Dictionnaire dict;
	private String motBut;
	private boolean fin;
	public void run(){
		try{
			ss=new ServerSocket(port);
			//int i=(int)Math.random()*(dict.getNbMots());
			motBut="etudiant";//=dict.getMot(i);
			while(true){  //pour chaque joueur qui se connecte on crée un objet de la classe Conversation(un thread)
				System.out.println("J'attends la connexion d'un client");
				Socket s1=ss.accept();
				Socket s2=ss.accept();
				joueursConnectes.add(s1);
				nbJoueurs++;
				joueursConnectes.add(s2);
				nbJoueurs++;
				new Conversation1(s1,s2).start();
				
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		//ss.close();
	}
	
	class Conversation1 extends Thread{
		private Socket socket1;
		private Socket socket2;
		public Conversation1(Socket socket1,Socket socket2){
			super();
			this.socket1=socket1;
			this.socket2=socket2;
		}
		public void run(){ //code de la conversation
			try{
				InputStream is1=socket1.getInputStream();
				InputStreamReader isr1=new InputStreamReader(is1);
				BufferedReader br1=new BufferedReader(isr1);
				InputStream is2=socket2.getInputStream();
				InputStreamReader isr2=new InputStreamReader(is2);
				BufferedReader br2=new BufferedReader(isr2);
				OutputStream os1=socket1.getOutputStream();
				PrintWriter pw1=new PrintWriter(os1,true);
				OutputStream os2=socket2.getOutputStream();
				PrintWriter pw2=new PrintWriter(os2,true);
				
				//String IP=socket.getRemoteSocketAddress().toString(); pour sauver les diffèrentes machine qui sont connectés
				pw1.println("Bienvenue,vus etes le joueur numéro 1! On vous donnera un mot but que vous devrez faire deviner au joueur2 avec des phrases le décrivant de longuer max de 50 caractères. Le trente pour cent des caractères seront automatiquement effacé ainsi comme tout synonyme.");
				pw2.println("Bienvenue,vus etes le joueur numéro 2! Vous devrez deviner le mot but décrit pa le joueur 1.Vous pouvez écrire des phrases de 50 caractère au max.Si la phrase contient le mot but vous avez gagné.");
				pw1.println("Le mot but secret est:"+motBut);
				pw2.println("S'il vous plait attendez");
				while(true){
					for(int i=0;i<5;i++){
						String req1=br1.readLine();
						if(req1!=null){
							// on doit supprimer le 20% des caracteres et synonymes
							pw2.println(req1);
							String req2=br2.readLine();
							if(req2!=null){
								pw1.println(req2);
								if(req2==motBut){
									pw1.println("Congratulation vous avez gagné! Le jeux est terminé.");
									pw2.println("Congratulation vous avez gagné! Le jeux est terminé.");
									break;
								}
							}
						}
					}
					pw1.println("Oh non,vous avex perdu! Le jeux est terminé.");
					pw2.println("Oh non,vous avex perdu! Le jeux est terminé.");
			
				}
			}catch(Exception e){
				e.printStackTrace();
			}
	
			
		}
	}
	public static void main(String[] args){
		Mot mot1=new Mot("etudiant");
		mot1.ajoutSyno("scolaire");
		mot1.ajoutSyno("écolier");
		mot1.ajoutSyno("élève");
		mot1.ajoutSyno("apprenant");
		Mot mot2=new Mot("plancher");
		mot2.ajoutSyno("parquet");
		mot2.ajoutSyno("sol");
		mot2.ajoutSyno("plateforme");
		mot2.ajoutSyno("estrade");
		Dictionnaire dict=new Dictionnaire();
		dict.ajoutMot(mot1);
		dict.ajoutMot(mot2);
		new ServeurMT().start();//on crée un objet de notre classe et donc on crée un objet thread
		
	}
	

}
