public class DistanceBetween implements Comparable<DistanceBetween> {

    @Override
    public int compareTo(DistanceBetween anotherNode) {
        return Double.compare(this.distance, anotherNode.distance);
    }

    private final Node nodeTest; //from test-set
    private final Node nodeTrain; //from train-set
    private final double distance; //exactly a sum o quarters of attributes

    public DistanceBetween(Node nodeTest, Node nodeTrain, double distance) {
        this.nodeTest = nodeTest;
        this.nodeTrain = nodeTrain;
        this.distance = distance;
    }

    public Node getNodeTrain() {
        return nodeTrain;
    }

    public double getDistance() {
        return distance;
    }

    public Node getNodeTest() {
        return nodeTest;
    }
}


