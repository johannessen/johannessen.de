/*------------------------------------------------------------------------------
 * Aufgabe: 4-1
 *   Autor: Arne Johannessen, mailto:aj3@informatik.uni-ulm.de
 *
 * Version: 1.0
 *----------------------------------------------------------------------------*/

public class aufg41
{

  /***** Aufgabe a) *****/
  static boolean less(int[] a, int i, int j)
  {
    if (a != null)
      if (i >= 0 && i < a.length && j >= 0 && j < a.length)
        return (a[i] < a[j]);

      else
        return false;
    else  // a == null
      return false;
  }

  /***** Aufgabe b) *****/
  static void swap(int[] a, int i, int j)
  {
    int k;

    if (a != null)
      if (i >= 0 && i < a.length && j >= 0 && j < a.length)
      {
        k = a[i];
        a[i] = a[j];
        a[j] = k;
      }
  }

  /***** Aufgabe c) *****/
  static boolean swapIfLess(int[] a, int i, int j)
  {
    boolean isLess = less(a, i, j);

    if (isLess)
      swap(a, i, j);
    return isLess;
  }


  public static void main(String[] args)
  {
    int[] a;
    int i, j;

    // Einleitung
    System.out.print("Es wird nun ein Array mit mindestens zwei");
    System.out.println(" Ganzzahlen erstellt. Anschließend");
    System.out.print("werden die Ergebnisse der Methoden less,");
    System.out.println(" swap und swapIfLess, auf diesen");
    System.out.print("Array mit zwei Indizes zusammen");
    System.out.println(" angewandt, ausgegeben.");
    System.out.println();

    // Array mit min. zwei Elementen erstellen und entsprechend Zahlen einlesen
    a = new int[PromptInt("Länge des Arrays eingeben:", 2)];
    for (int k = 1; k <= a.length; k++)
      a[k-1] = PromptInt(k+". Ganzzahl eingeben:");

    // Indizes einlesen
    i = PromptInt("Ersten Array-Index eingeben:", 0, a.length - 1);
    j = PromptInt("Zweiten Array-Index eingeben:", 0, a.length - 1);

    // Methoden anwenden und Ergebnis ausgeben
    System.out.println();
    System.out.println("less gibt "+less(a, i, j)+" zurück.");
    swap(a, i, j);
    System.out.println("swap ergibt den Array:");
    for (int k = 0; k < a.length; k++)
      System.out.print(a[k]+" ");
    System.out.println();
    swapIfLess(a, i, j);
    System.out.println("danach ergibt swapIfLess den Array:");
    for (int k = 0; k < a.length; k++)
      System.out.print(a[k]+" ");
    System.out.println();
    System.out.println();
  }



  /***** Helfer *****/

  static int PromptInt (String thePrompt, int min, int max)
  // PromptInt v1.0
  // Stellt solange wiederholt thePrompt auf dem Terminal dar und erwartet die
  // Eingabe einer Ganzzahl, bis die Eingabe im Bereich von min und max liegt
  {
    int input = 0;
    boolean inputValid = false;

    // wiederholen, bis Eingabe gültig ist (oder Benutzer mit ctrl-c abbricht)
    while (!inputValid)
    {
      
      // Prompt auf Bildschirm ausgeben
      System.out.print(thePrompt+" ");

      // eigentliche Eingaberoutine; deprecated API java.io.DataInput.readLine()
      // wurde ersetzt durch java.io.BufferedReader.readLine(); vgl. http://
      // java.sun.com/j2se/1.3/docs/api/java/io/DataInputStream.html#readLine()
      try
      {
        input = Integer.parseInt(new java.io.BufferedReader(new java.io.InputStreamReader(System.in)).readLine());
      }
      catch (java.io.IOException e)
      {
      }

      // Prüfung: liegt die Eingabe im erlaubten Bereich?
      // falls nein: Fehler melden und Eingabe wiederholen
      inputValid = (input >= min && input <= max || min >= max);
      if (!inputValid)
        System.out.println("Die Eingabe muß eine ganze Zahl im Intervall ["+min+";"+max+"] sein!");
    }

    return input;
  }

  static int PromptInt (String thePrompt, int minimum)
  // default: maximum = 2^31-1
  {
    return PromptInt(thePrompt, minimum, (int)(Math.pow(2, 31) - 1));
  }

  static int PromptInt (String thePrompt)
  // default: minimum = maximum (=> keine Einschränkung)
  {
    return PromptInt(thePrompt, 0, 0);
  }
}