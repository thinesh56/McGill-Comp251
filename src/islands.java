import java.io.*;


public class islands {

    private static final String INPUT_FILE_PATH = "./testIslands.txt";
    private static final String OUTPUT_FILE_PATH = "./testIslands_solution.txt";
    private int numOfProblems;
    private boolean[][][] allPixels;


    public islands() {
        parseFile();
    }


    public void parseFile() {

        BufferedReader br = null;
        File file = new File(INPUT_FILE_PATH);

        try {

            br = new BufferedReader(new FileReader(file));

            String firstLine = br.readLine();
            this.numOfProblems = Integer.parseInt(firstLine);
            this.allPixels = new boolean[this.numOfProblems][][];

            int problemIndex = 0;
            String oneLine = br.readLine();
            while (oneLine != null) {

                int height = Integer.parseInt(oneLine.split(" ")[0]);
                int width = Integer.parseInt(oneLine.split(" ")[1]);
                boolean[][] matrix = new boolean[height][width];

                oneLine = br.readLine();

                int hIndex = 0;
                while (oneLine != null && (oneLine.startsWith("#") || oneLine.startsWith("-"))) {

                    char[] cl = oneLine.toCharArray();
                    int wIndex = 0;
                    for (char c : cl) {
                        if (c == '#')
                            matrix[hIndex][wIndex] = false;
                        else if (c == '-')
                            matrix[hIndex][wIndex] = true;
                        wIndex += 1;
                    }

                    hIndex += 1;
                    oneLine = br.readLine();
                }

                this.allPixels[problemIndex] = matrix;
                problemIndex += 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean isValid(boolean[][] matrix, boolean traversed[][], int y, int x) {

        if (y >= 0 && y < traversed.length && x >= 0 && x < traversed[0].length)
            if (!traversed[y][x] && matrix[y][x])
                return true;

        return false;
    }


    public void matrixDFS(boolean[][] matrix, boolean[][] traversed, int y, int x) {

        traversed[y][x] = true;

        if (isValid(matrix, traversed, y - 1, x))
            matrixDFS(matrix, traversed, y - 1, x);

        if (isValid(matrix, traversed, y + 1, x))
            matrixDFS(matrix, traversed, y + 1, x);

        if (isValid(matrix, traversed, y, x - 1))
            matrixDFS(matrix, traversed, y, x - 1);

        if (isValid(matrix, traversed, y, x + 1))
            matrixDFS(matrix, traversed, y, x + 1);
    }


    public int solveOneIsland(int problemIndex) {

        int numOfIslands = 0;

        boolean[][] matrix = this.allPixels[problemIndex];
        int height = matrix.length;
        int width = matrix[0].length;

        boolean[][] traversed = new boolean[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix[y][x] && !traversed[y][x]) {
                    numOfIslands += 1;
                    matrixDFS(matrix, traversed, y, x);
                }
            }
        }

        return numOfIslands;
    }


    // Output the solution in a txt file
    public void writeFile() {

        BufferedWriter bw = null;
        File file = new File(OUTPUT_FILE_PATH);

        try {

            if (!file.exists()) {
                file.createNewFile();
            }

            bw = new BufferedWriter(new FileWriter(file));
            for (int i = 0; i < this.numOfProblems; i++) {
                bw.write("" + this.solveOneIsland(i));
                if (i < this.numOfProblems - 1) {
                    bw.write("\n");
                }
            }
            bw.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        long startTime = System.nanoTime();

        islands il = new islands();
        il.writeFile();

        long endtime = System.nanoTime();
        System.out.println(endtime - startTime);
    }


}
