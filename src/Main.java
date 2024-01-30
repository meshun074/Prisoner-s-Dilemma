import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
//        Genetic_Algorithm ga1 = new Genetic_Algorithm(300,4, 1.0F,0.1F,30);
//        ga1.start_GA();
        ArrayList<String> strings = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        for(int i=0; i<5; i++)
            strings.add(scanner.nextLine());
        System.out.println(Arrays.toString(new ArrayList<>(strings).toArray()).replaceAll("[^CD]",""));
    }
}