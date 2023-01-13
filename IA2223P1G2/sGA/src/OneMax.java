public class OneMax implements IProblem {
    /**
     * The fitness function evaluated the individual by the number of 1's in the string
     * 
     * @param string The individual to be evaluated.
     * @return The number of 1's in the string.
     */
    @Override
    public double fitness(IIndividual string) {
        int count = 0;
        String temp = string.getChromosome();
        for (int i = 0; i < temp.length(); i++) {
            if (temp.charAt(i) == '1')
                count++;
        }
        return count;
    }
    
}
