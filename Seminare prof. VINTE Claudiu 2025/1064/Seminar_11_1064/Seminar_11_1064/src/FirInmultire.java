public class FirInmultire extends Thread {
    private double[][] x;
    private double[][] y;
    private double[][] z;
    private int indexLinieStart;
    private int indexLinieEnd;
    private int indexColStart;
    private int getIndexColEnd;

    public FirInmultire(double[][] x, double[][] y, double[][] z,
                        int indexLinieStart, int indexLinieEnd,
                        int indexColStart, int getIndexColEnd) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.indexLinieStart = indexLinieStart;
        this.indexLinieEnd = indexLinieEnd;
        this.indexColStart = indexColStart;
        this.getIndexColEnd = getIndexColEnd;
    }

    @Override
    public void run() {
        super.run();
        for (int i=indexLinieStart; i<=indexLinieEnd; i++) {
            for (int j=indexColStart; j<=getIndexColEnd; j++) {
                z[i][j] = 0.0;
                for (int k=0; k<x.length; k++)
                    z[i][j] += x[i][k] * y[k][j];
            }
        }
    }
}
