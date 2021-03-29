import java.util.List;

public class Node {
    private final List<Double> attributesColumn; //attributes columns
    private final String nodeClassName; //decision column


    public Node(List<Double> attributesColumn,String irisClassName) {
        this.attributesColumn = attributesColumn;
        this.nodeClassName = irisClassName;
    }

    public List<Double> getAttributesColumn() {
        return attributesColumn;
    }

    public String getNodeClassName() {
        return nodeClassName;
    }

    @Override
    public String toString() {
        return "Node{" +
                "attributesColumn=" + attributesColumn +
                ", irisClassName='" + nodeClassName + '\'' +
                '}';
    }
}
