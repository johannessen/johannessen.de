/*------------------------------------------------------------------------------
 * Aufgabe: 3-2 a), b)
 *   Autor: Arne Johannessen
 *
 * Version: 1.0
 *----------------------------------------------------------------------------*/

public class aufg32
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

  static int quersumme(int zahl)
  {
    int quersumme = 0, ziffer;
    
    while (zahl > 0)
    {
      ziffer = zahl % 10;
      quersumme += ziffer;
      zahl = (zahl - ziffer) / 10;
    }
    return quersumme;
  }

  public static void main(String[] args)
  {
    int zahl, qSumme;
    boolean alleEigenschaften = true, kommaSetzen = false;

    System.out.print("Bitte Zahl eingeben: ");
    zahl = Eingabe();

    // Aufgabe a)
    System.out.print("Die Zahl "+zahl+" ist ");
    if (zahl % 2 != 0)
    {
      System.out.print("un");
      alleEigenschaften = false;
    }
    qSumme = quersumme(zahl);
    System.out.print("gerade; ihre Quersumme ("+qSumme+") ist ");
    if (qSumme % 5 != 0)
    {
      System.out.print("nicht ");
      alleEigenschaften = false;
    }
    System.out.println("durch 5 teilbar");
    System.out.print("und ");
    if (qSumme % 3 != 0)
      System.out.print("nicht ");
    else
      alleEigenschaften = false;
    System.out.println("durch 3 teilbar.");

    System.out.print("Die Zahl "+zahl+" besitzt ");
    if (!alleEigenschaften)
      System.out.print("nicht ");
    System.out.println("alle geforderten Eigenschaften.");

    // Aufgabe b)
    System.out.print("Die folgenden Zahlen sind alle ungeraden Zahlen kleiner");
    System.out.println(" oder gleich "+zahl+",");
    System.out.print("deren Quersumme durch 3, nicht aber durch 5 teilbar");
    System.out.println(" ist:");
    for (int i = 1; i <= zahl; i += 2)
    {
      qSumme = quersumme(i);
      if (qSumme % 3 == 0 && qSumme % 5 != 0)
      {
        if (kommaSetzen)  // kein Komma vor der ersten Zahl in der Liste
          System.out.print(", ");
        else
          kommaSetzen = true;
        System.out.print(i);
      }
    }
    System.out.println("");
  }
}