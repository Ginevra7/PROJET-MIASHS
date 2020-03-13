package projetJeu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Serveur extends MyThread {
	
	public static void main (String[] args) throws UnknownHostException, IOException {
		String hostName = "127.0.0.1";
		int portNumber = 1180;
		ServerSocket s = null;
				
		try {
			s = new ServerSocket(portNumber);
			System.out.println("Serveur ecoute");
		} catch (UnknownHostException e)	{
			System.err.println("Le port"+ portNumber + "est déjà utilisé !");
		}
		finally{
			if(s!=null){
				try{
					s.close();
				}catch(IOException e){
					e.printStackTrace();
					s=null;
					}
			}
		
		Socket soc = s.accept(); 
		
		BufferedReader plec = new BufferedReader(new InputStreamReader(soc.getInputStream()));
		
		PrintWriter pred = new PrintWriter(new BufferedWriter(new OutputStreamWriter(soc.getOutputStream())),true);
		
		while (true) {
			String str = plec.readLine();
			if(str.contentEquals("END"))break;
			pred.println(str);
			System.out.println("Serveur recoit :"+str);
		}
		System.out.println("Fin serveur");
		plec.close();
		pred.close();
		soc.close();
	}

	
}
