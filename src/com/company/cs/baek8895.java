package com.company.cs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 막대 배치 
 */
public class baek8895 {

	static int N, left, right;
	static long[][][] dp = new long[25][25][25];
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int testCase = Integer.parseInt(st.nextToken());
		for(int n = 1; n <= testCase; n++)
		{
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			left = Integer.parseInt(st.nextToken());
			right = Integer.parseInt(st.nextToken());
			
			dp[1][1][1] = 1;
			for(int i=2; i<= N; i++)
			{
				for(int j=1; j<= left; j++)
				{
					for(int k=1; k<= right; k++)
					{
						dp[i][j][k] = dp[i-1][j-1][k] + dp[i-1][j][k-1] + dp[i-1][j][k]*(i-2);
					}
				}
			}
			System.out.println(dp[N][left][right]);
		}
	}

}
