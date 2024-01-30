import java.util.ArrayList;
import java.util.List;

public class Evaluation {
    public static void Fitness(ArrayList<Chromosome> strategies){
        for(int i=0; i<strategies.size(); i++)
        {
            for (Chromosome c:strategies) {
                c.setFitness(playOff(c, strategies));
            }
        }
    }

    private static double playOff(Chromosome c, ArrayList<Chromosome> strategies) {
        double total = 0;
        for (Chromosome opponent:strategies) {
            total+=simulateMatch(c,opponent);
        }
    return total/strategies.size();
    }

    private static double simulateMatch(Chromosome c, Chromosome opponent) {
        ArrayList<String> c1 = new ArrayList<>(List.of(c.getStrategy().split("")));
        return 0.0;
    }
}
