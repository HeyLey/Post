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

/*
SELECT ФИО_РЕДАКТОРА FROM (SELECT SUM(КОЛИЧЕСТВО) S, R.ГАЗЕТА_ID FROM РАСПРЕДЕЛЕНИЕ R WHERE ON R.ТИПОГРАФИЯ_ID=2 GROUP BY ГАЗЕТА_ID ORDER BY S DESC) JOIN ГАЗЕТА G ON G.ID=ГАЗЕТА_ID WHERE ROWNUM <= 1;
 */

public class EditorOfBiggestPaper extends BaseRequestProcessor{
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String typography = req.getParameter("typography");

        Connection conn = DataProvider.createConnection();

        TableBuilder builder = new TableBuilder();
        builder.addHeader("ФИО РЕДАКТОРА", "ТИРАЖ");

        try {
            String query = "SELECT SUM(КОЛИЧЕСТВО) S, G.ФИО_РЕДАКТОРА FROM РАСПРЕДЕЛЕНИЕ R JOIN ГАЗЕТА G ON G.ID = R.ГАЗЕТА_ID WHERE R.ТИПОГРАФИЯ_ID=? GROUP BY ГАЗЕТА_ID ORDER BY S DESC LIMIT 1";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, typography);
            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                String num = rs.getString(1);
                String fio = rs.getString(2);
                builder.addRow(fio, num);
            };
            conn.close();
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        Map<String, Object> data = new HashMap<>();

        data.put("table", builder.toString());
        putTypography(data);

        processTemplate(resp, "editor-of-biggest-paper.ftl", data);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> data = new HashMap<>();

        putTypography(data);

        processTemplate(response, "editor-of-biggest-paper.ftl", data);
    }

    void putTypography(Map<String, Object> data) throws ServletException {
        Map<Integer, String> typography = new TreeMap<>();

        DataProvider.listTypography((id, address, name, open) -> typography.put(id, name));

        data.put("typography", typography);
    }
}
