public interface IIndividual {
    /**
     * It mutates the individual with a probability of pm.
     * 
     * @param pm The probability of mutation.
     * @return A new individual with a mutation applied to it.
     */
    IIndividual mutation(double pm);

    /**
     * It takes an individual and returns an array of individuals.
     * 
     * @param iIndividual The individual to crossover with.
     * @return An array of IIndividuals.
     */
    IIndividual[] crossover(IIndividual iIndividual);

    /**
     * Returns the chromosome of the variant
     * 
     * @return The chromosome of the gene.
     */
    String getChromosome();

    /**
     * Returns the fitness of the individual
     * 
     * @return The fitness of the individual.
     */
    double getFitness();

    /**
     * Returns a string representation of the object.
     * 
     * @return The string representation of the object.
     */
    String toString();
}