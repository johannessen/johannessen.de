/**
  * Aufgabe: 12-4
  *
  * @author <a href="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</a>
  * @version 1.0
  *
  * @see <a href="http://www.informatik.uni-ulm.de/ki/Edu/Vorlesungen/PI-1/WS0102/" lang="de" hreflang="de">Vorlesung <q>Praktische Informatik I</q></a>
  */
public class Aufg124
{
  static void bubbleSort (String[] theArray)
  {
    String tmp;
    boolean vertauscht = true;
    
    while (vertauscht)
    {
      vertauscht = false;
      for (int i = 1; i < theArray.length; i++)
        if (theArray[i - 1].compareTo(theArray[i]) > 0)  // vertauschen
        {
          tmp = theArray[i - 1];
          theArray[i - 1] = theArray[i];
          theArray[i] = tmp;
          vertauscht = true;
        }
    }
  }
  
  
  /** Erwartet einen Parameter, der entweder <code>-r</code> (bzw. <code>--random</code>) oder aber <code>-u</code> (bzw. <code>--user</code>) lauten mu&szlig;, und erstellt daraufhin ein String-Array mit mehreren Eintr&auml;gen, die dann sortiert werden. */
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
    out.println("Benutzung:  java Aufg126 [-]{ru}");
    out.println();
    out.println("-r, --random");
    out.println("\tErstellt einen Array  mit  20  zufällig  erzeugten Strings und sortiert");
    out.println("\tdiesen mittels Bubble-Sort.");
    out.println("-u, --user");
    out.println("\tGibt dem Nutzer die Gelegenheit, selbst mehrere Strings einzugeben. Das");
    out.println("\thieraus erstellte Array wird anschließend sortiert.");
    out.println();
    out.println("Es kann nur ein Parameter angegeben werden.");
    out.println();
  }
  
  private static void sortRandomList ()
  {
    String[] theArray = new String[20];
    for (int i = 0; i < theArray.length; i++)
    {
      theArray[i] = "";
      for (int j = 0; j < 3 + Math.random() * 3; j++)
        theArray[i] = theArray[i]+Integer.toString((int)(10 + Math.random() * 26), 36);
    }
    sortList(theArray);
  }
  
  private static void sortUserSpecifiedList ()
  {
    java.util.Vector theVector = new java.util.Vector();
    String[] theArray;
    
    System.out.println();
    System.out.println("Geben Sie  nun beliebig viele  nicht-leere Strings ein.  Nach beendeter Eingabe");
    System.out.println("geben Sie bitte einen leeren String ein.");
    
    while (theVector.isEmpty() || !"".equals((String)theVector.lastElement()))
      theVector.add((Object)MyIo.promptStr("> "));
    
    theArray = new String[theVector.size() - 1];
    for (int i = 0; i < theArray.length; i++)
      theArray[i] = (String)theVector.elementAt(i);
    sortList(theArray);
  }
  
  private static void sortList (String[] theArray)
  {
    System.out.println();
    System.out.print("Unsortierter Array: ");
    printArray(theArray);
    bubbleSort(theArray);
    System.out.print("Sortierter Array: ");
    printArray(theArray);
  }
  
  private static void printArray(String[] a)
  {
    if (a != null)
    {
      for (int i = 0; i < a.length - 1; i++)
        System.out.print(a[i]+", ");
      System.out.println(a[a.length - 1]);
    }
    else  // a == null
      System.out.println("<null>");
  }
}
