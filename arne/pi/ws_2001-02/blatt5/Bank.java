/*------------------------------------------------------------------------------
 * Aufgabe: 5-3 b), c)
 *   Autor: Arne Johannessen, mailto:aj3@informatik.uni-ulm.de
 *
 * Version: 1.0
 *
 * Änderungen in 1.0:
 * Die Methoden groesserAlsBank und zahlDerKonten sowie kontoUebertragen und
 * kontoAufloesen sind hinzugefügt worden.
 *----------------------------------------------------------------------------*/

/**
 * Eine Bank kann mehrere Konten verwalten
 * 
 * @author Thorsten Liebig, angepasst von Arne Johannessen
 * @version vom 26.11.2001
 */
public class Bank
{


     /***** Aufgabe 5-3 b) *****/

    /**
     * Überprüft, ob die als Parameter übergebene Bank mehr Konten verwaltet.
     */ 
    boolean groesserAlsBank (Bank bank2)
    {
      if (bank2 != null)
        return (bank2.zahlDerKonten() > anzahlKonten);
      else
        return false;
    }

    /**
     * gibt die Anzahl der Konten der Bank zurück. 
     */ 
    int zahlDerKonten ()
    {
      return anzahlKonten;
    }



    /***** Aufgabe 5-3 c) *****/

    /**
     * ÜbertrŠgt das übergebene Konto dieser Bank auf die übergebene Bank
     */ 
    void kontoUebertragen (Bank bank2, Konto konto)
    {
      if (bank2 != null && wirdverwaltet(konto))
      {
        bank2.kontoVerwalten(konto);
        kontoAufloesen(konto);
      }
    }

    /**
     * entfernt das übergebene Konto aus dieser Bank
     */ 
    void kontoAufloesen (Konto konto)
    {
      int pos = 0;

      // An welcher Position steht das Konto?
        while (pos < anzahlKonten)
        {
          if (konten[pos] == konto)
            break;
          pos++;
        }

      // existiert das Konto in dieser Bank?
      if (pos >= anzahlKonten)
        return;  // nein: Methode verlassen

      // Verweise auf Konto umsortieren; das zu entfernende Konto wird dabei entfernt
      for (int i = pos; i < anzahlKonten - 1; i++)
        konten[i] = konten[i + 1];
      konten[anzahlKonten - 1] = null;
    }



    /***** Originalklasse *****/

    /** Feld zur Speicherung der einzelnen Konten */
    Konto[] konten = new Konto[10];

    /** Zähler der zu verwaltenden Konten (Objektvariable) */
    int anzahlKonten;

    /** Zähler über alle vorhandenen Banken (Klassenvariable) */
    static int anzahlBanken = 0;

    /**
     * Konstruktor für Bank
     */ 
    Bank()
    {
        // Eine Bank besitzt initial kein Konto
        anzahlKonten = 0;
        // Die Gesamtanzahl der Banken erhöht sich um eine
        // weitere Bank
        anzahlBanken = anzahlBanken + 1;
    }

    /**
     * Übergibt ein Konto der Verwaltung der Bank
     * 
     * @param  konto   Das zu verwaltende Bankkonto
     */
    void kontoVerwalten(Konto konto)
    {
        // Konto hinzufügen
        konten[anzahlKonten] = konto;
        // Anzahl der zu verwaltenden Konten erhöhen
        anzahlKonten = anzahlKonten + 1;
    }

    /**
     * Gibt eine Liste der aktuellen Kontostände aller
     * verwalteter Konten aus
     **/
    void kontenuebersicht()
    {
        // put your code here
        for (int i = 0; i < anzahlKonten; i = i + 1)
            konten[i].abfragen_Kstd();
    }

    /**
     * Überprüft, ob das angegebene Konto von dieser Bank
     * verwaltet wird
     * 
     * @param  konto  Das zu überprüfende Konto
     * @return        true, falls ja, false sonst
     **/
    boolean wirdverwaltet(Konto konto)
    {
        int i = 0;
        // Schleife über alle Konten
        while (i < anzahlKonten) {
            // Ist das aktuelle Konto identisch mit dem
            // übergebenem, dann gib "true" zurück
            if (konten[i] == konto) return true;
            else i = i + 1;
        }
        // Es wurde keine Übereinstimmung mit den von dieser
        // Bank verwalteten Konten gefunden, deshalb "false"
        // zurückgeben
        return false;
    }
}