import java.util.ArrayList;
import java.util.List;

public class СarDealership {
    List<Car> cars = new ArrayList<>();
    private int saleCount;
    private static final int SELL_TIME = 1000;
    private static final int PRODUCTION_TIME = 2000;
    private static final int WAIT_TIME = 5000;
    private boolean readyForBuy = false;
    private boolean carDealershipIsOpen = true;
    private static final int MAX_COUNT_OF_SALES = 10;

    public synchronized void productionOfCars() {
        try {
            while (carDealershipIsOpen) {
                if (readyForBuy && cars.size() > 0)
                    wait();
                else {
                    System.out.println("====================");
                    System.out.println("Производитель Ford: Делаю 1 машину.");
                    Thread.sleep(PRODUCTION_TIME);
                    cars.add(new Car());
                    System.out.println("Производитель Ford: 1 машина выпущена в продажу.");
                    System.out.println("====================");
                    notifyAll();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void sellCar(Thread thread) {
        try {
            while (true) {
                if (saleCount <= MAX_COUNT_OF_SALES) {
                    System.out.println(thread.getName() + " пришел в магазин.");
                    Thread.sleep(WAIT_TIME);
                    System.out.println(thread.getName() + ": Хочу купить машину!");
                    readyForBuy = true;
                    while (cars.size() == 0) {
                        System.out.println("Машин нет в наличии!");
                        wait();
                    }
                    Thread.sleep(SELL_TIME);
                    System.out.println("Покупателю " + thread.getName() + " продана 1 машина.");
                    saleCount++;
                    readyForBuy = false;
                    cars.remove(0);
                    notify();
                } else
                    break;
            }
            System.out.println("Достигнуто максимальное число продаж, магазин закрывается.");
            carDealershipIsOpen = false;
            Thread.interrupted();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

