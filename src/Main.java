import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> size=new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50, 60));
        StringBuilder st =new StringBuilder("");
        PsoSolution sol = null;
        for(int s :size) {
            long time=0;
            int fitness=0;
            double opti = 0.0;
            for(int i=0;i<7;i++) {
                long startTime = System.currentTimeMillis();
//                sol = Genetic.Genetic_NQueen(100, s, 0.6, 100000);
                swarm a = new swarm(100, s);
                sol =  a.runPSO(15000,0.4f, 1.143f,1.3546f);
                long endTime = System.currentTimeMillis();

                time=time+endTime-startTime;
                fitness=fitness+sol.parti.fitness();
                if (sol.optimal) opti += 1;
            }
            time=time/3;
            fitness=fitness/3;
            opti = opti/10;


            st.append("size= "+ s + ",Time = " +time +",fitness= "+sol.parti.fitness()+", optimalRate= "+opti +"\n");

        }
        try{
            FileWriter writer = new FileWriter("finale.txt");
            writer.write(st.toString());
            writer.close();
            System.out.print("end");
        }catch(IOException e){
            System.out.print("error");
        }

        }
    }
