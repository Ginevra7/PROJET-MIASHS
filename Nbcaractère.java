package projetjeu;

public class Nbcaract�re {
	private String mot;

	public String getMot() {
		return mot;
	}

	public void setMot(String mot) {
		this.mot = mot;
	}

	public Nbcaract�re(String mot) {
		this.mot = mot;
	}
	
	public String cinquante() {
		if(mot.length() <= 50)
			return "On peut utiliser ce mot" ; 
		else
			return "Ce mot est trop long";			
	}
}
