public class MyThread extends Thread implements Runnable{
	public MyThread(String name){
		setName(name);
		setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
	}
	public void run(){
		System.out.println(Thread.currentThread().getName()+"-"+Serveur.entier);
		try {// on doit écrire le taches pour les quelles on va utiliser les threads}
		catch(InterruptedException e){
			e.printStackTrace;
		}
		}
		
	}
}
