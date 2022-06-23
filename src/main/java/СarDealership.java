import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class СarDealership {
    List<Car> cars = new ArrayList<>();
    private static final int SELL_TIME = 500;
    private static final int PRODUCTION_TIME = 1000;
    private static final Lock lock = new ReentrantLock(true);
    private static final Condition sellerCondition = lock.newCondition();
    public void productionOfCars() {
        try {
            while (true) {
                while (cars.size() < 2) {
                    try {
                        lock.lock();
                        System.out.println("====================");
                        System.out.println("Производитель Ford: Делаю 1 машину.");
                        Thread.sleep(PRODUCTION_TIME);
                        cars.add(new Car());
                        System.out.println("Производитель Ford: 1 машина выпущена в продажу.");
                        System.out.println("Количество машин на складе: " + cars.size());
                        System.out.println("====================\n");
                        sellerCondition.signalAll();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        lock.unlock();
                    }
                }
                Thread.sleep(Main.WAIT_TIME);
            }
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
    public void sellCar() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " пришел в магазин.");
            System.out.println(Thread.currentThread().getName() + ": Хочу купить машину!");
            while (cars.size() == 0) {
                System.out.println(Thread.currentThread().getName() + ", машин нет в наличии!\n");
                sellerCondition.await();
            }
            Thread.sleep(SELL_TIME);
            System.out.println("*********** Покупателю " + Thread.currentThread().getName() + " продана 1 машина. ***********");
            cars.remove(0);
            System.out.println("*********** Количество машин на складе: " + cars.size() + " ***********\n");
            Main.count++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

