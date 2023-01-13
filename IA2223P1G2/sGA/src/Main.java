import java.util.*;
import java.text.*;

public class Main {
    /**
     * The function takes in a number of cities, creates a list of individuals, and then runs the
     * genetic algorithm on the list of individuals.
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int nCities = sc.nextInt();
        ArrayList<IIndividual> individuals = new ArrayList<IIndividual>();
        for (int i = 0; i < nCities; i++) {
            individuals.add(new Path(nCities, new BestDist()));
        }
        sGA test = new sGA(Integer.parseInt(sc.next()), Double.parseDouble(sc.next()), Double.parseDouble(sc.next()), Integer.parseInt(sc.next()));
        test.generation(individuals, new BestDist());
        DecimalFormatSymbols unusualSymbols = new DecimalFormatSymbols();
        unusualSymbols.setDecimalSeparator('.');
        sc.close();
    }
}
