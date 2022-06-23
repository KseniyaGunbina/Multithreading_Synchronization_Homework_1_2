public class Seller extends Thread {
    СarDealership carDealership;
    public Seller(String name, СarDealership carDealership) {
        super(name);
        this.carDealership = carDealership;
    }
    @Override
    public void run() {
        while (true) {
            if ((Main.count <= Main.MAX_COUNT_OF_SALES)) {
                carDealership.sellCar();
                try {
                    Thread.sleep(Main.WAIT_TIME);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else break;
        }
    }
}