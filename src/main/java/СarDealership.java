/*класс дилера машин, содержит 2 метода:  productionOfCars - метод производителя машин (работает в отдельном потоке),
* sellCar - метод продажи машин */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class СarDealership {

    private static final long SELL_TIME = 1000; // время покупки
    private static final long PRODUCTION_TIME = 2000; // время изготовления машины
    public static final long WAIT_TIME = 5000; // время ожидания (хождения покупателя)
    private static final int MAX_COUNT_OF_SALES = 10; // максимальное число покупок
    private static final Lock lock = new ReentrantLock();
    private static final Condition dealerCondition = lock.newCondition(); // Condition производителя
    private static final Condition sellerCondition = lock.newCondition(); // Condition покупателя
    private int saleCount = 0; // счетчик покупок
    private boolean readyForBuy = false; // флаг остановки выпуска машин по готовности покупателя совершить покупку
    private List<Car> cars = new ArrayList<>(); // склад машин

    public void productionOfCars() {
        while (saleCount <= MAX_COUNT_OF_SALES) {
            try {
                lock.lock();
                if (readyForBuy && cars.size() > 0) { // проверка ожидания покупателя, если кто-то готов купить машину, производство останавливается, работет только если склад пуст
                    dealerCondition.await();
                } else {
                    while (cars.size() < 2) {
                        System.out.println("====================");
                        System.out.println("Производитель Ford: Делаю 1 машину.");
                        Thread.sleep(PRODUCTION_TIME);
                        cars.add(new Car());
                        System.out.println("Производитель Ford: 1 машина выпущена в продажу.");
                        System.out.println("Машин на складе: " + cars.size());
                        System.out.println("====================");
                    }
                    sellerCondition.signalAll(); // уведомление ожидающих покупателей
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }
        Thread.interrupted();
    }

    public void sellCar(Thread thread) {

        int wait = 15000; // один и тот же покупатель может приходить с перерывом в 15 сек
        long start = System.currentTimeMillis();

        while (saleCount <= MAX_COUNT_OF_SALES) {
            if ((System.currentTimeMillis() - start) >= wait) {
                lock.lock();
                try {
                    System.out.println(thread.getName() + " пришел в магазин.\n");
                    Thread.sleep(WAIT_TIME);
                    System.out.println(thread.getName() + ": Хочу купить машину!");
                    readyForBuy = true;
                    while (cars.size() == 0) {
                        System.out.println("Машин нет в наличии!");
                        sellerCondition.await(); // ожидание машины
                    }
                    Thread.sleep(SELL_TIME);
                    System.out.println("Покупателю " + thread.getName() + " продана 1 машина.");
                    readyForBuy = false;
                    cars.remove(0);
                    saleCount++;
                    System.out.println("Машин на складе: " + cars.size());
                    System.out.println("Количество сделок: " + saleCount + "\n");
                    start = System.currentTimeMillis(); // обновляем время старта след.цикла
                    dealerCondition.signal(); // возобновление работы производителя
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            } else
                continue;
        }
        System.out.println("Достигнуто максимальное число продаж, магазин закрывается.");
        Thread.interrupted();
    }
}


