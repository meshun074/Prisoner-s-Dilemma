import java.util.ArrayList;
import java.util.HashMap;

public class Population {
    public final static HashMap<String, String> record = new HashMap<>();
    public static ArrayList<String> Initialize(int pop_size, int chromosome_Len){
        ArrayList<String> population = new ArrayList<>();
        StringBuilder string = new StringBuilder();
        for(int i=0; i<pop_size; i++){
            for(int k = 0; k<chromosome_Len; k++)
                string.append(Math.random()>0.5?"C":"D");
            population.add(String.valueOf(string));
        }

        return population;
    }
    public static HashMap<String, String> getRecords(){
        return  record;
    }
}
