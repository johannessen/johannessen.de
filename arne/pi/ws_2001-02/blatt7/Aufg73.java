/**
 * Aufgabe: 7-3
 *
 * @author <A HREF="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</A>
 * @version 1.0
 *
 * @see <A HREF="http://www.informatik.uni-ulm.de/ki/Edu/Vorlesungen/PI-1/WS0102/">Vorlesung <Q>Praktische Informatik I</Q></A>
 */
public class Aufg73 extends LListe
{
  Aufg73 rest;  // ueberschreibt rest aus LListe

  public boolean sortiertIterativ ()
  {
    LListe liste = rest;

    if (liste != null)
    {
      while (liste.lrest() != null)
        if (liste.lkopf() <= liste.lrest().lkopf())
          liste = liste.lrest();
        else
          break;

      // Liste bis zum Ende sortiert?
      return (liste.lrest() == null);
    }

    else  // liste == null
      return false;
  }

  public boolean sortiertRekursiv ()
  {
    if (rest != null)
    {
      if (kopf <= rest.lkopf())
        return rest.sortiertRekursiv();
      else
        return false;
    }
    else  // rest == null; die Liste ist bis zum Ende sortiert
      return true;
  }

  public void ausgebenRueckwaerts ()
  {
    if (rest != null)
      rest.ausgebenRueckwaerts();
    System.out.print(kopf+" ");  // unsauber: "\n" am Ende mu§ von der aufrufenden Methode erzeugt werden
  }
}
