import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    protected BlockingQueue<String> symbolA;
    protected BlockingQueue<String> symbolB;
    protected BlockingQueue<String> symbolC;

    public Producer(BlockingQueue<String> symbolA, BlockingQueue<String> symbolB, BlockingQueue<String> symbolC) {
        this.symbolA = symbolA;
        this.symbolB = symbolB;
        this.symbolC = symbolC;
    }

    @Override
    public void run() {
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
    }

    public String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
