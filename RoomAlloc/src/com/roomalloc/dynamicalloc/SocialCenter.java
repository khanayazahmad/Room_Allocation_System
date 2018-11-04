package com.roomalloc.dynamicalloc;

public class SocialCenter {
	
	Block blocks[];
	int checkBlock[];
	
	public SocialCenter(int noOfBlocks){
		this.blocks=new Block[noOfBlocks];
		this.checkBlock=new int[noOfBlocks];
		for(int i=0;i<noOfBlocks;i++)
			this.checkBlock[i]=0;
	}
}
