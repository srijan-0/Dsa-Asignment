package Que1;

public class Planing {
    public static int minCostToDecorate(int[][] costs) {
        if (costs == null || costs.length == 0 || costs[0].length == 0) {
            return 0;
        }

        int n = costs.length;
        int k = costs[0].length;

        // dp[i][j] represents the minimum cost to decorate venues up to i with the last
        // venue decorated with theme j
        int[][] dp = new int[n][k];

        // Initialize the first row of dp with the costs of decorating the first venue
        for (int j = 0; j < k; j++) {
            dp[0][j] = costs[0][j];
        }

        // Iterate through the venues starting from the second one
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < k; j++) {
                // Find the minimum cost to decorate the current venue with theme j
                dp[i][j] = Integer.MAX_VALUE;
                for (int prevTheme = 0; prevTheme < k; prevTheme++) {
                    if (prevTheme != j) {
                        dp[i][j] = Math.min(dp[i][j], dp[i - 1][prevTheme] + costs[i][j]);
                    }
                }
            }
        }

        // Find the minimum cost among the last row of dp
        int minCost = Integer.MAX_VALUE;
        for (int j = 0; j < k; j++) {
            minCost = Math.min(minCost, dp[n - 1][j]);
        }

        return minCost;
    }

    public static void main(String[] args) {
        int[] a = { 1, 3, 2 };
        int[] b = { 4, 6, 8 };
        int[] c = { 3, 1, 2 };
        int[][] costs = { a, b, c };

        Planing p = new Planing();
        int result = p.minCostToDecorate(costs);
        System.out.println("Minimum cost to decorate all venues: " + result);
    }
}