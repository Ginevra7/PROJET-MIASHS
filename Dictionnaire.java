package socket;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collection.*;
import java.util.Iterator;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
public class Dictionnaire {
	private ArrayList<Mot> dict;
	public Dictionnaire(){
		dict=new ArrayList<Mot>();
	}
	public void ajoutMot(Mot mot){
		dict.add(mot);
	}
	public int getNbMots(){
		return dict.size();
	}
	public String getMot(int i){
		String mot=dict.get(i).getMot();
		return mot;
	}
	public boolean isSyno(String str,Mot mot){
		boolean trouve=true;
		int i=0;
		int j=0;
		char vide=str.charAt(j);;
		String mot1="";
		while(i<str.length()&& !trouve){
			while(vide!=' '){
				vide=str.charAt(j+1);
				mot1+=str.charAt(j);
				j++;
			}
			trouve=mot.isSyno(mot1);
			i=j+2;
		}
		return trouve;
	}
	public static String supprimer(String s){
		int j=(int)(30*s.length()/100);
		for(int k=0;k<j+1;k++){
			int l=s.length();
			int i=(int)(Math.random()*l);
			s=removeCh(s,i);
		}
		return s;
	}
	public static String removeCh(String s,int i){
		if((i>s.length()-1||i<0))
			return null;
		String str=s.substring(0,i)+s.substring(i+1,s.length());
		return str;
		}
	}

