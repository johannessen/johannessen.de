/** MyIo implementiert angepa&szlig;te Methoden zur Eingabe von Daten &uuml;ber die Tastatur.
  *
  * @author <A HREF="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</A>
  * @version 1.2 f&uuml;gt die Methoden promptDouble sowie promptYesNo hinzu.
  * @version 1.1 f&uuml;gt die Methoden readBufferedString sowie promptStr hinzu, &auml;ndert die Namen der Methoden PromptInt zu promptInt (lowercase) und verbessert die Fehlerbehandlung in promptInt(String, int, int).
  */
public class MyIo
{
  /** Liest einen String vom Terminal ein. Dazu werden java.io.BufferedReader und java.io.InputStreamReader genutzt. Im Falle eines Eingabefehlers (java.io.IOException) wird ein leerer String zur&uuml;ckgegeben.
    * @since 1.1 */
  static String readBufferedString ()
  {
    try
    {
      return new java.io.BufferedReader(new java.io.InputStreamReader(System.in)).readLine();
    }
    catch (java.io.IOException e)
    {
      return "";
    }
  }
  
  /** Stellt solange wiederholt thePrompt auf dem Terminal dar und erwartet die Eingabe einer Ganzzahl, bis die Eingabe im Bereich von min und max liegt. Die Eingabe wird dann zur&uuml;ckgegeben.
    * @since 1.1 */
  static int promptInt (String thePrompt, int min, int max)
  {
    int input = 0;
    boolean inputValid = false;

    // wiederholen, bis Eingabe gueltig ist (oder Benutzer Java mit ctrl-c abbricht)
    while (!inputValid)
    {
      try
      {
        input = Integer.parseInt(promptStr(thePrompt));

        // PrŸfung: liegt die Eingabe im erlaubten Bereich?
        // falls nein: Fehler melden und Eingabe wiederholen
        inputValid = (input >= min && input <= max || min >= max);
      }
      catch (NumberFormatException e)
      {
        inputValid = false;
      }
      
      if (!inputValid)
        System.out.println("Die Eingabe mu\u00DF eine ganze Zahl im Intervall ["+min+";"+max+"] sein!");
    }

    return input;
  }

  /** Standard: max = 2<SUP>31</SUP>-1
    * @since 1.1 */
  static int promptInt (String thePrompt, int min)
  {
    return promptInt(thePrompt, min, (int)(Math.pow(2, 31) - 1));
  }

  /** Standard: keine Einschr&auml;nkung
    * @since 1.1 */
  static int promptInt (String thePrompt)
  {
    return promptInt(thePrompt, 0, 0);
  }
  
  /** Seit 1.1 identisch mit promptInt(String, int, int). Wird in einer zuk&uuml;nftigen Version entfernt werden.
    * @since 1.0
    * @deprecated Anstattdessen ist promptInt zu benutzen. */
  static int PromptInt (String thePrompt, int min, int max)
  {
    return promptInt (thePrompt, min, max);
  }
  
  /** Seit 1.1 identisch mit promptInt(String, int). Wird in einer zuk&uuml;nftigen Version entfernt werden.
    * @since 1.0
    * @deprecated Anstattdessen ist promptInt zu benutzen. */
  static int PromptInt (String thePrompt, int min)
  {
    return promptInt (thePrompt, min);
  }
  
  /** Seit 1.1 identisch mit promptInt(String). Wird in einer zuk&uuml;nftigen Version entfernt werden.
    * @since 1.0
    * @deprecated Anstattdessen ist promptInt zu benutzen. */
  static int PromptInt (String thePrompt)
  {
    return promptInt (thePrompt);
  }
  
  /** Stellt thePrompt auf dem Terminal dar und erwartet die Eingabe eines Strings.
    * @since 1.1 */
  static String promptStr (String thePrompt)
  {
    System.out.print(thePrompt+" ");
    return readBufferedString();
  }
  
  /** Stellt solange wiederholt thePrompt auf dem Terminal dar und erwartet die Eingabe einer rationalen Zahl, bis die Eingabe im Bereich von min und max liegt. Die Eingabe wird dann zur&uuml;ckgegeben.
    * @since 1.2 */
  static double promptDouble (String thePrompt, double min, double max)
  {
    double input = 0;
    boolean inputValid = false;

    // wiederholen, bis Eingabe gueltig ist (oder Benutzer Java mit ctrl-c abbricht)
    while (!inputValid)
    {
      try
      {
        input = Double.parseDouble(promptStr(thePrompt));
        
        // PrŸfung: liegt die Eingabe im erlaubten Bereich?
        // falls nein: Fehler melden und Eingabe wiederholen
        inputValid = (input >= min && input <= max || min >= max);
      }
      catch (NumberFormatException e)
      {
        inputValid = false;
      }
      
      if (!inputValid)
        if (min >= max)
          System.out.println("Die Eingabe mu\u00DF eine rationale Zahl sein (Dezimalkomma: \".\")!");
        else
          System.out.println("Die Eingabe mu\u00DF eine rationale Zahl im Intervall ["+min+";"+max+"] sein!");
    }

    return input;
  }
  
  /** @since 1.2 */
  static double promptDouble (String thePrompt, int min, int max)
  {
    return promptDouble(thePrompt, (double)min, (double)max);
  }
  
  /** @since 1.2 */
  static double promptDouble (String thePrompt, double min, int max)
  {
    return promptDouble(thePrompt, min, (double)max);
  }
  
  /** @since 1.2 */
  static double promptDouble (String thePrompt, int min, double max)
  {
    return promptDouble(thePrompt, (double)min, max);
  }
  
  /** Standard: keine Einschr&auml;nkung
    * @since 1.2 */
  static double promptDouble (String thePrompt)
  {
    return promptDouble(thePrompt, 0.0, 0.0);
  }
  
  /** Stellt thePrompt auf dem Terminal dar und erwartet die Eingabe eines Strings. Falls die Eingabe den Worten <q lang="en">yes<q> oder <q lang="de">ja</q> &auml;hnelt, wird true zur&uuml;ckgegeben.
    * @return true, falls die Eingabe einem der Strings "yes", "y", "ja", "j", "zes", "z" entspricht, wobei die Gro&szlig;- und Kleinschriebung nicht ber&uuml;cksichtigt wird.
    * @since 1.2 */
  static boolean promptYesNo (String thePrompt)
  {
    String s = promptStr(thePrompt).trim();
    if (s.equalsIgnoreCase("yes"))
      return true;
    else if (s.equalsIgnoreCase("y"))
      return true;
    else if (s.equalsIgnoreCase("ja"))
      return true;
    else if (s.equalsIgnoreCase("j"))
      return true;
    else if (s.equalsIgnoreCase("zes"))
      return true;
    else if (s.equalsIgnoreCase("z"))
      return true;
    else
      return false;
  }
}
