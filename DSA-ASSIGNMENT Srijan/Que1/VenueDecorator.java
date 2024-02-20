package Que1;
public class VenueDecorator {
    public static int findMinimumCost(int[][] costs) {
        if (costs == null || costs.length == 0 || costs[0].length == 0) {
            return 0;
        }

        int numVenues = costs.length;
        int numThemes = costs[0].length;

        int[][] dp = new int[numVenues][numThemes];

        for (int theme = 0; theme < numThemes; theme++) {
            dp[0][theme] = costs[0][theme];
        }

        for (int venue = 1; venue < numVenues; venue++) {
            for (int theme = 0; theme < numThemes; theme++) {
                dp[venue][theme] = Integer.MAX_VALUE;
                for (int prevTheme = 0; prevTheme < numThemes; prevTheme++) {
                    if (prevTheme != theme) {
                        dp[venue][theme] = Math.min(dp[venue][theme], dp[venue - 1][prevTheme] + costs[venue][theme]);
                    }
                }
            }
        }

        int minCost = Integer.MAX_VALUE;
        for (int theme = 0; theme < numThemes; theme++) {
            minCost = Math.min(minCost, dp[numVenues - 1][theme]);
        }

        return minCost;
    }

    public static void main(String[] args) {
        int[] venue1 = {1, 3, 2};
        int[] venue2 = {4, 6, 8};
        int[] venue3 = {3, 1, 2};
        int[][] costs = {venue1, venue2, venue3};

        int result = findMinimumCost(costs);
        System.out.println("Minimum cost to decorate all venues: " + result);
    }
}
