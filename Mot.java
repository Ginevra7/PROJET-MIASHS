import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collection.*;
public class Mot extends Dictionnaire{
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
	public Boolean isSyno(String str){
		String s="";
		Iterator <String> i=synonymes.iterator();
		while(i.hasNext()){
			s+=i.next();
			if(str.equals(s))
				return true;
		}
		return false;
	}		
}
