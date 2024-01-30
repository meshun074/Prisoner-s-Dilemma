import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
        double total = 0.0;
        for (Chromosome opponent:strategies) {
            total+=simulateMatch(c.getStrategy(),opponent.getStrategy());
        }
//        System.out.println(total/strategies.size()+" "+total);
    return total/strategies.size();
    }

    private static double simulateMatch(String c, String opponent) {
        //System.out.println(c+" "+opponent);
        HashMap<String, Integer> records = Population.getRecords();
        ArrayList<String> c1 = new ArrayList<>(List.of(c.substring(64).split("")));
        ArrayList<String> c2 = new ArrayList<>(List.of(opponent.substring(64).split("")));
        char c1Move;
        char c2Move;
        StringBuilder moveList = new StringBuilder();
        for(int p=0; p<5; p++){
            c1Move = c.charAt(records.get(Arrays.toString(new ArrayList<>(c1).toArray()).replaceAll("[^CD]", "")));
            c2Move = opponent.charAt(records.get(Arrays.toString(new ArrayList<>(c2).toArray()).replaceAll("[^CD]", "")));
            c1.add(c1Move+"");
            c1.add(c2Move+"");
            c2.add(c2Move+"");
            c2.add(c1Move+"");
            moveList.append(c1Move).append(c2Move);
            for(int j=0; j<2; j++){
                c1.remove(0);
                c2.remove(0);
            }
        }
        return generateScore(moveList);
    }

    private static double generateScore(StringBuilder moveList) {
//        System.out.println(moveList);
        double total =0.0;
        for(int i=0; i<moveList.length(); i+=2){
            //System.out.println(moveList.charAt(i)+" "+moveList.charAt(i+1));
            if(moveList.charAt(i)=='C'&&moveList.charAt(i+1)=='C')
            {
                total+=3;
            }else if(moveList.charAt(i)=='D'&&moveList.charAt(i+1)=='C')
            {
                total+=5;
            }
            else if(moveList.charAt(i)=='D'&&moveList.charAt(i+1)=='D')
            {
                total+=1;
            }
        }
        return total;
    }
}
