package post;


import post.data.DataProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ChangePapersNumber extends BaseRequestProcessor{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String paper = req.getParameter("paper");
        String typography = req.getParameter("typography");
        String post = req.getParameter("post");
        String number = req.getParameter("number");

        Connection conn = DataProvider.createConnection();

        try {
            String query = "UPDATE ЗАКАЗ SET КОЛИЧЕСТВО = ? where ГАЗЕТА_ID = ? AND ОТДЕЛЕНИЕ_ID = ? AND ТИПОГРАФИЯ_ID = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, number);
            preparedStmt.setString(2, paper);
            preparedStmt.setString(3, post);
            preparedStmt.setString(4, typography);

            preparedStmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        doGet(req, resp);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> data = new HashMap<>();

        Map<Integer, String> papers = new TreeMap<>();

        DataProvider.listPapers((id, index, name, editor, price) -> papers.put(id, name));

        Map<Integer, String> typography = new TreeMap<>();

        DataProvider.listTypography((id, address, name, open) -> typography.put(id, name));

        Map<Integer, String> post = new TreeMap<>();

        DataProvider.listPosts((id, address, number) -> post.put(id, number));

        data.put("papers", papers);
        data.put("typography", typography);
        data.put("post", post);

        processTemplate(response, "change-papers-number.ftl", data);
    }
}
