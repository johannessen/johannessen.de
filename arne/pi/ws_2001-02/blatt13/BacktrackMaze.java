/**
  * Aufgabe: 13-4
  *
  * @author <a href="mailto:aj3@informatik.uni-ulm.de">Arne Johannessen</a>
  * @version 1.0.1 f&uuml;gt die Mini-Test-Main-Methode hinzu und behebt kleinere Bugs.
  *
  * @see <a href="http://www.informatik.uni-ulm.de/ki/Edu/Vorlesungen/PI-1/WS0102/" lang="de" hreflang="de">Vorlesung <q>Praktische Informatik I</q></a>
  */
public class BacktrackMaze
{
  static final int NORTH = 0, EAST = 90, SOUTH = 180, WEST = 270;
  
  static boolean findSolution (Maze maze, int x, int y)
  {
    return findSolution(maze, x, y, 0);
  }
  
  private static boolean findSolution (Maze maze, int x, int y, int stepCount)
  {
    int[] dir;
    int min, nextX, nextY;
    
    if (!isInMaze(maze, x, y))
      throw new IllegalArgumentException();
    else if (isWall(maze, x, y))
      return false;
    else if (stepCount > dimX(maze) * dimY(maze))
      return false;
    else if (isTarget(maze, x, y))
      return true;
    else if (!isVisited(maze, x, y))
      setField(maze, x, y, 1);
    
    // find a field less visited than me
    dir = lookAheadAll(maze, x, y);
    min = minMaze(dir);
    if (min == Maze.WALL || dir[min] == Maze.WALL)
      return false;  // eingeschlossen
    else if (dir[min] == Maze.TARGET)
      return true;
    else if (dir[min] == Maze.EMPTY)
      stepCount = 0;
    
    nextX = moveX(x, min * 90);
    nextY = moveY(y, min * 90);
    setField(maze, nextX, nextY, getField(maze, x, y) + 1);
    return findSolution(maze, nextX, nextY, stepCount + 1);
  }
  
  
  private static boolean isInMaze (Maze maze, int x, int y)
  {
    return x >= 0 && x < dimX(maze) && y >= 0 && y < dimY(maze);
  }
  
  private static int dimX (Maze maze)
  {
    return maze.field.length;
  }
  
  private static int dimY (Maze maze)
  {
    return maze.field[0].length;
  }
  
  private static boolean isWall (Maze maze, int x, int y)
  {
    return !isInMaze(maze, x, y) || getField(maze, x, y) == Maze.WALL;
  }
  
  private static boolean isTarget (Maze maze, int x, int y)
  {
    return getField(maze, x, y) == Maze.TARGET;
  }
  
  private static boolean isVisited (Maze maze, int x, int y)
  {
    return getField(maze, x, y) > Maze.EMPTY;
  }
  
  private static int getField (Maze maze, int x, int y)
  {
    if (!isInMaze(maze, x, y))
      throw new IllegalArgumentException();
    return maze.field[x][y];
  }
  
  private static int setField (Maze maze, int x, int y, int value)
  {
    int old = getField(maze, x, y);
    if (value < 0 || old == Maze.WALL)
      throw new IllegalArgumentException();
    maze.field[x][y] = value;
    return old;
  }
  
  private static int[] lookAheadAll (Maze maze, int x, int y)
  {
    int[] ahead = new int[4];
    for (int i = 0; i < 4; i++)
      ahead[i] = lookAhead(maze, x, y, i * 90);
    return ahead;
  }
  
  private static int lookAhead (Maze maze, int x, int y, int direction)
  {
    try
    {
      int dir = realDirection(direction);
      dir = getField(maze, moveX(x, dir), moveY(y, dir));
      return dir;
    }
    catch (IllegalArgumentException e)
    {
      return Maze.WALL;
    }
  }
  
  private static int moveX (int x, int direction)
  {
    switch (direction)
    {
      case NORTH:
        x--;
        break;
      case SOUTH:
        x++;
        break;
      case EAST: case WEST:
        break;
      default:
        throw new IllegalArgumentException();
    }
    
    return x;
  }
  
  private static int moveY (int y, int direction)
  {
    switch (direction)
    {
      case EAST:
        y++;
        break;
      case WEST:
        y--;
        break;
      case NORTH: case SOUTH:
        break;
      default:
        throw new IllegalArgumentException();
    }
    
    return y;
  }
  
  private static int realDirection (int direction)
  {
    while (direction >= 360 || direction < 0)
      direction -= sign(direction) * 360;
    
    return direction;
  }
  
  private static int sign (int number)
  {
    if (number == 0)
      return 0;
    else if (number > 0)
      return 1;
    else
      return -1;
  }
  
  // get best walk direction: field with smallest number, but no walls etc.
  private static int minMaze (int[] number)
  {
    if (number.length == 0)
      throw new IllegalArgumentException();
    
    int min = Maze.WALL;  // Maze.WALL < 0
    for (int i = 0; i < number.length; i++)
      if (number[i] >= Maze.EMPTY || number[i] == Maze.TARGET)
      {
        min = i;
        break;
      }
    
    if (min > Maze.WALL)
      for (int i = min + 1; i < number.length; i++)
        if (number[i] < number[min] && (number[i] >= Maze.EMPTY || number[i] == Maze.TARGET))
          min = i;
    
    return min;
  }
  
  
  // mini test method; bluej preferred!
  public static void main (String[] args)
  {
    int dimX = 12, dimY = 12, x = -1, y = -1, randomTries = 0;

    if (args.length == 1)
    {
      System.out.println("Two or zero arguments required:  int mazeDimX, int mazeDimY");
      return;
    }
    else if (args.length > 2)
      System.out.println("Extra input ignored.");
    try
    {
      dimX = Math.max(1, Integer.parseInt(args[0]));
      dimY = Math.max(1, Integer.parseInt(args[1]));
    }
    catch (Exception e)
    {
    }
    
    Maze maze = new Maze(dimX, dimY);
    while (isWall(maze, x, y))
    {
      x = (int)(Math.random() * dimX);
      y = (int)(Math.random() * dimY);
      randomTries++;
      if (randomTries > Math.pow(dimX * dimY, 2))
      {
        System.out.println("No entry point found.");
        return;
      }
    }
    
    System.out.print(findSolution(maze, x, y));
    maze.printMaze();
    
    System.out.println();
    System.out.println();
  }
}
