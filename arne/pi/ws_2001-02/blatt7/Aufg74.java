import LListe;
import MyIo;

/**
 * Aufgabe: 7-4
 *
 * @author <A HREF="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</A>
 * @version 1.0
 *
 * @see <A HREF="http://www.informatik.uni-ulm.de/ki/Edu/Vorlesungen/PI-1/WS0102/">Vorlesung <Q>Praktische Informatik I</Q></A>
 */
public class Aufg74
{
  static LListe[] stapel;

  static void bewegeTurm (int hoehe, int von, int nach)
  {
    int dritterStapel;

    if (hoehe != 0)
    {
      if (von == 1 && nach == 2 || nach == 1 && von == 2)
        dritterStapel = 0;
      else if (von == 0 && nach == 2 || nach == 0 && von == 2)
        dritterStapel = 1;
      else
        dritterStapel = 2;

      bewegeTurm(hoehe - 1, von, dritterStapel);
      bewegeScheibe(von, nach);
      bewegeTurm(hoehe - 1, dritterStapel, nach);
    }
  }

  static void bewegeScheibe (int von, int nach)
  {
    System.out.print("Scheibe "+stapel[von].lkopf()+" von Pfosten ");
    System.out.println((von + 1)+" nach Pfosten "+(nach + 1));

    stapel[nach] = new LListe(stapel[von].lkopf(), stapel[nach]);
    stapel[von] = stapel[von].lrest();
  }


  static public void main (String[] args)
  {
    stapel = new LListe[3];
    int hoehe;

    System.out.println("T\u00FCrme von Hanoi\n");
    hoehe = MyIo.PromptInt("Bitte geben Sie die Anfangsh\u00F6he des Turms an:", 0);

    stapel[0] = new LListe(1);
    for (int i = 2; i <= hoehe; i++)
      stapel[0].einfuegen(i);

    bewegeTurm(hoehe, 0, 2);

    System.out.println();
  }
}
