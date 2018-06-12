import java.util.LinkedList;
import java.util.Random;
public class GraphMatrix
{
    private int anzahlKnoten;
    private Knoten[] knoten;
    private int[][] matrix;
    private IndividuenGenerator generator;
    private Evaluator evaluator;
    private LinkedList<LinkedList> population;
    private Random random;
    public GraphMatrix(int maximaleKnoten)
    {
        anzahlKnoten = 0;
        knoten = new Knoten[maximaleKnoten];
        matrix = new int[maximaleKnoten][maximaleKnoten];     
        for(int i = 0;i < maximaleKnoten;i++)
        {
            for(int k = 0;k < maximaleKnoten;k++)
            {
                matrix[i][k] = -1;
            }
        }
        generator = new IndividuenGenerator();
        evaluator = new Evaluator();
        random = new Random();
    }
    
    public void knotenEinfuegen(String bez, int x, int y)
    {
        if(anzahlKnoten >= knoten.length)
        {
            System.out.println("Voll!");
            return;
        }      
        knoten[anzahlKnoten] = new Knoten(bez, x, y);
        matrix[anzahlKnoten][anzahlKnoten] = 0;         
        anzahlKnoten++;
    }
    
    public void knotenDarstellen()
    {
        for(int i = 0;i < anzahlKnoten;i++)
        {
            knoten[i].darstellen();
        }
    }
    
    private int knotenNummerGeben(String bez)
    {
        int ergeb = -1;
        for(int i = 0;i < anzahlKnoten;i++)
        {
            if(knoten[i].gibBezeichnung().equals(bez))
            {
                ergeb = i;
                break;
            }
        }
        return ergeb;
    }
    
    public Knoten gibKnoten(String name)
    {
        for(int i = 0;i < anzahlKnoten;i++)
        {
            if(knoten[i].gibBezeichnung().equals(name))
            {
                return knoten[i];
            }
        }
        return null;
    }
    
    public void kanteEinfuegen(String anfang, String ende, int abstand)
    {        
        int anfangNummer = knotenNummerGeben(anfang);
        int endeNummer = knotenNummerGeben(ende);
        Knoten anfangKnoten = gibKnoten(anfang);
        Knoten endeKnoten = gibKnoten(ende);
        Kante kante = new Kante(anfangKnoten, endeKnoten, abstand);
        anfangKnoten.kanteHinzufuegen(kante);
        endeKnoten.kanteHinzufuegen(kante);
        if(anfangNummer != -1 && endeNummer != -1 && anfangNummer != endeNummer)
        {
            matrix[anfangNummer][endeNummer] = abstand;
            matrix[endeNummer][anfangNummer] = abstand;
        }       
    }
    
    public void textAusgabe()
    {
        String text = "    ";
        for(int k = 0;k < anzahlKnoten;k++)
        {                
            char[] chars = (knoten[k].gibBezeichnung()+"    ").toCharArray();
            text = text + charArrayInString(chars, 4);
        }
        System.out.println(text);        
        for(int i = 0;i < anzahlKnoten;i++)
        {
            text = "";
            char[] chars = (knoten[i].gibBezeichnung()+"    ").toCharArray();
            text = charArrayInString(chars, 4);
            for(int k = 0;k < anzahlKnoten;k++)
            {           
                char[] chars2;
                if(matrix[i][k] != -1)
                {
                    chars2 = (matrix[i][k]+"    ").toCharArray();
                }
                else
                {
                    chars2 = "    ".toCharArray();
                }
                text = text + charArrayInString(chars2, 4);
            }
            System.out.println(text);
        }
    }
    
    private String charArrayInString(char[] chars, int stellen)
    {
        String text = "";
        for(int f = 0;f < stellen;f++)
        {
            text = text + chars[f];
        }
        return text;
    }
    
    public void evolutorischeSuche(String start, String ziel, int generationen)
    {
        initialisiere(start);
        population = new LinkedList();
        LinkedList<Integer> fitness = new LinkedList();        
        for(int i = 0;i < anzahlKnoten;i++)
        {
            population.add(generator.gibZufaelligesIndividuum(gibKnoten(start), gibKnoten(ziel)));
        }
        for(int k = 0;k < generationen;k++)
        {
            for(int i = 0;i < population.size();i++)
            {
                fitness.add(evaluator.evaluiere(population.get(i)));
            }
            sortierePopulation(fitness);
            System.out.println("---");
            kreuzeIndividuen(population.get(0), population.get(1));
            for(int i = 0;i < population.size();i++)
            {
                if(random.nextInt(5) == 0)
                {
                    mutiere(population.get(i));
                }
            }
        }        
        ausgabe(population.get(0));
    }
    
    private void sortierePopulation(LinkedList<Integer> fitness)
    {
        LinkedList<LinkedList> sortiertePopulation = new LinkedList();   
        int size = population.size();
        for(int i = 0;i < size;i++)
        {    
            LinkedList<Knoten> aktuellBestesIndividuum = null;
            int aktuellBesteFitness = Integer.MAX_VALUE;
            int r = 0;
            for(int k = 0;k < population.size();k++)
            {
                if(fitness.get(k) < aktuellBesteFitness)
                {
                    aktuellBesteFitness = fitness.get(k);
                    aktuellBestesIndividuum = population.get(k);
                    r = k;
                }
            }
            System.out.println(fitness.get(r));
            fitness.remove(r);
            population.remove(r);
            sortiertePopulation.add(aktuellBestesIndividuum);           
        }
        population = sortiertePopulation;
    }
    
    private void kreuzeIndividuen(LinkedList<Knoten> ahneEins, LinkedList<Knoten> ahneZwei)
    {
        LinkedList<Integer> ueberschneidungenEins = new LinkedList();
        LinkedList<Integer> ueberschneidungenZwei = new LinkedList();
        boolean[] genutzt = new boolean[ahneZwei.size()];
        for(int i = 0;i < genutzt.length;i++)
        {
            genutzt[i] = true;
        }
        LinkedList<Knoten> kindEins = new LinkedList();
        LinkedList<Knoten> kindZwei = new LinkedList();
        kindEins.add(ahneEins.getFirst());
        kindZwei.add(ahneZwei.getFirst());
               
        for(int i = 1;i < ahneEins.size();i++)
        {
            for(int k = 1;k < ahneZwei.size();k++)
            {
                if(ahneEins.get(i).equals(ahneZwei.get(k)) && genutzt[k])
                {
                    ueberschneidungenEins.add(i);
                    ueberschneidungenZwei.add(k);
                    genutzt[k] = false;
                    break;
                }
            }
        }
        int k = 1;
        int r = 1;
        boolean b = true;
        for(int i = 0;i < ueberschneidungenEins.size();i++)
        {
            while(k < ueberschneidungenEins.get(i))
            {
                if(b)
                {
                    kindEins.add(ahneEins.get(k));
                }
                else
                {
                    kindZwei.add(ahneEins.get(k));
                }
                k++;
            }
            while(r < ueberschneidungenZwei.get(i))
            {
                if(b)
                {
                    kindZwei.add(ahneZwei.get(r));
                }
                else
                {
                    kindEins.add(ahneZwei.get(r));
                }
                r++;
            }
            b = !b;
        }
        
        kindEins.add(ahneEins.getLast());
        kindZwei.add(ahneZwei.getLast());    
        
        population.removeLast();
        population.removeLast();
        
        population.add(kindEins);
        population.add(kindZwei);
    }
    
    private LinkedList<Knoten> mutiere(LinkedList<Knoten> individuum)
    {
        for(int i = 0;i < individuum.size();i++)
        {
            if(random.nextInt(10) == 0)
            {
                LinkedList<Knoten> mutation = generator.gibZufaelligesIndividuum(individuum.get(i), individuum.getLast());
                for(int k = i;k < individuum.size();k = k)
                {
                    individuum.remove(k);
                }
                for(int k = 0;k < mutation.size();k++)
                {
                    individuum.add(mutation.get(k));
                }
            }
        }
        return individuum;
    }
    
    private void ausgabe(LinkedList<Knoten> individuum)
    {
        String strecke = "";
        for(int i = 0;i < individuum.size();i++)
        {
            if(i < individuum.size() - 1)
            {
                individuum.get(i).gibKante(individuum.get(i+1).gibBezeichnung()).setzeFarbe("gruen");
            }
            strecke += individuum.get(i).gibBezeichnung() + "->";
        }
        strecke += evaluator.evaluiere(individuum) + "km";
        System.out.println(strecke);
    }
    
    private int minKnoten()
    {
        int minPos = 0;
        int minWert = Integer.MAX_VALUE;        
        for(int i = 0;i < anzahlKnoten;i++)
        {
            if(minWert > knoten[i].gibDistanz() && !knoten[i].gibBesucht())
            {
                minWert = knoten[i].gibDistanz();
                minPos = i;
            }
        }        
        return minPos;
    }
    
    private void initialisiere(String start)
    {
        for(int i = 0;i < knoten.length;i++)
        {
            if(knoten[i].gibBezeichnung().equals(start))
            {
                knoten[i].initialisiere(true);
            }
            else
            {
                knoten[i].initialisiere(false);
            }
            knoten[i].setzeKantenZurueck();
        }
    }
    
    private void streckeAusgeben(Knoten zielKnoten)
    {
        Knoten vorgaenger = zielKnoten.gibVorgaenger();
        zielKnoten.gibKante(zielKnoten.gibVorgaenger().gibBezeichnung()).setzeFarbe("gruen");
        String strecke = zielKnoten.gibBezeichnung() + ": " + zielKnoten.gibDistanz() + "km";
        while(vorgaenger != null)
        {    
            if(vorgaenger.gibVorgaenger() != null)
            {
                vorgaenger.gibKante(vorgaenger.gibVorgaenger().gibBezeichnung()).setzeFarbe("gruen");
            }
            strecke = vorgaenger.gibBezeichnung() + " -> " + strecke;
            vorgaenger = vorgaenger.gibVorgaenger();
        }
        System.out.println(strecke);
    }
    
    public void kuerzesterWeg(String start, String ende)
    {
        int startNummer = knotenNummerGeben(start);
        int endeNummer = knotenNummerGeben(ende);
        int knotenNummer = 0;
        int neueDistanz = 0;
        initialisiere(start);
        for(int i = 0;i < anzahlKnoten-1;i++)
        {
            knotenNummer = minKnoten();
            knoten[knotenNummer].setzeBesucht(true);
            for(int k = 0;k < anzahlKnoten-1;k++)
            {
                if(matrix[knotenNummer][k] > 0 && !knoten[k].gibBesucht())
                {
                    neueDistanz = knoten[knotenNummer].gibDistanz() + matrix[knotenNummer][k];
                    if(neueDistanz < knoten[k].gibDistanz())
                    {
                        knoten[k].setzeDistanz(neueDistanz);
                        knoten[k].setzeVorgaenger(knoten[knotenNummer]);
                    }
                }
            }
        }
        streckeAusgeben(knoten[endeNummer]);
    }
}
