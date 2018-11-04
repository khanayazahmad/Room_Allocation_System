package com.roomalloc.staticalloc;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CampusMind {
	int mid;
	String reg;
	String col;
	String bch;
	String mt;
	byte pf;
	InputStreamReader ir;
	BufferedReader br;

	CampusMind() {
		mid = 0;
		reg = "";
		col = "";
		bch = "";
		mt = "";
		pf = 0;
		ir = new InputStreamReader(System.in);
		br = new BufferedReader(ir);
	}

	void setDetails(int id, String r, String c, String b, String m, int p)
			throws IOException {

		mid = id;

		reg = r;

		col = c;

		bch = b;

		mt = m;

		pf = (byte) p;

	}

	void printDetails() {
		System.out.print("\nMID: " + mid);
		System.out.print("\nRegion: " + reg);
		System.out.print("\nCollege: " + col);
		System.out.print("\nBranch: " + bch);
		System.out.print("\nMother tongue: " + mt);
		System.out.print("\nPerformance " + pf);
	}

	int getScore(CampusMind cm) {
		int score = 0;
		if (!this.reg.equalsIgnoreCase(cm.reg))
			score += 5;
		if (!this.col.equalsIgnoreCase(cm.col))
			score += 3;
		if (!this.bch.equalsIgnoreCase(cm.bch))
			score += 2;
		if (!this.mt.equalsIgnoreCase(cm.mt))
			score += 4;
		if (inRange(this.pf, cm.pf))
			score += 1;
		return score;
	}

	static int getScore(CampusMind cm1[], CampusMind cm2[]) {
		int score = 0, k[] = { 0, 0, 0, 0, 0 }, i, j;
		for (i = 0; i < cm1.length; i++) {
			for (j = 0; j < cm2.length; j++) {
				if (cm1[i].reg.equalsIgnoreCase(cm2[j].reg)) {
					k[0]++;
				}
				if (cm1[i].col.equalsIgnoreCase(cm2[j].col)) {
					k[1]++;
				}
				if (cm1[i].bch.equalsIgnoreCase(cm2[j].bch)) {
					k[2]++;
				}
				if (cm1[i].mt.equalsIgnoreCase(cm2[j].mt)) {
					k[3]++;
				}
				if ((inRange(cm1[i].pf, cm2[j].pf))) {
					k[4]++;
				}

			}

		}
		score = 5 * (cm1.length - k[0]) + 3 * (cm1.length - k[1]) + 2
				* (cm1.length - k[2]) + 4 * (cm1.length - k[3])
				+ (cm1.length - k[4]);

		return score;

	}

	static boolean inRange(byte i, byte j) {
		int range = Math.abs(i - j);
		if (range >= 5 && range <= 10)
			return true;
		else
			return false;
	}
}
