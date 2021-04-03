import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class Car implements Runnable {
    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;
    private CyclicBarrier barrier;
    private static AtomicInteger ai = new AtomicInteger(0);
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed, CyclicBarrier barrier) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.barrier = barrier;
    }
    @Override
    public void run() {
        try {
            long time = System.currentTimeMillis();
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 8000));
            System.out.println(this.name + " готов");
            barrier.await();
           
            barrier.await();
            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }
            if(ai.incrementAndGet() == 1) {
                System.out.println(name + " " + "- WIN!");
            }
            barrier.await();
            System.out.println("Время прохождения "  + (System.currentTimeMillis() - time));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
