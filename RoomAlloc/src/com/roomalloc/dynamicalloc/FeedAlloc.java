package com.roomalloc.dynamicalloc;

public class FeedAlloc {
	
	SocialCenter sc;
	ConnectionUtil cu;
	
	FeedAlloc(SocialCenter sc){
		this.sc=sc;
		cu=new ConnectionUtil();
		
	}

	/*public int getBlockNo(char ch){
		
		switch(ch){
			case 'B':return 0;
			case 'C': return 1;
			case 'D': return 2;
			case 'E': return 3;
			default: return -1;
		}
	}
	
	public int getRoomNo(char ch){
		
		switch(ch){
			case 'A': return 0;
			case 'B': return 1;
			case 'C': return 2;
			case 'D': return 3;
			default: return -1;
		}
	}
	
	public int getFloorNo(char ch){
		
		switch(ch){
			case 'L': return 0;
			case 'U': return 1;
			default: return Integer.parseInt(""+ch)+1;
		}
	}*/

	
	public boolean feed(){
		
		String bedId;
		int mid;
		try{
			cu.exec("select * from room_allocation");
			while(cu.rst.next()){
				
				bedId=cu.rst.getString(2);
				mid=cu.rst.getInt(1);
				int i=Integer.parseInt(bedId.substring(bedId.indexOf('B')+1, bedId.lastIndexOf('F')))-1;
				int j=Integer.parseInt(bedId.substring(bedId.indexOf('F')+1, bedId.lastIndexOf('G')))-1;
				int k=Integer.parseInt(bedId.substring(bedId.indexOf('G')+1, bedId.lastIndexOf('R')))-1;
				int l=Integer.parseInt(bedId.substring(bedId.indexOf('R')+1, bedId.lastIndexOf('B')))-1;
				int m=Integer.parseInt(bedId.substring(bedId.lastIndexOf('B')+1))-1;
				sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[m]=mid;
				
			}
			return true;
		}catch(Exception e){
			
			e.printStackTrace();
			return false;
		}
		
		
		/*int i=getBlockNo(room.charAt(0)),
			j=getFloorNo(room.charAt(1)),
			k=Integer.parseInt(room.charAt(4)+"")-1,
			l=getRoomNo(room.charAt(5)),
			m=Integer.parseInt(room.charAt(6)+"")-1;*/
		
		/*if(sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[m]==-1){
			sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[m]=mid;
			return true;
		}
		return false;*/
	}
	
	
	public SocialCenter getSocialCenter(){
		return sc;
	}
}