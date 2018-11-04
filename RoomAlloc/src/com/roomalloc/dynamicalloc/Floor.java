package com.roomalloc.dynamicalloc;

public class Floor {
		
	GUnit gUnits[];
	int checkGUnit[];
	
	public Floor(int noOfGUnits){
		this.gUnits=new GUnit[noOfGUnits];
		this.checkGUnit=new int[noOfGUnits];
		for(int i=0;i<noOfGUnits;i++)
			this.checkGUnit[i]=0;
		
	}
}
