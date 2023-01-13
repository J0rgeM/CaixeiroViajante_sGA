/**
 * 
 * The fitness of a Path is the inverse of the sum of the distances between the cities in the path
 */
public class BestDist implements IProblem {
    @Override
    // Calculating the fitness of a path using distances.
    public double fitness(IIndividual individual) {
        City[] cities = ((Path) individual).cities;
        double sum = 0.0;
        for (int i = 1; i < cities.length; i++)
            sum += cities[i].distance(cities[i - 1]);
        return 1/sum;
    }
}
