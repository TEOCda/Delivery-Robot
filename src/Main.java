import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new Hashtable<>();

    public static void main(String[] args) throws InterruptedException {

        ExecutorService threadPool = Executors.newFixedThreadPool(1000);
        threadPool.submit(() -> {
            for (int i = 0; i <= 1000; i++) {

                String text = generateRoute("RLRFR", 100);
                int count = 0;
                int countMax = 0;

                for (int j = 0; j < text.length(); j++) {

                    char R = text.charAt(j);
                    char SearchR = 'R';


                    if (R == SearchR) {
                        count++;
                    } else {
                        count = 0;
                    }
                    if (countMax < count) {
                        countMax = count;
                    }

                }

                synchronized (sizeToFreq) {
                    sizeToFreq.put(text.length() - text.replace("R", "").length(), countMax);
                }


            }

            threadPool.shutdown();

        });


        if (threadPool.awaitTermination(10, TimeUnit.MINUTES)) {
            for (Integer name : sizeToFreq.keySet()) {
                int key = Integer.parseInt(name.toString());
                int value = Integer.parseInt(sizeToFreq.get(name).toString());
                System.out.println(key + " " + "(" + value + " раз)");
            }
        } else {
            System.out.println("!");
        }

    }


    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
