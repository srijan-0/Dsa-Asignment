package Que2;

import java.util.HashSet;
import java.util.Set;

public class SecretSharing {
    Set<Integer> known = new HashSet<>();

    void newData(int start, int end) {

        for (int i = start; i <= end; ++i) {
            known.add(i);
        }

    }

    Set<Integer> answer() {
        return known;
    }

    public static void main(String[] args) {
        SecretSharing sc = new SecretSharing();
        sc.newData(0, 2);
        sc.newData(1, 3);
        sc.newData(2, 4);

        System.out.println(sc.answer());

    }

}