/*------------------------------------------------------------------------------
 * Aufgabe: 4-2
 *   Autor: Arne Johannessen, mailto:aj3@informatik.uni-ulm.de
 *
 * Version: 1.0
 *----------------------------------------------------------------------------*/

public class aufg42
{

  static int minimumValue(int[] a)
  {
    int min = 0;
    
    if (a != null)
      if (a.length > 0)
        for (int i = 0; i < a.length; i++)
          if (a[i] > 0 && (a[i] < min || min == 0))
            min = a[i];

    return min;
  }


  public static void main(String[] args)
  {
    int[] a;
    int min;

    // Einleitung
    System.out.print("Es wird nun ein Array mit Ganzzahlen");
    System.out.println(" erstellt. Anschließend wird das kleinste");
    System.out.println("positive Element ausgegeben.");
    System.out.println();

    // Array erstellen und entsprechend Zahlen einlesen
    a = new int[PromptInt("Länge des Arrays eingeben:", 0)];
    for (int k = 1; k <= a.length; k++)
      a[k-1] = PromptInt(k+". Ganzzahl eingeben:");

    // Methoden anwenden und Ergebnis ausgeben
    System.out.println();
    min = minimumValue(a);
    if (min == 0)
      System.out.println("Es existiert kein positives Element im Array.");
    else
      System.out.println("Das kleinste positive Element im Array ist "+min+".");
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