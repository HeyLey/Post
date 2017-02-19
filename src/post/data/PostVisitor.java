package post.data;


public interface PostVisitor {
    void visit(int id, String address, String number);

}
