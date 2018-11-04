package com.roomalloc.dynamicalloc;

public class Block {
	
	String blockConstraint;
	Floor floors[];
	int checkFloor[];
	public Block(int noOfFloors){
		this.floors=new Floor[noOfFloors];
		this.checkFloor=new int[noOfFloors];
		for(int i=0;i<noOfFloors;i++)
		this.checkFloor[i]=0;
		this.blockConstraint="no constraint";
	}
	
	boolean checkIfBlockFull(){
		for(int j=0;j<floors.length;j++)
			for(int k=0;k<floors[j].gUnits.length;k++)
				for(int l=0;l<floors[j].gUnits[k].rooms.length;l++)
					for(int m=0;m<floors[j].gUnits[k].rooms[l].beds.length;m++)
						if(floors[j].gUnits[k].rooms[l].beds[m]==-1)
							return false;
		
		return true;
	}
	
	
	
	
}
