package com.roomalloc.staticalloc;
import java.io.IOException;
import java.sql.SQLException;


/**
 * 
 * @author M1037549
 *
 */
public class HalfUnit {
	CampusPair cp;
	int halfUnit[][];
	int alloc[];
	int num;
	int leftPair;
	int noH;
	HalfUnit(byte g,byte h) throws SQLException, IOException{
		cp=new CampusPair(g,h);
		cp.getData();
		cp.genPairs();
		//cp.printData();
		System.out.print("\n\n");
		//cp.displayPairs();
		System.out.print("\n");
		num=cp.noP/2;
		noH=(num%2!=0)?num-1:num;
		alloc=new int[num];
		halfUnit=new int[noH/2][4];
		for(int i=0;i<num;i++){
			alloc[i]=0;
		}
		leftPair=-1;
		
	}
	void genHalfUnits(){
		int i=0, j=0, match[]=new int[cp.num/2], count=0,k=0,p;
		while (count<noH/2)
		{
			match[i]=findMatch(i);
			
			CampusMind cm1[]={cp.cm[cp.pair[i][0]],cp.cm[cp.pair[i][1]]};
			CampusMind cm2[]={cp.cm[cp.pair[match[i]][0]],cp.cm[cp.pair[match[i]][1]]};
			
			p=CampusMind.getScore(cm1,cm2);
			if(p==30){
				halfUnit[k][0]=cp.pair[i][0];
				halfUnit[k][1]=cp.pair[i][1];
				halfUnit[k][2]=cp.pair[match[i]][0];
				halfUnit[k++][3]=cp.pair[match[i]][1];
				//alloc[cp.pair[i][0]]=alloc[cp.pair[i][1]]=alloc[cp.pair[match[i]][0]]=alloc[cp.pair[match[i]][1]]=1;
				alloc[i]=alloc[match[i]]=1;
				count+=2;
				while(alloc[i]!=0&&count<num/2)
					i = (i<num-1)? i+1 : 0;
				j=i;
			}
			else if(match[i]==j)
			{
				halfUnit[k][0]=cp.pair[i][0];
				halfUnit[k][1]=cp.pair[i][1];
				halfUnit[k][2]=cp.pair[match[i]][0];
				halfUnit[k++][3]=cp.pair[match[i]][1];
				//alloc[cp.pair[i][0]]=alloc[cp.pair[i][1]]=alloc[cp.pair[match[i]][0]]=alloc[cp.pair[match[i]][1]]=1;
				alloc[i]=alloc[match[i]]=1;
				count+=1;
				while(alloc[i]!=0&&count<num/2)
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
					leftPair=i;
					break;
				}
						
			}

	}
	private int findMatch(int i) {
		if(alloc[i]==1)
		return 0;
	int match=i;
	for(int k=0; k<num; k++)
	{
		if(alloc[k]==1||k==i)
			continue;
		CampusMind cmi[]={cp.cm[cp.pair[i][0]],cp.cm[cp.pair[i][1]]};
		CampusMind cmm[]={cp.cm[cp.pair[match][0]],cp.cm[cp.pair[match][1]]};
		CampusMind cmk[]={cp.cm[cp.pair[k][0]],cp.cm[cp.pair[k][1]]};
		if(CampusMind.getScore(cmi,cmm) < CampusMind.getScore(cmi,cmk))
			match=k;
		if(CampusMind.getScore(cmi,cmm)==30)
			return match;
	}
	return match;
}
	void displayHalfUnits(){
		System.out.println("\nHalfUnits of campus minds: \n");
		for(int i=0;i<noH/2;i++){
			CampusMind cm1[]={cp.cm[halfUnit[i][0]],cp.cm[halfUnit[i][1]]};
			CampusMind cm2[]={cp.cm[halfUnit[i][2]],cp.cm[halfUnit[i][3]]};
			System.out.println("HalfUnit "+(i+1)+":	{("+(cp.cm[halfUnit[i][0]].mid)+" , "+(cp.cm[halfUnit[i][1]].mid)+
					" ),("+(cp.cm[halfUnit[i][2]].mid)+" , "+(cp.cm[halfUnit[i][3]].mid)+
					" )} score = "+CampusMind.getScore(cm1,cm2)+"\n");
		}
		if(num%2!=0)
			System.out.println("\nThe left out pair:	("+cp.cm[cp.pair[leftPair][0]].mid+","+cp.cm[cp.pair[leftPair][1]].mid+")");
		else
			System.out.println("\nNo left out pair");
	}
	/*public static void main(String args[]) throws SQLException, IOException{
		HalfUnit h=new HalfUnit();
		h.genHalfUnits();
		h.displayHalfUnits();
		
	}*/

}
