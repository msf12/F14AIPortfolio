#include <iostream>
#include <vector>
#include <chrono>
#include <random>
#include <cstdint>

class Room
{
	std::uint8_t dimensionX, dimensionY, maxConnectedRooms, numConnectedRooms;
	std::vector<Room*> connectedRooms; //pointers to the rooms connected to this room
	public:
	Room() //http://www.cplusplus.com/reference/random/uniform_int_distribution/uniform_int_distribution/
	{
		// unsigned seed = std::chrono::system_clock::now().time_since_epoch().count();
		// std::default_random_engine generator (seed);
		// std::uniform_int_distribution<int> dimensionGenerator (1,5);
		// dimensionX = dimensionGenerator(generator);
		// dimensionY = dimensionGenerator(generator);
		dimensionX = 1;
		dimensionY = 1;
		maxConnectedRooms = dimensionX*2 + dimensionY*2;
		numConnectedRooms = 0;
	}
	~Room()
	{}

	void connectRooms(Room &r)
	{
		std::cout << "Worked???" << std::endl;
		(*connectedRooms.end()) = &r;
	}
	
	std::uint8_t getNumConnectedRooms()
	{
		return numConnectedRooms;
	}

	std::uint8_t getMaxConnectedRooms()
	{
		return maxConnectedRooms;
	}
};

int main(int argc, char const *argv[])
{
	using std::cout; using std::endl;
	std::uint8_t mazeX = 5, mazeY = 5;
	std::vector<std::vector<Room> > maze (mazeX,std::vector<Room> (mazeY));

	for (auto &vec : maze)
	{
		for (auto &room : vec)
		{
			room = Room();
		}
	}

	// for (auto &v : maze)
	// {
	// 	static int i = 0;
	// 	for (auto &r : v)
	// 	{
	// 		cout << (int)r.getNumConnectedRooms() << " " << i++ << endl;
	// 	}
	// }

	unsigned seed = std::chrono::system_clock::now().time_since_epoch().count();
	std::default_random_engine generator (seed);
	std::uniform_int_distribution<int> chooseX (0,mazeX-1);
	std::uniform_int_distribution<int> chooseY (0,mazeY-1);

	std::uint8_t startX = chooseX(generator), startY = chooseY(generator);

	Room *start = &maze[startX][startY]; //start is a pointer to the chosen starting room

	cout << (int)startX << " " << (int)startY << endl << static_cast<int>((*start).getMaxConnectedRooms()) << endl;

	std::uniform_int_distribution<int> chooseNeighbor (0,(*start).getMaxConnectedRooms());
	switch(chooseNeighbor(generator))
	{
		case 0:
			(*start).connectRooms(maze[startX][startY-1]);
			maze[startX][startY-1].connectRooms((*start));
		// case 1:
		// 	(*start).connectRooms(maze[startX+1][startY]);
		// 	maze[startX+1][startY].connectRooms((*start));
		// case 2:
		// 	(*start).connectRooms(maze[startX][startY+1]);
		// 	maze[startX][startY+1].connectRooms((*start));
		// case 3:
		// 	(*start).connectRooms(maze[startX-1][startY]);
		// 	maze[startX-1][startY].connectRooms((*start));
		default:
			cout << "Error: invalid neighbor code" << endl;
	}

	return 0;
}