/**
  * Aufgabe: 12-5
  *
  * @author <a href="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</a>
  * @version 1.0
  *
  * @see <a href="http://www.informatik.uni-ulm.de/ki/Edu/Vorlesungen/PI-1/WS0102/" lang="de" hreflang="de">Vorlesung <q>Praktische Informatik I</q></a>
  */
public class Aufg125
{
  /** Erwartet einen Parameter, der entweder <code>-r</code> (bzw. <code>--random</code>) oder aber <code>-u</code> (bzw. <code>--user</code>) lauten mu&szlig;, und erstellt daraufhin einen RadixSortPuffer mit mehreren dreistelligen Eintr&auml;gen, die dann sortiert werden. */
  public static void main (String[] args)
  {
    if (args.length == 1)
      if (args[0].equals("r") || args[0].equals("-r") || args[0].equals("--random"))
        sortRandomList();
      else if (args[0].equals("u") || args[0].equals("-u") || args[0].equals("--user"))
        sortUserSpecifiedList();
      
      else  // args[0] is none of: r, random, u, user
        printUsageInformation();
    else  // args.length != 1
      printUsageInformation();
  }
  
  private static void printUsageInformation ()
  {
    java.io.PrintStream out = System.out;  // shorthand
    out.println();
    out.println("Benutzung:  java Aufg125 [-]{ru}");
    out.println();
    out.println("-r, --random");
    out.println("\tErstellt einen Puffer mit 20 zufällig erzeugten dreistelligen Einträgen");
    out.println("\tund sortiert diesen mittels Radix-Sort.");
    out.println("-u, --user");
    out.println("\tGibt  dem  Nutzer  die  Gelegenheit, selbst  eine  Reihe  dreistelliger");
    out.println("\tEinträge in den Puffer zu schreiben. Diese Einträge werden anschließend");
    out.println("\tsortiert.");
    out.println();
    out.println("Es kann nur ein Parameter angegeben werden.");
    out.println();
  }
  
  private static void sortRandomList ()
  {
    RadixSortPuffer puffer = new RadixSortPuffer(3);
    for (int i = 0; i < 20; i++)
      puffer.speichere((int)(Math.random() * (puffer.maximaleZahl() + 1)));
    sortList(puffer);
  }
  
  private static void sortUserSpecifiedList ()
  {
    RadixSortPuffer puffer = new RadixSortPuffer(3);
    
    System.out.println();
    System.out.println("Geben Sie nun beliebig viele dreistellige Elemente ein. Nach beendeter Eingabe  geben Sie bitte -1 ein.");
    
    try
    {
      while (true)
        puffer.speichere(MyIo.promptInt("> ", -1, puffer.maximaleZahl()));
    }
    catch (IllegalArgumentException e)  // -1
    {
    }
    
    sortList(puffer);
  }
  
  private static void sortList (RadixSortPuffer puffer)
  {
    System.out.println();
    System.out.print("Unsortierte ");
    puffer.L.durchlaufe();
    puffer.radixSort();
    System.out.print("Sortierte ");
    puffer.L.durchlaufe();
  }
}



/** Implementiert einen Puffer f&uuml;r Dezimalzahlen und eine Sortierung mittels Radix-Sort */
class RadixSortPuffer extends Puffer
{
  final private int BASIS = 10;  // Dezimalzahlen
  private int stellen;
  
  /** Sortiert den Puffer mittels Radix-Sort. */
  void radixSort ()
  {
    int stellenWert, element;
    RadixSortPuffer[] fach = new RadixSortPuffer[BASIS];  // RadixSortPuffer wegen pufferIstLeer()
    for (int i = 0; i < fach.length; i++)
      fach[i] = new RadixSortPuffer(stellen);
    
    for (int stelle = 0; stelle < stellen; stelle++)
    {
      while (!this.pufferIstLeer())  // split
      {
        element = this.entnimm();
        stellenWert = (int)(element / (int)Math.pow(BASIS, stelle)) % BASIS;
        fach[stellenWert].speichere(element);
      }
      
      for (int i = 0; i < fach.length; i++)  // join
        while (!fach[i].pufferIstLeer())
          this.speichere(fach[i].entnimm());
    }
  }
  
  
  /** Initialisiert den Puffer f&uuml;r Verwendung mit Zahlen, die maximal <code>stellen</code> Stellen haben. */
  RadixSortPuffer (int stellen)
  {
    if (stellen > 0 && stellen < 11)  // 2^31 hat 10 Stellen
      this.stellen = stellen;
    else
      throw new IllegalArgumentException("0 < stellen < 11 !");
  }
  
  // sicherstellen, dass nur stellen-stellige Zahlen gespeichert werden
  void speichere (int zahl) throws IllegalArgumentException
  {
    if (zahl >= 0 && zahl < Math.pow(BASIS, stellen))
      L.erzeuge_Fuss(zahl);
    else
      throw new IllegalArgumentException("0 <= zahl < "+(maximaleZahl() + 1)+" !");
  }
  
  boolean pufferIstLeer ()
  {
    return L.Kopf == null && L.Fuss == null;
  }
  
  int maximaleZahl ()
  {
    return (int)Math.pow(BASIS, stellen) - 1;
  }
}
