import java.util.ArrayList;
import java.util.Random;

public class Utility {

    /**
     * This class extracts common method(s) that are used across different classes
     */

    /**
     * generates an array of random integers
     * @param bound - the desired max number generated should be bound - 1
     * @param size - desired size of the array
     * @return - array list of random indexes
     */
    public static ArrayList<Integer> randomIntArray(int bound, int size) {
        Random rand = new Random();
        ArrayList<Integer> randomNums = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int randInt = rand.nextInt(bound);
            while (randomNums.contains(randInt)) {
                randInt = rand.nextInt(bound);
            }
            randomNums.add(randInt);
        }
        return randomNums;
    }
}
