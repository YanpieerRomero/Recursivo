package eje02_1;

import java.util.Random;

/**
 *
 * @author Yanpieer
 */
public class Eje02_1 implements Runnable {

    public int vec[];
    public int ini;
    public int fin;
    public long sum;

    public Eje02_1(int[] vec, int ini, int fin) {
        this.vec = vec;
        this.ini = ini;
        this.fin = fin;
        this.sum = 0;
    }

    public static void main(String[] args) throws InterruptedException {
        int t = 100000000;
        int v[] = new int[t];

        //Iniciar
        Random num = new Random(1000);
        for (int i = 0; i < v.length; i++) {
            v[i] = num.nextInt();
        }

        Eje02_1 uno = new Eje02_1(v, 0, v.length / 2);
        Eje02_1 dos = new Eje02_1(v, v.length / 2, v.length);

        //Recursivo
        long inicio = System.currentTimeMillis();
        long sum = suma_recursiva(v, 0, v.length);
        long termino = System.currentTimeMillis();
        System.out.println("Tiempo recursivo = " + (termino - inicio));

        //Paralela
        inicio = System.currentTimeMillis();
        Thread h1 = new Thread(uno);
        Thread h2 = new Thread(dos);
        h1.start();
        h2.start();
        //Esperar a que termine h1 y h2
        h1.join();
        h2.join();
        termino = System.currentTimeMillis();

        System.out.println("Tiempo paralelo = " + (termino - inicio));

        System.out.println("Suma: " + (uno.sum + dos.sum));

    }

    static long suma_recursiva(int v[], int ini, int fin) {

        if (ini == fin-1) {
            return v[ini];
        } else {
            int m = (ini + fin) / 2;
            return suma_recursiva(v, ini, m) + suma_recursiva(v, m, fin);
        }
    }

    @Override
    public void run() {
        this.sum = suma_recursiva(this.vec, this.ini, this.fin);
    }
}
