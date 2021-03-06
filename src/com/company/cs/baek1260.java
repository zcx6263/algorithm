package com.company.cs;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
    백준 dfs / bfs 
 */
public class baek1260 {
	
	public static int V, E, start;
	
	public static int[][] map = new int[1002][1002];
	public static int[] visited = new int[1002];
	public static void dfs(int v) { /////////////////////////// dfs
		
		if(visited[v] == 1 ) return; // base case / 방문했다면 리턴
		visited[v] = 1;
		System.out.print(v+" ");
		for(int i= 1; i<= V ; i++) {
			if(map[v][i] == 1) {
				dfs(i);
			}
		}
	}
	public static void bfs(int v) { /////////////////////// bfs
		Queue<Integer> que = new LinkedList<Integer>();
		que.add(v);
		visited[v]=1;
		
		while(!que.isEmpty()) {
			int num = que.peek();
			System.out.print(num+" ");
			que.poll();
			
			for(int i= 1; i<= V ; i++) {
				if(map[num][i] == 1 && visited[i] == 0) { // 인접 행렬 검사 및 방문 체크 
					visited[i] = 1;
					que.add(i);
				}
			}
		}
	}
	public static void init() {
		for(int i=1; i<= V; i++) {
			visited[i] = 0;
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		int startP, endP;
		V = scanner.nextInt();
		E = scanner.nextInt();
		start = scanner.nextInt();
		
		for(int i=0; i < E; i++) {
			startP = scanner.nextInt();
			endP = scanner.nextInt();
			map[startP][endP] = 1;
			map[endP][startP] = 1;
		}
		init();
		dfs(start);
		init();
		System.out.println();
		bfs(start);
	}
}
