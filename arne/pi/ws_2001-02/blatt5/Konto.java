/*------------------------------------------------------------------------------
 * Aufgabe: 5-3 a)
 *   Autor: Arne Johannessen, mailto:aj3@informatik.uni-ulm.de
 *
 * Version: 1.0
 *
 * �nderungen in 1.0:
 * Der Typ der Instanzvariable kontostand wurde von int auf Geld ge�ndert; alle
 * Zugriffe auf kontostand wurden entsprechend angepasst.
 *----------------------------------------------------------------------------*/

/**
 * Diese Klasse beschreibt die prototypische Struktur eines
 * Bankkontos und erlaubt typische Operationen, die auf einem
 * Bankkonto m�glich sind.
 * 
 * @author Thorsten Liebig; angepasst von Arne Johannessen
 * @version vom 26.11.2001
 */
public class Konto
{
    /** Objektvariable f�r die Speicherung des Kontostands */
    Geld kontostand;

    /**
     * Konstruktor: Er�ffnet ein neues Konto mit Guthaben = 0
     */
    Konto()
    {
        // Kontostand initialisieren
        kontostand = new Geld();  // 5-3 a)
    }

    /**
     * Addiert den Einzahlungsbetrag zum aktuellen Kontostand
     * 
     * @param  Betrag Der Einzahlungsbetrag
     **/
    void einzahlen(int betrag)
    {
        // Addition mit aktuellem Kontostand
        if (betrag > 0)
            kontostand.geldmengeVeraendern(betrag);  // 5-3 a)
        else
            System.out.println("Geldbetrag muss gr��er Null sein!");
    }

    /**
     * Gibt den aktuellen Kontostand auf der Standardausgabe aus
     * 
     **/
    void abfragen_Kstd()
    {
        System.out.println("Der aktuelle Kontostand betr�gt: " +
                           kontostand.geldBetrag());  // 5-3 a)
    }

    /**
     * Reduziert den Kontostand um den angegebenen Betrag
     * 
     * @param  Betrag Der abzuhebende Betrag
     **/
    void abheben(int betrag)
    {
        // Subtraktion mit aktuellem Kontostand
        if (betrag > 0)
            kontostand.geldmengeVeraendern(-1 * betrag);  // 5-3 a)
        else
            System.out.println("Geldbetrag muss gr��er Null sein!");
    }

    /**
     * �berweist den Betrag auf das angegebene Konto
     * 
     * @param  konto   Das Zielkonto
     * @param  betrag  Der �berweisungsbetrag
     **/
    void ueberweisen(Konto konto, int betrag)
    {
        // 1. Schritt: Geld vom Urspungskonto abheben
        this.abheben(betrag);
        // 2. Schritt: Geld auf Zielkonto einzahlen
        konto.einzahlen(betrag);
    }

    /**
     * Vergleicht zwei Konten bzgl. ihres Kontostands
     * 
     * @param  konto  Das Vergleichs-Konto
     * @return        true, falls gleich, false sonst 
     **/
    boolean gleicherKontostand(Konto konto)
    {
        return kontostand.geldBetrag() == konto.kontostand.geldBetrag();  // 5-3 a)
    }

    /**
     * �berpr�ft, ob zwei Konten gleich sind
     * 
     * @param  konto  Das Vergleichs-Konto
     * @return        true, falls gleich, false sonst 
     **/
    boolean gleichesKonto(Konto konto)
    {
        return this == konto;
    }
}