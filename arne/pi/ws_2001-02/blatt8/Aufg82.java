import Knoten;

/**
 * Aufgabe: 8-2
 *
 * @author <A HREF="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</A>
 * @version 1.0
 *
 * @see <A HREF="http://www.informatik.uni-ulm.de/ki/Edu/Vorlesungen/PI-1/WS0102/">Vorlesung <Q>Praktische Informatik I</Q></A>
 */
public class Aufg82
{
  /** Aufgabe 8-2 a) */
  public static int baumTiefe (Knoten baum)
  {
    if (baum == null)
      return 0;
    else
      return 1 + Math.max(baumTiefe(baum.links), baumTiefe(baum.rechts));
  }

  /** Aufgabe 8-2 b) */
  public static int baumKnotenZahl (Knoten baum)
  {
    if (baum == null)
      return 0;
    else
      return 1 + baumKnotenZahl(baum.links) + baumKnotenZahl(baum.rechts);
  }

  /** Aufgabe 8-2 c) */
  public static int baumBlattZahl (Knoten baum)
  {
    if (baum == null)
      return 0;
    else if (baum.links == null && baum.rechts == null)
      return 1;  // dies ist ein Blatt
    else
      return baumBlattZahl(baum.links) + baumKnotenZahl(baum.rechts);
  }
}
