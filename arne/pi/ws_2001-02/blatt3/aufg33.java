/*------------------------------------------------------------------------------
 * Aufgabe: 3-3 a), b)
 *   Autor: Arne Johannessen
 *
 * Version: 1.0
 *----------------------------------------------------------------------------*/

public class aufg33
{
  static int Eingabe()
  {
    String s = "";
    try {
      s = new java.io.DataInputStream(System.in).
      readLine(); }
    catch (java.io.IOException e) {}
    return java.lang.Integer.parseInt(s);
  }

  public static void main(String[] args)
  {
    int a[];
    int summe = 0, alternierendeSumme = 0;

    // Array anlegen
    System.out.print("Anzahl der zu addierenden Zahlen: ");
    a = new int[Eingabe()];

    // Zahlen einlesen
    for (int i = 0; i < a.length; i++)
    {
      System.out.print((i + 1)+". Zahl: ");
      a[i] = Eingabe();
    }

    // Aufgabe a): Summe errechnen
    for (int i = 0; i < a.length; i++)
      summe += a[i];
    System.out.println("Summe dieser Zahlen: "+summe);

    // Aufgabe b): "alternierende Summe" errechnen (lt. Aufgabenstellung)
    for (int i = 0; i < a.length; i++)
      if (i % 2 != 0 || i == 0)
        alternierendeSumme += a[i];
      else
        alternierendeSumme -= a[i];
    System.out.println("Alternierende Summe: "+alternierendeSumme);
  }
}