/**
 * Klasse Knoten aus dem Vorlesungsskript
 *
 * @author Lehrbuch der Programmierung mit Java, Echtle Goedicke, Heidelberg, &copy; dpunkt 2000
 *
 * es wurde &uuml;berall public eingef&uuml;gt
 *
 * @see <A HREF="http://www.informatik.uni-ulm.de/ki/Edu/Vorlesungen/PI-1/WS0102/">Vorlesung <Q>Praktische Informatik I</Q></A>
 */
public class Knoten
{
  public int Zahl;
  public Knoten links, rechts;

  public Knoten (int Zahl)
  {
    this.Zahl = Zahl;
    links = null;
    rechts = null;
  }
}