
public class Kante
{
    private int gewicht;
    private Knoten knoten1, knoten2;
    private Linie linie;
    public Kante(Knoten knoten1, Knoten knoten2, int gewicht)
    {
        this.knoten1 = knoten1;
        this.knoten2 = knoten2;
        this.gewicht = gewicht;
        linie = new Linie(knoten1.gibX() + 25, knoten1.gibY() + 25, knoten2.gibX() + 25, knoten2.gibY() + 25);
    }
    
    public int gibGewicht()
    {
        return gewicht;
    }
    
    public void setzeFarbe(String farbe)
    {
        linie.setzeFarbe(farbe);
    }
    
    public Knoten gibAnderenKnoten(Knoten knoten)
    {
        if(knoten.gibBezeichnung().equals(knoten1.gibBezeichnung()))
        {
            return knoten2;
        }
        else
        {
            return knoten1;
        }
    }
}
