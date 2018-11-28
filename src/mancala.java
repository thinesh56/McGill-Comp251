import java.io.*;
import java.util.*;


public class mancala {

    private static final String INPUT_FILE_PATH = "./testMancala.txt";
    private static final String OUTPUT_FILE_PATH = "./testMancala_solution.txt";
    private static final int CAVITIES_PER_LINE = 12;
    private int numOfProblems;
    private int[][] listOfBoards;
    private int[] solutions;


    public mancala() {
        parseFile();
    }


    // Parse the input txt file & initialize the fields
    public void parseFile() {

        BufferedReader br = null;
        File file = new File(INPUT_FILE_PATH);

        try {

            br = new BufferedReader(new FileReader(file));

            String firstLine = br.readLine();
            this.numOfProblems = Integer.parseInt(firstLine);
            this.listOfBoards = new int[this.numOfProblems][CAVITIES_PER_LINE];
            this.solutions = new int[this.numOfProblems];
            for (int i = 0; i < this.solutions.length; i++)
                this.solutions[i] = 12;

            int index = 0;
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] l = line.split(" ");

                int[] board = new int[CAVITIES_PER_LINE];
                for (int i = 0; i < board.length; i++) {
                    board[i] = Integer.parseInt(l[i]);

                }

                this.listOfBoards[index] = board;
                index += 1;
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int[] move(int[] board, int startIndex) {

        int[] moved = board.clone();
        moved[startIndex] = moved[startIndex] == 0 ? 1 : 0;
        moved[startIndex + 2] = moved[startIndex + 2] == 0 ? 1 : 0;
        moved[startIndex + 1] = 0;

        return moved;
    }


    public int countOnes(int[] board) {

        int numOfOnes = 0;
        for (int i : board) {
            if (i == 1)
                numOfOnes += 1;
        }
        return numOfOnes;
    }

    public void solveOneBoard(int[] board, int boardIndex) {

        int numOfOnesLeft = 0;

        boolean movable = false;
        for (int i = 0; i < CAVITIES_PER_LINE - 2; i++) {
            if ((board[i] == 0 && board[i + 1] == 1 && board[i + 2] == 1)
                    || (board[i] == 1 && board[i + 1] == 1 && board[i + 2] == 0)) {

                solveOneBoard(move(board, i), boardIndex);
                movable = true;
            }
        }

        int numOfOnes = countOnes(board);
        if (numOfOnes < this.solutions[boardIndex])
            this.solutions[boardIndex] = numOfOnes;

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
            for (int n = 0; n < this.numOfProblems; n++) {
                this.solveOneBoard(this.listOfBoards[n], n);
            }
            for (int i = 0; i < this.numOfProblems; i++) {
                bw.write("" + this.solutions[i]);
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

        mancala m = new mancala();
        m.writeFile();

        long endtime = System.nanoTime();
        System.out.println(endtime - startTime);
    }
}
