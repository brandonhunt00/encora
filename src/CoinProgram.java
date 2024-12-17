import java.util.Scanner;

public class CoinProgram {

    // A simple custom Set-like class to store unique combinations of coins.
    static class MySet {
        private int[][] elements; // Stores each combination.
        private int size;         // How many combinations are currently stored.

        public MySet() {
            // Initialize with capacity 10.
            elements = new int[10][];
            size = 0;
        }

        /**
         * Adds a new combination to the set if not already present.
         * @param combination an array [q, d, n, p] representing one combination
         * @return true if added, false if already exists
         */
        public boolean add(int[] combination) {
            if (contains(combination)) {
                return false;
            }
            ensureCapacity();
            elements[size] = combination;
            size++;
            return true;
        }

        /**
         * Checks if a given combination already exists in the set.
         * @param combination the combination to check
         * @return true if it exists, false otherwise
         */
        private boolean contains(int[] combination) {
            for (int i = 0; i < size; i++) {
                if (areEqual(elements[i], combination)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Checks if two combinations are equal by comparing each element.
         * @param a first combination
         * @param b second combination
         * @return true if identical, false otherwise
         */
        private boolean areEqual(int[] a, int[] b) {
            if (a.length != b.length) return false;
            for (int i = 0; i < a.length; i++) {
                if (a[i] != b[i]) return false;
            }
            return true;
        }

        /**
         * Ensures the internal array can store at least one more combination.
         * If not, it doubles the array size.
         */
        private void ensureCapacity() {
            if (size >= elements.length) {
                int newCapacity = elements.length * 2;
                int[][] newArr = new int[newCapacity][];
                for (int i = 0; i < size; i++) {
                    newArr[i] = elements[i];
                }
                elements = newArr;
            }
        }

        /**
         * Returns all stored combinations as a 2D array.
         * @return a 2D int array of all combinations
         */
        public int[][] toArray() {
            int[][] result = new int[size][];
            for (int i = 0; i < size; i++) {
                result[i] = elements[i];
            }
            return result;
        }
    }

    /**
     * Computes all combinations of quarters(25c), dimes(10c), nickels(5c), and pennies(1c)
     * that sum up to n cents. It returns these combinations in a custom MySet to avoid duplicates.
     * @param n the total amount in cents to make change for
     * @return a MySet containing arrays [q, d, n, p]
     */
    public static MySet makeChange(int n) {
        MySet result = new MySet();
        for (int q = 0; q <= n / 25; q++) {
            int afterQuarters = n - q * 25;
            for (int d = 0; d <= afterQuarters / 10; d++) {
                int afterDimes = afterQuarters - d * 10;
                for (int ni = 0; ni <= afterDimes / 5; ni++) {
                    int afterNickels = afterDimes - ni * 5;
                    int p = afterNickels;
                    if (q * 25 + d * 10 + ni * 5 + p == n) {
                        int[] combo = {q, d, ni, p};
                        result.add(combo);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Main method:
     * - Reads an integer (cents) from the user
     * - Calls makeChange to find all combinations
     * - Prints each combination
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the amount in cents: ");
        int n = scanner.nextInt();
        scanner.close();

        MySet ways = makeChange(n);
        int[][] arr = ways.toArray();

        System.out.println("Ways to make " + n + " cents:");
        for (int[] combination : arr) {
            System.out.println("[" + combination[0] + "," 
                                   + combination[1] + "," 
                                   + combination[2] + "," 
                                   + combination[3] + "]");
        }
    }
}
