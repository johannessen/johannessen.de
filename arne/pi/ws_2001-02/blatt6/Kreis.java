/*------------------------------------------------------------------------------
 * Aufgabe: 6-3 a)
 *   Autor: Arne Johannessen
 *----------------------------------------------------------------------------*/

import Punkt2;

/**
 * Diese Klasse beschreibt und ver&auml;ndert einen Kreis.
 * @author <A HREF="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</A>
 * @version 1.0
 */
public class Kreis
{
  /** Kreismittelpunkt */
  Punkt2 mittelpunkt;

  /** Kreisradius */
  float radius;

  /** Konstruktor: Standardwert: Radius 1 */
  Kreis (Punkt2 mittelpunkt)
  {
    this.mittelpunkt = mittelpunkt;
    radius = 1;
  }

  /**
   * Konstruktor: Standardwert von Punkt
   * @see Punkt2
   */
  Kreis (float radius)
  {
    mittelpunkt = new Punkt2();
    this.radius = radius;
  }

  /** Konstruktor */
  Kreis (Punkt2 mittelpunkt, float radius)
  {
    this.mittelpunkt = mittelpunkt;
    this.radius = radius;
  }

  /**
   * Kreismittelpunkt lesen
   * @returns Kreismittelpunkt
   */
  public Punkt2 mittelpunkt ()
  {
    return mittelpunkt;
  }

  /**
   * Kreismittelpunkt auf &uuml;bergebenen Punkt setzen
   * @returns false, falls das Setzen nicht erfolgreich war
   */
  public boolean mittelpunktSetzen (Punkt2 punkt)
  {
    if (punkt != null)
    {
      mittelpunkt.setzen(punkt.x(),punkt.y());
      return true;
    }
    else
      return false;
  }

  /**
   * Kreismittelpunkt um &uuml;bergebenen Vektor verschieben
   * @returns false, falls das Verschieben nicht erfolgreich war
   */
  public boolean verschieben (Punkt2 vektor)
  {
    return mittelpunkt.verschieben(vektor);
  }

  /**
   * Kreisradius lesen
   * @returns Kreisradius
   */
  public float radius ()
  {
    return radius;
  }

  /** Kreisradius setzen */
  public void radiusSetzen (float radius)
  {
    this.radius = radius;
  }

  /**
   * Kreisumfang errechnen
   * @returns Kreisumfang
   */
  public float umfang ()
  {
    return (float)(2 * Math.PI * radius);
  }

  /**
   * Kreisfl&auml;che errechnen
   * @returns Kreisfl&auml;che
   */
  public float flaeche ()
  {
    return (float)(Math.PI * Math.pow(radius, 2));
  }
}
