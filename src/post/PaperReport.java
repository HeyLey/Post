package post;

import post.data.DataProvider;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class PaperReport extends BaseRequestProcessor {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, Object> data = new HashMap<String, Object>();

        Map<Integer, String> papers = new TreeMap<>();

        StringBuilder papersData = new StringBuilder();

        DataProvider.listPapers((id, index, name, editor, price) -> {
            papers.put(id, name);
            papersData.append("{")
                    .append("'id': ").append(quote(Integer.toString(id))).append(",")
                    .append("'name': ").append(quote(name)).append(",")
                    .append("'index': ").append(quote(index)).append(",")
                    .append("'price': ").append(quote(price))
                    .append("},\n");
        });

        data.put("papers_data", papersData.toString());
        data.put("papers", papers);

        processTemplate(response, "paper-report.ftl", data);
    }
}