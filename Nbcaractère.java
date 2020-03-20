package projetjeu;

public class Nbcaractère {
	private String mot;

	public String getMot() {
		return mot;
	}

	public void setMot(String mot) {
		this.mot = mot;
	}

	public Nbcaractère(String mot) {
		this.mot = mot;
	}
	
	public String cinquante() {
		if(mot.length() <= 50)
			return "On peut utiliser ce mot" ; 
		else
			return "Ce mot est trop long";			
	}
}
