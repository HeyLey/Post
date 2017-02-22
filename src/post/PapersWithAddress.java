package post;


import post.data.DataProvider;
import post.html.TableBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


public class PapersWithAddress extends BaseRequestProcessor{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String paper = req.getParameter("paper");
        String typography = req.getParameter("typography");


        Connection conn = DataProvider.createConnection();

        TableBuilder builder = new TableBuilder();
        builder.addHeader("АДРЕС");

        try {
            String query = "SELECT O.АДРЕС FROM ЗАКАЗ R JOIN ОТДЕЛЕНИЕ_ПОЧТЫ O ON R.ОТДЕЛЕНИЕ_ID=O.ID WHERE R.ТИПОГРАФИЯ_ID = ? AND R.ГАЗЕТА_ID = ?;\n";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, paper);
            preparedStmt.setString(2, typography);

            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                String address = rs.getString(1);
                builder.addRow(address);
            };
            conn.close();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        Map<String, Object> data = new HashMap<>();

        data.put("table", builder.toString());
        putPapers(data);

        processTemplate(resp, "papers-and-address.ftl", data);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> data = new HashMap<>();

        putPapers(data);

        processTemplate(response, "papers-and-address.ftl", data);
    }

    void putPapers(Map<String, Object> data) throws ServletException {
        Map<Integer, String> papers = new TreeMap<>();

        DataProvider.listPapers((id, index, name, editor, price) -> papers.put(id, name));

        data.put("papers", papers);

        Map<Integer, String> typography = new TreeMap<>();

        DataProvider.listTypography((id, address, name, open) -> typography.put(id, address));

        data.put("typography", typography);

    }
}
