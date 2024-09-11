import java.util.HashSet;
import java.util.Set;
public class Genetic {
    public static Genome[] create_popu(int popu_size, int Geno_size){
        Genome[] population = new Genome[popu_size];
        for(int i = 0; i<popu_size; i++){
            Genome gen = new Genome(Geno_size);
            population[i] = gen;
        }
        return population;
    }
    public static Genome crossover(Genome parent1, Genome parent2) {
        int n = parent1.numQueens;
        Genome child = new Genome(n);

        // Choose a random crossover point between 1 and n-1
        int crossoverPoint = (int) (Math.random() * (n - 1)) + 1;
        //System.out.println(crossoverPoint);
        // Copy the first part of parent1 to the child
        for (int i = 0; i < crossoverPoint; i++) {
            child.board[i] = parent1.board[i];
        }

        // Copy the remaining part of parent2 to the child
        for (int i = crossoverPoint; i < n; i++) {
            child.board[i] = parent2.board[i];
        }

        // Fix any duplicate queen positions in the child
        Set<Integer> usedPositions = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (!usedPositions.add(child.board[i])) {
                // The ith queen position is a duplicate, so find a new position
                for (int j = 0; j < n; j++) {
                    if (usedPositions.add(j)) {
                        child.board[i] = j;
                        break;
                    }
                }
            }
        }

        return child;
    }

    public static Genome[] PickParent(Genome[] population) {
        Genome[] parents = new Genome[2];
        int totalFitnessScore = 0;
        for (int i = 0; i < population.length; i++) {
            totalFitnessScore += population[i].fitness();
        }

        // Select the first parent based on fitness score
        int accumulatedFitnessScore = 0;
        double randomValue = Math.random() * totalFitnessScore;
        for (int i = 0; i < population.length; i++) {
            accumulatedFitnessScore += population[i].fitness();
            if (accumulatedFitnessScore >= randomValue) {
                parents[0] = population[i];
                break;
            }
        }

        // Select the second parent based on fitness score, avoiding duplicates
        do {
            accumulatedFitnessScore = 0;
            randomValue = Math.random() * totalFitnessScore;
            for (int i = 0; i < population.length; i++) {
                if (population[i] != parents[0]) {
                    accumulatedFitnessScore += population[i].fitness();
                    if (accumulatedFitnessScore >= randomValue) {
                        parents[1] = population[i];
                        break;
                    }
                }
            }
        } while (parents[1] == null);

        return parents;
    }
    private static Genome getFittestGenome(Genome[] population) {
        Genome fittest = population[0];
        for (Genome genome : population) {
            if (genome.fitness() > fittest.fitness()) {
                fittest = genome;
            }
        }
        return fittest;
    }
    private static int getLeastfit(Genome[] population) {
        Genome least = population[0];
        int Ind = 0;
        for (int i =0; i<population.length;i++) {
            if (population[i].fitness() <= least.fitness()) {
                least = population[i];
                Ind = i;
            }
        }
        return Ind;
    }
    public static GenSolution Genetic_NQueen(
            int popu_size,
            int Geno_size,
            double mutation_proba,
            long Max_generations){
        GenSolution genSolution = null;

        // Create initial population
        Genome[] population = create_popu(popu_size, Geno_size);

        // Evolve population for Max_generations generations
        for (int generation = 0; generation < Max_generations; generation++) {

            // Select two parents for crossover
            Genome[] parents = PickParent(population);
            // Create a child genome through crossover
            Genome child = crossover(parents[0], parents[1]);

            // Mutate the child with given probability
            if (Math.random() < mutation_proba) {
                child.mutate();
            }

            // Replace the least fit genome in the population with the child
            population[getLeastfit(population)] = child;

            // Check if any genome in the population is a solution
            for (Genome genome : population) {
                if (genome.fitness() == genome.maxFitness) {
                    genSolution = new GenSolution(generation, genome, true);
                    break;
                }
            }

            // If solution is found, terminate evolution
            if (genSolution != null) {
                break;
            }
        }
        if (genSolution == null) {
            genSolution = new GenSolution(Max_generations, getFittestGenome(population), false);
        }
        return genSolution;
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

}
