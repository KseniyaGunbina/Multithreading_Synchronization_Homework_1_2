import java.util.ArrayList;
import java.util.List;

public class СarDealership {
    List<Car> cars = new ArrayList<>();
    private static final int SELL_TIME = 500;
    private static final int PRODUCTION_TIME = 1000;
    private static final int WAIT_TIME = 2000;

    public void productionOfCars() {
        try {
            while (true) {
                synchronized (this) {
                    while (cars.size() < 2) {
                        System.out.println("====================");
                        System.out.println("Производитель Ford: Делаю 1 машину.");
                        Thread.sleep(PRODUCTION_TIME);
                        cars.add(new Car());

                        System.out.println("Производитель Ford: 1 машина выпущена в продажу.");
                        System.out.println("Количество машин на складе: " + cars.size());
                        System.out.println("====================\n");
                        notifyAll();
                    }
                }
                Thread.sleep(WAIT_TIME);

            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public synchronized void sellCar() {
        try {
            System.out.println(Thread.currentThread().getName() + " пришел в магазин.");
            Thread.sleep(WAIT_TIME);
            System.out.println(Thread.currentThread().getName() + ": Хочу купить машину!");
            while (cars.size() == 0) {
                System.out.println("Машин нет в наличии!");
                wait();
            }
            Thread.sleep(SELL_TIME);
            System.out.println("Покупателю " + Thread.currentThread().getName() + " продана 1 машина.");
            System.out.println("Количество машин на складе: " + cars.size());
            cars.remove(0);
            Main.count++;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

