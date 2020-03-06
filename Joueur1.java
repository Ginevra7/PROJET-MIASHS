package projetjeu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Joueur1 {
static final int port = 6112 ;
	
	public static void main(String[] args) throws Exception{
		Socket socket = new Socket ("127.0.0.1", port);
		System.out.println("SOCKET+" + socket);
		
		BufferedReader plec = new BufferedReader( new InputStreamReader(socket.getInputStream()));
		
		PrintWriter pred = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),
				true);
		
		Scanner scan = new Scanner (System.in);
		for (int i =0; i<10; i++) {
			String s=scan.nextLine();
			pred.println(s);
			String str = plec.readLine();
			System.out.println("Joueur1 recoit:" +str);
		}
		
		System.out.println("Fin Joueur1");
		pred.println("END");
		plec.close();
		pred.close();
		socket.close();
}

}


