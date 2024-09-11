import java.util.Random;
public class swarm {
        public particle[] particles;
        public int globalBestFitness;
        public particle globalBest;
        public int numQueens;

        public swarm(int numParticles, int size) {
            particles = new particle[numParticles];
            globalBestFitness = 0;
            globalBest = new particle(size);
            this.numQueens = size;

            // initialize particles with random positions and velocities
            for (int i = 0; i < numParticles; i++) {
                particles[i] = new particle(size);
                int fitness = particles[i].fitness();
                if (fitness > globalBestFitness) {
                    globalBestFitness = fitness;
                    globalBest.position = particles[i].position.clone();
                }
            }

        }
    public PsoSolution runPSO(long numIterations,float w, float c1, float c2) {
        // run the PSO algorithm for the specified number of iterations
        PsoSolution solution = null;
        for (int i = 0; i < numIterations; i++) {
            for (int j = 0; j < particles.length; j++) {
                particles[j].velocityUpdate(globalBest.position,w, c1,c2);
                particles[j].positionUpdate();
                int fitness = particles[j].fitness();
                if (fitness > globalBestFitness) {
                    globalBestFitness = fitness;
                    globalBest.position = particles[j].position.clone();
                }
                if(fitness ==particles[j].maxFitness){
                    solution = new PsoSolution(i, particles[j], true);
                }
            }
            if (solution != null) {
                break;
            }
        }
        if (solution == null) {
            solution = new PsoSolution(numIterations, globalBest, false);
        }
        return solution;
    }

    public static void main(String[] args) {
        swarm a = new swarm(400, 10);
         float w = 0.7f;// Inertia
         float c1 = 1.49445f;// Personal Influence
         float c2 = 1.49445f;// Social Influence
        PsoSolution sol =  a.runPSO(1000000000,w, c1,c2);
        System.out.println(sol);
        particle.afficherPlateau(a.globalBest.position);
    }
    }


