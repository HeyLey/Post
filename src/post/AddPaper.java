package post;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import post.data.DataProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AddPaper extends BaseRequestProcessor {

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String index = request.getParameter("index");
        String name = request.getParameter("name");
        String editor = request.getParameter("editor");
        String price = request.getParameter("price");

        Connection conn = DataProvider.createConnection();

        try {
            String query = "INSERT INTO ГАЗЕТА (ИНДЕКС, НАЗВАНИЕ, ФИО_РЕДАКТОРА, ЦЕНА) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, index);
            preparedStmt.setString(2, name);
            preparedStmt.setString(3, editor);
            preparedStmt.setString(4, price);

            preparedStmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        Map<String, Object> data = new HashMap<>();

        processTemplate(response, "added-paper.ftl", data);
    }

}