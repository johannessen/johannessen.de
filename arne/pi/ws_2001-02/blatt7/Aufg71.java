/**
 * Aufgabe: 7-1
 *
 * @author <A HREF="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</A>
 * @version 1.0
 *
 * @see <A HREF="http://www.informatik.uni-ulm.de/ki/Edu/Vorlesungen/PI-1/WS0102/">Vorlesung <Q>Praktische Informatik I</Q></A>
 */
public class Aufg71
{
  /** Aufgabe a) */
  static int p (int a, int n)
  {
    if (n > 1)
      return a * p(a, n - 1);
    else  // n <= 1
      return 1;
  }

  /** Aufgabe b) */
  static int t (int n)
  {
    if (n > 1)
      return p(2, t(n - 1));
    else  // n <= 1
      return 1;
  }

  /** Aufgabe c) */
  static int f (int n)
  {
    if (n > 1)
      return f(n - 1) + f(n - 2);
    else if (n == 1)
      return 1;
    else  // n <= 0
      return 0;
  }

  /** Aufgabe d) */
  static int g (int a, int b)
  {
    if (a % b == 0)
      return b;
    else
      return g(b, a % b);
  }

}
