package post;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import post.data.DataProvider;
import post.html.TableBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class AllPosts extends BaseRequestProcessor {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        Configuration cfg = new Configuration();

        cfg.setClassForTemplateLoading(AllPosts.class, "templates");

        Template template = cfg.getTemplate("all-posts.ftl");

        Map<String, Object> data = new HashMap<>();

        data.put("table", createTableData());

        try {
            template.process(data, out);
        } catch (TemplateException e) {
            throw new ServletException("Template", e);
        }
    }

    private String createTableData() throws ServletException {
        TableBuilder builder = new TableBuilder();

        builder.addHeader("АДРЕС",  "НОМЕР");

        DataProvider.listPosts((id, address, number) -> {
            builder.addRow(address, number);
        });
        return builder.toString();
    }
}