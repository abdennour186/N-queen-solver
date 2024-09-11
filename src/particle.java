import java.util.Arrays;
import java.util.Random;

public class particle {
    public int maxFitness;
    public int numQueens;
    public int localBestFitness;
    public int[] position;
    public int[] localBest;
    public int[] velocity;

    public particle(int dimension) {
        this.maxFitness = (dimension * (dimension - 1)) / 2;
        this.numQueens = dimension;
        this.position = new int[dimension];
        this.velocity = new int[dimension];
        this.localBest = new int[dimension];
        Random random = new Random();
        for (int i = 0; i < dimension; i++) {
            this.localBest[i] = this.position[i] = random.nextInt(dimension);
        }
        for (int i = 0; i < dimension; i++) {
            this.velocity[i] = random.nextInt(dimension);
        }
        this.localBestFitness = this.fitness();
    }

    public int fitness() {
        int n = this.numQueens;
        int score = this.maxFitness;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // Check if the ith and jth queens are attacking each other
                if (this.position[i] == this.position[j] || Math.abs(i - j) == Math.abs(this.position[i] - this.position[j])) {
                    score--; // Decrement the score for each attacking pair
                }
            }
        }
        // update personal best if a better solution is found
        if (score > this.localBestFitness) {
            this.localBestFitness = score;
            this.localBest = position.clone();
        }
        return score;
    }
    public void velocityUpdate(int[] globalBest,float w, float c1, float c2) {
        int n = this.numQueens;
        float r1;
        float r2;
        for (int i = 0; i < n; i++) {
            r1 = (float) Math.random();
            r2 = (float) Math.random();
            this.velocity[i] = mode(Math.round((this.velocity[i] * w +
                    (this.localBest[i] - this.position[i]) * c1 * r1 +
                    (globalBest[i] - position[i]) * c2 * r2)), n);

        }
    }
    public void positionUpdate() {
        for (int i = 0; i < this.numQueens; i++) {
            // update the position of each queen
            position[i] = mode(Math.round(position[i] + velocity[i]), this.numQueens);
        }
    }

    public static int mode(int a, int b) {
        int rest = a % b;
        while (rest < 0) {rest += b;}
        return rest;
    }
    public static void afficherPlateau(int[] plateau) {
        int n = plateau.length;
        for (int k : plateau) {
            for (int j = 0; j < n; j++) {
                if (k == j + 1) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    public String toString() {
        return "position: " + Arrays.toString(this.position);
    }
}
