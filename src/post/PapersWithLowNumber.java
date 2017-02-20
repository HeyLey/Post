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


public class PapersWithLowNumber extends BaseRequestProcessor{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String number = req.getParameter("number");

        Connection conn = DataProvider.createConnection();

        TableBuilder builder = new TableBuilder();
        builder.addHeader("НАЗВАНИЕ", "НОМЕР ОТДЕЛЕНИЯ");

        try {
            String query = "SELECT G.НАЗВАНИЕ, O.НОМЕР FROM ГАЗЕТА G JOIN РАСПРЕДЕЛЕНИЕ R ON G.ID=R.ГАЗЕТА_ID JOIN ОТДЕЛЕНИЕ_ПОЧТЫ O ON O.ID=R.ОТДЕЛЕНИЕ_ID WHERE R.КОЛИЧЕСТВО < ?";;
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, number);
            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString(1);
                String num = rs.getString(2);
                builder.addRow(name, num);
            }
            conn.close();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("table", builder.toString());
        processTemplate(resp, "papers-with-low-number.ftl", data);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> data = new HashMap<>();
        processTemplate(response, "papers-with-low-number.ftl", data);
    }
}
