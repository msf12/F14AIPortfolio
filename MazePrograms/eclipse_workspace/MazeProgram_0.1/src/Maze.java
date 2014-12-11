public class Maze {

	int mazeX, mazeY, numConnections;
	int[] startLocation;
	Room start;
	Room[][] maze;

	public Maze()
	{
		this(5,5);
	}

	public Maze(int x, int y)
	{
		mazeX = x;
		mazeY = y;
		numConnections = 0;
		maze = new Room[mazeX][mazeY];

		for (int i = 0; i < maze.length; i++)
		{
			for (int j = 0; j < maze[i].length; j++)
			{
				maze[i][j] = new Room();
			}
		}

		startLocation = new int[]{0,0};//(int)(Math.random()*(mazeX-1)), (int)(Math.random()*(mazeY-1))};

		Room start = maze[startLocation[0]][startLocation[1]]; //start is the chosen starting room

		generateMaze(start, startLocation, -1);
	}

	private void generateMaze(Room r, int[] rLocation, int directionOfPrevious)
	{
		int[][] neighborLocations = new int[][] {
				{rLocation[0],rLocation[1]-1},
				{rLocation[0]+1,rLocation[1]},
				{rLocation[0],rLocation[1]+1},
				{rLocation[0]-1,rLocation[1]}
		}; //4 neighbors each with an x and y coordinate in the order n,e,s,w

		if (rLocation[1] == 0) //North
			neighborLocations[0] = null;
		else if (rLocation[1] == mazeY-1) //South
			neighborLocations[2] = null;
		if (rLocation[0] == 0) //West
			neighborLocations[3] = null;
		else if (rLocation[0] == mazeX-1) //East
			neighborLocations[1] = null;

		int chosenNeighbor;
		Room neighbor;
		
		while (true)
		{
			chosenNeighbor = (int)(Math.random()*(r.getMaxConnectedRooms()-1));

			if (neighborLocations[chosenNeighbor] != null && chosenNeighbor != directionOfPrevious)
			{
				neighbor = maze[neighborLocations[chosenNeighbor][0]][neighborLocations[chosenNeighbor][1]];
				r.connectRooms(neighbor);
				neighbor.connectRooms(r,3-chosenNeighbor);
				break;
			}
		}
		
		generateMaze(neighbor, neighborLocations[chosenNeighbor], 3-chosenNeighbor);
	}

	public static void main(String[] args) {

		Maze maze = new Maze();
	}

}
