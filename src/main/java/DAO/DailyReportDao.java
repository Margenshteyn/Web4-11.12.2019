package DAO;

import model.DailyReport;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class DailyReportDao {

    private Session session;

    public DailyReportDao(Session session) {
        this.session = session;
    }

 /*   public List<DailyReport> getAllDailyReport() {
        Transaction transaction = session.beginTransaction();
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        transaction.commit();
        session.close();
        return dailyReports;
    }*/

    public List<DailyReport> getAllDailyReport() {
        return session.createQuery("FROM DailyReport").list();
    }

    public DailyReport getLastReport() {
        List<DailyReport> dailyReports = getAllDailyReport();
        if (dailyReports.size() < 2) {
            return null;
        } else {
            return dailyReports.get(dailyReports.size() - 2);
        }
    }

    public DailyReport getCurrentReport() {
        List<DailyReport> dailyReports = getAllDailyReport();
        if (dailyReports.size() == 0) {
            DailyReport firstReport = newReport();
            dailyReports.add(firstReport);
        }
        return dailyReports.get(dailyReports.size() - 1);
    }

    public DailyReport newReport() {
        DailyReport newReport = new DailyReport(0L, 0L);
        session.save(newReport);
        return newReport;
    }

    public void toDelete() {
        session.createQuery("delete from Car").executeUpdate();
        session.createQuery("delete from DailyReport").executeUpdate();
    }

    public void insertToReport(long carPrice) {
        List<DailyReport> dailyReports = session.createQuery("FROM DailyReport").list();
        if (dailyReports.size() == 0) {
            session.save(new DailyReport(carPrice, 1L));
        } else {
            DailyReport newReport = dailyReports.get(dailyReports.size() - 1);
            newReport.setEarnings(newReport.getEarnings() + carPrice);
            newReport.setSoldCars(newReport.getSoldCars() +1);
        }
    }
}
