import java.util.Vector;

/**
  * Aufgabe: 9-1
  *
  * This class implements methods to access a Vector for managing a tree structure as described in 9-1.
  *
  * @author <a href="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</a>
  * @version 1.1 <br>introduces better exception checking, minor code tweaks and fixes a bug in getString(int), where "" had to be replaced by null.
  *
  * @see <a href="http://www.informatik.uni-ulm.de/ki/Edu/Vorlesungen/PI-1/WS0102/" lang="de" hreflang="de">Vorlesung <q>Praktische Informatik I</q></a>
  */
public class ArrayBaum extends Vector
{
  /** The array index of the tree's root item (<code>ROOT = 0</code>). */
  final public int ROOT = 0;
  
  /**
    * @return The array index of the parent tree item.
    * @throws IndexOutOfBoundsException if the given index is the index of the tree's root item or less or if it is greater than the last item's index.
    * @see #ROOT
    */
  public int treeParent (int index) throws IndexOutOfBoundsException
  {
    if (index > ROOT && index < this.size())
      return (int)((index + 1) / 2) - 1;
    else
      throw new IndexOutOfBoundsException();
  }
  
  /** @return The array index of the left tree branch.
    * @throws IndexOutOfBoundsException if the given index is less than the index of the tree's root item or if it is greater than the last item's index.
    */
  public int treeLeftBranch (int index)
  {
    if (index >= ROOT && index < this.size())
      return 2 * (index + 1) - 1;
    else
      throw new IndexOutOfBoundsException();
  }
  
  /** @return The array index of the right tree branch.
    * @throws IndexOutOfBoundsException if the given index is less than the index of the tree's root item or if it is greater than the last item's index.
    */
  public int treeRightBranch (int index)
  {
    if (index >= ROOT && index < this.size())
      return 2 * (index + 1);
    else
      throw new IndexOutOfBoundsException();
  }
  
  /** @return true if the array represents a valid tree (that is, there are no items without parents). */
  public boolean isValidTree ()
  {
    for (int i = this.size() - 1; i > ROOT; i--)
      if (this.get(i) != null)  // "nothing" has no need of a parent
        if (this.get(treeParent(i)) == null)
          return false;

    // at this point, every item's parent has been tested
    return true;
  }



  /**
   * Extends <code>get</code> method
   * @see Vector
   */
  public String getString (int index)
  {
    if (this.get(index) != null)  // bug in 1.0: null was ""; fixed
      return (String)this.get(index);
    else
      return "";
  }

  /**
   * Extends <code>setElementAt</code> method
   * @see Vector
   */
  public void setElementAt (String str, int index)
  {
    if (str != "")
      this.setElementAt((Object)str, index);
    else
      this.setElementAt(null, index);
  }

  /**
   * Extends <code>addElement</code> method
   * @see Vector
   */
  public void addElement (String str)
  {
    if (str != "")
      this.addElement((Object)str);
    else
      this.addElement(null);
  }

  /**
   * <i>Inherited constructor</i>
   * @see Vector
   */
  ArrayBaum ()
  {
    super();
  }

  /** Imports an array of strings given in the correct order. */
  ArrayBaum (String[] stringArray)
  {
    super();
    for (int i = 0; i < stringArray.length; i++)
      addElement(stringArray[i]);
  }

  /** @return An array of strings containing all of the tree elements in the correct order. */
  public String[] toStringArray ()
  {
    String[] stringArray = new String[this.size()];
    for (int i = 0; i < stringArray.length; i++)
      stringArray[i] = getString(i);
    
    return stringArray;
  }
}
