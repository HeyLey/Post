package post.data;


public interface TypographyVisitor {
    void visit(int id, String address, String name, boolean open);
}
