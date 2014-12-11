#include <iostream>
#include <vector>
#include "room.h"


int main(int argc, char const *argv[])
{
	using std::cout; using std::endl;
	std::vector<std::vector<Room> > maze (5,std::vector<Room> (5));
	for (std::vector<std::vector<Room> >::iterator i = maze.begin(); i != maze.end(); ++i)
	{
		for (std::vector<Room>::iterator j = (*i).begin(); j != (*i).end(); ++j)
		{
			(*j) = Room();
		}
	}

	int i = 0;
	for (auto &v : maze)
	{
		for (auto &r : v)
		{
			cout << (int)r.getNumConnectedRooms() << " " << i++ << endl;
		}
	}
	return 0;
}