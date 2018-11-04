package com.roomalloc.dynamicalloc;

public class Room {

	int beds[];
	int checkBed[];
	String bedId[];

	public Room(int noOfBeds) {
		super();
		this.beds = new int[noOfBeds];
		this.checkBed=new int[noOfBeds];
		this.bedId=new String[noOfBeds];
		for(int i=0;i<noOfBeds;i++){
			this.beds[i]=-1;
			this.checkBed[i]=0;
		}
	}

}
