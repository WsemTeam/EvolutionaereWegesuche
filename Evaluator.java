import java.util.LinkedList;
public class Evaluator
{
    
    public Evaluator()
    {
        
    }
    
    public int evaluiere(LinkedList<Knoten> individuum)
    {
        return berechneLaenge(individuum);
    }
    
    private int berechneLaenge(LinkedList<Knoten> individuum)
    {
        int laenge = 0;       
        for(int i = 0;i < individuum.size() - 1;i++)
        {
            Kante kante = individuum.get(i).gibKante(individuum.get(i+1).gibBezeichnung());
            if(kante == null)
            {
                return Integer.MAX_VALUE;
            }
            laenge = laenge + kante.gibGewicht();
        }
        return laenge;
    }
}
