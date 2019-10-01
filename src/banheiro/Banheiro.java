package banheiro;

public class Banheiro extends Thread {

	private static int homensDentro;
	private static int mulheresDentro;
	private static int filaHomem;
	private static int filaMulher;
	private static int sexo;

	public Banheiro(int sexo) {
		mulheresDentro = 0;
		homensDentro = 0;
		filaHomem = 0;
		filaMulher = 0;	
	}

	public synchronized void filaBanheiro(int sexo) {
		switch (sexo) {
		case 1:
			if (filaHomem < 10)
				filaHomem++;
		case 2:
			if (filaMulher < 10)
				filaMulher++;
		}
	}

	public synchronized void entraBanheiro(int sexo) {
		switch (sexo) {
		case 1:
			if (homensDentro < 3 && mulheresDentro == 0 && filaMulher == 0) {

				try {
					filaHomem--;
					homensDentro++;
					System.out.println("Um homem entrou no banheiro, total de " + homensDentro + " homem(ns)");
					wait(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (homensDentro == 3)
				System.out.println("Já existem 3 homens ocupando o banheiro");
			else if (mulheresDentro > 0)
				System.out.println("Homem, não entre! Pois existe(m) " + mulheresDentro + " mulher(es) no banheiro");

		case 2:
			if (mulheresDentro < 3 && homensDentro == 0) {

				try {
					filaMulher--;
					mulheresDentro++;
					System.out.println("Uma mulher entrou no banheiro, total de " + mulheresDentro + " mulher(es)");
					wait(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (mulheresDentro == 3)
				System.out.println("Já existem 3 mulheres ocupando o banheiro");
			else if (homensDentro > 0)
				System.out.println("Mulher, não entre! Pois existe(m) " + homensDentro + " homem(ns) no banheiro");
		}
	}

	public synchronized void saiBanheiro(int sexo) {
		switch (sexo) {
		case 1:
			if (homensDentro > 0) {
				homensDentro--;
				System.out.println("Um homem saiu do banheiro, total de " + homensDentro + " homem(en)");
				notify();
			}
		case 2:
			if (mulheresDentro > 0) {
				mulheresDentro--;
				System.out.println("Uma mulher saiu do banheiro, total de " + mulheresDentro + " mulher(es)");
				notify();
			}
		}
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			if (sexo == 1) {
				try {
					filaBanheiro(sexo);
					entraBanheiro(sexo);
					sleep((long) (Math.random() * 2));
					saiBanheiro(sexo);
					sleep((long) (Math.random() * 2));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				try {
					filaBanheiro(sexo);
					entraBanheiro(sexo);
					sleep((long) (Math.random() * 2));
					saiBanheiro(sexo);
					sleep((long) (Math.random() * 3));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		Banheiro mulher = new Banheiro(2);
		Banheiro homem = new Banheiro(1);

		homem.start();
		mulher.start();
	}
}