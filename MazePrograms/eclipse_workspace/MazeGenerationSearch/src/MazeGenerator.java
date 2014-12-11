import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class MazeGenerator {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Maze x?");
		int x = sc.nextInt();
		sc.nextLine();
		System.out.println("Maze y?");
		int y = sc.nextInt();
		sc.close();
		
		List<int[]> frontier = new ArrayList<int[]>();

		Maze maze = new Maze(x,y);
		frontier.add(maze.getStartLocation());
	}
	
	//goal is for the maze to be "complete"
	//(each room is connected to at least once and the end is only connected to once)
	private static boolean goalFunction(Maze m, int x, int y)
	{
		for (int i = 0; i < x; i++)
		{
			for (int j = 0; j < y; j++)
			{
				if(m.getRoom(i,j).getNumConnectedRooms() == 0)
					return false;
			}
		}
		
		if(m.getEnd().getNumConnectedRooms() > 1)
			return false;
		
		return true;
	}

	private static List<Maze> generateSuccessors(List<int[]> frontier, Maze m)
	{
		ArrayList<Maze> successors = new ArrayList<Maze>();
		
		//frontier needs to be a list of Mazes? If so each Maze needs to keep track of its own edge rooms
		
		return null;
	}

}