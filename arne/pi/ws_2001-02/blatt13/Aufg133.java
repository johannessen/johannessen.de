/**
  * Aufgabe: 13-3
  *
  * @author <a href="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</a>
  * @version 0.9
  *
  * @see <a href="http://www.informatik.uni-ulm.de/ki/Edu/Vorlesungen/PI-1/WS0102/" lang="de" hreflang="de">Vorlesung <q>Praktische Informatik I</q></a>
  */
public class Aufg133
{
  static int[] zahlensumme(int[] folge, int summe)
  {
    int n, bitZahl;
    int[] kombination = null;
    boolean[] bit = new boolean[folge.length];
    
    for (int i = 1; i < Math.pow(2, folge.length); i++)
    {
      bitZahl = 0;
      n = 0;
      for (int j = 0; j < folge.length; j++)  // nach passender Bitkombination suchen
      {
        bit[j] = (i & (int)Math.pow(2, j)) >= 1;
        if (bit[j])
        {
          n += folge[j];
          bitZahl++;
        }
      }
            
      if (n == summe)  // Kombination gefunden: Array erstellen und zur�ckgeben
      {
        kombination = new int[bitZahl];
        n = 0;
        for (int j = 0; j < folge.length; j++)
          if (bit[j])
          {
            kombination[n] = folge[j];
            n++;
          }
        //printArray(kombination, ", ");
        break;
      }
    }
    
    return kombination;
  }
  
  
  
  public static void main (String[] args)
  {
    try
    {
      if (args.length == 1 && (args[0].equals("r") || args[0].equals("-r") || args[0].equals("--random")))
        doRandomList();
      else if (args.length == 1 && (args[0].equals("e") || args[0].equals("-e") || args[0].equals("--example")))
        doExampleList();
      else if (args.length >= 1 && (args[0].equals("u") || args[0].equals("-u") || args[0].equals("--user")))
        doUserSpecifiedList(args);
      else
        throw new IllegalArgumentException();
    }
    catch (IllegalArgumentException e)
    {
      printUsageInformation();
    }
  }
  
  private static void printUsageInformation ()
  {
    java.io.PrintStream out = System.out;  // shorthand
    out.println();
    out.println("Benutzung:  java Aufg133 [-]{er}");
    out.println("            java Aufg133 [-]u summe zahl1 [zahl2 [zahl3 ... ]]");
    out.println();
    out.println("-r, --random");
    out.println("\tErstellt eine Folge mit 6 zufällig erzeugten Zahlen und gibt eine Kom-");
    out.println("\tbination dieser Zahlen aus, deren Summe eine weitere zufällige Zahl er-");
    out.println("\tgibt.");
    out.println("-u, --user");
    out.println("\tGibt dem Nutzer die Gelegenheit,  selbst eine Summe  und eine Folge von");
    out.println("\tZahlen einzugeben.  Das Programm gibt dann eine Kombination dieser Zah-");
    out.println("\tlen aus, deren Summe der Eingabe entspricht.");
    out.println("-e, --example");
    out.println("\tSimuliert die Eingabe von 9 und 1, 2, 3, 4, 5 als Beispiel.");
    out.println();
  }
  
  private static void doRandomList ()
  {
    int summe = (int)(Math.random() * 12) + 6;
    int[] folge = new int[6];
    for (int i = 0; i < folge.length; i++)
      folge[i] = (int)(Math.random() * 12) + 1;
    
    doList(folge, summe);
  }
  
  private static void doUserSpecifiedList (String[] args) throws IllegalArgumentException
  {
    int summe;
    int[] folge;
    
    if (args.length < 3)
      throw new IllegalArgumentException();
    
    try
    {
      summe = Integer.parseInt(args[1]);
      folge = new int[args.length - 2];
      for (int i = 0; i < folge.length; i++)
        folge[i] = Integer.parseInt(args[i + 2]);
    }
    catch (NumberFormatException e)
    {
      throw new IllegalArgumentException();
    }
    
    doList(folge, summe);
  }
  
  private static void doExampleList ()
  {
    int[] folge = {1, 2, 3, 4, 5};
    doList(folge, 9);
  }
  
  private static void doList (int[] folge, int summe)
  {
    int[] kombination;
    System.out.println();
    System.out.print("Zahlenfolge: ");
    printArray(folge, ", ");
    System.out.println("Summe: "+summe);
    kombination = zahlensumme(folge, summe);
    if (kombination != null)
    {
      System.out.print("Kombination: "+summe+" = ");
      printArray(kombination, " + ");
    }
    else  // kombination == null
      System.out.println("Es existiert keine Kombination.");
  }
  
  private static void printArray(int[] a, String delimiter)
  {
    if (a != null)
      if (a.length > 0)
      {
        for (int i = 0; i < a.length - 1; i++)
          System.out.print(a[i]+delimiter);
        System.out.println(a[a.length - 1]);
      }
      else  // a.length == 0
        System.out.println();
    else  // a == null
      System.out.println("<null>");
  }
  
  private static void printArray(boolean[] a, String delimiter)
  {
    if (a != null)
      if (a.length > 0)
      {
        for (int i = 0; i < a.length - 1; i++)
          System.out.print(a[i]+delimiter);
        System.out.println(a[a.length - 1]);
      }
      else  // a.length == 0
        System.out.println();
    else  // a == null
      System.out.println("<null>");
  }
}
