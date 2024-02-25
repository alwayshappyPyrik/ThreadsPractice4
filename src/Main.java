import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

public class Main {

    static public BlockingQueue<String> symbolA = new ArrayBlockingQueue<>(100);
    static public BlockingQueue<String> symbolB = new ArrayBlockingQueue<>(100);
    static public BlockingQueue<String> symbolC = new ArrayBlockingQueue<>(100);

    static public AtomicLong countSymbolA = new AtomicLong(0);
    static public AtomicLong countSymbolB = new AtomicLong(0);
    static public AtomicLong countSymbolC = new AtomicLong(0);

    public static void main(String[] args) {

        //Producer
        new Thread(() -> {
            try {
                for (int i = 0; i < 10_000; i++) {
                    symbolA.put(generateText("abc", 100_000));
                    symbolB.put(generateText("abc", 100_000));
                    symbolC.put(generateText("abc", 100_000));
                }
                symbolA.put("DONE");
                symbolB.put("DONE");
                symbolC.put("DONE");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        //Consumer
        new Thread(() -> {
            String line;
            try {
                while (!((line = symbolA.take()).equals("DONE"))) {
                    long count = line.chars().filter(ch -> ch == 'a').count();
                    countSymbolA.addAndGet(count);
                }
                System.out.println("Очередь symbolA разобрана. " + "Кол-во букв a: " + countSymbolA);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            String line;
            try {
                while (!((line = symbolB.take()).equals("DONE"))) {
                    long count = line.chars().filter(ch -> ch == 'b').count();
                    countSymbolB.addAndGet(count);
                }
                System.out.println("Очередь symbolB разобрана. " + "Кол-во букв b: " + countSymbolB);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {
            String line;
            try {
                while (!((line = symbolC.take()).equals("DONE"))) {
                    long count = line.chars().filter(ch -> ch == 'c').count();
                    countSymbolC.addAndGet(count);
                }
                System.out.println("Очередь symbolC разобрана " + "Кол-во букв c: " + countSymbolC);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

}