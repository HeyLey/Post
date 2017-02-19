package post.data;

import javax.servlet.ServletException;
import java.sql.*;


public class DataProvider {
    public static Connection createConnection() throws ServletException {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        String url = "jdbc:mysql://localhost:3306/post";
        String username = "ley";
        String password = "ley";

        System.out.println("Connecting database...");

        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new ServletException("Cannot connect the database!", e);
        }
    }

    public static void listTypography(TypographyVisitor visitor) throws ServletException {
        try {
            Connection connection = DataProvider.createConnection();
            Statement stmt = connection.createStatement();
            String sql = "SELECT ID, АДРЕС, НАЗВАНИЕ, ОТКРЫТА FROM ТИПОГРАФИЯ";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("ID");
                String address = rs.getString("АДРЕС");
                String name = rs.getString("НАЗВАНИЕ");
                boolean open = rs.getBoolean("ОТКРЫТА");

                visitor.visit(id, address, name, open);
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public static void listPapers(PaperVisitor visitor) throws ServletException {
        try {
            Connection connection = DataProvider.createConnection();
            Statement stmt = connection.createStatement();
            String sql = "SELECT ID, ИНДЕКС, НАЗВАНИЕ, ФИО_РЕДАКТОРА, ЦЕНА FROM ГАЗЕТА";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("ID");
                String index = rs.getString("ИНДЕКС");
                String name = rs.getString("НАЗВАНИЕ");
                String editor = rs.getString("ФИО_РЕДАКТОРА");
                String price = rs.getString("ЦЕНА");

                visitor.visit(id, index, name, editor, price);

            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public static void listPosts(PostVisitor visitor) throws ServletException {
        try {
            Connection connection = DataProvider.createConnection();
            Statement stmt = connection.createStatement();
            String sql = "SELECT ID, АДРЕС, НОМЕР FROM ОТДЕЛЕНИЕ_ПОЧТЫ";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("ID");
                String address = rs.getString("АДРЕС");
                String number = rs.getString("НОМЕР");

                visitor.visit(id, address, number);
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }
}
