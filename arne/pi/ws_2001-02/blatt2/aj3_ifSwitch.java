/*------------------------------------------------------------------------------
 * Aufgabe: 2-5
 * Autor: Arne Johannessen
 *          
 *
 * Version: 1.0
 *----------------------------------------------------------------------------*/

/* zu Aufgabe 2-5 a):
 * Die Ausgabe der switch-Anweisung ist -- genau wie im folgenden Beispiel --
 * "Null oder Eins" (2 * 5 % 3 == 1).
 */

public class aj3_ifSwitch
{
    public static void main (String [] param)
    {
        // Wert des Ausdrucks in Variable zwischenspeichern
        int evalExpression = 2 * 5 % 3;
        
        if (evalExpression == 0 || evalExpression == 1)  // case 0 , case 1
            System.out.println("Null oder Eins");
        else
        {
            if (evalExpression == 2)  // case 2
                System.out.println("Zwei");
            else
                System.out.println("Fertig");  // default
        }
    }
}
