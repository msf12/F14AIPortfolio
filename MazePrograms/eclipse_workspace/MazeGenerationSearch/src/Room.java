public class Room
{
	private int dimensionX, dimensionY, maxConnectedRooms, numConnectedRooms;
	private Room[] connectedRooms; //pointers to the rooms connected to this room in the order n,e,s,w

	public Room()
	{
		dimensionX = 1;
		dimensionY = 1;
		maxConnectedRooms = dimensionX*2 + dimensionY*2;
		numConnectedRooms = 0;
		connectedRooms = new Room[maxConnectedRooms];
	}


	/*
	 * returns the integer direction of the new room from the given room
	 * 0 - North
	 * 1 - East
	 * 2 - South
	 * 3 - West
	 * -1 - connection failed because there are no available spaces
	 */
	public int connectRooms(Room r)
	{
		if(numConnectedRooms == maxConnectedRooms)
			return -1;
		connectedRooms[numConnectedRooms] = r;
		return numConnectedRooms;
	}

	
	/*
	 * returns the integer direction of the new room from the given room
	 * 0 - North
	 * 1 - East
	 * 2 - South
	 * 3 - West
	 * -1 - connection failed because there are no available spaces or that space was taken
	 */
	public int connectRooms(Room r, int direction)
	{
		if(numConnectedRooms == maxConnectedRooms || connectedRooms[direction] != null)
			return -1;
		connectedRooms[direction] = r;
		numConnectedRooms++;
		return direction;
	}
	
	public int getNumConnectedRooms()
	{
		return numConnectedRooms;
	}

	public int getMaxConnectedRooms()
	{
		return maxConnectedRooms;
	}
	
	public String[] toStringArray()
	{
		String[] roomString = new String[3];
		roomString[0] = (connectedRooms[0] == null) ? "#---#" : "#   #";
		roomString[1] = (connectedRooms[3] == null) ? "|   " : "    ";
		roomString[1] += (connectedRooms[1] == null) ? "|" : " ";
		roomString[2] = (connectedRooms[2] == null) ? "#---#" : "#   #";		
		return roomString;
	}
	
	public String[] toStringArray(int roomType) //0 if start, 1 if end, -1 if neither
	{
		String room = " ";
		if (roomType == 0)
			room = "S";
		else if (roomType == 1)
			room = "E";
		String[] roomString = new String[3];
		roomString[0] = (connectedRooms[0] == null) ? "#---#" : "#   #";
		roomString[1] = (connectedRooms[3] == null) ? "| " + room + " " : "  " + room + " ";
		roomString[1] += (connectedRooms[1] == null) ? "|" : " ";
		roomString[2] = (connectedRooms[2] == null) ? "#---#" : "#   #";		
		return roomString;
	}
	
//	public static void main(String[] args)
//	{
//		int mazeX = 5, mazeY = 5;
//		Room[][] maze = new Room[mazeX][mazeY];
//
//		for (int i = 0; i < maze.length; i++)
//		{
//			for (int j = 0; j < maze[i].length; j++)
//			{
//				maze[i][j] = new Room();
//			}
//		}
//
//		int startX = (int)(Math.random()*(mazeX-1)),
//			startY = (int)(Math.random()*(mazeY-1));
//
//		Room start = maze[startX][startY]; //start is the chosen starting room
//
//		switch((int)(Math.random()*(start.getMaxConnectedRooms()-1))) //FIX ARRAYINDEXOUTOFBOUNDS
//		{
//		case 0:
//			start.connectRooms(maze[startX][startY-1]);
//			maze[startX][startY-1].connectRooms(start);
//			break;
//		case 1:
//			start.connectRooms(maze[startX+1][startY]);
//			maze[startX+1][startY].connectRooms(start);
//			break;
//		case 2:
//			start.connectRooms(maze[startX][startY+1]);
//			maze[startX][startY+1].connectRooms(start);
//			break;
//		case 3:
//			start.connectRooms(maze[startX-1][startY]);
//			maze[startX-1][startY].connectRooms(start);
//			break;
//		default:
//			System.out.println("Error: invalid neighbor code");
//		}
//	}
}