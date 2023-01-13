import java.util.ArrayList;

public class sGA {
    private int s, generations;
    private double pm, pc;

    // A constructor, it is used to create an object of the class sGA, with s(tournaments), pm(probability of mutation), pc(probability of crossover) and generations.
    public sGA(int s, double pm, double pc, int generations) {
        this.s = s;
        this.pm = pm;
        this.pc = pc;
        this.generations = generations;
    }

    // A constructor, it is used to create an object of the class sGA, with s(tournaments), pm(probability of mutation) and pc(probability of crossover).
    public sGA(int s, double pm, double pc) {
        this.s = s;
        this.pm = pm;
        this.pc = pc;
        this.generations = 1;
    }

    /**
     * It takes an arraylist of individuals, and a problem, and then it creates a population from the
     * arraylist, and then it prints the population, without any changes.
     * Now we work with that population, doing a tournament selection, a crossover, mutatations, and print
     * them all again to see differences, n times depending on the generations. At the end we get the best one.
     * 
     * @param individuals the population of individuals
     * @param problem the problem to be solved
     */
    final void generation(ArrayList<IIndividual> individuals, IProblem problem) {
        Population inds = new Population(individuals), selected;
        System.out.print("0: ");
        IIndividual temp = inds.print(), max = inds.print();
        inds.print();
        for (int i = 1; i < generations; i++) {
            selected = inds.tournamentWithoutReplacement(s);
            selected.populationCrossover(pc, problem);
            selected.individuals.forEach(a -> a.mutation(pm));
            System.out.print(i + ": ");
            temp = selected.print();
            if (temp.getFitness() > max.getFitness()) max = temp;
            inds = selected;
        }
        System.out.println("best one: " + max.toString() + "fitness: " + max.getFitness());
    }
}