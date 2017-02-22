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


public class PostForPaperByPrice extends BaseRequestProcessor{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String price = req.getParameter("price");

        Connection conn = DataProvider.createConnection();

        TableBuilder builder = new TableBuilder();
        builder.addHeader("АДРЕС");

        try {
            String query = "SELECT DISTINCT T.АДРЕС FROM ТИПОГРАФИЯ T JOIN ЗАКАЗ R ON T.ID=R.ТИПОГРАФИЯ_ID JOIN ГАЗЕТА G ON R.ГАЗЕТА_ID=G.ID WHERE G.ЦЕНА > ?;";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, price);
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
        processTemplate(resp, "post-for-paper-by-price.ftl", data);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> data = new HashMap<>();
        processTemplate(response, "post-for-paper-by-price.ftl", data);
    }
}
