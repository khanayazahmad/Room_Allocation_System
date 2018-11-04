package com.roomalloc.dynamicalloc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
	
	public static void main(String args[]) throws NumberFormatException, IOException, SQLException{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		
		String result;
		int ch,mid;
		DynamicAllocation dg;
		FeedAlloc fa;
		SpaceConfig sg=new SpaceConfig();
		do{
			
			System.out.println("\n\nSelect an option from below: ");
			System.out.println("1.Initialize Application Configuration");
			System.out.println("2.Create New Space Configuration Manually");
			System.out.println("3.Load Default Space Configuration");
			System.out.println("4.Load CSV data into DB");
			System.out.println("5.Pre Load Space Configuration");
			System.out.println("6.Create a New Random Space Configuration");
			System.out.println("7.Proceed\n");
			
			System.out.println("Enter the Choice(1/2/3/4/5/6/7)");
			ch=Integer.parseInt(br.readLine());
			
			switch(ch){
			
				case 1: if(DBUtility.createTables()==true){
							System.out.println("Initialized successfully");
							sg.ConfigureSpaceHardcoded();
						}
					else
						System.out.println("Could not be Initialized! Check Database");
						break;
						
				case 2: sg.ConfigureSpaceManual();
						break;
						
				case 3: sg.ConfigureSpaceHardcoded();
						break;
						
				case 4: System.out.println("\n\nFILE UPLOAD DESCRIPTION:"
						+ "The excel file should contain 7 columns.\n\n"
						+ "MID        | int(11)\n"
						+ "gender     | varchar(1)\n"
						+ "region     | varchar(10)\n"
						+ "college    | varchar(100)\n"
						+ "branch     | varchar(100)\n"
						+ "state      | varchar(20)\n"
						+ "percentage | float\n\n"
						+ "There should not be any header row.\n"
						+ "Remove all commas from the file.\n"
						+ "Save the file as a CSV File.\n"
						+ "Enter the filename.csv along with complete path and replace all '\' with '\\'.\n\n");
						
						if(DBUtility.excelToCampusDataTable(br.readLine()))
							System.out.println("\nSUCCESS: File Loaded To DB");
						else
							System.out.println("\nFAILURE: File could not be loaded to DB");
						
						break;
				case 5: sg.ConfigureSpacePreload();
						break;
				
				case 6: sg.ConfigureSpaceRandomize();
						break;
						
				case 7: System.out.println("PROCEEDING......");
						break;
				default: System.out.println("INVALID CHOICE! Try Again\n");

				
			}
			
			try{
				//System.out.println("Configuration of Blocks:\n");
				sg.printConfig();
				
			}catch(Exception e){
				System.out.println("\nERROR: Re-Configure again\n");
				continue;
			}
			
		}while(ch!=7);
		
		
		dg=new DynamicAllocation(sg);
		fa=new FeedAlloc(dg.sc);
		fa.feed();
		dg.sc=fa.getSocialCenter();

		
		do{
			System.out.println();
			System.out.println("\n\nSelect an option from below: ");
			System.out.println("1. Allocate Bed to MID ");
			System.out.println("2. Get Details for every G8 units ");
			System.out.println("3. Get bed deatils of MID");
			System.out.println("4. Check Out");
			System.out.println("5. Set bed as Unavailable");
			System.out.println("6. Set bed as Available");
			System.out.println("7. Exit ");
			System.out.println("Enter choice (1/2/3/4/5/6/7):  ");
			ch=Integer.parseInt(br.readLine());
			switch(ch){
			case 1:
				fa.feed();
				dg.sc=fa.getSocialCenter();
				System.out.print("\n\nMID: ");
				
				mid=Integer.parseInt(br.readLine());

				/*Collections.shuffle(a);
				
				List<Integer> a=new ArrayList<Integer>();
				
				a=DBUtility.getMIDList();
				
				System.out.println("Sequence of MIDs of Arriving Minds:");
				for(int i=0;i<a.size();i++)
					System.out.println(a.get(i));
				System.out.println("\n");
				
				for(int i=0;i<a.size();i++){
				mid=a.get(i);*/
				
				
				if(!dg.validateMID(mid)){
					System.out.print("\nInvalid MID entered or MID already allocated room! Try Again\n");
					continue;
				}
				
					result=dg.allocate(mid);
					if(!result.equals("false"))
					{
						System.out.println("\nMID: "+mid+"\nRoom: "+result);
						//db.displayAllocatedRoom(mid);
					}
					else{
						System.out.print("\nAll beds have been occupied!\nPlease find accomodation elsewhere! At MID: "+mid+"\n");
						System.out.println("\n");
						continue;
					}
				
				//}
				
				break;
				
			case 2:
				fa.feed();
				dg.sc=fa.getSocialCenter();
				System.out.print("\n************Allocation Details*****************\n");
				dg.getAllG8();
				System.out.print("\n*************************************************\n");
				break;
			case 3:
				fa.feed();
				dg.sc=fa.getSocialCenter();
				System.out.print("\n\nMID: ");
				
				mid=Integer.parseInt(br.readLine());
				
				dg.displayAllocatedRoom(mid);
				
				break;
			case 4:
					fa.feed();
					dg.sc=fa.getSocialCenter();
				
				System.out.print("\n\nMID: ");
				
				mid=Integer.parseInt(br.readLine());
				
				/*
				Collections.shuffle(a);
				
				System.out.println("Enter number of check outs: ");
				int n=Integer.parseInt(br.readLine());
				c=a.subList(0, n);
				
				System.out.println("MIDs Checking out in sequence:");
				for(int i=0;i<n;i++){*/
					
					if((!dg.checkSocialCenterIsEmpty()&&dg.checkOut(mid)))
					
						System.out.println(mid+" has checked out!\n");
					
					else if(dg.checkSocialCenterIsEmpty())
						
						System.out.println("\nAll rooms have been vacated!\n");
					else
						System.out.println(mid+" is not present!\n");
						
					
				/*}
				a=c.subList(0, c.size());*/
				
				break;
				
			case 5:
				fa.feed();
				dg.sc=fa.getSocialCenter();
				
				System.out.print("\n\nBed Id: ");
			
				String bedId=br.readLine();
				
				if(dg.setUnavailable(bedId))
					System.out.println("Bed "+bedId+" set to unavailable");
				else
					System.out.println("Bed "+bedId+" cannot be set to unavailable");
				break;
			case 6:
				fa.feed();
				dg.sc=fa.getSocialCenter();//
				
				System.out.print("\n\nBed Id: ");
			
				String bedId1=br.readLine();
			
				if(dg.setAvailable(bedId1))
					System.out.println("Bed "+bedId1+" set to available");
				else
					System.out.println("Bed "+bedId1+" cannot be set to available");
				break;
				
				
			case 7:
				
				System.out.println("\nExiting...........\n");
				break;
			default:
				System.out.println("\nERROR: Invalid choice entered! Please try again!\n");
			}
		
		}while(ch!=7);
		System.out.println("\nExit Successful\n");
		
	}
	

}
