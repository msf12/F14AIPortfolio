#ifndef ROOM_H
#define ROOM_H

class Room
{
	std::uint8_t dimensionX, dimensionY, maxConnectedRooms, numConnectedRooms;
	std::vector<Room> connectedRooms; //rooms connected to this room
	public:
	Room();
	~Room();

	void connectRooms(Room r);
	
	std::uint8_t getNumConnectedRooms();
};

#endif