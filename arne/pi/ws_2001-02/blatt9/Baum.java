class Baum 
{ Knoten Wurzel; 
 
  Baum () 
  { Wurzel = null; 
  } 
 
  Knoten suche (int Zahl) 
  { return suche (Zahl, Wurzel); 
  } 
 
  Knoten suche (int Zahl, Knoten k) 
  { if (k == null) 
      return null; 
    else if (k.Zahl == Zahl) 
      return k; 
    else { 
      Knoten s = suche(Zahl, k.links); 
      if (s != null) 
        return s; 
      else 
        return suche(Zahl, k.rechts); 
    } 
  } 
 
  void erzeuge_Blatt (int Zahl) 
  { Wurzel = erzeuge_Blatt (Zahl, Wurzel); 
  } 
 
  Knoten erzeuge_Blatt (int Zahl, Knoten k) 
  { if (k == null) 
      return new Knoten (Zahl); 
    else if (Zahl < k.Zahl) 
    { k.links = erzeuge_Blatt (Zahl, k.links);  
      return k; 
    } 
    else 
    { k.rechts = erzeuge_Blatt (Zahl, k.rechts);  
          return k; 
    } 
  } 
 
  void durchlaufe () 
  { System.out.println ("Baum:");  
    durchlaufe (Wurzel, 0); 
  } 
 
  void durchlaufe (Knoten k, int Einrueckung) 
  { if (k != null) 
    { durchlaufe (k.links, Einrueckung + 2); 
      for (int i = 1; 
           i <= Einrueckung; 
           i = i + 1) 
        System.out.print (" "); 
      System.out.println (k.Zahl); 
      durchlaufe (k.rechts, Einrueckung + 2); 
    } 
  } 
 
// K: Knoten K, RUB/LUB: Rechter/Linker Unterbaum 
 
void Pre_Order_Tiefendurchlauf () 
{ 
    System.out.print("Pre-Order Tiefendurchlauf: "); 
    Pre_Order_Tiefendurchlauf (Wurzel); 
    System.out.println(); 
} 
 
void Pre_Order_Tiefendurchlauf (Knoten k) 
{ if (k != null) 
  { System.out.print (" " + k.Zahl);       // 1. K 
    Pre_Order_Tiefendurchlauf (k.links);   // 2. LUB 
    Pre_Order_Tiefendurchlauf (k.rechts);  // 3. RUB 
  } 
} 
 
void In_Order_Tiefendurchlauf () 
{ 
    System.out.print("In-Order Tiefendurchlauf:  "); 
    In_Order_Tiefendurchlauf (Wurzel); 
    System.out.println(); 
} 
 
void In_Order_Tiefendurchlauf (Knoten k) 
{ if (k != null) 
  { In_Order_Tiefendurchlauf (k.links);    // 1. LUB 
    System.out.print (" " + k.Zahl);       // 2. K 
    In_Order_Tiefendurchlauf (k.rechts);   // 3. RUB 
  } 
} 
 
void Post_Order_Tiefendurchlauf () 
{ 
    System.out.print("Post-Order Tiefendurchlauf:"); 
    Post_Order_Tiefendurchlauf (Wurzel); 
    System.out.println(); 
} 
 
void Post_Order_Tiefendurchlauf (Knoten k) 
{ if (k != null) 
  { Post_Order_Tiefendurchlauf (k.links);   // 1. LUB 
    Post_Order_Tiefendurchlauf (k.rechts);  // 2. RUB 
    System.out.print (" " + k.Zahl);        // 3. K 
  } 
} 
 
void Breitendurchlauf ()  
{ Liste L = new Liste ();  
  L.erzeuge_Fuss (Wurzel);  
  System.out.print("Breitendurchlauf:          "); 
  while (L.Kopf != null)  
  { System.out.print(" " + L.Kopf.Kn.Zahl);  
    L.erzeuge_Fuss (L.Kopf.Kn.links);  
    L.erzeuge_Fuss (L.Kopf.Kn.rechts);  
    L.entferne_Kopf ();  
  } 
  System.out.println(); 
} 
 
void baumErzeugen1() 
{ 
    this.erzeuge_Blatt(5); 
    Wurzel.links = new Knoten(2); 
    Wurzel.links.links = new Knoten(7); 
    Wurzel.links.rechts = new Knoten(8); 
    Wurzel.rechts = new Knoten(3); 
    Wurzel.rechts.links = new Knoten(4); 
} 
 
void baumErzeugen2() 
{ 
    this.erzeuge_Blatt(6); 
    this.erzeuge_Blatt(2); 
    this.erzeuge_Blatt(5); 
    this.erzeuge_Blatt(8); 
    this.erzeuge_Blatt(7); 
    this.erzeuge_Blatt(1); 
    this.erzeuge_Blatt(3); 
    this.erzeuge_Blatt(9); 
    this.erzeuge_Blatt(4); 
    this.erzeuge_Blatt(5); 
} 
} 