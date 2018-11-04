package com.roomalloc.staticalloc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;


public class G8Unit {
	HalfUnit hu;
	int alloc[];
	int num;
	int gUnit[][];
	int noG;
	int leftHalfUnit;
	int room[][];
	int onHold[];
	int noR;
	byte gen;
	G8Unit(byte g,byte h,int n) throws SQLException, IOException{
		gen=g;
		hu=new HalfUnit(g,h);
		hu.genHalfUnits();
		//hu.displayHalfUnits();
		noR=n;
		room=new int[noR][2];
		if(hu.cp.num-noR>0)
			onHold=new int[hu.cp.num-noR];
		else
			onHold=new int[0];
		num=hu.noH/2;
		noG=(num%2!=0)?num-1:num;
		alloc=new int[num];
		gUnit=new int[(noG/2)+1][8];
		for(int i=0;i<num;i++){
			alloc[i]=0;
		}
		leftHalfUnit=-1;
	}
	void genG8Units(){
		int i=0, j=0, match[]=new int[num], count=0,k=0,p;
		while (count<noG/2)
		{
			match[i]=findMatch(i);
			
			CampusMind cm1[]={hu.cp.cm[hu.halfUnit[i][0]],hu.cp.cm[hu.halfUnit[i][1]],
								hu.cp.cm[hu.halfUnit[i][2]],hu.cp.cm[hu.halfUnit[i][3]]};
			CampusMind cm2[]={hu.cp.cm[hu.halfUnit[match[i]][0]],hu.cp.cm[hu.halfUnit[match[i]][1]],
								hu.cp.cm[hu.halfUnit[match[i]][2]],hu.cp.cm[hu.halfUnit[match[i]][3]]};
			
			p=CampusMind.getScore(cm1,cm2);
			if(p==30){
				gUnit[k][0]=hu.halfUnit[i][0];
				gUnit[k][1]=hu.halfUnit[i][1];
				gUnit[k][2]=hu.halfUnit[i][2];
				gUnit[k][3]=hu.halfUnit[i][3];
				gUnit[k][4]=hu.halfUnit[match[i]][0];
				gUnit[k][5]=hu.halfUnit[match[i]][1];
				gUnit[k][6]=hu.halfUnit[match[i]][2];
				gUnit[k++][7]=hu.halfUnit[match[i]][3];
				//alloc[hu.halfUnit[i][0]]=alloc[hu.halfUnit[i][1]]=alloc[hu.halfUnit[match[i]][0]]=alloc[hu.halfUnit[match[i]][1]]=1;
				alloc[i]=alloc[match[i]]=1;
				count+=2;
				while(alloc[i]!=0&&count<noG/2)
					i = (i<num-1)? i+1 : 0;
				j=i;
			}
			else if(match[i]==j)
			{
				gUnit[k][0]=hu.halfUnit[i][0];
				gUnit[k][1]=hu.halfUnit[i][1];
				gUnit[k][2]=hu.halfUnit[i][2];
				gUnit[k][3]=hu.halfUnit[i][3];
				gUnit[k][4]=hu.halfUnit[match[i]][0];
				gUnit[k][5]=hu.halfUnit[match[i]][1];
				gUnit[k][6]=hu.halfUnit[match[i]][2];
				gUnit[k++][7]=hu.halfUnit[match[i]][3];
				//alloc[hu.pair[i][0]]=alloc[hu.pair[i][1]]=alloc[hu.pair[match[i]][0]]=alloc[hu.pair[match[i]][1]]=1;
				alloc[i]=alloc[match[i]]=1;
				count+=1;
				while(alloc[i]!=0&&count<noG/2)
					i = (i<num-1)? i+1 : 0;
				j=i;
			}
			
			else
			{
				j=i;
				i=match[i];
			}
		}
		if(num%2!=0)
			for(i=0;i<num;i++){
				if(alloc[i]==0){
					leftHalfUnit=i;
					break;
				}
						
			}
		k=noG/2;
		gUnit[k][0]=(leftHalfUnit!=-1)?hu.halfUnit[leftHalfUnit][0]:-1;
		gUnit[k][1]=(leftHalfUnit!=-1)?hu.halfUnit[leftHalfUnit][1]:-1;
		gUnit[k][2]=(leftHalfUnit!=-1)?hu.halfUnit[leftHalfUnit][2]:-1;
		gUnit[k][3]=(leftHalfUnit!=-1)?hu.halfUnit[leftHalfUnit][3]:-1;
		gUnit[k][4]=(hu.leftPair!=-1)?hu.cp.pair[hu.leftPair][0]:-1;
		gUnit[k][5]=(hu.leftPair!=-1)?hu.cp.pair[hu.leftPair][1]:-1;
		gUnit[k][6]=hu.cp.unpaired;
		gUnit[k][7]=-1;
		

	}
	private int findMatch(int i) {
		if(alloc[i]==1)
		return 0;
	int match=i;
	for(int k=0; k<num; k++)
	{
		if(alloc[k]==1||k==i)
			continue;
		CampusMind cmi[]={hu.cp.cm[hu.halfUnit[i][0]],hu.cp.cm[hu.halfUnit[i][1]],
							hu.cp.cm[hu.halfUnit[i][2]],hu.cp.cm[hu.halfUnit[i][3]]};
		CampusMind cmm[]={hu.cp.cm[hu.halfUnit[match][0]],hu.cp.cm[hu.halfUnit[match][1]],
							hu.cp.cm[hu.halfUnit[match][2]],hu.cp.cm[hu.halfUnit[match][3]]};
		CampusMind cmk[]={hu.cp.cm[hu.halfUnit[k][0]],hu.cp.cm[hu.halfUnit[k][1]],
							hu.cp.cm[hu.halfUnit[k][2]],hu.cp.cm[hu.halfUnit[k][3]]};
		if(CampusMind.getScore(cmi,cmm) < CampusMind.getScore(cmi,cmk))
			match=k;
		if(CampusMind.getScore(cmi,cmm)==30)
			return match;
	}
	return match;
}
	void displayG8Units(){
		System.out.println("\nG8Units of campus minds: \n");
		for(int i=0;i<noG/2;i++){
			
			/*CampusMind cm1[]={hu.cp.cm[gUnit[i][0]],hu.cp.cm[gUnit[i][1]],
					hu.cp.cm[gUnit[i][2]],hu.cp.cm[gUnit[i][3]]};
			
			CampusMind cm2[]={hu.cp.cm[gUnit[i][4]],hu.cp.cm[gUnit[i][5]],
					hu.cp.cm[gUnit[i][6]],hu.cp.cm[gUnit[i][7]]};
			*/
			System.out.println("gUnit "+(i+1)+":	[{("+(hu.cp.cm[gUnit[i][0]].mid)+" , "+(hu.cp.cm[gUnit[i][1]].mid)+
					" ),("+(hu.cp.cm[gUnit[i][2]].mid)+" , "+(hu.cp.cm[gUnit[i][3]].mid)+" )},{("+(hu.cp.cm[gUnit[i][4]].mid)+" , "+(hu.cp.cm[gUnit[i][5]].mid)+
					" ),("+(hu.cp.cm[gUnit[i][6]].mid)+" , "+(hu.cp.cm[gUnit[i][7]].mid)+
					" )}]\n");
			System.out.println("\n\nMID\t\t	REGION\t\t	STATE\t\t				COLLEGE | BRANCH\t\t						PERFORMANCE\n\n");
			for(int j=0;j<8;j++){
				System.out.println(hu.cp.cm[gUnit[i][j]].mid+"\t\t\t"+hu.cp.cm[gUnit[i][j]].reg+"\t\t\t"+hu.cp.cm[gUnit[i][j]].mt+"\t\t\t\t"+hu.cp.cm[gUnit[i][j]].col+
						" | "+hu.cp.cm[gUnit[i][j]].bch+"\t\t\t\t\t\t\t\t			"+hu.cp.cm[gUnit[i][j]].pf);
				if((j+1)%2==0)
					System.out.println("\n");
					
			}
		}
		if(hu.cp.unpaired==-1&&hu.leftPair==-1&&leftHalfUnit==-1){
			System.out.println("******All units are complete******");
		}
		else{
			int p;
			String s="Incomplete Unit: {(";
			for(p=0;p<7;p++){
				if(gUnit[noG/2][p]!=-1){
					s=s+(hu.cp.cm[gUnit[noG/2][p]].mid);
				}
				s=((p+1)%2==0)?s+"),(":s+",";
			}
			if(gUnit[noG/2][p]!=-1)
				s=s+hu.cp.cm[gUnit[noG/2][p]].mid;
			s=s+")}";
			System.out.println("\n"+s);
			System.out.println("\nMID\t\t	REGION\t\t	STATE\t\t				COLLEGE | BRANCH\t\t						PERFORMANCE\n");
			for(int j=0;j<8;j++)
				if(gUnit[noG/2][j]!=-1){
					System.out.println(hu.cp.cm[gUnit[noG/2][j]].mid+"\t\t  "+hu.cp.cm[gUnit[noG/2][j]].reg+"\t\t "+hu.cp.cm[gUnit[noG/2][j]].mt+"\t\t\t"
							+hu.cp.cm[gUnit[noG/2][j]].col+" | "+hu.cp.cm[gUnit[noG/2][j]].bch+"\t\t\t"+hu.cp.cm[gUnit[noG/2][j]].pf);
					if((j+1)%2==0)
						System.out.println("\n");
				}
		}
				
				
			
		
	}
	
	void allocRooms(){
		int i=0,j=0,c=0;
		while(i<=noG/2&&c<noR){
			for(j=0;j<8&&c<noR;j++){
				if(gUnit[i][j]!=-1){
					room[c][0]=gUnit[i][j];
					room[c++][1]=0;
				}
			}
			i++;
		}
		if(c==noR){
			if(j<8)i--;else j=0;
		for(int k=0;k<onHold.length&&i<=noG/2;k++)
		{
			if(gUnit[i][j]!=-1){
				onHold[k]=gUnit[i][j++];
				if(j==8){
					i++;
					j=0;
				}
			}
		}
	
	 }
			
	}
	int returnRoom(int id){
		int i,f=-1;
		String blk=(gen==0)?"Boys":"Girls";
		for(i=0;i<noR;i++){
			if(hu.cp.cm[room[i][0]].mid==id){
				System.out.println("\nMID\t\tBLOCK\t\tGUNIT\t\tROOM\t\tBED");
				System.out.println(id+"\t\t"+blk+"\t\t"+((i/8)+1)+"\t\t"+(((i%8)/2)+1)+"\t\t"+((i%2)+1)+"\n\n\n");
				f=i;
				break;
			}
		}
		if(i==noR)
			
			for(i=0;i<onHold.length;i++)
				if(hu.cp.cm[onHold[i]].mid==id){
					System.out.println("\nMID\t\tSTATUS");
					System.out.println(id+"\t\tON HOLD\n\n\n");
					f=i+noR;
				}
					
		return f;
	}
	
	boolean checkIn(int i){
		if(i!=-1&&i<noR&&room[i][1]!=1){
			room[i][1]=1;
			return true;
		}
		else if(i!=-1&&i<noR){
			System.out.println("\nNot Possible!Room already allocated to MID: "+hu.cp.cm[room[i][0]].mid);
			return false;
		}
		
		else if(i!=-1&&i-noR>0){
			System.out.println("\nCandidate's allocation to the room is on hold");
			return false;
		}
		else{
			
			return false;
		}
	}
	
	int countLeftRooms(){
		int left=0;
		for(int i=0;i<noR;i++){
			if(room[i][1]==0)
				left++;
		}
		return left;
	}
	
	
	public static void main(String args[]) throws SQLException, IOException{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the total number of rooms available in Boys' Block:	");
		int n=Integer.parseInt(br.readLine());
		System.out.println("Enter the total number of rooms available in Girls' Block:	");
		int m=Integer.parseInt(br.readLine());
		G8Unit h00=new G8Unit((byte)0,(byte)0,n);
		G8Unit h10=new G8Unit((byte)1,(byte)0,m);
		h00.genG8Units();
		h00.allocRooms();
		h10.genG8Units();
		h10.allocRooms();
		int ch;
		
		do{
			
			System.out.println("Select an option from below: ");
			System.out.println("1. Display Allocation Details ");
			System.out.println("2. Get Room Status ");
			System.out.println("3. Check In ");
			System.out.println("4. Get Number of unchecked Beds");
			System.out.println("5. Get Number Campus Minds On Hold");
			System.out.println("6. Exit ");
			System.out.println("Enter choice:  ");
			ch=Integer.parseInt(br.readLine());
		switch(ch){	
		case 1:
			System.out.println("\n*******************************ALLOCATION FOR BOYS*****************************************");
			h00.displayG8Units();
			System.out.println("\n*******************************************************************************************");
			System.out.println("\n*******************************ALLOCATION FOR GIRLS**************************************** ");
			h10.displayG8Units();
			System.out.println("\n*******************************************************************************************");
			/*System.out.println("\n***********************ALLOCATION FOR BOYS WITH HEALTH PROBLEMS*************************** ");
			G8Unit h01=new G8Unit((byte)0,(byte)1);
			h01.genG8Units();
			h01.displayG8Units();
			System.out.println("\n*******************************************************************************************");
			System.out.println("\n***********************ALLOCATION FOR GIRLS WITH HEALTH PROBLEMS************************** ");
			G8Unit h11=new G8Unit((byte)1,(byte)1);
			h11.genG8Units();
			h11.displayG8Units();
			System.out.println("\n*******************************************************************************************");
			 */
			System.out.println(h10.hu.cp.cm[199].mid);
			break;
			
		case 2:
			System.out.print("\nMID:	");
			int rm=Integer.parseInt(br.readLine());
			
			if(h00.returnRoom(rm)!=-1);
			else if(h10.returnRoom(rm)!=-1);
			else
				System.out.println("\nInvalid MID!! TRY AGAIN\n");
			break;
			
		case 3:
			System.out.print("\nMID:	");
			rm=Integer.parseInt(br.readLine());
			if(h00.checkIn(h00.returnRoom(rm)))
				System.out.println("\n"+rm+"	has checked in\n");
			else if(h10.checkIn(h10.returnRoom(rm)))
				System.out.println("\n"+rm+"	has checked in\n");
			else
				System.out.println("\nNo Check in performed\n");
			break;
		case 4:
			System.out.println("\nBoys' Block:	 "+h00.countLeftRooms());
			System.out.println("\nGirls' Block:	"+h10.countLeftRooms()+"\n");
			break;
		case 5:
			System.out.println("\nBoys:	 "+h00.onHold.length);
			System.out.println("\nGirls:	"+h10.onHold.length+"\n");
			break;
		case 6:
			System.out.println("\nExiting...........\n");
			break;
		default:System.out.println("\nInvalid Choice Entered! Try again (1/2/3/4)");
	}
		}while(ch!=6);
		System.out.println("\nExit Successful\n");
	}
			
			
}