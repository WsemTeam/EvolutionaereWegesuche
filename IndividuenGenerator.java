import java.util.Random;
import java.util.LinkedList;
public class IndividuenGenerator
{
    private Random random;
    public IndividuenGenerator()
    {
        random = new Random();
    }

    public LinkedList<Knoten> gibZufaelligesIndividuum(Knoten start, Knoten ziel)
    {
        LinkedList<Knoten> individuum = new LinkedList();
        individuum.add(start);
        int i = 0;
        Knoten naechsterKnoten = null;
        do
        {
            naechsterKnoten = individuum.get(i).gibZufaelligeKante().gibAnderenKnoten(individuum.get(i));
            individuum.add(naechsterKnoten);
            i++;
        }while(naechsterKnoten != ziel);
        return individuum;
    }
}
