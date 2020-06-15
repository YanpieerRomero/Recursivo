package eje01_1;

import java.util.Random;

public class Eje01_1 implements Runnable {

    public int vec[];
    public int ini;
    public int fin;
    public long sum;

    public Eje01_1(int[] vec, int ini, int fin) {
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

        Eje01_1 uno = new Eje01_1(v, 0, v.length / 2);
        Eje01_1 dos = new Eje01_1(v, v.length / 2, v.length);

        // Secuencial
        long inicio = System.currentTimeMillis();
        long sum = sumar(v, 0, v.length);
        long termino = System.currentTimeMillis();
        System.out.println("Demoró = " + (termino - inicio));

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

        System.out.println("Demoró = " + (termino - inicio));

        System.out.println("Suma: " + (uno.sum + dos.sum));

    }

    static long sumar(int v[], int ini, int fin) {
        long s = 0;
        for (int i = ini; i < fin; i++) {
            s = s + v[i];
        }
        return s;
    }

    @Override
    public void run() {
        this.sum = sumar(this.vec, this.ini, this.fin);
    }

}
