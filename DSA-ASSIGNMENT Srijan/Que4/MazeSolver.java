package Que4;

import java.util.*;

public class MazeSolver {

    private static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final char START = 'S';
    private static final char KEY_PREFIX = 'a';
    private static final char DOOR_PREFIX = 'A';

    public static int solve(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        Map<Character, Point> keyLocations = new HashMap<>();
        Set<Character> collectedKeys = new HashSet<>();
        Point start = null;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                char cell = grid[i][j];
                if (cell == START) {
                    start = new Point(i, j);
                } else if (Character.isLowerCase(cell)) {
                    keyLocations.put(cell, new Point(i, j));
                } else if (Character.isUpperCase(cell)) {
                    // Skip doors without corresponding keys
                    if (!keyLocations.containsKey(Character.toLowerCase(cell))) {
                        continue;
                    }
                }
            }
        }

        if (start == null || keyLocations.isEmpty()) {
            return -1;
        }

        int[][] distances = BFS(grid, start);
        return findMinMoves(keyLocations, collectedKeys, start, distances);
    }

    private static int[][] BFS(char[][] grid, Point start) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] distances = new int[m][n];
        Queue<Point> queue = new LinkedList<>();

        queue.add(start);
        distances[start.x][start.y] = 0;

        while (!queue.isEmpty()) {
            Point current = queue.poll();
            int x = current.x;
            int y = current.y;

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) {
                        continue;
                    }
                    int newX = x + dx;
                    int newY = y + dy;

                    if (newX >= 0 && newX < m && newY >= 0 && newY < n && grid[newX][newY] != 'W') {
                        if (distances[newX][newY] == 0) {
                            distances[newX][newY] = distances[x][y] + 1;
                            queue.add(new Point(newX, newY));
                        }
                    }
                }
            }
        }

        return distances;
    }

    // Corrected function to avoid redundant exploration
    private static int findMinMoves(Map<Character, Point> keyLocations, Set<Character> collectedKeys, Point current,
            int[][] distances) {
        if (collectedKeys.size() == keyLocations.size()) {
            return distances[current.x][current.y];
        }

        int minMoves = Integer.MAX_VALUE;
        for (Map.Entry<Character, Point> entry : keyLocations.entrySet()) {
            char key = entry.getKey();
            Point keyLocation = entry.getValue();

            if (!collectedKeys.contains(key)) {
                collectedKeys.add(key);
                // Only explore reachable keys that haven't been explored before
                if (distances[keyLocation.x][keyLocation.y] > 0) {
                    int distanceToKey = distances[keyLocation.x][keyLocation.y];
                    int movesFromKey = findMinMoves(keyLocations, collectedKeys, keyLocation, distances);
                    if (movesFromKey != -1) {
                        minMoves = Math.min(minMoves, distanceToKey + movesFromKey);
                    }
                }
                collectedKeys.remove(key);
            }
        }

        return minMoves == Integer.MAX_VALUE ? -1 : minMoves;
    }

    public static void main(String[] args) {
        char[][] grid = {
                { 'S', 'P', 'q', 'P', 'P' },
                { 'W', 'W', 'W', 'P', 'W' },
                { 'r', 'P', 'Q', 'P', 'R' }
        };

        int shortestPathLength = solve(grid);
        System.out.println("Shortest path length to collect all keys: " + shortestPathLength);
    }
}
