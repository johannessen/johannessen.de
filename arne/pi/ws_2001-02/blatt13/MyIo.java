import java.io.*;

/** MyIo implementiert angepa&szlig;te Methoden zur Eingabe von Daten &uuml;ber die Tastatur.
  *
  * @author <A HREF="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</A>
  * @version 1.2.1a
  * @version 1.2 f&uuml;gt die Methoden promptDouble sowie promptYesNo hinzu.
  * @version 1.1 f&uuml;gt die Methoden readBufferedString sowie promptStr hinzu, &auml;ndert die Namen der Methoden PromptInt zu promptInt (lowercase) und verbessert die Fehlerbehandlung in promptInt(String, int, int).
  *
  * <strong>Note.</strong> Alpha Release.
  */
public class MyIo
{
  /** Liest einen String vom Terminal ein. Dazu werden java.io.BufferedReader und java.io.InputStreamReader genutzt. Im Falle eines Eingabefehlers (java.io.IOException) wird ein leerer String zur&uuml;ckgegeben.
    * @throws java.io.InterruptedIOException, falls die Eingabe unterbrochen wird (z.B. mittels ^D)
    * @since 1.1 */
  static String readBufferedString () throws InterruptedIOException
  {
    String s = "";
    try
    {
      s = new BufferedReader(new InputStreamReader(System.in)).readLine();
    }
    catch (IOException e)
    {
      InterruptedIOException newE = new InterruptedIOException();
      if (newE.getClass().isInstance((Object)e))
        throw new InterruptedIOException();
    }
    return s;
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

        // Prüfung: liegt die Eingabe im erlaubten Bereich?
        // falls nein: Fehler melden und Eingabe wiederholen
        inputValid = (input >= min && input <= max || min >= max);
      }
      catch (Exception e)
      {
        inputValid = false;
      }
      
      if (!inputValid)
        System.out.println("Die Eingabe muß eine ganze Zahl im Intervall ["+min+";"+max+"] sein!");
    }

    return input;
  }

  /** Standard: max = Integer.MAX_VALUE
    * @since 1.1 */
  static int promptInt (String thePrompt, int min)
  {
    return promptInt(thePrompt, min, Integer.MAX_VALUE);
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
    * @throws java.io.InterruptedIOException, falls die Eingabe unterbrochen wurde (z.B. mittels ^D)
    * @since 1.1 */
  static String promptStr (String thePrompt) throws InterruptedIOException
  {
    String s;
    System.out.print(thePrompt+" ");
    s = readBufferedString();
    if (s == null || s.equals(null))
      throw new InterruptedIOException();
    return s;

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
        
        // Prüfung: liegt die Eingabe im erlaubten Bereich?
        // falls nein: Fehler melden und Eingabe wiederholen
        inputValid = (input >= min && input <= max || min >= max);
      }
      catch (Exception e)
      {
        inputValid = false;
      }
      
      if (!inputValid)
        if (min >= max)
          System.out.println("Die Eingabe muß eine rationale Zahl sein (Dezimalkomma: \".\")!");
        else
          System.out.println("Die Eingabe muß eine rationale Zahl im Intervall ["+min+";"+max+"] sein!");
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
    String s = "";
    try
    {
      s = promptStr(thePrompt).trim();
    }
    catch (java.io.InterruptedIOException e)
    {
    }
    
    if (s.equalsIgnoreCase("yes") || s.equalsIgnoreCase("y"))
      return true;
    else if (s.equalsIgnoreCase("ja") || s.equalsIgnoreCase("j"))
      return true;
    else if (s.equalsIgnoreCase("zes") || s.equalsIgnoreCase("z"))
      return true;
    else
      return false;
  }
}
