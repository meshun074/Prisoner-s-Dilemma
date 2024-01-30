public class Chromosome {
    private String strategy;
    private double fittness;

    public Chromosome(String strategy, double fitness) {
        this.strategy = strategy.replaceAll("[^CD]","");
        this.fittness = fitness;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy.replaceAll("[^CD]","");
    }

    public double getFitness() {
        return fittness;
    }

    public void setFitness(double fittness) {
        this.fittness = fittness;
    }
}
