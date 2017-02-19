package post;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import post.data.DataProvider;
import post.html.TableBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AllTypography extends BaseRequestProcessor {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, Object> data = new HashMap<>();

        data.put("table", createTableData());

        processTemplate(response, "all-typography.ftl", data);


    }

    private String createTableData() throws ServletException {
        final TableBuilder builder = new TableBuilder();

        builder.addHeader( "АДРЕС",  "НАЗВАНИЕ", "ОТКРЫТА");

        DataProvider.listTypography((id, address, name, open) -> {
            builder.addRow(address, name, open);
        });

        return builder.toString();
    }
}