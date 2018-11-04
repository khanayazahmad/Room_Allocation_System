package com.roomalloc.staticalloc;
import java.io.IOException;
import java.sql.SQLException;

public class CampusPair {
	CampusMind cm[];
	byte alloc[];
	int num;
	int noP;
	int pair[][];
	int unpaired;
	byte gen;
	byte hlt;
	ConnectionUtil cu;

	CampusPair(byte g, byte h) throws SQLException {
		char gen = (g==0)?'M':'F';
		hlt = h;
		cu = new ConnectionUtil();
		cu.exec("select * from campus_data where gender='" + gen + "'");
		cu.rst.last();
		num = cu.rst.getRow();
		cu.rst.beforeFirst();
		if (num == 0) {
			System.out.print("Database empty");
			return;
		}
		noP = (num % 2 != 0) ? num - 1 : num;
		cm = new CampusMind[num];
		alloc = new byte[num];
		for (int i = 0; i < num; i++) {
			cm[i] = new CampusMind();
			alloc[i] = 0;
		}
		pair = new int[noP / 2][2];
		unpaired = -1;

	}

	void genPairs() {
		int i = 0, j = 0, match[] = new int[num], count = 0, k = 0, p;
		while (count < noP) {
			match[i] = findMatch(i);
			p = cm[i].getScore(cm[match[i]]);
			if (p == 15) {
				pair[k][0] = i;
				pair[k++][1] = match[i];
				alloc[i] = alloc[match[i]] = 1;
				count += 2;
				while (alloc[i] != 0 && count < num)
					i = (i < num - 1) ? i + 1 : 0;
				j = i;
			} else if (match[i] == j) {
				pair[k][0] = i;
				pair[k++][1] = j;
				alloc[i] = alloc[j] = 1;
				count += 2;
				while (alloc[i] != 0 && count < num)
					i = (i < num - 1) ? i + 1 : 0;
				j = i;
			}

			else {
				j = i;
				i = match[i];
			}
		}

		if (num % 2 != 0)
			for (i = 0; i < num; i++) {
				if (alloc[i] == 0) {
					unpaired = i;
					break;
				}

			}

	}

	private int findMatch(int i) {
		if (alloc[i] == 1)
			return 0;
		int match = i;
		for (int k = 0; k < num; k++) {
			if (alloc[k] == 1 || k == i)
				continue;
			if (cm[i].getScore(cm[match]) < cm[i].getScore(cm[k]))
				match = k;
			if (cm[i].getScore(cm[match]) == 15)
				return match;
		}
		return match;
	}

	void getData() throws SQLException, IOException {
		
		//cu.exec("select * from campus_data where gender='" + gen + "'");
		int i = 0;
		while (cu.rst.next()) {
			
			
			cm[i].setDetails(cu.rst.getInt(1), cu.rst.getString(3),
					cu.rst.getString(4), cu.rst.getString(5), cu.rst.getString(6),
					cu.rst.getInt(7));
			i++;

		}
		System.out.println("**************************DETAILS TAKEN**************************");

	}

	void printData() {
		for (int i = 0; i < num; i++) {
			System.out.println("Details for each campus mind " + (i + 1));
			cm[i].printDetails();
			System.out.println("\n\n");
		}
	}

	void displayPairs() {
		System.out.println("\nPairs of campus minds: \n");
		for (int i = 0; i < num / 2; i++)
			System.out.println("Pair " + (i + 1) + ":	(" + (pair[i][0] + 1)
					+ " , " + (pair[i][1] + 1) + " ) score = "
					+ cm[pair[i][0]].getScore(cm[pair[i][1]]));
		if (num % 2 != 0)
			System.out.println("The unpaired one:	" + (unpaired));
		else
			System.out.println("Everyone has been paired");
	}
	
	
	 /* public static void main(String args[]) throws NumberFormatException,
	  IOException, SQLException{ BufferedReader br=new BufferedReader(new
	  InputStreamReader(System.in));
	  System.out.println("Enter total number of campus minds: "); int
	  n=Integer.parseInt(br.readLine()); CampusPair cp=new CampusPair((byte)0,(byte)0);
	  cp.getData(); cp.printData(); cp.genPairs(); cp.displayPairs();
	  
	  
	  }
	 */
}
