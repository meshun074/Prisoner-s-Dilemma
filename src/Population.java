import java.util.ArrayList;
import java.util.HashMap;

public class Population {
    public final static HashMap<String, Integer> record = new HashMap<>();
    public static ArrayList<String> Initialize(int pop_size, int chromosome_Len){
        ArrayList<String> population = new ArrayList<>();
        StringBuilder string;
        for(int i=0; i<pop_size; i++){
            string = new StringBuilder();
            for(int k = 0; k<chromosome_Len; k++)
                string.append(Math.random()>0.5?"C":"D");
            population.add(String.valueOf(string));
        }
        updateRecords();
        return population;
    }

    private static void updateRecords() {
        ArrayList<StringBuilder> pastRecords =new ArrayList<>();
        int num = 32;
        String symbol = "D";
        for (int i=0; i<6; i++) {
            for (int k = 0; k<64; k++) {
                if(k%num==0)
                    symbol=symbol.equals("C")?"D":"C";
                if (i == 0) {
                    pastRecords.add(new StringBuilder(symbol));
                }
                else {
                    pastRecords.get(k).append(symbol);
                }
            }
            num/=2;
        }
        for(int i=0; i<pastRecords.size(); i++)
            record.put(String.valueOf(pastRecords.get(i)),i);
    }

    public static HashMap<String, Integer> getRecords(){
        return record;
    }
}
