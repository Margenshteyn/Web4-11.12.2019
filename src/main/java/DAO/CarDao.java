package DAO;

import model.Car;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

public class CarDao {

    private Session session;

    public CarDao(Session session) {
        this.session = session;
    }

    public List<Car> getCarsByBrand(String brand) {
        Query query = session.createQuery("FROM Car where brand = :paramName");
        query.setParameter("paramName", brand);
        return query.getResultList();
    }

    public long addCar(Car car) {
        try {
            Car findCar = findCar(car.getBrand(), car.getModel(), car.getLicensePlate());
            if (findCar != null) {
                return -1;
            } else {
                return (Long) session.save(car);
            }
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            return -1;
        }
    }

    private Car findCar(String brand, String model, String licensePlate) {
        Query query = session.createQuery("FROM Car where brand = :brand and model = :model and licensePlate = :licensePlate");
        query.setParameter("brand", brand).setParameter("model", model).setParameter("licensePlate", licensePlate);
        return (Car) (query.getResultList().size() > 0 ? query.getResultList().get(0) : null);
    }

    public List<Car> getAllCars() {
//        return session.createQuery("From Car").getResultList();
        return session.createQuery("From Car").list();
    }

    public long buyCar(String brand, String model, String licensePlate) {
        Car findCar = findCar(brand, model, licensePlate);
        if (findCar != null) {
            long price = findCar.getPrice();
            session.delete(findCar);
            return price;
        } else {
            return 0;
        }
    }

   /* public Long insertCar(String brand, String model, String licensePlate, Long price) {
        return (Long) session.save(new Car(brand, model, licensePlate, price));
    }*/
}
