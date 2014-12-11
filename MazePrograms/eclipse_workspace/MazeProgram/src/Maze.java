import java.util.Stack;

public class Maze {

	int mazeX, mazeY; //dimensions of the maze
	int[] startLocation, endLocation; //the location of the start and end rooms
	Room start, end; //the start and end rooms
	Room[][] maze; //the maze as an array of rooms
	Stack<Room> path; //the path the maze creation has taken so far for use backtracking after finding the end
	Stack<int[]> pathLocations; //the locations of the rooms along the path


	public Maze() //default size is 5x5
	{
		this(5,5);
	}

	public Maze(int x, int y)
	{
		mazeX = x;
		mazeY = y;
		maze = new Room[mazeX][mazeY];
		path = new Stack<Room>();
		pathLocations = new Stack<int[]>();

		//initialize the maze as a 2D array of unconnected rooms
		for (int i = 0; i < maze.length; i++)
		{
			for (int j = 0; j < maze[i].length; j++)
			{
				maze[i][j] = new Room();
			}
		}

		//choose a starting location and an ending location
		startLocation = new int[]{0, (int)(Math.random()*(mazeY))};

		endLocation = new int[]{mazeX-1, (int)(Math.random()*(mazeY))};

		start = maze[startLocation[0]][startLocation[1]]; //start is the chosen starting room
		end = maze[endLocation[0]][endLocation[1]]; //start is the chosen starting room

		int numConnectedRooms = 1, //includes the start room
				directionOfPrevious = -1; //direction of the room the path came from in the order 0=N,1=E,2=S,3=W
		int[] rLocation = startLocation; //location of the current room

		//create the maze
		for (Room currentRoom = start; numConnectedRooms < mazeX*mazeY;)
		{
			//calculate the locations of the four neighboring rooms
			int[][] neighborLocations = new int[][] {
					{rLocation[0],rLocation[1]-1},
					{rLocation[0]+1,rLocation[1]},
					{rLocation[0],rLocation[1]+1},
					{rLocation[0]-1,rLocation[1]}
			};

			//remove locations that are outside the maze's bounds
			if (rLocation[1] == 0) //North
				neighborLocations[0] = null;
			else if (rLocation[1] == mazeY-1) //South
				neighborLocations[2] = null;
			if (rLocation[0] == 0) //West
				neighborLocations[3] = null;
			else if (rLocation[0] == mazeX-1) //East
				neighborLocations[1] = null;

			int chosenNeighbor; //the direction number of the chosen neighbor
			Room neighbor; //the chosen neighbor

			while (true)
			{
				chosenNeighbor = (int)(Math.random()*currentRoom.getMaxConnectedRooms()); //choose a random neighbor

				//				System.out.println("Looping! There are " + numConnectedRooms + " connected rooms. Currently at room ("
				//						+ rLocation[0] + ", " + rLocation[1] + "). Chosen neighbor is neighbor "
				//						+ chosenNeighbor);

				if (neighborLocations[chosenNeighbor] != null && chosenNeighbor != directionOfPrevious) //if neighbor exists and the path didn't come from it
				{

					//					System.out.println("Chose a room");

					neighbor = maze[neighborLocations[chosenNeighbor][0]][neighborLocations[chosenNeighbor][1]]; //get and store neighbor

					if (neighbor == end && neighbor.getNumConnectedRooms() > 0) //if the neighbor is the end or has a connection already continue
					{
						//						System.out.println("At the end again");
						continue;
					}

					if (deadEnd(neighborLocations)) //if the current room is a dead end start over from a random valid neighbor
					{
						//						System.out.println("Dead end");
						currentRoom = neighbor;
						rLocation = neighborLocations[chosenNeighbor];
						directionOfPrevious = (chosenNeighbor+2)%4;
						break;
					}

					if (neighbor.getNumConnectedRooms() > 0)
						continue;

					//push the current room and its location to the stacks
					path.push(currentRoom);
					pathLocations.push(rLocation);

					//					System.out.println("Connecting room at (" + rLocation[0] + ", " + rLocation[1] + ") to room at ("
					//							+ neighborLocations[chosenNeighbor][0] + ", " + neighborLocations[chosenNeighbor][1] + ") with "
					//							+ neighbor.getNumConnectedRooms() + " previous connections.");

					//connect the rooms
					currentRoom.connectRooms(neighbor,chosenNeighbor);
					neighbor.connectRooms(currentRoom,(chosenNeighbor+2)%4);

					//change current room and location to the room and room location just connected to and increment numConnectedRooms
					currentRoom = neighbor;
					rLocation = neighborLocations[chosenNeighbor];
					numConnectedRooms++;

					//if the path has found the end
					if (neighbor == end)
					{
						//						System.out.println("At the end");
						//go back a random amount in the path and continue from there
						for (int i = 0; i < (int)(Math.random()*(path.size()-3))+1; i++)
						{
							path.pop();
							pathLocations.pop();
						}
						currentRoom = path.pop();
						rLocation = pathLocations.pop();
						directionOfPrevious = -1;
						break;
					}

					directionOfPrevious = (chosenNeighbor+2)%4;
					break;
				}
			}

		}

	}

	private boolean deadEnd (int[][] neighborLocations)
	{
		for (int[] neighbor : neighborLocations)
		{
			if (neighbor != null && maze[neighbor[0]][neighbor[1]].getNumConnectedRooms() == 0)
				return false;
		}
		return true;
	}

	public String toString()
	{
		String mazeString = "";

		for (int i = 0; i < mazeY; i++)
		{
			for (int k = 0; k < 3; k++)
			{
				for (int j = 0; j < mazeX; j++)
				{
					if (maze[j][i] == end)
						mazeString += maze[j][i].toStringArray(1)[k];
					else if (maze[j][i] == start)
						mazeString += maze[j][i].toStringArray(0)[k];
					else
						mazeString += maze[j][i].toStringArray()[k];
				}
				mazeString += "\n";
			}
		}

		return mazeString;
	}

	public static void main(String[] args) {
			Maze maze = new Maze(10,10);
			System.out.println(maze);
	}

}
