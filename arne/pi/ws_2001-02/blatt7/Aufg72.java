/**
 * Aufgabe: 7-2
 *
 * @author <A HREF="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</A>
 * @version 1.0
 *
 * @see <A HREF="http://www.informatik.uni-ulm.de/ki/Edu/Vorlesungen/PI-1/WS0102/">Vorlesung <Q>Praktische Informatik I</Q></A>
 */
public class Aufg72
{
  /** Die Zahl der Gastgeber (die sind auch anwesend, stossen also mit an) */
  int gastgeber = 1;

  /** Standard: 1 Gastgeber */
  Aufg72 ()
  {
    gastgeber = 1;
  }

  Aufg72 (int gastgeber)
  {
    this.gastgeber = gastgeber;
  }

  /**
   * Gibt f&uuml;r Gast Nummer 'gast' zur&uuml;ck, wie oft die Gl&auml;ser an
   * diesem Abend schon erklungen sind.
   */
  public int angestossen (int gast)
  {
    if (gast > 0)
      // es wird mit den Gastgebern und den anderen GŠsten angesto§en
      return gastgeber + gast - 1 + angestossen(gast - 1);
    else
      // keine GŠste
      return 0;
  }
}
