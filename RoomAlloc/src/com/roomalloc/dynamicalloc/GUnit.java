package com.roomalloc.dynamicalloc;

public class GUnit {

	Room rooms[];
	int checkRoom[];
	public GUnit(int noOfRoom){
		this.rooms=new Room[noOfRoom];
		this.checkRoom=new int[noOfRoom];
		for(int i=0;i<noOfRoom;i++)
			this.checkRoom[i]=0;
	}
}
