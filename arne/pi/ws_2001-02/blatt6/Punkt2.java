/*------------------------------------------------------------------------------
 * Aufgabe: 6-3 a)
 *   Autor: Arne Johannessen
 *----------------------------------------------------------------------------*/

/**
 * Diese Klasse beschreibt und ver&auml;ndert einen Punkt bzw. Vektor in
 * einem zwei-dimensionalem System.
 * @author <A HREF="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</A>
 * @version 1.0
 */
public class Punkt2
{
  /** x-Koordinate */
  float x;

  /** y-Koordinate */
  float y;

  /** Konstruktor: Standardwert: Nullpunkt */
  Punkt2 ()
  {
    x = 0;
    y = 0;
  }

  /** Konstruktor */
  Punkt2 (float x, float y)
  {
    this.x = x;
    this.y = y;
  }

  /**
   * x-Koordinate lesen
   * @returns x-Koordinate
   */
  public float x ()
  {
    return x;
  }

  /**
   * y-Koordinate lesen
   * @returns y-Koordinate
   */
  public float y ()
  {
    return y;
  }

  /** Punkt auf &uuml;bergebene Koordinaten setzen */
  public void setzen (float x, float y)
  {
    this.x = x;
    this.y = y;
  }

  /**
   * Ppunkt um &uuml;bergebenen Vektor verschieben
   * @returns false, falls das Verschieben nicht erfolgreich war
   */
  public boolean verschieben (Punkt2 vektor)
  {
    if (vektor != null)
    {
      x += vektor.x();
      y += vektor.y();
      return true;
    }
    else
      return false;
  }
}
