/*------------------------------------------------------------------------------
 * Aufgabe: 4-4
 *   Autor: Arne Johannessen, mailto:aj3@informatik.uni-ulm.de
 *
 * Version: 1.0
 *----------------------------------------------------------------------------*/

public class aufg44
{

  /***** Aufgabe a) *****/
  static int[] liesArray(int n)
  {
    int[] a;

    if (n > 0)
    {
      System.out.println("Es wird nun ein Array mit "+n+" Ganzzahl"+(n==1?"":"en")+" erstellt.");
      a = new int[n];

      for (int i = 1; i <= a.length; i++)
        a[i-1] = PromptInt(i+". Ganzzahl eingeben:");
      
      return a;
    }
    else  // n <= 0
      return null;
  }

  /***** Aufgabe b) *****/
  static int[] swapArray1(int[] a)
  {
    int[] b;

    if (a != null)
    {
      b = new int[a.length];

      for (int i = 0; i < a.length; i++)
        b[i] = a[a.length - 1 - i];
      
      return b;
    }
    else  // a == null
      return null;
  }

  /***** Aufgabe c) *****/
  /* benutzt Aufgabe 4-1 b) */
  static void swapArray2(int[] a)
  {
    if (a != null)
      for (int i = 0; i < a.length - 1 - i; i++)
        swap(a, i, a.length - 1 - i);
  }


  static void printArray(int[] a)
  {
    if (a != null)
    {
      for (int i = 0; i < a.length - 1; i++)
        System.out.print(a[i]+", ");
      System.out.println(a[a.length - 1]);
    }
    else  // a == null
      System.out.println("null");
  }

  public static void main(String[] args)
  {
    int[] a, b;

    // Einleitung
    System.out.print("Es wird nun ein Array mit Ganzzahlen");
    System.out.println(" erstellt. Anschließend wird der Array");
    System.out.print("gespiegelt (d.h. in umgekehrter Reihenfolge");
    System.out.println(" der Elemente) ausgegeben.");
    System.out.println();

    // Array erstellen mit Aufgabe a)
    a = liesArray(PromptInt("Länge des Arrays eingeben:", 0));
    System.out.println();

    // Aufgabe b) anwenden
    System.out.println("Array vor Anwendung von swapArray1:");
    printArray(a);
    b = swapArray1(a);
    System.out.println("Rückgabearray von swapArray1:");
    printArray(b);
    System.out.println("Originalarray nach Anwendung:");
    printArray(a);
    System.out.println();

    // Aufgabe c) anwenden
    System.out.println("Originalarray vor Anwendung von swapArray2:");
    printArray(a);
    swapArray2(a);
    System.out.println("Array nach Anwendung:");
    printArray(a);
    System.out.println();
}



  /***** Helfer *****/

  /* Aufgabe 4-1 b) */
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