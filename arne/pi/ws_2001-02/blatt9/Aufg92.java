/**
 * Aufgabe: 9-2
 *
 * @author <a href="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</a>
 * @version 1.0
 *
 * @see <a href="http://www.informatik.uni-ulm.de/ki/Edu/Vorlesungen/PI-1/WS0102/" lang="de" hreflang="de">Vorlesung <q>Praktische Informatik I</q></a>
 */
public class Aufg92
{
  /**
   * Diese Methode gibt die Anzahl der Nadeln des Weihnachtsbaumes aus Aufgabe 9-2
   * zur&uuml;ck.
   *
   * <br><br><em>Unklare Formulierung:</em><br>
   * Was hei&szlig;t: <q>die Zahl verdreifacht sich um eine Zahl</q>? Es
   * wird angenommen, da&szlig; diese Formulierung meint: <q>die Zahl
   * erh&ouml;ht sich um das dreifache einer Zahl</q>.
   *
   * @param baumHoehe Die H&ouml;he (= das Alter) des Baumes.
   * @return Die Zahl der Nadeln entsprechend der H&ouml;he.
   */
  public static int nadelZahl(int baumHoehe)
  {
    if (baumHoehe > 3)
      return 5 * 20 + 3 * nadelZahl(baumHoehe - 2);
    else if (baumHoehe > 1)  // 1 < baumHoehe <= 3
      return 5 * 20 + 2 * nadelZahl(baumHoehe - 1);
    else if (baumHoehe == 1)
      return 1 * 20;
    else  // baumHoehe < 1: es existiert kein Baum
      return 0;
  }
}
