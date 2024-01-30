import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Genetic_Algorithm {
    private final int popSize;
    private final int TSrate;
    private final float crossoverRate;
    private final int No_Gen;
    private final float mutationRate;
    private ArrayList<Chromosome> population = new ArrayList<>();
    private final ArrayList<Double> genBest = new ArrayList<>();
    private ArrayList<Chromosome> newPopulation;
    private final Random rand = new Random();

    public Genetic_Algorithm(int popSize, int TSrate, float crossoverRate, float mutationRate, int No_Gen) {
        this.popSize = popSize;
        this.TSrate = TSrate;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.No_Gen = No_Gen;
    }
    public void start_GA(){
        //Population Initialization
        for (String string: Population.Initialize(popSize,70))
            population.add(new Chromosome(string,0.0));
        // loop generation
        for (int i=0; i<No_Gen; i++) {
            newPopulation = new ArrayList<>();
            //Evaluate fitness
            Evaluation.Fitness(population);
            //sort population
            population = new ArrayList<>(sortPop(population));
            //output generation best;
            System.out.println(" Generation " + i+1 + " " + population.get(0).getFitness());
            // Store generation best
            genBest.add(population.get(0).getFitness());
            if(i==No_Gen-1)break;
            //allow elitism
            //implement elitism
            newPopulation.addAll(elitismPop(population));
            //Tournament Selection
            List<Chromosome> tempPopulation = new ArrayList<>(tournamentSelect(population, TSrate));
            //Perform uniformCrossover and mutation
            uniformCrossoverMutation(tempPopulation, crossoverRate, mutationRate);
            //updating population for new generation
            for (int ch = 0; ch < population.size(); ch++) {
                population.get(ch).setStrategy(newPopulation.get(ch).getStrategy());
            }
        }
        LineChart.DrawChart(new ArrayList<>(genBest));
    }
    //Sort chromosome according to  fitness
    private ArrayList<Chromosome> sortPop(ArrayList<Chromosome> popu) {
        ArrayList<Chromosome> list = new ArrayList<>(popu);
        list.sort((o1, o2) -> {
            if (o1.getFitness() == o2.getFitness()) return 0;
            else if (o1.getFitness() > o2.getFitness()) return 1;
            return -1;
        });
        return list;
    }
    //sort and return 1% of the best chromosome as string
    private ArrayList<Chromosome> elitismPop(ArrayList<Chromosome> population2) {

        int rate = population2.size ()>=100?(int) (population2.size() * 0.01) : population2.size()/10;
        int counter = 0;
        ArrayList<Chromosome> elitismPopulation = new ArrayList<>();
        for (Chromosome x : population2) {
            elitismPopulation.add(new Chromosome(x.getStrategy(), x.getFitness()));
            counter++;
            if (counter == rate) break;
        }
        return elitismPopulation;
    }
    private ArrayList<Chromosome> tournamentSelect(List<Chromosome> population2, int rate) {
        ArrayList<Chromosome> TSpop = new ArrayList<>();
        ArrayList<Chromosome> temp;
        for (int i = 0; i < population.size(); i++) {
            temp = new ArrayList<>();
            for (int k = 0; k < rate; k++) {
                temp.add(population2.get(rand.nextInt(population2.size())));
            }
            temp = sortPop(temp);
            TSpop.add(new Chromosome(temp.get(0).getStrategy(), temp.get(0).getFitness()));
        }
        return TSpop;
    }
    private void uniformCrossoverMutation(List<Chromosome> newPop, float CO_Rate, float M_Rate) {
        int i2 = 1;
        int i1;
        ArrayList<String> c1;
        ArrayList<String> c2;
        ArrayList<String> ch1;
        ArrayList<String> ch2;
        while (i2 < newPop.size()) {
            i1 = i2 - 1;
            ch1 = new ArrayList<>(List.of(newPop.get(i1).getStrategy().split("")));
            ch2 = new ArrayList<>(List.of(newPop.get(i2).getStrategy().split("")));
            //ensures same parents are not used for crossover
            int check = 0;
            while (newPop.get(i1).getStrategy().equals(newPop.get(i2).getStrategy())) {
                i1 = rand.nextInt(newPop.size());
                if (check > newPop.size() / 10)
                    break;
                check++;
            }
            c1 = new ArrayList<>();
            c2 = new ArrayList<>();
//			Crossover rate
            if (Math.random() < CO_Rate) {
                for (int c = 0; c < ch1.size(); c++) {
                    if (Math.random() < 0.5) {
                        c1.add(ch2.get(c));
                        c2.add(ch1.get(c));
                    } else {
                        c1.add(ch1.get(c));
                        c2.add(ch2.get(c));
                    }
                }
            }
            //mutationRate
            if (Math.random() < M_Rate) {
                if (c1.isEmpty()) c1 = new ArrayList<>(ch1);
                Mutation(c1);
            }
            if (Math.random() < M_Rate) {
                if (c2.isEmpty()) {
                    c2 = new ArrayList<>(ch2);
                }
                Mutation(c2);
            }
            if (emptyChromosome(newPop, i1, c1)) break;
            if (emptyChromosome(newPop, i2, c2)) break;
            i2 += 2;
        }
    }
    //Perform mutation by changing 2 random character
    private void Mutation(ArrayList<String> c) {
        int num= rand.nextInt(c.size()/4);
        num=num==0?1:num;
        int index;
        for(int i = 0; i<num; i++) {
            index = rand.nextInt(c.size());
            c.set(index, Math.random()>0.5?"D":"C");
        }
    }
    private boolean emptyChromosome(List<Chromosome> newPop, int i1, ArrayList<String> c1) {
        if (!c1.isEmpty()) {
            newPopulation.add(new Chromosome(Arrays.toString(new ArrayList<>(c1).toArray()), 0.0));
        } else {
            newPopulation.add(newPop.get(i1));
        }
        return population.size() == newPopulation.size();
    }
}
