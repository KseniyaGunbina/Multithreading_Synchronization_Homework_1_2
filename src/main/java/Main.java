public class Main {
    public static void main(String[] args) throws InterruptedException {
        final СarDealership сarDealership = new СarDealership();

        // потоки покупателей, которые ПОКУПАЮТ машину
        for (int i = 1; i < 4; i++) {
            Thread seller = new Seller(("Покупатель " + i), сarDealership);
            seller.start();
        }

        // поток производителя машин, который ВЫПУСКАЕТ машины на продажу
        new Thread(null, сarDealership::productionOfCars, "Производитель машин").start();



    }
}
