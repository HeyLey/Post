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
import java.util.Map;
import java.util.TreeMap;

public class BaseRequestProcessor {

    PrintWriter getResponseWriter(HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        return response.getWriter();
    }

    void processTemplate(HttpServletResponse response,
                         String templateFile,
                         Map<String, Object> data) throws IOException, ServletException {
        try {
            PrintWriter out = getResponseWriter(response);
            Configuration cfg = new Configuration();

            cfg.setClassForTemplateLoading(AllPapers.class, "templates");

            Template template = cfg.getTemplate(templateFile);

            template.process(data, out);
        } catch (TemplateException e) {
            throw new ServletException("Template", e);
        }
    }

    String quote(String str) {
        return "'" + str + "'";
    }

    Map<Integer, String> getTypographyMap() throws ServletException {
        Map<Integer, String> typography = new TreeMap<>();

        DataProvider.listTypography((id, address, name, open) -> typography.put(id, name));
        return typography;
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
