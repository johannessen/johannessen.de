/**
  * Aufgabe: 13-2
  *
  * @author <a href="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</a>
  * @version 1.0a1
  *
  * @see <a href="http://www.informatik.uni-ulm.de/ki/Edu/Vorlesungen/PI-1/WS0102/" lang="de" hreflang="de">Vorlesung <q>Praktische Informatik I</q></a>
  *
  * <strong>Note.</strong> Alpha Release. In Version 1.0a1 funktioniert die manuelle Dateneingabe nicht;
  * dies halte ich f&uuml;r einen Fehler in Java. Das Problem tritt in void sortUserSpecifiedList() auf.
  */
public class Aufg132
{
  static void bucketSort (double[] a, double grenzeA, double grenzeB)
  {
    java.util.LinkedList[] b = new java.util.LinkedList[a.length];
    for (int j = 0; j < b.length; j++)
      b[j] = new java.util.LinkedList();
    
    for (int i = 0; i < a.length; i++)  // split
      b[(int)(a.length * (a[i] - grenzeA) / (grenzeB - grenzeA))].addLast((Object)(new Double(a[i])));
    
    for (int j = 0; j < b.length; j++)  // sort
      bubbleSort(b[j]);
    
    int i = 0;
    for (int j = 0; j < b.length; j++)  // join
      for (int k = i + b[j].size(); i < k; i++)  // i von i bis i + b[j].size() zählen
        a[i] = ((Double)b[j].removeFirst()).doubleValue();
  }
  
  static void bubbleSort (java.util.LinkedList list)
  {
    Object tmp1, tmp2;
    boolean vertauscht = true;
    
    if (list.size() > 0)
      while (vertauscht)
      {
        vertauscht = false;
        tmp1 = list.getFirst();
        for (int i = 1; i < list.size(); i++)
        {
          tmp2 = list.get(i);
          if (((Double)tmp1).doubleValue() > ((Double)tmp2).doubleValue())  // vertauschen
          {
            list.set(i - 1, tmp2);
            list.set(i, tmp1);
            vertauscht = true;
          }
          else
            tmp1 = tmp2;
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
    out.println("Benutzung:  java Aufg132 [-]{ru}");
    out.println();
    out.println("-r, --random");
    out.println("\tErstellt einen Array  mit 15 zufällig erzeugten  rationalen Zahlen  und");
    out.println("\tsortiert diesen mittels Bucket-Sort.");
    /*out.println("-u, --user");
    out.println("\tGibt dem Nutzer die Gelegenheit, selbst mehrere rationale Zahlen einzu-");
    out.println("\tgeben. Das hieraus erstellte Array wird anschließend sortiert.");*/
    out.println();
    out.println("Es kann nur ein Parameter angegeben werden.");
    out.println();
  }
  
  private static void sortRandomList ()
  {
    double[] theArray = new double[15];
    
    double a = Math.random() * 5;
    double b = a + 1 + Math.random() * 4;
    for (int i = 0; i < theArray.length; i++)
      theArray[i] = a + Math.random() * (b - a);
    
    sortList(theArray, a, b);
  }
  
  private static void sortUserSpecifiedList ()
  {
    java.util.Vector theVector = new java.util.Vector();
    double[] theArray = {};
    double a, b, d;
    String s;
    boolean inputValid = true;
    
    System.out.println();
    System.out.println("Sie erhalten gleich Gelegenheit, die zu sortierenden Zahlen einzugeben. Da die-");
    System.out.println("se Zahlen alle aus einem festen Intervall  [a; b)  stammen, müssen Sie zunächst");
    System.out.println("diese Intervalle bestimmen.");
    a = MyIo.promptDouble("Untere Intervallgrenze:  a =");
    b = a - 1;
    while (b <= a)
    {
      b = MyIo.promptDouble(" Obere Intervallgrenze:  b =");
      if (b <= a)
        System.out.println("b muß größer als a = "+a+" sein.");
    }
    System.out.println("Geben Sie nun beliebig viele rationale Zahlen ein. Nach beendeter Eingabe drük-");
    System.out.println("ken Sie bitte control-D (^D).");

    try
    {
      while (true)  // repeat until Exception is being raised
        while (!inputValid)
        {
          s = MyIo.promptStr(">");  // funktioniert merkwürdigerweise nicht!
          try
          {
            d = Double.parseDouble(s);
            // Prüfung: liegt die Eingabe im erlaubten Bereich? falls nein: Fehler melden und Eingabe wiederholen
            if (d < a || d>= b)
              throw new NumberFormatException();
            theVector.add((Object)(new Double(d)));
          }
          catch (NumberFormatException e)
          {
            System.out.println("Die Eingabe muß eine rationale Zahl im Intervall ["+a+"; "+b+") sein!");
          }
        }
    }
    catch (java.io.InterruptedIOException e)
    {
      theVector.add(null);
    }
    
    theArray = new double[theVector.size() - 1];
    for (int i = 0; i < theArray.length; i++)
      theArray[i] = ((Double)theVector.elementAt(i)).doubleValue();
    sortList(theArray, a, b);
  }
  
  private static void sortList (double[] theArray, double a, double b)
  {
    System.out.println();
    System.out.println("Intervall: ["+a+"; "+b+")");
    System.out.print("Unsortierter Array: ");
    printArray(theArray);
    bucketSort(theArray, a, b);
    System.out.print("Sortierter Array: ");
    printArray(theArray);
  }
  
  private static void printArray(double[] a)
  {
    if (a != null)
      if (a.length > 0)
      {
        for (int i = 0; i < a.length - 1; i++)
          System.out.print(a[i]+", ");
        System.out.println(a[a.length - 1]);
      }
      else  // a.length == 0
        System.out.println();
    else  // a == null
      System.out.println("<null>");
  }
}
