/**
 * Klasse Knoten aus der Vorlesung
 *
 * erweitert um die Z&auml;hlvariable zaehler entsprechend Aufgabe 9-3
 *
 * @see <A HREF="http://www.informatik.uni-ulm.de/ki/Edu/Vorlesungen/PI-1/WS0102/">Vorlesung <Q>Praktische Informatik I</Q></A>
 */

class Knoten
{
  int Zahl, zaehler;
  Knoten links, rechts;
  
  Knoten (int Zahl)
  {
    this.Zahl = Zahl;
    zaehler = 1;  // zunaechst ist Zahl einmal vorhanden
    links = null;
    rechts = null;
  }
}
