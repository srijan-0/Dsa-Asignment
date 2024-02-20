package Que3;

import java.util.PriorityQueue;

class ScoreTracker {
    private PriorityQueue<Double> minHeap; // Stores the larger half of scores
    private PriorityQueue<Double> maxHeap; // Stores the smaller half of scores

    public ScoreTracker() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>((a, b) -> Double.compare(b, a)); // Reverse order
    }

    public void addScore(double score) {
        if (minHeap.isEmpty() || score >= minHeap.peek()) {
            minHeap.offer(score);
        } else {
            maxHeap.offer(score);
        }

        // Balance the heaps
        if (minHeap.size() > maxHeap.size() + 1) {
            maxHeap.offer(minHeap.poll());
        } else if (maxHeap.size() > minHeap.size()) {
            minHeap.offer(maxHeap.poll());
        }
    }

    public double getMedianScore() {
        if (minHeap.size() == maxHeap.size()) {
            return (minHeap.peek() + maxHeap.peek()) / 2.0;
        } else {
            return minHeap.peek();
        }
    }

    public static void main(String[] args) {
        ScoreTracker scoreTracker = new ScoreTracker();
        scoreTracker.addScore(85.5);
        scoreTracker.addScore(92.3);
        scoreTracker.addScore(77.8);
        scoreTracker.addScore(90.1);
        double median1 = scoreTracker.getMedianScore();
        System.out.println("Median 1: " + median1); // Output: 87.8

        scoreTracker.addScore(81.2);
        scoreTracker.addScore(88.7);

        double median2 = scoreTracker.getMedianScore();
        System.out.println("Median 2: " + median2); // Output: 87.1
    }
}
