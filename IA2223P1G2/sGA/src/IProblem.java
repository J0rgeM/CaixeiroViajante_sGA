public interface IProblem {
    /**
     * > The fitness function is a function evaluates the individual fitness
     * 
     * @param Individual The individual to be evaluated.
     * @return The fitness of the individual.
     */
    double fitness(IIndividual Individual);
}