package com.roomalloc.dynamicalloc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class SpaceConfig {
	
	SocialCenter sc;
	
	BufferedReader br;
	
	ConnectionUtil cu;
	
	int c;
	
	public SpaceConfig(){
		br=new BufferedReader(new InputStreamReader(System.in));
		
		cu=new ConnectionUtil();
		c=0;
	}
	
	public void ConfigureSpaceManual() throws NumberFormatException, IOException, SQLException{
		
		cu.stmt.executeUpdate("truncate room_allocation");
		cu.stmt.executeUpdate("truncate space_config");
		cu.stmt.executeUpdate("truncate block_constraints");
		
		System.out.println("Enter no. of blocks:");
		
		sc = new SocialCenter(Integer.parseInt(br.readLine()));
		
		cu.stmt.executeUpdate("insert into space_config values(\""+sc.blocks.length+"\")");
		
		for(int i=0;i<sc.blocks.length;i++){
			System.out.println("Set gender constraint(M/F/none) for block "+(i+1));
			sc.blocks[i].blockConstraint=br.readLine();
			cu.stmt.executeUpdate("insert into block_constraints values(\""+i+"\",\""+sc.blocks[i].blockConstraint+"\")");
			System.out.println("Enter no. of Floors in Block "+(i+1)+":");
			sc.blocks[i]=new Block(Integer.parseInt(br.readLine()));
			cu.stmt.executeUpdate("insert into space_config values(\""+sc.blocks[i].floors.length+"\")");
			
			for(int j=0;j<sc.blocks[i].floors.length;j++){
				System.out.println("Enter no. of GUnts in Floor "+(j+1)+":");
				sc.blocks[i].floors[j]=new Floor(Integer.parseInt(br.readLine()));
				cu.stmt.executeUpdate("insert into space_config values(\""+sc.blocks[i].floors[j].gUnits.length+"\")");
				
				for(int k=0;k<sc.blocks[i].floors[j].gUnits.length;k++){
					System.out.println("Enter no. of Rooms in GUnit "+(k+1)+":");
					sc.blocks[i].floors[j].gUnits[k]=new GUnit(Integer.parseInt(br.readLine()));
					cu.stmt.executeUpdate("insert into space_config values(\""+sc.blocks[i].floors[j].gUnits[k].rooms.length+"\")");
					
					for(int l=0;l<sc.blocks[i].floors[j].gUnits[k].rooms.length;l++){
						//System.out.println("Enter no. of Beds in Room "+(l+1)+":");
						sc.blocks[i].floors[j].gUnits[k].rooms[l]=new Room(2);//Integer.parseInt(br.readLine()));
						cu.stmt.executeUpdate("insert into space_config values(\""+sc.blocks[i].floors[j].gUnits[k].rooms[l].beds.length+"\")");
						
						for(int m=0;m<sc.blocks[i].floors[j].gUnits[k].rooms[l].beds.length;m++){
							
							sc.blocks[i].floors[j].gUnits[k].rooms[l].bedId[m]="B"+(i+1)+"F"+(j+1)+"G"+(k+1)+"R"+(l+1)+"B"+(m+1);
							
							cu.stmt.executeUpdate("insert into room_allocation values(\""+
									sc.blocks[i].floors[j].gUnits[k].rooms[l].bedId[m]+"\",\"-1\")");
							
							
							
						}
					}
				}		
			}
		}
	}
	
	public void ConfigureSpaceRandomize() throws NumberFormatException, IOException, SQLException{
	
		
		cu.stmt.executeUpdate("truncate room_allocation");
		cu.stmt.executeUpdate("truncate space_config");
		cu.stmt.executeUpdate("truncate block_constraints");
		
		Random r=new Random();

		System.out.println("Enter Max no. of blocks:");
		
		int maxBlock=Integer.parseInt(br.readLine());
		System.out.println("Enter Max no. of Floors/block:");
		int maxFloor=Integer.parseInt(br.readLine());
		System.out.println("Enter Max no. of GUnits/floor:");
		int maxGUnit=Integer.parseInt(br.readLine());
		System.out.println("Enter Max no. of Rooms/gUnit:");
		int maxRoom=Integer.parseInt(br.readLine());
		System.out.println("Enter Max no. of Beds/room:");
		int maxBed=Integer.parseInt(br.readLine());
		
		System.out.print("RANDOMIZING THE CONFIGURATION.......\n\n");
		
		
		sc = new SocialCenter(2+r.nextInt(maxBlock-1));
		cu.stmt.executeUpdate("insert into space_config values(\""+sc.blocks.length+"\")");
		
		for(int i=0;i<sc.blocks.length;i++){
			
			System.out.println("Set gender constraint(male/female/none) for block "+(i+1));
			sc.blocks[i].blockConstraint=br.readLine();
			cu.stmt.executeUpdate("insert into block_constraints values(\""+i+"\",\""+sc.blocks[i].blockConstraint+"\")");
			
			sc.blocks[i]=new Block(3+r.nextInt(maxFloor-2));
			cu.stmt.executeUpdate("insert into space_config values(\""+sc.blocks[i].floors.length+"\")");
			
			
			for(int j=0;j<sc.blocks[i].floors.length;j++){
				
				sc.blocks[i].floors[j]=new Floor(2+r.nextInt(maxGUnit-1));
				cu.stmt.executeUpdate("insert into space_config values(\""+sc.blocks[i].floors[j].gUnits.length+"\")");

				
				for(int k=0;k<sc.blocks[i].floors[j].gUnits.length;k++){
					
					sc.blocks[i].floors[j].gUnits[k]=new GUnit(2+r.nextInt(maxRoom-1));
					cu.stmt.executeUpdate("insert into space_config values(\""+sc.blocks[i].floors[j].gUnits[k].rooms.length+"\")");

					
					for(int l=0;l<sc.blocks[i].floors[j].gUnits[k].rooms.length;l++){
						
						sc.blocks[i].floors[j].gUnits[k].rooms[l]=new Room(r.nextInt(maxBed)+1);
						cu.stmt.executeUpdate("insert into space_config values(\""+sc.blocks[i].floors[j].gUnits[k].rooms[l].beds.length+"\")");

						
						for(int m=0;m<sc.blocks[i].floors[j].gUnits[k].rooms[l].beds.length;m++){
							
							sc.blocks[i].floors[j].gUnits[k].rooms[l].bedId[m]="B"+(i+1)+"F"+(j+1)+"G"+(k+1)+"R"+(l+1)+"B"+(m+1);
							
							cu.stmt.executeUpdate("insert into room_allocation values(\""+
									sc.blocks[i].floors[j].gUnits[k].rooms[l].bedId[m]+"\",\"-1\")");
						}
							
							
					}
				}		
			}
		}
		System.out.println("Randomized!\n");
	}

	public void ConfigureSpaceHardcoded() throws NumberFormatException, IOException, SQLException{
		
		cu.stmt.executeUpdate("truncate room_allocation");
		cu.stmt.executeUpdate("truncate space_config");
		cu.stmt.executeUpdate("truncate block_constraints");
		sc = new SocialCenter(4);
		
		cu.stmt.executeUpdate("insert into space_config values(\""+4+"\")");
		
		for(int i=0;i<sc.blocks.length;i++){
			
			System.out.println("Enter no. of Floors in Block "+(i+1)+":");
			sc.blocks[i]=new Block(Integer.parseInt(br.readLine()));
			cu.stmt.executeUpdate("insert into space_config values(\""+sc.blocks[i].floors.length+"\")");
			
			//sc.blocks[i]=new Block(2);
			
			//cu.stmt.executeUpdate("insert into space_config values(\""+2+"\")");
			System.out.println("Set gender constraint(M/F/none) for block "+(i+1));
			sc.blocks[i].blockConstraint=br.readLine();
			cu.stmt.executeUpdate("insert into block_constraints values(\""+i+"\",\""+sc.blocks[i].blockConstraint+"\")");
			
			

			for(int j=0;j<sc.blocks[i].floors.length;j++){
				
				System.out.println("Enter no. of GUnts in Floor "+(j+1)+":");
				sc.blocks[i].floors[j]=new Floor(Integer.parseInt(br.readLine()));
				cu.stmt.executeUpdate("insert into space_config values(\""+sc.blocks[i].floors[j].gUnits.length+"\")");
				for(int k=0;k<sc.blocks[i].floors[j].gUnits.length;k++){
					
					sc.blocks[i].floors[j].gUnits[k]=new GUnit(4);
					
					
					cu.stmt.executeUpdate("insert into space_config values(\""+4+"\")");
					for(int l=0;l<sc.blocks[i].floors[j].gUnits[k].rooms.length;l++){
						
						sc.blocks[i].floors[j].gUnits[k].rooms[l]=new Room(2);

						cu.stmt.executeUpdate("insert into space_config values(\""+2+"\")");
						for(int m=0;m<sc.blocks[i].floors[j].gUnits[k].rooms[l].beds.length;m++){
							
							sc.blocks[i].floors[j].gUnits[k].rooms[l].bedId[m]="B"+(i+1)+"F"+(j+1)+"G"+(k+1)+"R"+(l+1)+"B"+(m+1);
							
							cu.stmt.executeUpdate("insert into room_allocation values(\""+(-1)
									+"\",\""+sc.blocks[i].floors[j].gUnits[k].rooms[l].bedId[m]+"\")");
							
							
							
						}
					}
				}		
			}
		}
		
		
	}
	
	public void ConfigureSpacePreload() throws NumberFormatException, IOException, SQLException{
		
		
		ArrayList<Integer> configArray=new ArrayList<Integer>();
		cu.rst=cu.stmt.executeQuery("select * from space_config");
		
		while(cu.rst.next()){
			configArray.add(cu.rst.getInt(1));
		}
		
		Iterator<Integer> ir=configArray.iterator();
		
		while(ir.hasNext()){
		
		sc = new SocialCenter(ir.next());
		
		
		for(int i=0;i<sc.blocks.length;i++){
			
			sc.blocks[i]=new Block(ir.next());
			
			cu.rst=cu.stmt.executeQuery("select gender_constraint from block_constraints where blockNo="+i);
			while(cu.rst.next())
				sc.blocks[i].blockConstraint=cu.rst.getString(1);
			
			
			for(int j=0;j<sc.blocks[i].floors.length;j++){
				
				sc.blocks[i].floors[j]=new Floor(ir.next());
				
				
				for(int k=0;k<sc.blocks[i].floors[j].gUnits.length;k++){
					
					sc.blocks[i].floors[j].gUnits[k]=new GUnit(ir.next());
					
					
					for(int l=0;l<sc.blocks[i].floors[j].gUnits[k].rooms.length;l++){
						
						sc.blocks[i].floors[j].gUnits[k].rooms[l]=new Room(ir.next());

						
						for(int m=0;m<sc.blocks[i].floors[j].gUnits[k].rooms[l].beds.length;m++){
							
							sc.blocks[i].floors[j].gUnits[k].rooms[l].bedId[m]="B"+(i+1)+"F"+(j+1)+"G"+(k+1)+"R"+(l+1)+"B"+(m+1);
							
						}
					}
				}		
			}
		}
		
		}
		
		
	}
	
	public void printConfig(){
		
		c=0;
		for(int i=0;i<sc.blocks.length;i++){
			
			//System.out.println("\n\n\n#Block-"+(i+1));
			
			for(int j=0;j<sc.blocks[i].floors.length;j++){
				
				//System.out.println("\n\n\t<<Floor-"+(j+1));
				
				for(int k=0;k<sc.blocks[i].floors[j].gUnits.length;k++){
					
					//System.out.print("\n\t\t<GUnit-"+(k+1)+"\n\n\t\t\t");
					
					for(int l=0;l<sc.blocks[i].floors[j].gUnits[k].rooms.length;l++){
						
						//System.out.print("((Room-"+(l+1)+" ");
						
						for(int m=0;m<sc.blocks[i].floors[j].gUnits[k].rooms[l].beds.length;m++){
						
							//System.out.print("|Bed-"+(m+1)+"|");
							
							c++;
						}
						
						//System.out.print(" ))\t");
						
					}
					
					//System.out.println("\n\n\t\t>\n");
					
				}
				//System.out.println("\t>>\n\n");
				
				
			}
			
			//System.out.println("###\n\n");
			
		}
		
		System.out.println("\nTOTAL NUMBER OF BEDS GENERATED = "+c+"\n\n\n");
		

		
	}
	
	
	public SocialCenter getSocialCenter(){
		return sc;
	}
	
	public int getTotalRooms(){
		return c;
	}
	
	/*public static void main(String args[]) throws NumberFormatException, IOException{
		SpaceConfig s=new SpaceConfig();
		
		s.ConfigureSpaceRandomize();
		s.printConfig();
		
		
	}*/

	

}
