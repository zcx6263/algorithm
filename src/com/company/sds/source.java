package com.company.sds;
/**
 * 기판 설계 
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.StringTokenizer;

public class source {

	static int N, totalGroup, maxCrossCnt, minGroupResult;
	static Line[] line = new Line[2005];
	static Node[] par = new Node[2005];
	static HashSet<Integer> set = new HashSet<>(); // 그룹의 부모 대표값 
	static ArrayList<Integer> ans = new ArrayList<>();
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int testCase = Integer.parseInt(st.nextToken());
		long dx1 =0, dy1 =0, dx2 =0, dy2 =0;
		for(int k=1; k<= testCase; k++)
		{
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			totalGroup = N; maxCrossCnt = 0;
			set.clear(); ans.clear();
			for(int i=1; i<= N; i++)
			{
				st = new StringTokenizer(br.readLine());
				dx1 = (long)Integer.parseInt(st.nextToken());
				dy1 = (long)Integer.parseInt(st.nextToken());
				dx2 = (long)Integer.parseInt(st.nextToken());
				dy2 = (long)Integer.parseInt(st.nextToken());
				line[i] = new Line(dx1, dy1, dx2, dy2);
				par[i] = new Node(i);
				par[i].dx1 = dx1; par[i].dy1 = dy1; par[i].dx2 = dx2; par[i].dy2 = dy2;
			}
			
			for(int i = 1; i < N; i++)
			{
				for(int j= i + 1; j <= N; j++)
				{
					if(isCross(line[i], line[j]))
					{
						int ap1 = find(i);
						int ap2 = find(j);
						if(ap1 != ap2)
						{
							union(ap1,ap2);
							totalGroup--;
						}
					}
				}
			}
			
			for(int i=1; i<= N; i++)
			{
				find(i);
				set.add(par[i].order);
				if(maxCrossCnt < par[i].crossCnt)
				{
					maxCrossCnt = par[i].crossCnt;
				}
			}
			
			ans.addAll(set);
			long resultCnt = 0;
			for(int i=0; i< ans.size(); i++)
			{
				int num = ans.get(i);
				dx1 = par[num].dx1;
				dx2 = par[num].dx2;
				dy1 = par[num].dy1;
				dy2 = par[num].dy2;
				
				long dx1Cnt = 0, dx2Cnt =0, dy1Cnt =0, dy2Cnt =0;
				for(int j=0; j< ans.size(); j++)
				{
					int target = ans.get(j);
					long rdx1 = par[target].dx1;
					long rdx2 = par[target].dx2;
					long rdy1 = par[target].dy1;
					long rdy2 = par[target].dy2;
					
					if(rdx1 <= dx1 && dx1 <= rdx2) dx1Cnt++;
					//if(rdy1 <= dx1 && dx1 <= rdy2) dx1Cnt++;
					
					if(rdx1 <= dx2 && dx2 <= rdx2) dx2Cnt++;
					//if(rdy1 <= dx2 && dx2 <= rdy2) dx2Cnt++;
					
					//if(rdx1 <= dy1 && dy1 <= rdx2) dy1Cnt++;
					if(rdy1 <= dy1 && dy1 <= rdy2) dy1Cnt++;
					
					//if(rdx1 <= dy2 && dy2 <= rdx2) dy2Cnt++;
					if(rdy1 <= dy2 && dy2 <= rdy2) dy2Cnt++;
				}
				
				resultCnt = max( max(dx1Cnt,dx2Cnt) , max(dy1Cnt,dy2Cnt) );
				
			}
			System.out.println(resultCnt);
			bw.write("#"+k+" "+totalGroup + " " + maxCrossCnt+"\n");
		}
		bw.flush();
	}
	public static void union(int a, int b)
	{
		int ap1 = find(a);
		int ap2 = find(b);
		par[ap1].order = ap2; // ap1 -> ap2 가리키도록 설정 (대표값)
		
	}
	public static int find(int a)
	{
		if(par[a].order == a) return a;
		else 
		{
			par[par[a].order].crossCnt += par[a].crossCnt;
			par[a].crossCnt = 0;
			par[par[a].order].dx1 = min(par[par[a].order].dx1, par[a].dx1);
			par[par[a].order].dy1 = min(par[par[a].order].dy1, par[a].dy1);
			par[par[a].order].dx2 = max(par[par[a].order].dx2, par[a].dx2);
			par[par[a].order].dy2 = max(par[par[a].order].dy2, par[a].dy2);
			return par[a].order = find(par[a].order);
		}
	}
	public static boolean isCross(Line a, Line b)
	{
		int ap1 = ccw(a.p1, a.p2, b.p1)*ccw(a.p1, a.p2, b.p2);
		int ap2 = ccw(b.p1, b.p2, a.p1)*ccw(b.p1, b.p2, a.p2);
		if(ap1 == 0 && ap2 ==0)
		{
			if(a.p_max.dx < b.p_min.dx ||
				a.p_max.dy < b.p_min.dy ||
				b.p_max.dx < a.p_min.dx ||
				b.p_max.dy < a.p_min.dy) return false;
		}
		return (ap1 <= 0 && ap2 <= 0);
	}
	static class Node { // group 
		long dx1, dy1, dx2, dy2;
		int order, crossCnt;
		Node(int a) {
			order = a;
			crossCnt = 1;
		}
	}
	static class Line {
		Point p1, p2, p_max, p_min;
		Line(long dx1, long dy1, long dx2, long dy2) {
			p1 = new Point(dx1, dy1);
			p2 = new Point(dx2, dy2);
			p_max = new Point(max(dx1,dx2) , max(dy1, dy2));
			p_min = new Point(min(dx1,dx2) , min(dy1, dy2));
		}
	}
	static class Point {
		long dx, dy;
		Point(long a, long b) {
			dx=a;dy=b;
		}
	}
	public static long dis(Point a, Point b)
	{
		return (a.dx-b.dx)*(a.dx-b.dx) + (a.dy-b.dy)*(a.dy-b.dy);
	}
	public static int ccw(Point a, Point b, Point c)
	{
		long op = ( (a.dx*b.dy)+ (b.dx*c.dy) + (c.dx*a.dy) ) - ( (a.dy*b.dx)+(b.dy*c.dx) +(c.dy*a.dx) );
		if(op > 0) return 1;
		else if(op < 0) return -1;
		else return 0 ;
	}
	public static long max(long a, long b) { return a > b ? a : b; }
	public static long min(long a, long b) { return a > b ? b : a; }
}


/*
3 
13 
-12 9 -6 6 
-8 -7 -2 -3 
7 -10 14 -4 
4 10 16 10 
7 1 11 5 
8 10 15 7 
-7 4 -8 11 
-4 10 -11 6 
9 -6 7 1 
0 6 -5 11 
7 -10 9 -6 
15 12 15 5 
14 -4 7 1 
8 
1 6 6 10 
2 5 2 8 
7 9 8 6 
13 7 7 7 
16 6 8 4 
12 10 8 0 
16 2 16 6 
2 5 7 9 
8 
0 12 4 14 
3 9 5 10 
7 11 5 10 
6 5 10 7 
12 8 8 6 
11 1 17 4 
15 3 11 1 
13 2 17 4 
 */
