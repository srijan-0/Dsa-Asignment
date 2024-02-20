package Que2;

public class EqualizeDresses {

    public static int EqualizeDresses(int[] sewingMachines) {
        int totalDresses = 0;
        int n = sewingMachines.length;

        for (int dresses : sewingMachines) {
            totalDresses += dresses;
        }

        if (totalDresses % n != 0) {
            return -1;
        }

        int targetDresses = totalDresses / n;
        int moves = 0;
        int balance = 0;

        for (int dresses : sewingMachines) {
            balance += dresses - targetDresses;
            moves = Math.max(moves, Math.abs(balance));
        }

        return moves;
    }

    public static void main(String[] args) {
        int[] sewingMachines = { 1, 0, 5 };
        int result = EqualizeDresses(sewingMachines);
        System.out.println(result);
    }
}