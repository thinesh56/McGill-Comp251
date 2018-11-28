import java.io.*;
import java.util.*;


public class balloon {

    private static final String INPUT_FILE_PATH = "./testBalloons.txt";
    private static final String OUTPUT_FILE_PATH = "./testBalloons_solution.txt";
    private int numOfProblems;
    private int[] numOfBalloons;
    private ArrayList<ArrayList<Integer>> listOfBalloons;


    public balloon() {
        parseFile();
    }


    // Parse the input txt file & initialize the fields
    public void parseFile() {

        BufferedReader br = null;
        File file = new File(INPUT_FILE_PATH);

        try {

            br = new BufferedReader(new FileReader(file));

            // Assign the number of problems
            String firstLine = br.readLine();
            this.numOfProblems = Integer.parseInt(firstLine);
            this.numOfBalloons = new int[this.numOfProblems];

            // Assign the number of balloons in each problem
            String secondLine = br.readLine();
            String[] sl = secondLine.split(" ");
            for (int n = 0; n < sl.length; n++) {
                this.numOfBalloons[n] = Integer.parseInt(sl[n]);
            }

            this.listOfBalloons = new ArrayList<ArrayList<Integer>>(this.numOfProblems);

            // Store the balloons of each problem in an ArrayList
            int index = 0;
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                String[] l = line.split(" ");

                ArrayList<Integer> al = new ArrayList<Integer>(this.numOfBalloons[index]);
                for (String e : l) {
                    al.add(Integer.parseInt(e));
                }
                this.listOfBalloons.add(al);

                index += 1;
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int solveOneProblem(int problemIndex) {

        int numOfArrows = 0;
        ArrayList<Integer> balloons = this.listOfBalloons.get(problemIndex);

        while (true) {

            // Set initial height of the arrow
            int initialHeight = -1;
            int initialSlot = 0;

            for (int i = 0; i < balloons.size(); i++) {
                int balloonHeight = balloons.get(i);
                if (balloonHeight != -1) {
                    initialHeight = balloonHeight;
                    initialSlot = i;
                    break;
                }
            }

            // Break when all slots are -1, i.e. all balloons have been shot
            if (initialHeight == -1) {
                break;
            }

            // Shoot the arrow
            int arrowHeight = initialHeight;
            for (int slot = initialSlot; slot < balloons.size(); slot++) {

                if (arrowHeight == 0)
                    break;

                if (balloons.get(slot) == arrowHeight) {
                    balloons.set(slot, -1);
                    arrowHeight -= 1;
                }
            }

            numOfArrows += 1;
        }

        return numOfArrows;
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
                bw.write("" + this.solveOneProblem(i));
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
        balloon b = new balloon();
        b.writeFile();
    }

}
