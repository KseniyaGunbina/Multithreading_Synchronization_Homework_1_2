public class Main {
    public static final int MAX_COUNT_OF_SALES = 10;
    public static volatile int count = 0;
    public static void main(String[] args) throws InterruptedException {
        final СarDealership сarDealership = new СarDealership();

        // потоки покупателей, которые ПОКУПАЮТ машину
        for (int i = 1; i < 4; i++) {
            new Seller( "Покупатель " + i, сarDealership).start();
            //seller.start();
        }

        // поток производителя машин, который ВЫПУСКАЕТ машины на продажу
        Thread dealer = new Thread(null, сarDealership::productionOfCars, "Производитель машин");
        dealer.setDaemon(true);
        dealer.start();
    }
}
