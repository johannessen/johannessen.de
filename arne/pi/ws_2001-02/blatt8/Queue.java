import LListe;

/**
 * Aufgabe: 8-3
 *
 * @author <A HREF="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</A>
 * @version 1.0
 *
 * @see <A HREF="http://www.informatik.uni-ulm.de/ki/Edu/Vorlesungen/PI-1/WS0102/">Vorlesung <Q>Praktische Informatik I</Q></A>
 */
public class Queue
{
  LListe cue;

  public void put (int zahl)
  {
    cue = new LListe(zahl, cue);
  }

  public int get ()
  {
    int returnValue;
    LListe liste = cue;

    if (liste.lrest() == null)
    {
      returnValue = cue.lkopf();
      cue = null;
    }

    else  // mehr als ein Listenelement
    {
      while (liste.lrest().lrest() != null)
        liste = liste.lrest();
      returnValue = liste.lrest().lkopf();
      liste.rest = null;
    }

    return returnValue;
  }

  public boolean isEmpty ()
  {
    return (cue == null);
  }
}
