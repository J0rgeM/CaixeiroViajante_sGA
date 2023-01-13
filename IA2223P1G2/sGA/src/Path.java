import java.util.Random;

public class Path implements IIndividual {
    Random generator = new Random(0);
    protected City[] cities;
    private int size;
    private IProblem problem;
    private double fitness;

    // A constructor that receives an array of cities and a problem. It sets the cities, the size, the
    // problem and the fitness.
    public Path(City[] cities, IProblem problem) {
        this.cities = cities;
        this.size = (int) (cities.length * 1.5);
        this.problem = problem;
        this.fitness = this.problem.fitness(this);
    }

    // A constructor that receives a int x and a problem. It sets the cities and shuffle them, the size, the
    // problem and the fitness.
    public Path(int x, IProblem problem) {
        this.size = (int) (x * 1.5);
        this.cities = new City[x];
        for (int i = 0; i < cities.length; i++) {
            cities[i] = new City((int) Math.round(generator.nextDouble() * (cities.length - 1)),
                    (int) Math.round(generator.nextDouble() * (cities.length - 1)), i);
        }
        this.problem = problem;
        this.cities = this.shuffle(0, cities.length - 1).cities;
        this.fitness = this.problem.fitness(this);
    }

   // A constructor that creates a random path.
    public Path(IProblem problem) {
        this.size = (int) Math.round(generator.nextDouble() * 50);
        this.cities = new City[(int) (size * 1.5)];
        for (int i = 0; i < cities.length; i++) {
            cities[i] = new City((int) Math.round(generator.nextDouble() * (cities.length - 1)),
                    (int) Math.round(generator.nextDouble() * (cities.length - 1)), i);
        }
        this.problem = problem;
        this.fitness = this.problem.fitness(this);
    }

    /**
     * This function is used to find the fitness of the current chromosome
     */
    private void findFitness() {
        this.fitness = this.problem.fitness(this);
    }

    /**
     * It takes two indexes and swaps the cities at those indexes
     * 
     * @param first the index of the first city to swap
     * @param second the index of the second city to swap
     * @return A new path with the cities swapped.
     */
    private Path swap(int first, int second) {
        Path result = new Path(this.cities, this.problem);
        City temp = result.cities[first];
        result.cities[first] = result.cities[second];
        result.cities[second] = temp;
        result.findFitness();
        return result;
    }

    /**
     * It takes two integers, first and second, and returns a new Path object with the cities between
     * first and second shifted
     * 
     * @param first the first index of the city to be swapped
     * @param second the index of the city to be moved
     * @return A new path is being returned.
     */
    private Path shift(int first, int second) {
        Path result = new Path(this.cities, this.problem);
        if (first > second) {
            int temp = first;
            first = second;
            second = temp;
        }
        City temp;
        for (int i = second; i - 1 != first && i > 1; i--) {
            temp = result.cities[i];
            result.cities[i] = result.cities[i - 1];
            result.cities[i - 1] = temp;
        }
        result.findFitness();
        return result;
    }

    /**
     * It takes two integers, first and second, and shuffles the cities between them
     * 
     * @param first the first index
     * @param second the second index
     * @return A Path object shuffled.
     */
    protected Path shuffle(int first, int second) {
        Random generator = new Random();
        if (first > second) {
            int temp = first;
            first = second;
            second = temp;
        }
        int secondCity;
        Path result = new Path(this.cities, this.problem);
        City temp;
        for (int i = first; i < second - 1; i++) {
            secondCity = generator.nextInt(i, second + 1);
            temp = result.cities[i];
            result.cities[i] = result.cities[secondCity];
            result.cities[secondCity] = temp;
        }
        result.findFitness();
        return result;
    }

    /**
     * It takes two integers, first and second, and reserve the path between first and second.
     * 
     * @param first the first index of the inversion
     * @param second the index of the second city to be swapped
     * @return A new Path object is being returned.
     */
    private Path inversion(int first, int second) {
        Path result = new Path(this.cities, this.problem);
        if (first > second) {
            int temp = first;
            first = second;
            second = temp;
        }
        City temp;
        for (int i = first; i < first + (second - first) / 2; i++) {
            temp = result.cities[i];
            result.cities[i] = result.cities[second - i + first];
            result.cities[second - i + first] = temp;
        }
        result.findFitness();
        return result;
    }

    /**
     * The function mutates the path by swapping, shifting, shuffling,
     *  or inverting the path and chooses the mutation with highest fitness.
     * 
     * @param pm the probability of mutation
     * @return The best path.
     */
    @Override
    public IIndividual mutation(double pm) {
        Random generator = new Random();
        if (generator.nextDouble() < pm) {
            int firstPoint = (int) Math.round(generator.nextDouble() * (cities.length - 1)),
                    secondPoint = (int) Math.round(// A random number generator.
                            generator.nextDouble() * (cities.length - 1));
            Path best = swap(firstPoint, secondPoint);
            Path temp = shift(firstPoint, secondPoint);
            if (temp.fitness > best.fitness)
                best = temp;
            temp = shuffle(firstPoint, secondPoint);
            if (temp.fitness > best.fitness)
                best = temp;
            temp = inversion(firstPoint, secondPoint);
            if (temp.fitness > best.fitness)
                best = temp;
            return best;
        }
        return this;
    }

    /**
     * returns the index of the city in the cities array, or -1 if the city is not in the array.
     * 
     * @param other The city to find the index of
     * @return The index of the city in the array.
     */
    private int findIndex(City other) {
        for (int i = 0; i < cities.length; i++) {
            if (cities[i].equals(other))
                return i;
        }
        return -1;
    }

    /**
     * It takes two parents, and creates two children by taking the first city from the first parent,
     * and then finding the index of that city in the second parent, and then taking the city at that
     * index in the first parent, and then finding the index of that city in the second parent, and so
     * on till the cycle ends.
     * 
     * @param startingPoint The starting index of the cycle
     * @param parent2 The second parent
     * @param child1 The first child to be created
     * @param child2 The second child to be created
     */
    private void cycle(int startingPoint, Path parent2, City[] child1, City[] child2) {
        int currentIndex = startingPoint;
        City start = this.cities[startingPoint];
        City currentCity = start;
        do {
            child1[currentIndex] = currentCity;
            currentIndex = parent2.findIndex(currentCity);
            currentCity = parent2.cities[currentIndex];
            child2[currentIndex] = currentCity;
            currentCity = this.cities[currentIndex];
        } while (!start.equals(currentCity));
    }

    /**
     * It takes two parents and creates two children to use in the cycles till they are fully complete.
     * 
     * @param parent2 The other parent
     * @return An array of two IIndividuals.
     */
    @Override
    public IIndividual[] crossover(IIndividual parent2) {
        if (this.equals((Path) parent2))
            return new IIndividual[] { this, parent2 };
        City[] child1 = new City[this.cities.length];
        City[] child2 = new City[this.cities.length];
        int cycleCount = 0;
        for (int i = 0; i < cities.length; i++) {
            if (child1[i] == null) {
                if (cycleCount % 2 == 0)
                    this.cycle(i, (Path) parent2, child1, child2);
                else
                    this.cycle(i, (Path) parent2, child2, child1);
            }
        }
        return new IIndividual[] { new Path(child1, problem), new Path(child2, problem) };
    }

    
    /**
     * The function returns true if the two paths are equal, and false otherwise
     * 
     * @param other The other path to compare to
     * @return The boolean value of the comparison of the two paths.
     */
    private boolean equals(Path other) {
        for (int i = 0; i < cities.length; i++)
            if (!cities[i].equals(other.cities[i]))
                return false;
        return true;
    }

    /**
     * It returns a string of the numbers 0 to the number of cities in the tournament
     * 
     * @return The indexs of the cities array.
     */
    @Override
    public String getChromosome() {
        String result = "";
        for (int i = 0; i < cities.length; i++) {
            result += i;
        }
        return result;
    }

    /**
     * This function returns the fitness of the individual.
     * 
     * @return The fitness of the individual.
     */
    @Override
    public double getFitness() {
        return this.fitness;
    }

    /**
     * This function returns a string representation of the cities coordinates in the tour
     * 
     * @return a string with the coordinates of the cities in the cities array.
     */
    public String toString() {
        String result = "";
        for (int index = 0; index < cities.length; index++) {
            result += "(" + cities[index].getX() + ", " + cities[index].getY() + "), ";
        }
        return result;
    }
}
