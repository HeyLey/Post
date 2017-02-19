package post.html;


import java.util.Objects;

public class TableBuilder {
    private StringBuilder builder = new StringBuilder();

    public TableBuilder() {

    }

    public void addHeader(String... columns) {
        builder.append("<tr>\n");
        for (String column : columns) {
            builder.append("<th>").append(column).append("</th>");
        }
        builder.append("</tr>\n");
    }

    public void addRow(Object... values) {
        builder.append("<tr>\n");
        for (Object value : values) {
            builder.append("<td>").append(value).append("</td>");
        }
        builder.append("</tr>\n");
    }

    @Override
    public String toString() {
        return "<table>\n" + builder.toString() + "</table>\n";
    }
}
