/**
  * Aufgabe: 9-3
  *
  * Benutzt die Klassen Baum und Element (aus der Vorlesung) sowie
  * die um die Z&auml;hlervariable erweiterte Klasse Liste
  *
  * @author <a href="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</a>
  * @version 1.0
  *
  * @see <a href="http://www.informatik.uni-ulm.de/ki/Edu/Vorlesungen/PI-1/WS0102/" lang="de" hreflang="de">Vorlesung <q>Praktische Informatik I</q></a>
  */
class Aufg93 extends Baum
{
  /** @see Liste */
  Element listeKopf, listeFuss;

  /** Setzt <code>listeKopf</code> und <code>listeFuss</code> auf
    * <code>null</code> und ruft den Konstruktor von <code>Baum</code> auf.
    * @see Baum */
  Aufg93 ()
  {
    super();
    listeKopf = listeFuss = null;
  }

  /** @see Liste */
  void erzeuge_Fuss (Knoten k)
  {
    if (k != null)
      if (listeKopf == null)
      {
        listeFuss = new Element(k, null);
        listeKopf = listeFuss;
      }
      else
      {
        listeFuss.Nf = new Element(k, null);
        listeFuss = listeFuss.Nf;
      }
  }

  /** @see Liste */
  void entferne_Kopf ()
  {
    if (listeKopf != null && listeKopf != listeFuss)
      listeKopf = listeKopf.Nf;
    else
      listeKopf = listeFuss = null;
  }

  /** Sortiert einen Knoten in absteigender Reihenfolge in die Liste ein.
    * @param knoten der einzusortierende Knoten
    */
  void listeElementEinfuegen (Knoten knoten)
  {
    listeElementEinfuegen(knoten, listeKopf, null);
  }
  
  private void listeElementEinfuegen (Knoten k, Element e, Element prevE)
  {
    if (e == null)  // e == listeFuss: einfuegen!
      if (prevE != null)
        listeFuss = prevE.Nf = new Element(k, null);
      else  // e == listeKopf
        listeFuss = listeKopf = new Element(k, e);
    else if (k.Zahl < e.Kn.Zahl)
      listeElementEinfuegen(k, e.Nf, e);
    else  // k.Zahl >= e.Zahl: einfuegen!
      if (prevE != null)
        prevE.Nf = new Element(k, e);
      else  // e == listeKopf
        listeKopf = new Element(k, e);
  }

  /**
    * Erg&auml;nzt erzeuge_Blatt aus Baum. Wird eine Zahl in
    * den Baum eingef&uuml;gt, die sich schon im Baum befindet,
    * so wird nun ein Z&auml;hler im Knoten, der diese Zahl
    * repr&auml;sentiert, entsprechend erh&ouml;ht.
    * Ferner werden neu erstellte Knoten oder solche, bei denen die
    * Zahl bereits im Baum vorhanden war, der Liste hinzugef&uuml;gt.
    */
  Knoten erzeuge_Blatt (int Zahl, Knoten k)
  {
    if (k == null)
    {
      Knoten neuerKnoten = new Knoten(Zahl);
      listeElementEinfuegen(neuerKnoten);
      return neuerKnoten;
    }
    else if (Zahl < k.Zahl)
    {
      k.links = erzeuge_Blatt(Zahl, k.links);
      return k;
    }
    else if (Zahl > k.Zahl)
    {
      k.rechts = erzeuge_Blatt(Zahl, k.rechts);
      return k;
    }
    else  // Zahl == k.Zahl
    {
      k.zaehler++;
      listeElementEinfuegen(k);
      return k;
    }
  }

}