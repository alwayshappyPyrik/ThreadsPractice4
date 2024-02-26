import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {

    public static void main(String[] args) {

        BlockingQueue<String> symbolA = new ArrayBlockingQueue<>(100);
        BlockingQueue<String> symbolB = new ArrayBlockingQueue<>(100);
        BlockingQueue<String> symbolC = new ArrayBlockingQueue<>(100);

        new Thread(new Producer(symbolA, symbolB, symbolC)).start();
        new Thread(new Consumer(symbolA, symbolB, symbolC)).start();
    }
}