import java.util.Arrays;
import java.util.Random;
public class Genome {
    public int[] board;
    public int numQueens;
    public int maxFitness;
    public  Genome(int size){
        this.numQueens = size;
        this.maxFitness =  (size * (size - 1)) / 2;
        this.board = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            this.board[i] = random.nextInt(size);
        }
    }
    public int fitness(){
        int n = this.numQueens;
        int score = this.maxFitness;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // Check if the ith and jth queens are attacking each other
                if (this.board[i] == this.board[j] || Math.abs(i - j) == Math.abs(this.board[i] - this.board[j])) {
                    score--; // Decrement the score for each attacking pair
                }
            }
        }

        return score;
    }
    public void mutate() {
        int n = this.numQueens;

        // Choose two random positions to swap
        int position1 = (int) (Math.random() * n);
        int position2 = (int) (Math.random() * n);

        // Swap the queens at the chosen positions
        int temp = this.board[position1];
        this.board[position1] = this.board[position2];
        this.board[position2] = temp;

    }

    public String toString() {
        return "Board: " + Arrays.toString(this.board);
    }
}
