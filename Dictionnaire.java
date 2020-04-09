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
	public Mot getMOT(int i){
		return dict.get(i);
	}
	public String getMot(int i){
		String mot=dict.get(i).getMot();
		return mot;
	}
	public static String isSyno(String str,Mot mot){
		boolean trouve=true;
		boolean flag=true;
		int i=0;
		int j=0;
		char vide;
		String mot1="";
		while(i++<str.length()){
			vide=str.charAt(j);
			mot1="";
			while(vide!=' '&& flag){
				vide=str.charAt(j+1);
				mot1+=str.charAt(j);
				if(j+1==(str.length()-1)){
					mot1+=str.charAt(j+1);
					flag=false;
				}
				j++;
			}
			trouve=mot.isSyno(mot1);
			if(trouve){
				int l=mot1.length();
				str=str.substring(0,j-l)+str.substring(j+1,str.length());
				j=j-l-1;
			}
			j++;
			i=j;
		}
		return str;
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

