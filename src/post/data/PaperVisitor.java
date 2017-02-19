package post.data;


public interface PaperVisitor {
    void visit(int id, String index, String name, String editor, String price);
}
