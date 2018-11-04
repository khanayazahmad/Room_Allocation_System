package com.roomalloc.dynamicalloc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DynamicAllocation {

	SocialCenter sc;
	CampusMind cm1;
	CampusMind cm2;
	int uLR;
	int uLG;
	int rooms;
	int totalRooms;
	ConnectionUtil cu;
	char gen;
	BufferedReader br;
	int na;
	int minScoreEver;
	String bedAllocated;
	
	
	DynamicAllocation(SpaceConfig config)
	{
		sc=config.getSocialCenter();
		
		totalRooms=rooms=config.getTotalRooms();
		na=totalRooms%8;	
		cu=new ConnectionUtil();
		cm1=new CampusMind();
		cm2=new CampusMind();
		uLR=15;
		uLG=10;
		br=new BufferedReader(new InputStreamReader(System.in));
		minScoreEver=0;
		
	}
	
	void readMindCm1(int id) throws SQLException, IOException{
		
		cu.exec("select * from campus_data where mid='"+id+"'");
		while(cu.rst.next()){
			cm1.setDetails(cu.rst.getInt(1), cu.rst.getString(3),
					cu.rst.getString(4), cu.rst.getString(5), cu.rst.getString(6),
					cu.rst.getInt(7));
			gen=cu.rst.getString(2).charAt(0);
		
		}

		
	}
	void readMindCm2(int id) throws SQLException, IOException{
		
		cu.exec("select * from campus_data where mid='"+id+"'");
		while(cu.rst.next()){
			cm2.setDetails(cu.rst.getInt(1), cu.rst.getString(3),
					cu.rst.getString(4), cu.rst.getString(5), cu.rst.getString(6),
					cu.rst.getInt(7));
		
		}
		
		
	}
	
	public void getSocialCenter(SocialCenter sc){
		this.sc = sc;
 	}
	
	String tryAllocation(int id, String genderConstraint) throws SQLException, IOException{
		
		boolean goAhead=true;
		int i,j,k,l,m;
		String bed=null;
		byte flag;
		readMindCm1(id);
		//int count=0;
		
		for(i=0;i<sc.blocks.length;i++){
			
			if(!(sc.blocks[i].blockConstraint.equals(genderConstraint)||sc.blocks[i].blockConstraint.equals("none")))
				continue;
			
			for(j=0;j<sc.blocks[i].floors.length;j++){
				
				for(k=0;k<sc.blocks[i].floors[j].gUnits.length;k++){
					if(sc.blocks[i].floors[j].checkGUnit[k]==1)
						continue;
				
			if(sc.blocks[i].floors[j].gUnits[k].rooms[0].beds[0]==-1){
				sc.blocks[i].floors[j].gUnits[k].rooms[0].beds[0]=id;
				return sc.blocks[i].floors[j].gUnits[k].rooms[0].bedId[0];
			}
			if(uLG==0||rooms==1){
				for(l=0;l<sc.blocks[i].floors[j].gUnits[k].rooms.length;l++){
					for(m=0;m<sc.blocks[i].floors[j].gUnits[k].rooms[l].beds.length;m++){
						if(sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[m]==-1){
							sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[m]=id;
							return sc.blocks[i].floors[j].gUnits[k].rooms[l].bedId[m];
						}
					}
				}
			}
			goAhead=checkCompatibilityWithG8(i,j,k);
			if(goAhead){
				bed=bestPossibleBedInG8(i,j,k);
				if(bed!=null){
					l=Integer.parseInt(bed.split(",")[0]);
					m=Integer.parseInt(bed.split(",")[1]);
					sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[m]=id;
					bedAllocated=sc.blocks[i].floors[j].gUnits[k].rooms[l].bedId[m];
					flag=1;
					for(l=0;l<sc.blocks[i].floors[j].gUnits[k].rooms.length;l++)
						for(m=0;m<sc.blocks[i].floors[j].gUnits[k].rooms[l].beds.length;m++){
							if(sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[m]==-1)
								flag=0;
						}
					sc.blocks[i].floors[j].checkGUnit[k]=flag;
					return bedAllocated;
				}				
			}
			
			}
					
		}
	}
			
		
		return "false";
	}
	
	boolean checkCompatibilityWithG8(int i, int j, int k) throws SQLException, IOException{
		int score;
		for(int l=0;l<sc.blocks[i].floors[j].gUnits[k].rooms.length;l++)
			for(int m=0;m<sc.blocks[i].floors[j].gUnits[k].rooms[l].beds.length;m++)
				if(sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[m]>-1){
					readMindCm2(sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[m]);
					score=cm1.getScore(cm2);
					if(score<uLG){
						return false;
					}
				}
		
		return true;
		
	}
	
	String bestPossibleBedInG8(int i, int j, int k) throws SQLException, IOException{
		String bed=null;
		int score,maxScore=uLR;
		for(int l=0;l<sc.blocks[i].floors[j].gUnits[k].rooms.length;l++)
			for(int m=0;m<sc.blocks[i].floors[j].gUnits[k].rooms[l].beds.length;m++){
			if(sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[m]==-1){
				for(int n=0;n<sc.blocks[i].floors[j].gUnits[k].rooms[l].beds.length;n++){
				
					if(sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[n]>-1){
						readMindCm2(sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[n]);
						score = cm1.getScore(cm2);
						if(score>maxScore){
							bed=""+l+","+m;
							maxScore=score;
						}
					}
				}
			}
		}
		if(bed==null){
			for(int l=0;l<sc.blocks[i].floors[j].gUnits[k].rooms.length;l++)
				for(int m=0;m<sc.blocks[i].floors[j].gUnits[k].rooms[l].beds.length;m++)
				if(sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[m]==-1)
					bed=""+l+","+m;
		}
			
		return bed;
	}
	
	void displayAllocatedRoom(int id) throws SQLException, IOException{
		
		
		for(int i=0;i<sc.blocks.length;i++)
			for(int j=0;j<sc.blocks[i].floors.length;j++)
				for(int k=0;k<sc.blocks[i].floors[j].gUnits.length;k++)
					for(int l=0;l<sc.blocks[i].floors[j].gUnits[k].rooms.length;l++)
						for(int m=0;m<sc.blocks[i].floors[j].gUnits[k].rooms[l].beds.length;m++)
					if(sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[m]==id){
						readMindCm2(id);
						System.out.println("\n");
						cm2.printDetails();
						
						System.out.print("\nBED:	");
						System.out.println(sc.blocks[i].floors[j].gUnits[k].rooms[l].bedId[m]+"\n\n\n");
						return;
						
					}
		
		System.out.println("\nMID: "+id+" is not present\n");
					
	}
	
	
	String forceAllocation(int id,String genderConstraint) throws SQLException, IOException{
		int rl=uLR;
		int gl=uLG;
		
		String triedAllocation;
		boolean notfull=false;
		do{
			triedAllocation=tryAllocation(id,genderConstraint);
			for(int i=0;i<sc.blocks.length;i++){
				
				if(!(sc.blocks[i].blockConstraint.equals(genderConstraint)))
					continue;
				
				if(!(sc.blocks[i].checkIfBlockFull()))
					notfull=true;
			}
			
			uLR--;
			uLG--;
			
			//System.out.println(id+": tryingAllocation	:"+triedAllocation+": not full	:"+notfull);
			
		}while(triedAllocation.equals("false")&&notfull==true);
		uLR=rl;
		uLG=gl;
		
		rooms--;
		//System.out.println(id+": Tried Allocation:	"+triedAllocation);
		
		return triedAllocation;
	}
	
	void getAllG8() throws SQLException, IOException{
		String s="",s1="";
		
		System.out.println("\nMAX SCORE=15\n");
		
			
			for(int i=0;i<sc.blocks.length;i++)
				
				for(int j=0;j<sc.blocks[i].floors.length;j++)
					for(int k=0;k<sc.blocks[i].floors[j].gUnits.length;k++){
						System.out.println("\nGENDER\t\tBLOCK\t\tFLOOR\t\tGUNIT\t\tROOM\t\tBED\t\tMID\t\tG8 UNIT SCORE												REGION\t\tSTATE\t\tCOLLEGE | BRANCH\t\t\t\t\tPERFORMANCE");
						for(int l=0;l<sc.blocks[i].floors[j].gUnits[k].rooms.length;l++){
							for(int m=0;m<sc.blocks[i].floors[j].gUnits[k].rooms[l].beds.length;m++){
					
					if(sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[m]==-1) {
						s="Vacant";
						System.out.println(gen+"\t\t"+(i+1)+"\t\t"+(j+1)+"\t\t"+(k+1)+"\t\t"+(l+1)+"\t\t"+(m+1)+"\t\t"+s);
						
					}
					else if(sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[m]==-2) {
						s="Unavailable";
						System.out.println(gen+"\t\t"+(i+1)+"\t\t"+(j+1)+"\t\t"+(k+1)+"\t\t"+(l+1)+"\t\t"+(m+1)+"\t\t"+s);
						
					}
					else{
						s=""+sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[m];
						
						s1=getG8Scores(i,j,k,l,m);
						
						readMindCm1(sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[m]);
						System.out.println(gen+"\t\t"+(i+1)+"\t\t"+(j+1)+"\t\t"+(k+1)+"\t\t"+(l+1)+"\t\t"+(m+1)+"\t\t"+s+"\t\t"+s1+"										"+cm1.reg+"\t\t"+cm1.mt+"\t\t"+cm1.col+" | "+cm1.bch+"\t\t\t\t"+cm1.pf);
						
						
					}
					
					
				}
							
				System.out.println("\n");
			}
			System.out.println("\n\n\n");
		}
		
	}

	private String getG8Scores(int i, int j,int k, int l, int m) throws SQLException, IOException {
		String s="";
		readMindCm1(sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[m]);
		for(int b=0;b<sc.blocks[i].floors[j].gUnits[k].rooms.length;b++)
			for(int c=0;c<sc.blocks[i].floors[j].gUnits[k].rooms[b].beds.length;c++)
			{
				if(!((c==m&&b==l)||sc.blocks[i].floors[j].gUnits[k].rooms[b].beds[c]==-1)){
					readMindCm2(sc.blocks[i].floors[j].gUnits[k].rooms[b].beds[c]);
					s=s+cm1.getScore(cm2)+", ";
				}
				
			}
		
		
		return s;
	}
	
	public boolean checkSocialCenterIsEmpty(){
		for(int i=0;i<sc.blocks.length;i++)
			for(int j=0;j<sc.blocks[i].floors.length;j++)
				for(int k=0;k<sc.blocks[i].floors[j].gUnits.length;k++)
					for(int l=0;l<sc.blocks[i].floors[j].gUnits[k].rooms.length;l++)
						for(int m=0;m<sc.blocks[i].floors[j].gUnits[k].rooms[l].beds.length;m++)
							if(sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[m]>-1)
								return false;
					return true;
	}
	
	public boolean checkOut(int id) throws SQLException{
		for(int i=0;i<sc.blocks.length;i++)
			for(int j=0;j<sc.blocks[i].floors.length;j++)
				for(int k=0;k<sc.blocks[i].floors[j].gUnits.length;k++)
					for(int l=0;l<sc.blocks[i].floors[j].gUnits[k].rooms.length;l++)
						for(int m=0;m<sc.blocks[i].floors[j].gUnits[k].rooms[l].beds.length;m++)
							if(sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[m]==id){
								sc.blocks[i].floors[j].gUnits[k].rooms[l].beds[m]=-1;
								sc.blocks[i].floors[j].checkGUnit[k]=0;
								
								cu.stmt.executeUpdate("update room_allocation set id="+-1+" where bed='"+("B"+(i+1)+"F"+(j+1)+"G"+(k+1)+"R"+(l+1)+"B"+(m+1))+"'");
								rooms++;
								return true;
							}
		return false;
	}
	
	boolean validateMID(int id) throws SQLException {
		boolean valid=false;
		cu.exec("select mid from campus_data");
		while(cu.rst.next()){
			if(id==cu.rst.getInt(1))
				valid=true;
		}
		
		cu.exec("select id from room_allocation");
		while(cu.rst.next()){
			if(id==cu.rst.getInt(1))
				valid=false;
		}
		return valid;
	}
	
	boolean setUnavailable(String bedId){
		
		int mid=0;
		/*int i=Integer.parseInt(bedId.substring(bedId.indexOf('B')+1, bedId.lastIndexOf('F')));
		int j=Integer.parseInt(bedId.substring(bedId.indexOf('F')+1, bedId.lastIndexOf('G')));
		int k=Integer.parseInt(bedId.substring(bedId.indexOf('G')+1, bedId.lastIndexOf('R')));
		int l=Integer.parseInt(bedId.substring(bedId.indexOf('R')+1, bedId.lastIndexOf('B')));
		int m=Integer.parseInt(bedId.substring(bedId.indexOf('B')+1));*/
		try {
			cu.exec("select id from room_allocation where bed='"+bedId+"'");
			
			while(cu.rst.next())
				mid=cu.rst.getInt(1);
			
			if(mid==-1){
				cu.stmt.executeUpdate("update room_allocation set id="+-2+" where bed='"+bedId+"'");
				return true;
			}
			
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
	
	boolean setAvailable(String bedId){
		
		int mid = 0;
		/*int i=Integer.parseInt(bedId.substring(bedId.indexOf('B')+1, bedId.lastIndexOf('F')));
		int j=Integer.parseInt(bedId.substring(bedId.indexOf('F')+1, bedId.lastIndexOf('G')));
		int k=Integer.parseInt(bedId.substring(bedId.indexOf('G')+1, bedId.lastIndexOf('R')));
		int l=Integer.parseInt(bedId.substring(bedId.indexOf('R')+1, bedId.lastIndexOf('B')));
		int m=Integer.parseInt(bedId.substring(bedId.indexOf('B')+1));*/
		try {
			cu.exec("select id from room_allocation where bed='"+bedId+"'");
			
			while(cu.rst.next())
				mid=cu.rst.getInt(1);
			
			if(mid==-2){
				cu.stmt.executeUpdate("update room_allocation set id="+-1+" where bed='"+bedId+"'");
				return true;
			}
			return false;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}

	}
	
	String allocate(int mid) throws SQLException, IOException{
		
		String genderConstraint="";
		cu.exec("select * from campus_data where mid='"+mid+"'");
		while(cu.rst.next())
			genderConstraint=cu.rst.getString(2);
		
		String result=forceAllocation(mid,genderConstraint);
		
		if(!result.equals("false"))
			cu.stmt.executeUpdate("update room_allocation set id="+mid+" where bed='"+result+"'");
		
		return result;
	}
	
	
}
