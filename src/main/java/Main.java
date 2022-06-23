public class Main {
    public static final int MAX_COUNT_OF_SALES = 10;
    public static volatile int count = 0;
    public static final int WAIT_TIME = 5000;
    public static void main(String[] args) throws InterruptedException {
        final СarDealership сarDealership = new СarDealership();

        // поток производителя машин, который ВЫПУСКАЕТ машины на продажу
        Thread dealer = new Thread(null, сarDealership::productionOfCars, "Производитель машин");
        dealer.setDaemon(true);
        dealer.start();

        // потоки покупателей, которые ПОКУПАЮТ машину
        for (int i = 4; i > 0; i--) {
            new Seller( "Покупатель " + i, сarDealership).start();
        }
    }
}
