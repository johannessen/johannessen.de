/*------------------------------------------------------------------------------
 * Aufgabe: 6-2
 *   Autor: Arne Johannessen
 *----------------------------------------------------------------------------*/

/**
 * Diese Klasse speichert einige Eigenschaften eines Autos.
 * @author <A HREF="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</A>
 * @version 1.0
 */
public class Auto
{
  /** Anzahl der T&uuml;ren */
  int tueren;

  /** gefahrene Kilometer */
  int loggestand;

  /** Steuer links (Rechtsverkehr) */
  boolean linkslenker;

  /** Konstruktor 1: Standardwerte: 3 T&uuml;ren, 0 Kilometer, Steuer links */
  Auto ()
  {
    tueren = 3;
    loggestand = 0;
    linkslenker = true;
  }

  /** Konstruktor 2: Standardwerte: 3 T&uuml;ren, 0 Kilometer */
  Auto (boolean linkslenker)
  {
    tueren = 3;
    loggestand = 0;
    this.linkslenker = linkslenker;
  }

  /** Konstruktor 3: Standardwert: 0 Kilometer */
  Auto (boolean linkslenker, int tueren)
  {
    this.tueren = tueren;
    loggestand = 0;
    this.linkslenker = linkslenker;
  }

  /** Konstruktor 4: Standardwerte: 0 Kilometer, Steuer links */
  Auto (int tueren)
  {
    this.tueren = tueren;
    loggestand = 0;
    linkslenker = true;
  }

  /** Konstruktor 5: Standardwert: 3 T&uuml;ren */
  Auto (int loggestand, boolean linkslenker)
  {
    tueren = 3;
    this.loggestand = loggestand;
    this.linkslenker = linkslenker;
  }

  /** Konstruktor 6: Standardwert: Steuer links */
  Auto (int tueren, int loggestand)
  {
    this.tueren = tueren;
    this.loggestand = loggestand;
    linkslenker = true;
  }

  /** Konstruktor 7 */
  Auto (int tueren, int loggestand, boolean linkslenker)
  {
    this.tueren = tueren;
    this.loggestand = loggestand;
    this.linkslenker = linkslenker;
  }
}
