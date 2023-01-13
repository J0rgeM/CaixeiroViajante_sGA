import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Population {
    // Declaring the variables that will be used in the class.
    protected ArrayList<IIndividual> individuals;
    private static Random rand = new Random(0);
    private Population selected;
    private int n;
    
    // A constructor that initializes the individuals array list and the n variable.
    public Population() {
        this.individuals = new ArrayList<IIndividual>();
        this.n = individuals.size();
    }
    
    // A constructor that initializes the individuals array list,
    // add all the given ones and the n variable with the individuals size.
    public Population(ArrayList<IIndividual> individuals) {
        this.individuals = new ArrayList<IIndividual>();
        this.individuals.addAll(individuals);
        this.n = individuals.size();
    }
    
    // Creating a n population of individuals with a given length and a given problem.
    public Population(int n, int length, IProblem problem) {
        this.individuals = new ArrayList<IIndividual>();
        for (int i = 0; i < n; i++) {
            String temp = "";
            for (int j = 0; j < length; j++) {
                if (rand.nextDouble() < 0.5) temp += '0';
                else temp += '1';
            }
            individuals.add(new Individual(temp, problem));
        }
        this.n = individuals.size();
    }

    // Creating a n population of individuals with a given length.
    public Population(int n, int length) {
        this.individuals = new ArrayList<IIndividual>();
        for (int i = 0; i < n; i++) {
            String temp = "";
            for (int j = 0; j < length; j++) {
                if (rand.nextDouble() < 0.5) temp += '0';
                else temp += '1';
            }
            individuals.add(new Individual(temp));
        }
        this.n = individuals.size();
    }
    
    // Creating a n population of individuals with a given length and a given fitnesses.
    public Population(int n, int length, double[] fitnesses) {
        this.individuals = new ArrayList<IIndividual>();
        for (int i = 0; i < n; i++) {
            String temp = "";
            for (int j = 0; j < length; j++) {
                if (rand.nextDouble() < 0.5) temp += '0';
                else temp += '1';
            }
            individuals.add(new Individual(temp, fitnesses[i]));
        }
        this.n = individuals.size();
    }

    /**
     * "If the random number is less than the crossover probability, then crossover the two individuals."
     * 
     * The crossover function is defined in the individual class
     * 
     * @param pc crossover probability
     * @param problem The problem that the population is trying to solve.
     * @return The population is being returned.
     */
    public Population populationCrossover(double pc, IProblem problem) {
        IIndividual[] offspring;
        for (int i = 0; i < this.n; i += 2) {
            if (rand.nextDouble() <= pc) {
                offspring = this.individuals.get(i).crossover(this.individuals.get(i + 1));
                this.individuals.set(i, offspring[0]);
                this.individuals.set(i + 1, offspring[1]);
            }
        }
        return this;
    }

    /**
     * It takes a population of individuals and randomly selects two individuals from the population.
     * It then compares the fitness of the two individuals and adds the one with the higher fitness to
     * a new population. It repeats this process n times and returns the new population
     * 
     * @param n number of individuals to select
     * @return A population of individuals.
     */
    public Population tournament(int n) {
        ArrayList<IIndividual> results = new ArrayList<IIndividual>();
        int first, second;
        for (int i = 0; i < n; i++) {
            first = (int) Math.round(rand.nextDouble() * (individuals.size() - 1)); // a + round(u * (b − a)); interval [a..b]; in this case a = 0 b = individuals.size()
            second = (int) Math.round(rand.nextDouble() * (individuals.size() - 1));
            if (individuals.get(first).getFitness() >= individuals.get(second).getFitness())
                results.add(individuals.get(first));
            else results.add(individuals.get(second));
        }
        selected = new Population(results);
        return selected;
    }

    /**
     * It takes a population of individuals, sorts them by fitness, then randomly selects individuals
     * from the population based on their fitness
     * 
     * @return A Population object.
     */
    public Population roulette() {
        ArrayList<IIndividual> results = new ArrayList<IIndividual>();
        Collections.sort(individuals, (s1, s2) -> (int) (s2.getFitness() - s1.getFitness()));
        double totalFitness = 0.0, f, sumProbs = 0.0;
        for (IIndividual individual : individuals) totalFitness += individual.getFitness();
        for (int i = 0; i < individuals.size(); i++) {
            f = rand.nextDouble();
            for (int j = 0; j < individuals.size(); j++) {
                sumProbs += individuals.get(j).getFitness()/totalFitness;
                if(sumProbs >= f) {
                    results.add(individuals.get(j));
                    break;
                }
            }
            sumProbs = 0.0;
        }
        Collections.sort(results, (s1, s2) -> s1.getChromosome().compareTo(s2.getChromosome()));
        selected = new Population(results);
        return selected;
    }

    /**
     * It takes a population of individuals and randomly shuffles them
     */
    public void randPermutation() {
        IIndividual temp;
        int second, n = this.individuals.size();
        for (int i = 0; i < n - 1; i++) {
            second = i + (int) Math.round(rand.nextDouble() * (n - 1 - i));
            temp = individuals.get(i);
            individuals.set(i, individuals.get(second));
            individuals.set(second, temp);
        }
    }

    /**
     * It takes a population of individuals, and randomly selects a subset of them, then selects the
     * best individual from that subset, and repeats this process until the population is exhausted
     * 
     * @param s tournament size
     * @return The selected population.
     */
    public Population tournamentWithoutReplacement(int s) {
        ArrayList<IIndividual> results = new ArrayList<IIndividual>();
        Population temp = new Population();
        IIndividual winner;
        for (int i = 0; i < s; i++) {
            temp.individuals.clear();
            temp.individuals.addAll(individuals);
            temp.randPermutation();
            for (int j = 0; j < individuals.size(); j += s) {
                winner = temp.individuals.get(j);
                for (int k = j + 1; k < j + s; k++) {
                    if(temp.individuals.get(k).getFitness() > winner.getFitness())
                        winner = temp.individuals.get(k);
                }
                results.add(winner);
            }
        }
        this.selected = new Population(results);
        return selected;
    }

    /**
     * It prints the maximum, average, and minimum fitness of the population
     * 
     * @return The best individual in the population.
     */
    public IIndividual print() {
        IIndividual best = individuals.get(0);
        double max = best.getFitness(), min = individuals.get(0).getFitness(), sum = 0;
        for (IIndividual individual : individuals) {
            if (individual.getFitness() > max) {
                max = individual.getFitness();
                best = individual;
            }
            if (individual.getFitness() < min)
                min = individual.getFitness();
            sum += individual.getFitness();
        }
        System.out.println(max + " " + sum/this.n + " " + min);
        return best;
    }

    /**
     * This function is used to get the individual to string
     * 
     * @return The individual value in string format
     */
    public String toString() {
        String res = "";
        for (IIndividual individual : individuals) {
            res += individual.toString() + "\n";
        }
        return res;
    }

    /**
     * This function returns the selected population
     * 
     * @return The selected population.
     */
    public Population getSelected() {
        return this.selected;
    }

    /**
     * This function returns the individuals size
     * 
     * @return individuals size.
     */
    public int size() {
        return this.n;
    }
}