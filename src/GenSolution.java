public class GenSolution {
    long Generation;
    Genome geno;
    boolean optimal;
    public GenSolution(long generation, Genome geno, boolean optimal){
        this.Generation = generation;
        this.geno = geno;
        this.optimal = optimal;
    }
    public String toString() {
        String returned= new String();
        if (this.optimal == true) {
            returned = "the solution " + this.geno + " is optimal and was found on the " + this.Generation + "th generation";
        }
        if (this.optimal == false){
            returned = "the solution "+ this.geno +" is not optimal and its fitness is "+ this.geno.fitness();
        }
        return returned;
    }
}
