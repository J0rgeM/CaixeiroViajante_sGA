public class XSquared implements IProblem {
    /**
     * The fitness function takes in a string of binary digits and returns the square of the integer
     * value of the binary string
     * 
     * @param string The individual to be evaluated.
     * @return The fitness of the individual.
     */
    @Override
    public double fitness(IIndividual string) {
        return (int) Math.pow(Integer.parseInt(string.getChromosome(), 2), 2);
    }
}
