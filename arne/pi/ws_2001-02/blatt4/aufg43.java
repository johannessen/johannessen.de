/*------------------------------------------------------------------------------
 * Aufgabe: 4-3
 *   Autor: Arne Johannessen, mailto:aj3@informatik.uni-ulm.de
 *
 * Version: 1.0
 *----------------------------------------------------------------------------*/

public class aufg43
{

  static long[] erzeugeZahlen(int n)
  {
    long[] a;
    
    if (n > 1)
    {
      a = new long[n];

      a[0] = 0;
      a[1] = 1;
      for (int k = 2; k < a.length; k++)
        a[k] = a[k - 1] + a[k - 2];

      return a;
    }
    else  // n <= 0
      return null;
  }


  public static void main(String[] args)
  {
    long[] a;

    // Einleitung
    System.out.print("Es wird nun ein Array mit Fibonacci-Zahlen");
    System.out.println(" erstellt und anschließend ausgegeben.");
    System.out.println();

    // Array erstellen
    a = erzeugeZahlen(PromptInt("Länge des Arrays eingeben:", 2));

    // Ergebnis ausgeben
    System.out.println();
    System.out.println("Die ersten "+a.length+" Fibonacci-Zahlen:");
    for (int i = 0; i < a.length - 1; i++)
      System.out.print(a[i]+", ");
    System.out.println(a[a.length - 1]);
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