package Que5;

import java.util.*;

class Antcolonyy {
    private final int numAnts;
    private final int numIterations;
    private final double alpha;
    private final double beta;
    private final double evaporationRate;

    // Problem data
    private final double[][] distances;
    private final int startCity;

    public Antcolonyy(int numAnts, int numIterations, double alpha, double beta, double evaporationRate,
            double[][] distances, int startCity) {
        this.numAnts = numAnts;
        this.numIterations = numIterations;
        this.alpha = alpha;
        this.beta = beta;
        this.evaporationRate = evaporationRate;
        this.distances = distances;
        this.startCity = startCity;
    }

    public List<Integer> solve() {
        // Initialize pheromone trails
        double[][] pheromoneTrails = new double[distances.length][distances.length];
        for (int i = 0; i < pheromoneTrails.length; i++) {
            for (int j = 0; j < pheromoneTrails[i].length; j++) {
                pheromoneTrails[i][j] = 0.1;
            }
        }

        // Main loop
        List<Integer> bestTour = null;
        double bestTourLength = Double.MAX_VALUE;
        for (int iteration = 0; iteration < numIterations; iteration++) {
            // Move ants
            List<List<Integer>> antTours = moveAnts(pheromoneTrails);

            // Calculate tour lengths
            List<Double> tourLengths = calculateTourLengths(antTours, pheromoneTrails);

            // Update best tour
            int bestAntIndex = findBestAnt(tourLengths);
            if (tourLengths.get(bestAntIndex) < bestTourLength) {
                bestTour = antTours.get(bestAntIndex);
                bestTourLength = tourLengths.get(bestAntIndex);
            }

            // Update pheromone trails
            updatePheromoneTrails(pheromoneTrails, antTours, bestTourLength);
        }

        return bestTour;
    }

    private List<List<Integer>> moveAnts(double[][] pheromoneTrails) {
        List<List<Integer>> antTours = new ArrayList<>();
        for (int ant = 0; ant < numAnts; ant++) {
            List<Integer> tour = new ArrayList<>();
            int currentCity = startCity;
            tour.add(currentCity);
            while (tour.size() < distances.length) {
                int nextCity = chooseNextCity(currentCity, pheromoneTrails, tour);
                if (tour.contains(nextCity)) {
                    continue; // Avoid revisiting cities
                }
                tour.add(nextCity);
                currentCity = nextCity;
            }
            tour.add(startCity); // Return to starting city
            antTours.add(tour);
        }
        return antTours;
    }

    private List<Double> calculateTourLengths(List<List<Integer>> antTours, double[][] pheromoneTrails) {
        List<Double> tourLengths = new ArrayList<>();
        for (List<Integer> tour : antTours) {
            double tourLength = 0;
            for (int i = 0; i < tour.size() - 1; i++) {
                int cityA = tour.get(i);
                int cityB = tour.get(i + 1);
                tourLength += distances[cityA][cityB];
            }
            tourLengths.add(tourLength);
        }
        return tourLengths;
    }

    private int findBestAnt(List<Double> tourLengths) {
        int bestAntIndex = 0;
        double bestTourLength = Double.MAX_VALUE;
        for (int i = 0; i < tourLengths.size(); i++) {
            if (tourLengths.get(i) < bestTourLength) {
                bestAntIndex = i;
                bestTourLength = tourLengths.get(i);
            }
        }
        return bestAntIndex;
    }

    private void updatePheromoneTrails(double[][] pheromoneTrails, List<List<Integer>> antTours,
            double bestTourLength) {
        // Evaporate existing pheromone
        for (int i = 0; i < pheromoneTrails.length; i++) {
            for (int j = 0; j < pheromoneTrails[i].length; j++) {
                pheromoneTrails[i][j] *= (1.0 - evaporationRate);
            }
        }

        // Update with new pheromone
        for (List<Integer> tour : antTours) {
            double tourPheromoneDelta = 1.0 / bestTourLength;
            for (int i = 0; i < tour.size() - 1; i++) {
                int cityA = tour.get(i);
                int cityB = tour.get(i + 1);
                pheromoneTrails[cityA][cityB] += tourPheromoneDelta;
                pheromoneTrails[cityB][cityA] += tourPheromoneDelta; // Symmetric matrix
            }
        }
    }

    private int chooseNextCity(int currentCity, double[][] pheromoneTrails, List<Integer> tour) {
        double[] probabilities = new double[distances.length];
        double totalProbability = 0;

        // Calculate the denominator for the probability formula
        for (int i = 0; i < distances.length; i++) {
            if (i == currentCity || tour.contains(i)) {
                continue; // Skip the city if already visited
            }
            probabilities[i] = Math.pow(pheromoneTrails[currentCity][i], alpha)
                    * Math.pow(1.0 / distances[currentCity][i], beta);
            totalProbability += probabilities[i];
        }

        // Select the next city based on probabilities
        double rand = Math.random() * totalProbability;
        double cumulativeProbability = 0;
        for (int i = 0; i < distances.length; i++) {
            if (i == currentCity || tour.contains(i)) {
                continue;
            }
            cumulativeProbability += probabilities[i];
            if (cumulativeProbability >= rand) {
                return i;
            }
        }

        // If for some reason, no city is chosen, return a random one (shouldn't happen)
        return (int) (Math.random() * distances.length);
    }
}

public class Antcolony {
    public static void main(String[] args) {
        // Example parameters
        int numAnts = 10;
        int numIterations = 100;
        double alpha = 1.0;
        double beta = 2.0;
        double evaporationRate = 0.1;
        double[][] distances = { { 0, 10, 15 }, { 10, 0, 20 }, { 15, 20, 0 } };
        int startCity = 0;

        // Create an instance of Antcolonyy
        Antcolonyy antColonyy = new Antcolonyy(numAnts, numIterations, alpha, beta, evaporationRate, distances,
                startCity);

        // Solve the problem
        List<Integer> bestTour = antColonyy.solve();
        System.out.println("Best tour: " + bestTour);
    }
}