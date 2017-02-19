package post;


import post.BaseRequestProcessor;
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

public class OpenCloseTypography extends BaseRequestProcessor {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        String typography = req.getParameter("typography");



        boolean open;

        if ("open".equals(action)) {
            open = true;
        } else if ("close".equals(action)) {
            open = false;
        } else {
            throw new ServletException("Unknown action");
        }

        if (!typography.equals("-1")) {
            Connection conn = DataProvider.createConnection();

            try {
                String query = "UPDATE ТИПОГРАФИЯ SET ОТКРЫТА = ? where ID = ?";
                PreparedStatement preparedStmt = conn.prepareStatement(query);
                preparedStmt.setBoolean(1, open);
                preparedStmt.setString(2, typography);
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

        data.put("typography", getTypographyMap());

        processTemplate(response, "open-close-typography.ftl", data);
    }
}