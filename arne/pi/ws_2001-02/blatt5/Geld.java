/*------------------------------------------------------------------------------
 * Aufgabe: 5-3 a)
 *   Autor: Arne Johannessen, mailto:aj3@informatik.uni-ulm.de
 *
 * Version: 1.0
 *----------------------------------------------------------------------------*/

/**
 * Diese Klasse ist zur Speicherung eines Geldbetrages gedacht und besitzt
 * lediglich die entsprechenden Zugriffsmethoden zum Ver�ndern und Abfragen
 * des Betrages.
 */
public class Geld
{
  /** Instanzvariable zur Speicherung des Geldbetrages */
  int geldmenge;

  /** Konstruktor: initialisiert den Geldbetrag mit 0 */
  Geld ()
  {
    geldmenge = 0;
  }

  /**
   * Erh�ht den Geldbetrag um den Wert des Parameters bzw. verringert
   * den Geldbetrag, falls der Parameter negativ ist
   */
  void geldmengeVeraendern (int betrag)
  {
    geldmenge += betrag;
  }
  
  /** Gibt den aktuellen Geldbetrag zur�ck */
  int geldBetrag ()
  {
    return geldmenge;
  }
}
