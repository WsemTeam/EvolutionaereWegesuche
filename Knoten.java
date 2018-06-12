import java.util.LinkedList;
import java.util.Random;
public class Knoten
{
    private String bezeichnung;
    private int distanz, x, y;
    private Knoten vorgaenger;
    private LinkedList<Kante> kanten; 
    private boolean besucht;
    private Ausgabe text;
    private Random random;
    public Knoten(String bezeichnung, int x, int y)
    {
        this.x = x; this.y = y;        
        kanten = new LinkedList<>();
        this.bezeichnung = bezeichnung;
        random = new Random();
    }
    
    public void darstellen()
    {
        text = new Ausgabe(bezeichnung, x, y, 50, 50);
    }
    
    public int gibX()
    {
        return x;
    }
    
    public int gibY()
    {
        return y;
    }
    
    public String gibBezeichnung()
    {
        return bezeichnung;
    }
    
    public int gibDistanz()
    {
        return distanz;
    }
    
    public void setzeDistanz(int distanz)
    {
        this.distanz = distanz;
    }
    
    public void initialisiere(boolean start)
    {
        setzeBesucht(false);
        vorgaenger = null;
        if(start)
        {
            distanz = 0;
        }
        else
        {
            distanz = 1000000;
        }
    }
    
    public void kanteHinzufuegen(Kante kante)
    {
        kanten.add(kante);
    }
    
    public boolean gibBesucht()
    {
        return besucht;
    }
    
    public void setzeBesucht(boolean besucht)
    {
        this.besucht = besucht;
    }
    
    public Knoten gibVorgaenger()
    {
        return vorgaenger;
    }
  
    public void update()
    {       
        setzeBesucht(true);
        for(int i = 0;i < kanten.size();i++)
        {                       
            Knoten knoten = kanten.get(i).gibAnderenKnoten(this);         
            if(distanz + kanten.get(i).gibGewicht() < knoten.gibDistanz())
            {
                knoten.setzeDistanz(distanz + kanten.get(i).gibGewicht());
                knoten.setzeVorgaenger(this);
            }
        }
    }
    
    public void setzeVorgaenger(Knoten vorgaenger)
    {
        this.vorgaenger = vorgaenger;
    }
    
    public Knoten gibNaechsten()
    {
        int aktuell = -1;
        int letztesGewicht = 1000000;
        for(int i = 0;i < kanten.size();i++)
        {
            if(kanten.get(i).gibGewicht() < letztesGewicht && !kanten.get(i).gibAnderenKnoten(this).gibBesucht())
            {
                aktuell = i;
                letztesGewicht = kanten.get(i).gibGewicht();
            }           
        }
        if(aktuell != -1)
        {            
            return kanten.get(aktuell).gibAnderenKnoten(this);
        }
        else
        {
            return null;
        }
    }
    
    public Kante gibKante(String ziel)
    {
        Kante kante = null;
        for(int i = 0;i < kanten.size();i++)
        {
            if(kanten.get(i).gibAnderenKnoten(this).gibBezeichnung().equals(ziel))
            {
                kante = kanten.get(i);
            }           
        }
        return kante;
    }
    
    public Kante gibZufaelligeKante()
    {        
        return kanten.get(random.nextInt(kanten.size()));
    }
    
    public void setzeKantenZurueck()
    {
        for(int i = 0;i < kanten.size();i++)
        {
            kanten.get(i).setzeFarbe("hellgrau");      
        }
    }
}
