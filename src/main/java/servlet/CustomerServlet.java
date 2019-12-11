package servlet;

import com.google.gson.Gson;
import service.CarService;
import service.DailyReportService;
import util.DBException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String json = null;
        try {
            json = gson.toJson(CarService.getInstance().getAllCars());
        } catch (DBException e) {
            e.printStackTrace();
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(json);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        String licensePlate = req.getParameter("licensePlate");
        try {
            long carPrice = CarService.getInstance().buyCar(brand, model, licensePlate);
            if (carPrice != 0) {
                DailyReportService.getInstance().insertToReport(carPrice);
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
            resp.setContentType("text/html;charset=utf-8");
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
