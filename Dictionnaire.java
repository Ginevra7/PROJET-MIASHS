package socket;

import java.util.ArrayList;
import java.util.Collection.*;
import java.util.Iterator;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Random;

//Chaque instance de cette classe crée un objet composé d'une liste d'objets Mot,dans notre jeux on utilise seulement une instance qui contient tous les possibles mots but.  

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
	
	//Cette méthode est utilisé dans le jeux pour effacer les synonymes et le mot but (si présent) de la phrase du joueur1 avant qu'elle soit envoyée au joueur2
	//Elle prend en paramètres une String (la phrase du joueur1),l'objet Mot et renvoie la phrase sans les synonymes et sans le mot but 
	public String isSyno(String str,Mot mot){
		boolean trouve=true;
		boolean flag=true;
		int i=0;
		int j=0;
		char vide;
		String mot1="";
		while(i++<str.length()){          //Dans cette boucle on compare les mots de la phrase un par un avec les synonymes du mot but et les effacer eventuellement de la phrase
			vide=str.charAt(j);
			mot1="";
			while(vide!=' '&& vide!='\''&& flag){    //Dans cette boucle on isole les mots de la phrase un par un
				vide=str.charAt(j+1);
				mot1+=str.charAt(j);
				if(j+1==(str.length()-1)){
					mot1+=str.charAt(j+1);
					flag=false;
				}
				j++;
			}
			trouve=mot.isSyno(mot1); //Ici on utilise la méthode de la classe Mot
			
			if(trouve||(mot.getMot()).equals(mot1)){
				if(mot1.length()==str.length())  //ici on traite le cas où la phrase écrit par le joueur1 est le mot but,dans ce cas on renvoie la phrase vide.
					return str="";
				int l=mot1.length();
				str=str.substring(0,j-l)+str.substring(j+1,str.length());
				j=j-l-1;
			}
			j++;
			i=j;
		}
		return str;
	}
	
	//Cette méthode est utilisé dans le jeux pour effacer le 30% des caractères(y compris les espaces) de la phrase du joueur1 après avoir éliminé tous synonymes
	//Elle prend en paramètres une String (la phrase du joueur1) et renvoie la phrase sans le 30% des caractères 
	public String supprimer(String s){
		int j=(int)(30*s.length()/100);
		if(j>1){                  //on utilise une condition if/else pour traiter le cas d'une phrase du joueur1 trop courte 
		for(int k=0;k<j+1;k++){
			int l=s.length();
			int i=(int)(Math.random()*l);   
			s=removeCh(s,i);
		}
		return s;
		}
		else return s="";
	}
	
	//On appelle cette méthode dans la méthode 'supprimer' pour supprimer un caractère au hasard de la phrase
	public String removeCh(String s,int i){
		if((i>s.length()-1||i<0))
			return null;
		String str=s.substring(0,i)+s.substring(i+1,s.length());
		return str;
		}
	
	
	//On utlise cette méthode dans le jeux après que le jouer2 a écrit sa phrase pour vérifier s'il y a le mot but écrit.
	//Elle prend en paramères une String(la phrase du joueur2) et une String (le mot but). Elle renvoie un boolean: True si le joueur2 a déviné,False sinon.
	public Boolean isMot(String str,String mot){
		boolean flag=true;
		int i=0;
		int j=0;
		char vide;
		String mot1="";
		while(i++<str.length()){ //Cette boucle compare chaque mot de la phrase un par un avec le mot but. Si on trouve les memes on renvoie true,on continue la boucle sinon.
			vide=str.charAt(j);
			mot1="";
			while(vide!=' '&& flag){  //Cette boucle isole chaque mot de la phrase un par un.
				vide=str.charAt(j+1);
				mot1+=str.charAt(j);
				if(j+1==(str.length()-1)){
					mot1+=str.charAt(j+1);
					flag=false;
				}
				j++;
			}
		if(mot.equals(mot1))
			return true;
		else{
			j++;
			i=j;
		}
			
	}
		return false;
	}
}
