package post;


import post.data.DataProvider;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.*;
import javax.servlet.http.*;

public class MoveOrder extends BaseRequestProcessor {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String paper = req.getParameter("paper");
        String typography1 = req.getParameter("typography1");
        String typography2 = req.getParameter("typography2");
        String post = req.getParameter("post");
        String number = req.getParameter("number");

        Connection conn = DataProvider.createConnection();

        try {
            String query1 = "UPDATE ЗАКАЗ SET КОЛИЧЕСТВО = КОЛИЧЕСТВО - ? where ГАЗЕТА_ID = ? and ОТДЕЛЕНИЕ_ID = ? and ТИПОГРАФИЯ_ID = ?";
            PreparedStatement preparedStmt1 = conn.prepareStatement(query1);
            preparedStmt1.setString(1, number);
            preparedStmt1.setString(2, paper);
            preparedStmt1.setString(3, post);
            preparedStmt1.setString(4, typography1);
            preparedStmt1.executeUpdate();

            String query2 = "UPDATE ЗАКАЗ SET КОЛИЧЕСТВО = КОЛИЧЕСТВО + ? where ГАЗЕТА_ID = ? and ОТДЕЛЕНИЕ_ID = ? and ТИПОГРАФИЯ_ID = ?";
            PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
            preparedStmt2.setString(1, number);
            preparedStmt2.setString(2, paper);
            preparedStmt2.setString(3, post);
            preparedStmt2.setString(4, typography2);
            preparedStmt2.executeUpdate();

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

        processTemplate(response, "move-order.ftl", data);
    }
}