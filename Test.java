
public class Test
{
    private GraphMatrix graphMatrix;
    public Test()
    {
        graphMatrix = new GraphMatrix(14);
        knotenEinfuegen("N",300,200);
        knotenEinfuegen("HO",350,50);
        knotenEinfuegen("WÜ",200,150);
        knotenEinfuegen("FD",200,50);
        knotenEinfuegen("F",100,100);
        knotenEinfuegen("KA",50,200);
        knotenEinfuegen("S",150,250);
        knotenEinfuegen("UL",200,350);
        knotenEinfuegen("LI",150,500);
        knotenEinfuegen("A",250,450);
        knotenEinfuegen("M",350,450);
        knotenEinfuegen("RO",450,500);
        knotenEinfuegen("R",400,250);
        knotenEinfuegen("PA",450,350);
        kanteEinfuegen("M", "A", 64);
        kanteEinfuegen("M", "RO", 60);    
        kanteEinfuegen("M", "N", 163);
        kanteEinfuegen("M", "R", 117);
        kanteEinfuegen("R", "PA", 72);
        kanteEinfuegen("R", "N", 80);
        kanteEinfuegen("R", "HO", 166);
        kanteEinfuegen("HO", "N", 116);
        kanteEinfuegen("HO", "WÜ", 192);
        kanteEinfuegen("WÜ", "N", 104);
        kanteEinfuegen("WÜ", "FD", 98);
        kanteEinfuegen("WÜ", "F", 131);
        kanteEinfuegen("WÜ", "S", 155);
        kanteEinfuegen("WÜ", "UL", 165);
        kanteEinfuegen("UL", "A", 59);
        kanteEinfuegen("UL", "LI", 126);
        kanteEinfuegen("UL", "S", 103);
        kanteEinfuegen("S", "KA", 53);
        kanteEinfuegen("KA", "F", 127);
        textAusgabe();
        graphMatrix.knotenDarstellen();
    }
    
    private void textAusgabe()
    {
        graphMatrix.textAusgabe();
    }
       
    private void knotenEinfuegen(String name, int x, int y)
    {
        graphMatrix.knotenEinfuegen(name, x, y);
    }
    
    private void kanteEinfuegen(String anfang, String ende, int abstand)
    {
        graphMatrix.kanteEinfuegen(anfang, ende, abstand);
    }
    
    public void evoltorischeWegesuche(String start, String ziel, int generationen)
    {
        graphMatrix.evolutorischeSuche(start, ziel, generationen);
    }
    
    public void wegSuchen(String anfang, String ende)
    {
        graphMatrix.kuerzesterWeg(anfang, ende);
    }
}
