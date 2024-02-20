package Que2;
public class DressesEqualizer {

    public static int calculateMinMoves(int[] sewingMachines) {
        int totalDresses = 0;
        int n = sewingMachines.length;

        for (int dressCount : sewingMachines) {
            totalDresses += dressCount;
        }

        if (totalDresses % n != 0) {
            return -1;
        }

        int targetDresses = totalDresses / n;
        int moves = 0;
        int balance = 0;

        for (int dressCount : sewingMachines) {
            balance += dressCount - targetDresses;
            moves = Math.max(moves, Math.abs(balance));
        }

        return moves;
    }

    public static void main(String[] args) {
        int[] sewingMachines = {1, 0, 5};
        int result = calculateMinMoves(sewingMachines);
        System.out.println(result); // Output: 2
    }
}
