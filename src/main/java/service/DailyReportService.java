package service;

import DAO.CarDao;
import DAO.DailyReportDao;
import model.DailyReport;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import util.DBException;
import util.DBHelper;

import java.util.List;

public class DailyReportService {

    private static DailyReportService dailyReportService;

    private SessionFactory sessionFactory;

    private DailyReportService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public static DailyReportService getInstance() {
        if (dailyReportService == null) {
            dailyReportService = new DailyReportService(DBHelper.getSessionFactory());
        }
        return dailyReportService;
    }

    public List<DailyReport> getAllDailyReports() throws DBException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            transaction.commit();
            return new DailyReportDao(session).getAllDailyReport();
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }


    public DailyReport getLastReport() throws DBException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            transaction.commit();
            return new DailyReportDao(session).getLastReport();
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public DailyReport getCurrentReport() throws DBException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            transaction.commit();
            return new DailyReportDao(session).getCurrentReport();
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public void toDelete() throws DBException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            transaction.commit();
            new DailyReportDao(session).toDelete();
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public DailyReport newReport() throws DBException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            transaction.commit();
            return new DailyReportDao(session).newReport();
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }

    public void insertToReport(long carPrice) throws DBException {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            transaction.commit();
            new DailyReportDao(session).insertToReport(carPrice);
        } catch (HibernateException e) {
            throw new DBException(e);
        }
    }
}
