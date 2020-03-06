import java.io.*;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
public class Joueur2 {
	public static final int port=1180;
	public static void main(String [] args)throws Exception{
		Socket socket;

		try{
		socket=new Socket("127.0.0.1",port);
		System.out.println("SOCKET="+ socket);
		}
		catch(UnknownHostException e){
			e.printStackTrace();
		}catch(IOException e){
		}
		finally{
			socket.close();
		}
		BufferedReader plec=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter pred=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
		Scanner scanner=new Scanner(System.in);
		for(int i=0;i<5;i++){
		String s=scanner.nextLine();
		pred.println(s);
		String str=plec.readLine();
		System.out.println("Joueur2 reçoit:" +str);
		System.out.println("FIN JOUEUR2");
		pred.println("END");
}
}
