public class PsoSolution {
    long numiter;
    particle parti;
    boolean optimal;
    public PsoSolution(long numiter, particle part, boolean optimal){
        this.numiter = numiter;
        this.parti = part;
        this.optimal = optimal;
    }
    public String toString() {
        String returned= new String();
        if (this.optimal == true) {
            returned = "the solution " + this.parti + " is optimal and was found on the " + this.numiter + "th iteration";
        }
        if (this.optimal == false){
            returned = "the solution "+ this.parti +" is not optimal and its fitness is "+ this.parti.fitness();
        }
        return returned;
    }
}
