public class MasiveMultidimensionale {
    public static void main(String[] args) {
        System.out.println("Buna din Java?!");

        int[][][] cub = new int[][][]{
                {{1, 2}, {3, 4}, {5, 6}},
                {{1, 2}, {3, 4}, {5, 6}}
        };

        // parcurgere secventiala a elementelor cubului
        for (int[][] matrice : cub) {
            for (int[] vector : matrice) {
                for (int element : vector) {
                    System.out.print(element + "\t");
                }
                System.out.println();
            }
            System.out.println();
            System.out.println();
        }

    }
}
