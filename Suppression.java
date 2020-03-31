package projetjeu;
// NE FONCTIONNE PAS 
import java.util.Random;

public class Suppression {
	private String message ;
	
	
	public Suppression(String message) {
		this.message = message;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}



	public String supprimer () {
		while(message.length() != (message.length()*0.7)) {
			Random random = new Random();
			int nb;
			nb = random.nextInt(message.length());
			message = message.remove(nb);
			return message ;
			}
		}

	
	

}
