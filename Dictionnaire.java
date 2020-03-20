import java.util.ArrayList;
import java.util.Collection.*;
import java.util.Iterator;
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
}
