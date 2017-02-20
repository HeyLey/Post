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
Отчет должен содержать по каждой типографии следующие сведения:
общее количество печатающихся в типографии газет,
количество газет каждого наименования,
какие газеты и в каком количестве типография отправляет в каждое почтовое отделение.

 */

public class TypographyReport extends BaseRequestProcessor {


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> data = new HashMap<>();

        StringBuilder typographyReportData = new StringBuilder();

        Map<Integer, String> typography = new TreeMap<>();

        DataProvider.listTypography((id, address, name, open) -> typography.put(id, name));

        Connection conn = DataProvider.createConnection();

        for (Map.Entry<Integer, String> entry : typography.entrySet()) {
            Integer id = entry.getKey();
            String name = entry.getValue();

            typographyReportData.append("<h2>").append("Типография: ").append(name).append("</h2>");


            addNum(typographyReportData, conn, id);

            typographyReportData.append("<p>Количество газет каждого наименования</p>");

            addPapers(typographyReportData, conn, id);

            typographyReportData.append("<p>Какие газеты и в каком количестве типография отправляет в каждое почтовое отделение.</p>");

            addPosts(typographyReportData, conn, id);
        }

        try {
            conn.close();
        } catch (SQLException e) {
            throw new ServletException(e);
        }


        data.put("data", typographyReportData);

        processTemplate(response, "typography-report.ftl", data);
    }

    public void addPosts(StringBuilder typographyReportData, Connection conn, Integer id) throws ServletException {
        TableBuilder builder = new TableBuilder();
        builder.addHeader("ГАЗЕТА", "ОТДЕЛЕНИЕ","КОЛИЧЕСТВО");

        try {
            String query = "SELECT G.НАЗВАНИЕ, P.НОМЕР, SUM(КОЛИЧЕСТВО) FROM РАСПРЕДЕЛЕНИЕ JOIN ГАЗЕТА G ON ГАЗЕТА_ID=G.ID JOIN ОТДЕЛЕНИЕ_ПОЧТЫ P ON ОТДЕЛЕНИЕ_ID=P.ID WHERE ТИПОГРАФИЯ_ID = ? GROUP BY ГАЗЕТА_ID, ОТДЕЛЕНИЕ_ID;";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id);

            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                String paper = rs.getString(1);
                String post = rs.getString(2);
                String num = rs.getString(3);
                builder.addRow(paper, post, num);
            }
            rs.close();
            typographyReportData.append(builder);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        typographyReportData.append(builder);
    }

    public void addPapers(StringBuilder typographyReportData, Connection conn, Integer id) throws ServletException {
        TableBuilder builder = new TableBuilder();
        builder.addHeader("ГАЗЕТА", "КОЛИЧЕСТВО");

        try {
            String query = "SELECT G.НАЗВАНИЕ, SUM(КОЛИЧЕСТВО) FROM РАСПРЕДЕЛЕНИЕ JOIN ГАЗЕТА G ON ГАЗЕТА_ID=G.ID WHERE ТИПОГРАФИЯ_ID = ? GROUP BY ГАЗЕТА_ID";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id);

            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                String paper = rs.getString(1);
                String num = rs.getString(2);
                builder.addRow(paper, num);
            }
            rs.close();
            typographyReportData.append(builder);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    public void addNum(StringBuilder typographyReportData, Connection conn, Integer id) throws ServletException {
        try {
            String query = "SELECT SUM(КОЛИЧЕСТВО) FROM РАСПРЕДЕЛЕНИЕ WHERE ТИПОГРАФИЯ_ID = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1, id);

            ResultSet rs = preparedStmt.executeQuery();

            rs.next();
            String num = rs.getString(1);
            typographyReportData.append("<p>")
                    .append("Общее количество печатающихся в типографии газет: ").append(num)
                    .append("</p>");
            rs.close();

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }


}

