package servlet;

import com.google.gson.Gson;
import service.DailyReportService;
import util.DBException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DailyReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String json = "";
        if (req.getPathInfo().contains("all")) {
            try {
                json = gson.toJson(DailyReportService.getInstance().getAllDailyReports());
            } catch (DBException e) {
                e.printStackTrace();
            }
        } else if (req.getPathInfo().contains("last")) {
            try {
                json = gson.toJson(DailyReportService.getInstance().getLastReport());
            } catch (DBException e) {
                e.printStackTrace();
            }
        }
        resp.setContentType("text/html;charset=utf-8");
        resp.getWriter().println(json);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            DailyReportService.getInstance().toDelete();
            resp.setContentType("text/html;charset=utf-8");
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
