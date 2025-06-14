public class InmultireMatriceMultitreaded {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Numar incorect de argumente!" +
                    " Utilizare: <rang matrice> <nr. blocuri>");
            System.exit(-1);
        }

        int n = Integer.parseInt(args[0]);
        int nrBlocuri = Integer.parseInt(args[1]);
        System.out.println("Rangul matricelor de inmultit: "+ n);
        System.out.println("Nr. blocuri pentru descompunere matrici: " +
                nrBlocuri);

        double[][] x = new double[n][n],
                y = new double[n][n],
                z = new double[n][n];

        // populare matrice x si y cu valori aleaqtoare intre [-5, 5)
        for (int i=0; i<n; i++) {
            for (int j = 0; j < n; j++) {
                // f(x) = a + x (b-a), pentru x in [0, 1], f(x) in [-5, 5]
                x[i][j] = Math.random() * 10 - 5;
                y[i][j] = Math.random() * 10 - 5;
            }
        }

        System.out.println("Inmultire matrici pe un singur fir!");
        long contorStart = System.nanoTime();
        FirInmultire inmultirePeUnFir = new FirInmultire(x, y, z,
                0, n-1, 0, n-1);
        inmultirePeUnFir.start();
        try {
            inmultirePeUnFir.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Timp executie pe 1 thread: " +
                (System.nanoTime()-contorStart)/1000000);
        // timp executie in milisecunde

        System.out.println("Inmultire matrici pe " + nrBlocuri*nrBlocuri +
                " blocuri (fire de executie)");
        contorStart = System.nanoTime();
        int nrBlocuriLinie = n / nrBlocuri;
        int nrBlocuriCol = n / nrBlocuri;

        FirInmultire[][] matriceFire = new FirInmultire[nrBlocuri][nrBlocuri];

        for (int i=0; i<nrBlocuri; i++) {
            int indexLinieStart = i * nrBlocuriLinie;
            int indexLinieEnd = (i+1) * nrBlocuriLinie - 1;
            if (i==nrBlocuri-1)
                indexLinieEnd = n-1;
            for (int j=0; j<nrBlocuri; j++) {
                int indexColStart = j * nrBlocuriCol;
                int indexColEnd = (j+1) * nrBlocuriCol - 1;
                if (j==nrBlocuri-1)
                    indexColEnd = n-1;
                matriceFire[i][j] = new FirInmultire(x, y, z,
                        indexLinieStart, indexLinieEnd,
                        indexColStart, indexColEnd);
                matriceFire[i][j].start();
            }
        }

        for (int i=0; i<nrBlocuri; i++) {
            for (int j=0; j<nrBlocuri; j++) {
                try {
                    matriceFire[i][j].join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        System.out.println("Timp executie pe " + nrBlocuri*nrBlocuri
        + " fire: " + (System.nanoTime()-contorStart)/1000000);
    }
}
