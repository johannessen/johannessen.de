class Liste  
{ Element Kopf, Fuss;  
 
  Liste ()  
  { Kopf = Fuss = null;  
  }  
 
  void erzeuge_Fuss (Knoten k)  
  { if (k != null)  
      if (Kopf == null) 
        Kopf = Fuss = new Element (k, null);  
      else Fuss = Fuss.Nf = new Element (k, null);  
  }  
 
  void entferne_Kopf ()  
  { if (Kopf != null && Kopf != Fuss) 
      Kopf = Kopf.Nf;  
    else Kopf = Fuss = null;  
  }  
} 