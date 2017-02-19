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

public class ChangePaper extends BaseRequestProcessor {

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String paper = req.getParameter("paper");
        String index = req.getParameter("index");
        String price = req.getParameter("price");

        int id = -1;
        try {
            id = Integer.parseInt(paper);
        } catch (NumberFormatException e) {

        }

        if (id != -1) {
            Connection conn = DataProvider.createConnection();

            try {
                String query = "UPDATE ГАЗЕТА SET ИНДЕКС = ?, ЦЕНА= ? where ID = ?";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setString(1, index);
                preparedStmt.setString(2, price);
                preparedStmt.setInt(3, id);

                preparedStmt.executeUpdate();
                conn.close();
            } catch (SQLException e) {
                throw new ServletException(e);
            }
        }

        doGet(req, resp);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> data = new HashMap<String, Object>();

        Map<Integer, String> papers = new TreeMap<>();

        StringBuilder papersData = new StringBuilder();

        DataProvider.listPapers((id, index, name, editor, price) -> {
            papers.put(id, name);
            papersData.append("{")
                    .append("'id': ").append(quote(Integer.toString(id))).append(",")
                    .append("'index': ").append(quote(index)).append(",")
                    .append("'price': ").append(quote(price))
                    .append("},\n");
        });

        data.put("papers_data", papersData.toString());
        data.put("papers", papers);

        processTemplate(response,"change-paper.ftl", data);
    }
}