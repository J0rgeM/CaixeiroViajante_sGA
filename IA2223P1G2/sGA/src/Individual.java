import java.util.Random;

public class Individual implements IIndividual {
    // The declaration of the variables that the class will use.
    private String chromosome;
    private double fitness = 0;
    Random rand = new Random(0);
    IProblem problem;

   // A constructor that takes a chromosome as input and creates an individual.
    public Individual(String chromosome) {
        this.chromosome = chromosome;
    }

    // A constructor that takes a chromosome and a fitness value as input and creates an individual.
    public Individual(String chromosome, IProblem problem) {
        this.chromosome = chromosome;
        this.problem = problem;
        this.fitness = this.problem.fitness(this);
    }

    // This is a constructor that takes a chromosome and a fitness value as input and creates an
    // individual.
    public Individual(String chromosome, double fitness) {
        this.chromosome = chromosome;
        this.fitness = fitness;
    }

    @Override
    // This is the mutation operator. It takes a probability as input and mutates the chromosome of the
    // individual with that probability.
    public Individual mutation(double probability) {
        char[] ind = this.chromosome.toCharArray();
        for (int i = 0; i < ind.length; i++) {
            if (rand.nextDouble() < probability) {
                if (ind[i] == '0')
                    ind[i] = '1';
                else
                    ind[i] = '0';
            }
        }
        this.chromosome = new String(ind);
        this.fitness = this.problem.fitness(this);
        return this;
    }

    @Override
    // This is the crossover operator. It takes another individual as input and returns two children.
    public Individual[] crossover(IIndividual parent2) {
        int point = (int) Math.round(rand.nextDouble() * (this.chromosome.length() - 1));
        String child1 = this.chromosome.substring(0, point) + parent2.getChromosome().substring(point);
        String child2 = parent2.getChromosome().substring(0, point) + this.chromosome.substring(point);
        return new Individual[] { new Individual(child1, problem.fitness(new Individual(child1))),
                new Individual(child2, problem.fitness(new Individual(child2))) };
    }

    /**
     * > The function takes two parents, if the random "f" is less then 0.5 
     * it switch each other char's and at the end it returns two children as results of the crossover
     * 
     * @param parent2 the other parent to cross
     * @return An array with the results, the childrens.
     */
    public Individual[] uniformCrossover(Individual parent2) {
        char[] child1 = this.chromosome.toCharArray(), child2 = parent2.getChromosome().toCharArray();
        double f;
        char temp;
        for (int i = 0; i < this.chromosome.length(); i++) {
            f = rand.nextDouble();
            if (f < 0.5) {// head, exchange info
                temp = child1[i];
                child1[i] = child2[i];
                child2[i] = temp;
            }
        }
        return new Individual[] { new Individual(new String(child1)), new Individual(new String(child2)) };
    }

    @Override
    // It returns the chromosome of the individual.
    public String getChromosome() {
        return chromosome;
    }

    @Override
    // It returns the fitness of the individual.
    public double getFitness() {
        return this.fitness;
    }

    @Override
    // A method that returns a string representation of the individual.
    public String toString() {
        return this.chromosome + " " + this.fitness;
    }
}