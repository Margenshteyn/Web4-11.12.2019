package service;

import DAO.CarDao;
import model.Car;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.DBException;
import util.DBHelper;

import java.util.List;

public class CarService {

    private static CarService carService;

    private SessionFactory sessionFactory;

    private CarService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static CarService getInstance() {
        if (carService == null) {
            carService = new CarService(DBHelper.getSessionFactory());
        }
        return carService;
    }

 /*   public boolean addCar(String brand, String model, String licensePlate, Long price) {
            if (getCarsByBrand(brand).size() > 10) {
                return false;
            } else {
                Car car = new Car(brand, model, licensePlate, price);
                Long carId = new CarDao(sessionFactory.openSession()).addCar(car);
                return carId != -1;
            }
    }*/

    public boolean addCar(String brand, String model, String licensePlate, Long price) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            if (getCarsByBrand(brand).size() > 10) {
                return false;
            } else {
                Car car = new Car(brand, model, licensePlate, price);
                long carId = new CarDao(session).addCar(car);
                transaction.commit();
                return carId != -1;
            }
        } catch (HibernateException | DBException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Car> getCarsByBrand(String brand) throws DBException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            transaction.commit();
            return new CarDao(session).getCarsByBrand(brand);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public List<Car> getAllCars() throws DBException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            transaction.commit();
            return new CarDao(session).getAllCars();
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public long buyCar(String brand, String model, String licensePlate) throws DBException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            transaction.commit();
            return new CarDao(session).buyCar(brand, model, licensePlate);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

}
