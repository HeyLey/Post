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

public class TypographyForPaper extends BaseRequestProcessor{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String paper = req.getParameter("paper");

        Connection conn = DataProvider.createConnection();

        TableBuilder builder = new TableBuilder();
        builder.addHeader("АДРЕС");

        try {
            String query = "SELECT DISTINCT T.АДРЕС FROM ЗАКАЗ R JOIN ТИПОГРАФИЯ T ON T.ID=R.ТИПОГРАФИЯ_ID WHERE R.ГАЗЕТА_ID=?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, paper);
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

        processTemplate(resp, "typography-for-paper.ftl", data);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> data = new HashMap<>();

        putPapers(data);

        processTemplate(response, "typography-for-paper.ftl", data);
    }

    void putPapers(Map<String, Object> data) throws ServletException {
        Map<Integer, String> papers = new TreeMap<>();

        DataProvider.listPapers((id, index, name, editor, price) -> papers.put(id, name));

        data.put("papers", papers);
    }
}
