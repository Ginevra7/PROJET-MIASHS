package socket;

import java.util.ArrayList;
import java.util.Iterator;

public class Mot {
	private String mot;
	private static ArrayList<String> synonymes;
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


