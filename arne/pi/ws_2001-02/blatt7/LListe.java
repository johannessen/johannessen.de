public class LListe { 
  int kopf; 
  LListe rest; 
 
  LListe () { } 
 
  LListe (int kpf) { 
    this.kopf = kpf; 
    this.rest = null; 
  } 
 
  LListe (int kpf, LListe rst) { 
    this.kopf = kpf; 
    this.rest = rst; 
  } 
 
  public int lkopf () { 
    return kopf; 
  } 
 
  public LListe lrest () { 
    return rest; 
  } 
 
    // Test, ob elem in der Liste vorkommt 
  public boolean elemtest (int elem) { 
    if (elem == this.kopf) 
      return true; 
    else if (this.rest==null) 
           return false; 
    else 
      return this.rest.elemtest(elem); 
  } 
 
    // Berechne Summe der linearen Liste 
    public int summe () { 
        if (this.rest==null) 
            return this.kopf; 
        else 
            return this.kopf + this.rest.summe(); 
    } 
 
    public void ausgeben () { 
        System.out.print ( this.kopf + "  "); 
        if (this.rest==null) 
            System.out.println (" "); 
        else 
            rest.ausgeben(); 
    } 
 
 
    // Streiche alle Vorkommen von elem in Liste 
  public LListe streichen (int elem) { 
    if (this.rest!=null) 
      this.rest = this.rest.streichen(elem); 
    if (elem == this.kopf) 
      return this.rest; 
    else 
      return this; 
  } 
 
    // Element einfuegen in geordnete Liste 
  public LListe einfuegen (int elem) { 
    if (elem <= this.kopf) 
      return new LListe(elem, this); 
    else { 
      if (this.rest==null) 
         this.rest = new LListe(elem); 
      else 
         this.rest = this.rest.einfuegen(elem); 
      return this; 
    } 
  } 
} 