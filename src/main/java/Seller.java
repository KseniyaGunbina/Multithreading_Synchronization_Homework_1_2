public class Seller extends Thread {
    СarDealership carDealership;

    public Seller(String name, СarDealership carDealership) {
        super(name);
        this.carDealership = carDealership;
    }
    @Override
    public void run() {
        carDealership.sellCar(this);
    }
}
