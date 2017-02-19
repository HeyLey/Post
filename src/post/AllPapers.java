package post;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import post.data.DataProvider;
import post.html.TableBuilder;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.*;
import javax.servlet.http.*;

public class AllPapers extends BaseRequestProcessor {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        Configuration cfg = new Configuration();

        cfg.setClassForTemplateLoading(AllPapers.class, "templates");

        Template template = cfg.getTemplate("all-papers.ftl");

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

        builder.addHeader("ИНДЕКС", "НАЗВАНИЕ", "ФИО_РЕДАКТОРА", "ЦЕНА");

        DataProvider.listPapers((id, index, name, editor, price) -> {
            builder.addRow(index, name, editor, price);

        });

        return builder.toString();
    }
}