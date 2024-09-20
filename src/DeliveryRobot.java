import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DeliveryRobot {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        int numberOfRoutes = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfRoutes);

        for (int i = 0; i < numberOfRoutes; i++) {
            executorService.execute(() -> {
                String route = generateRoute("RLRFR", 100);
                int countR = countOccurrences(route, 'R');
                updateFrequency(countR);
            });
        }

        executorService.shutdown();

        while (!executorService.isTerminated()) {
        }

        printResults();
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static int countOccurrences(String route, char target) {
        int count = 0;
        for (char ch : route.toCharArray()) {
            if (ch == target) {
                count++;
            }
        }
        return count;
    }

    public static synchronized void updateFrequency(int frequency) {
        sizeToFreq.put(frequency, sizeToFreq.getOrDefault(frequency, 0) + 1);
    }

    public static void printResults() {
        int maxFrequency = -1;
        int maxCount = -1;

        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxFrequency = entry.getKey();
            }
        }

        System.out.println("Самое частое количество повторений " + maxFrequency + " (встретилось " + maxCount + " раз)");

        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getKey() != maxFrequency) {
                System.out.println("- " + entry.getKey() + " (" + entry.getValue() + " раз)");
            }
        }
    }
}