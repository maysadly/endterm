import java.util.ArrayList;
import java.util.List;

interface Vehicle {
    void rent();
}

class Car implements Vehicle {
    @Override
    public void rent() {
        System.out.println("Car rented.");
    }
}

class Motorcycle implements Vehicle {
    @Override
    public void rent() {
        System.out.println("Motorcycle rented.");
    }
}

class VehicleFactory {
    public static Vehicle createVehicle(String type) {
        switch (type.toLowerCase()) {
            case "car":
                return new Car();
            case "motorcycle":
                return new Motorcycle();
            default:
                throw new IllegalArgumentException("Invalid vehicle type");
        }
    }
}

interface PricingStrategy {
    double calculatePrice(int days);
}

class DailyPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(int days) {
        return days * 50; // $50 per day
    }
}

class WeeklyPricingStrategy implements PricingStrategy {
    @Override
    public double calculatePrice(int days) {
        return days / 7 * 200 + (days % 7) * 50;
    }
}

abstract class RentalFactory {
    abstract Vehicle createVehicle();
    abstract PricingStrategy createPricingStrategy();
}

class CarRentalFactory extends RentalFactory {
    @Override
    Vehicle createVehicle() {
        return new Car();
    }

    @Override
    PricingStrategy createPricingStrategy() {
        return new DailyPricingStrategy();
    }
}

class MotorcycleRentalFactory extends RentalFactory {
    @Override
    Vehicle createVehicle() {
        return new Motorcycle();
    }

    @Override
    PricingStrategy createPricingStrategy() {
        return new WeeklyPricingStrategy();
    }
}

interface RentalObserver {
    void notifyRented(Vehicle vehicle);
}

class EmailNotification implements RentalObserver {
    @Override
    public void notifyRented(Vehicle vehicle) {
        System.out.println("Email notification: " + vehicle.getClass().getSimpleName() + " rented.");
    }
}

public class Main {
    public static void main(String[] args) {
        RentalFactory carRentalFactory = new CarRentalFactory();
        RentalFactory motorcycleRentalFactory = new MotorcycleRentalFactory();

        Vehicle car = carRentalFactory.createVehicle();
        PricingStrategy carPricingStrategy = carRentalFactory.createPricingStrategy();

        Vehicle motorcycle = motorcycleRentalFactory.createVehicle();
        PricingStrategy motorcyclePricingStrategy = motorcycleRentalFactory.createPricingStrategy();

        car.rent();
        System.out.println("Car rental price: $" + carPricingStrategy.calculatePrice(3));

        motorcycle.rent();
        System.out.println("Motorcycle rental price: $" + motorcyclePricingStrategy.calculatePrice(7));

        List<RentalObserver> observers = new ArrayList<>();
        observers.add(new EmailNotification());

        for (RentalObserver observer : observers) {
            observer.notifyRented(car);
            observer.notifyRented(motorcycle);
        }
    }
}
