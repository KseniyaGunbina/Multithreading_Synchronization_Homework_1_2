public class Main {
    public static final int MAX_COUNT_OF_SALES = 10;
    public static volatile int count = 0;
    public static void main(String[] args) throws InterruptedException {
        final СarDealership сarDealership = new СarDealership();

        // поток производителя машин, который ВЫПУСКАЕТ машины на продажу
        //new Thread(null, сarDealership::productionOfCars, "Производитель машин").start();

        // потоки покупателей, которые ПОКУПАЮТ машину (приходят раз в 10 секунд)
        int wait = 10000;
        long start = System.currentTimeMillis();
        int sellerCount = 1;
        while (sellerCount <= 3) { // создаю трех покупателей
            if (sellerCount == 1) // убираю задержку при создании первого покупателя
                new Seller(("Покупатель " + sellerCount++), сarDealership).start();
            else if ((System.currentTimeMillis() - start) >= wait) // ослальные два создаются с интервалом wait
                new Seller(("Покупатель " + sellerCount++), сarDealership).start();
            else
                continue;
        }

        // поток производителя машин, который ВЫПУСКАЕТ машины на продажу
        Thread dealer = new Thread(null, сarDealership::productionOfCars, "Производитель машин");
        dealer.setDaemon(true);
        dealer.start();
    }
}
