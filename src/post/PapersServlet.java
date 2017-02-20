package post;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PapersServlet extends HttpServlet {

    Map<String, BaseRequestProcessor> processorMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        processorMap.put("all-papers", new AllPapers());
        processorMap.put("change-paper", new ChangePaper());
        processorMap.put("paper-report", new PaperReport());
        processorMap.put("add-paper", new AddPaper());

        processorMap.put("all-typography", new AllTypography());
        processorMap.put("add-new-order", new AddNewOrder());
        processorMap.put("move-order", new MoveOrder());
        processorMap.put("open-close-typography", new OpenCloseTypography());
        processorMap.put("typography-report", new TypographyReport());

        processorMap.put("all-posts", new AllPosts());
        processorMap.put("change-papers-number", new ChangePapersNumber());

        processorMap.put("typography-for-paper", new TypographyForPaper());
        processorMap.put("editor-of-biggest-paper", new EditorOfBiggestPaper());
        processorMap.put("post-for-paper-by-price", new PostForPaperByPrice());
        processorMap.put("papers-with-low-number", new PapersWithLowNumber());
        processorMap.put("papers-and-address", new PapersWithAddress());

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        uri = uri.substring(6);
        BaseRequestProcessor baseRequestProcessor = processorMap.get(uri);
        if (baseRequestProcessor != null) {
            baseRequestProcessor.doPost(request, response);
        } else {
            throw new ServletException("URI: " + uri + " not supported");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        uri = uri.substring(6);
        BaseRequestProcessor baseRequestProcessor = processorMap.get(uri);
        if (baseRequestProcessor != null) {
            baseRequestProcessor.doGet(request, response);
        } else {
            throw new ServletException("URI: " + uri + " not supported");
        }
    }
}
