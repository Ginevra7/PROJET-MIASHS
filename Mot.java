package socket;

import java.util.ArrayList;
import java.util.Iterator;

//Créé par Martin Elisa Martini Solène Romagnoni Nadia

//Chaque instance de cette classe créée un objet composé d'un String (le mot) et une liste de String (les synonymes du mot) 

public class Mot {
	private String mot;
	private ArrayList<String> synonymes;
	public Mot(String mot){
		setMot(mot);
		synonymes=new ArrayList <String>();
	}
	public void setMot(String mot){
		if(mot.length()>2)
			this.mot=mot;
		else 
			System.out.println("Le mot n'est pas valide");
	}
	
	public String getMot(){
		return mot;
	}
	
	public void ajoutSyno(String syno){
		synonymes.add(syno);
	}
	
	public String getSyno(int i){
		return synonymes.get(i);
	}
	
	public int nbSyno(){
		return synonymes.size();
	}
	
	//Cette méthode est utilisée par la méthode isSyno de la classe Dictionnaire et compare le mot d'une phrase du joueur1 avec tous les synonymes du mot but du jeu 
	public Boolean isSyno(String str){ 
		String s="";
		Iterator <String> i=synonymes.iterator(); 
		while(i.hasNext()){
			{
				s+=i.next();
				if(str.equals(s))
					return true;
			}
			s="";
				
		}
		return false;
	}		
}


