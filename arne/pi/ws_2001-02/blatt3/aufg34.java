/*------------------------------------------------------------------------------
 * Aufgabe: 3-4
 *   Autor: Arne Johannessen
 *
 * Version: 1.0.1
 *----------------------------------------------------------------------------*/

public class aufg34
{
  static int Eingabe ()
  {
    String s = "";
    try {
      s = new java.io.DataInputStream(System.in).
      readLine(); }
    catch (java.io.IOException e) {}
    return java.lang.Integer.parseInt(s);
  }

  static int[] Erathosthenes(int obereGrenze)
  // Erathosthenes gibt einen Array zurück, der alle
  // Primzahlen im Intervall [2;obereGrenze] enthält
  {
    int[] a, b;
    int primzahlen = 0;

    // Arbeitsarray initialisieren
    a = new int[obereGrenze + 1];
    for (int i = 2; i < a.length; i++)
      a[i] = i;

    // Erathosthenes
    for (int i = 2; i < a.length; i++)
      if (a[i] != 0)  // noch nicht weggestrichen?
      {
        primzahlen++;
        for (int j = 2 * a[i]; j < a.length; j = j + a[i])
          a[j] = 0;  // Vielfache wegstreichen
      }

    // Primzahlenarray füllen und zurückgeben
    b = new int[primzahlen];
    for (int i = 2, j = 0; i < a.length; i++)
      if (a[i] != 0)
      {
        b[j] = a[i];
        j++;
      }
    return b;
  }

  public static void main(String[] args)
  {
    int prim[];
    System.out.print("Primzahlen bis zu welcher oberen Grenze berechnen? ");
    int obereGrenze = Eingabe();
    if (obereGrenze >= 2)
    {

      prim = Erathosthenes(obereGrenze);

      // Ausgabe der Primzahlen
      System.out.println("Alle Primzahlen im Intervall [2;"+obereGrenze+"]:");
      for (int i = 0; i < prim.length - 1; i++)
        System.out.print(prim[i]+", ");
      System.out.println(prim[prim.length - 1]);
    }
  }
}