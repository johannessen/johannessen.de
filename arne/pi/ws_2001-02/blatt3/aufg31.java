/*------------------------------------------------------------------------------
 * Aufgabe: 3-1
 *   Autor: Arne Johannessen
 *
 * Version: 1.0
 *----------------------------------------------------------------------------*/

public class aufg31
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

  static int ggT(int a, int b)
  {
    if (a % b == 0)
      return b;
    else
      return ggT(b, a % b);
  }

  public static void main(String[] args)
  {
    int zahl, qSumme;

    System.out.print("Bitte erste Zahl eingeben: ");
    zahl = Eingabe();
    System.out.print("Bitte zweite Zahl eingeben: ");
    System.out.println("Grš§ter gemeinsamer Teiler: "+ggT(zahl,Eingabe()));
  }
}