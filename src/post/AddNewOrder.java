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

public class AddNewOrder extends BaseRequestProcessor {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String paper = req.getParameter("paper");
        String typography = req.getParameter("typography");
        String post = req.getParameter("post");
        String number = req.getParameter("number");

        Connection conn = DataProvider.createConnection();

        try {
            String query = "INSERT INTO РАСПРЕДЕЛЕНИЕ (ГАЗЕТА_ID, ОТДЕЛЕНИЕ_ID, ТИПОГРАФИЯ_ID, КОЛИЧЕСТВО) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, paper);
            stmt.setString(2, typography);
            stmt.setString(3, post);
            stmt.setString(4, number);

            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            throw new ServletException(e);
        }


        doGet(req, resp);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> data = new HashMap<String, Object>();

        Map<Integer, String> papers = new TreeMap<>();

        DataProvider.listPapers((id, index, name, editor, price) -> papers.put(id, name));

        Map<Integer, String> typography = new TreeMap<>();

        DataProvider.listTypography((id, address, name, open) -> typography.put(id, name));

        Map<Integer, String> post = new TreeMap<>();

        DataProvider.listPosts((id, address, number) -> post.put(id, number));

        data.put("papers", papers);
        data.put("typography", typography);
        data.put("post", post);

        processTemplate(response, "add-new-order.ftl", data);
    }
}