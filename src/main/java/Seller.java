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
                    System.out.println("Количество продаж: " + Main.count + "\n");
                } else break;
            }
            System.out.println("Достигнуто максимальное число продаж.");
    }
}

