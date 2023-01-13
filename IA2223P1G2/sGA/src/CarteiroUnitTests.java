import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

public class CarteiroUnitTests {
    @Test
    public void testConstructor() {
        ArrayList<IIndividual> individuals1 = new ArrayList<IIndividual>();
        City lisboa = new City(1, 0, 1);
        City sintra = new City(1, 0, 1);
        City porto = new City(2, 0, 1);
        assertEquals(lisboa.distance(porto), 1,0); 
        assertTrue(lisboa.equals(sintra)); 
        Path carteiroPaulo = new Path(new City[]{lisboa, sintra, porto}, new BestDist());
        assertEquals(carteiroPaulo.getFitness(), 1,0);
        assertEquals(carteiroPaulo.getChromosome(), "012");
        assertEquals(carteiroPaulo.toString(), "(1, 0), (1, 0), (2, 0), ");
        for (int i = 0; i < 5; i++) { // 5 INVIDIVUDOS COM 5 CITIES
            individuals1.add(new Path(5, new BestDist()));
        }
    }
} 