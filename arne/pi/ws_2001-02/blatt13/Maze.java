import java.util.Random;

public class Maze
{
    // Konstanten zur symbolischen Felderbelegung
    final static int EMPTY = 0, WALL = -1, TARGET = -10;
    // Irrgarten
    int[][] field;

    /**
     * Konstruktor für Objekte der Klasse Maze
     */
    public Maze(int n, int m)
    {
        // Initialisierung der Instanzvariablen
        field = new int[n][m];
        Random ranObj = new Random();

        // Zufällige Belegung der Felder (30 % Wände und
        // 70 % Leer)
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field[0].length; j++)
            {
                int ran = ranObj.nextInt(11);
                if (ran >= 7)
                    field[i][j] = WALL;
                else
                    field[i][j] = EMPTY;
            }

        // Ein zufälliges Zielfeld wählen
        int targetX, targetY;
        do {
            targetX = ranObj.nextInt(field.length);
            targetY = ranObj.nextInt(field[0].length);
            if ( field[targetX][targetY] == EMPTY )
                field[targetX][targetY] = TARGET;
        } while ( field[targetX][targetY] != TARGET );
    }

    /**
     * Ausgabe des Irrgartens mit min. zwei Stellen
     */
    public void printMaze()
    {
		int max = TARGET;
		for (int i = 0; i < field.length; i++)
			for (int j = 0; j < field[0].length; j++)
				if (field[i][j] > max)
					max = field[i][j];
		printMaze(Math.max(2, (int)(Math.log(max) / Math.log(10)) + 1));
    }

    /**
     * Ausgabe des Irrgartens
     */
    void printMaze(int stellen)
    {
        System.out.println();
        // Oberen Rand zeichnen
        for (int j = 0; j < stellen * field[0].length + stellen; j++)
            System.out.print("_");
        System.out.println();
        for (int i = 0; i < field.length; i++)
        {
            System.out.print("|");
            // Felder zeichnen
            for (int j = 0; j < field[0].length; j++)
                if (field[i][j] == WALL)
					for (int k = 0; k < stellen; k++)
                    	System.out.print("#");
                else
                    if (field[i][j] == EMPTY)
						for (int k = 0; k < stellen; k++)
                   	    	System.out.print(" ");
                    else
                        if (field[i][j] == TARGET)
							for (int k = 0; k < stellen; k++)
	                            System.out.print("*");
                        else
						{
                            for (int k = 1; k < stellen - (int)(Math.log(field[i][j]) / Math.log(10)); k++)
                                System.out.print(" ");
                        	System.out.print(field[i][j]);
						}
            System.out.println("|");
        }
        // Unteren Rand Zeichnen
        for (int j = 0; j < stellen * field[0].length + 2; j++)
            System.out.print("-");
    }

    /**
     * Setzt den Irrgarten in seinen initialen Zustand (d.h.
     * ursprüngliche Belegung von Wänden) zurück
     */
    public void resetMaze()
    {
        for (int i = 0; i < field.length; i++)
            for (int j = 0; j < field[0].length; j++)
                // Alles ausser Wänden und dem Ziel auf 'EMPTY' setzen
                if ( field[i][j] != WALL && field[i][j] != TARGET )
                    field[i][j] = EMPTY;
    }
}