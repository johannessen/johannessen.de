/**
 * MyIo implementiert angepa&szlig;te Methoden zur Eingabe von Daten
 * &uuml;ber die Tastatur.
 *
 * @author <A HREF="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</A>
 * @version 1.0
 */
public class MyIo
{
  /**
   * Stellt solange wiederholt thePrompt auf dem Terminal dar und
   * erwartet die Eingabe einer Ganzzahl, bis die Eingabe im Bereich
   * von min und max liegt. Die Eingabe wird dann zur&uuml;ckgegeben.
   *
   * @author <A HREF="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</A>
   * @version 1.0
   */
  static int PromptInt (String thePrompt, int min, int max)
  {
    int input = 0;
    boolean inputValid = false;

    // wiederholen, bis Eingabe gültig ist (oder Benutzer mit ctrl-c abbricht)
    while (!inputValid)
    {
      
      // Prompt auf Bildschirm ausgeben
      System.out.print(thePrompt+" ");

      // eigentliche Eingaberoutine
      try
      {
        input = Integer.parseInt(new java.io.BufferedReader(new java.io.InputStreamReader(System.in)).readLine());
      }
      catch (java.io.IOException e)
      {
      }

      // PrŸfung: liegt die Eingabe im erlaubten Bereich?
      // falls nein: Fehler melden und Eingabe wiederholen
      inputValid = (input >= min && input <= max || min >= max);
      if (!inputValid)
        System.out.println("Die Eingabe mu\u00DF eine ganze Zahl im Intervall ["+min+";"+max+"] sein!");
    }

    return input;
  }

  /** Standard: max = 2<SUP>31</SUP>-1 */
  static int PromptInt (String thePrompt, int min)
  {
    return PromptInt(thePrompt, min, (int)(Math.pow(2, 31) - 1));
  }

  /** Standard: keine Einschr&auml;nkung */
  static int PromptInt (String thePrompt)
  {
    return PromptInt(thePrompt, 0, 0);
  }
}