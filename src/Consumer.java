import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

public class Consumer implements Runnable {

    protected BlockingQueue<String> symbolA;
    protected BlockingQueue<String> symbolB;
    protected BlockingQueue<String> symbolC;

    protected AtomicLong countSymbolA = new AtomicLong(0);
    protected AtomicLong countSymbolB = new AtomicLong(0);
    protected AtomicLong countSymbolC = new AtomicLong(0);

    public Consumer(BlockingQueue<String> symbolA, BlockingQueue<String> symbolB, BlockingQueue<String> symbolC) {
        this.symbolA = symbolA;
        this.symbolB = symbolB;
        this.symbolC = symbolC;
    }

    @Override
    public void run() {
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
                System.out.println("Очередь symbolC разобрана. " + "Кол-во букв c: " + countSymbolC);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
