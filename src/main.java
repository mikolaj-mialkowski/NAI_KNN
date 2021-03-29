//s20635 Mikolaj Mialkowski C22 NAI lab(2)
//zakladam poprawnosc danych wejciowych (prawidlowy i staly rozmiar wektora, dwie klasy wejściowe)

import java.io.*;
import java.util.*;

public class main {
    public static void main(String[] args) throws IOException {

        List<Node> nodeTrainList;
        List<Node> nodeTestList;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Pass K:");
            int k = Integer.parseInt(bufferedReader.readLine());
            System.out.println("Pass train-set:");
            String trainSetAddress = bufferedReader.readLine();
            System.out.println("Pass test-set: [type \"MY OWN VECTOR\", if you want to pass custom vector]");
            String testSetAddress = bufferedReader.readLine();

            nodeTrainList = getNodeList(trainSetAddress);

            if (testSetAddress.equals("MY OWN VECTOR")) {
                while (true) {
                    System.out.println("Pass correct vector [split with \";\"]");

                    String line = bufferedReader.readLine();
                    line += ";[NO LABEL]";
                    List<Node> nodeSet = new ArrayList<>();
                    String[] tmp = line.split(";");
                    List<Double> attributesColumn = new ArrayList<>();

                    for (int i = 0; i < tmp.length - 1; i++)
                        attributesColumn.add(Double.parseDouble(tmp[i]));

                    nodeSet.add(new Node(attributesColumn, tmp[tmp.length - 1]));
                    nodeTestList = nodeSet;
                    doKNNAlgorithm(nodeTestList,nodeTrainList,k,false);
                }
            }
            else{
                nodeTestList = getNodeList(testSetAddress);
                doKNNAlgorithm(nodeTestList,nodeTrainList,k,true);
            }
    }

    public static List<Node> getNodeList(String fileAddress) throws IOException {
        String line;
        List<Node> nodeSet = new ArrayList<>();

        FileReader fileReader = new FileReader(fileAddress);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        while ((line = bufferedReader.readLine())!=null && (!line.equals(""))){
            String [] tmp = line.split(";");

            List<Double> attributesColumn = new ArrayList<>(); // JELISLI BLAD TUTAJ TO USUNAC OSTATNI PUSTY WIERSZ
            for (int i = 0; i < tmp.length-1 ; i++)
                attributesColumn.add(Double.parseDouble(tmp[i]));

            nodeSet.add(new Node(attributesColumn,tmp[tmp.length-1]));
        }
        return nodeSet;
    }

    public static double getDistanceBetweenNodes(Node node1, Node node2){
        if (node1 == null || node2 == null) {
            System.err.println("NULL!");
            return 2137;
        }
        double distance=0;
        for (int i = 0; i < node1.getAttributesColumn().size() ; i++) {
            distance += Math.pow(node1.getAttributesColumn().get(i)-node2.getAttributesColumn().get(i),2);
        }
        return distance;
    }

    public static String doKNNAlgorithm(List<Node> nodeTestList, List<Node> nodeTrainList, int k, boolean showAllInfo){

        String answer="";
        double correctAnswer=0;
        for (Node testedNode : nodeTestList) {

            List<DistanceBetween> distanceList = new ArrayList<>();

            for (Node trainedNode : nodeTrainList)
                distanceList.add(new DistanceBetween(testedNode, trainedNode, getDistanceBetweenNodes(testedNode, trainedNode)));

            Collections.sort(distanceList);

            double correctVectors = 0;
            List<String> stringList = new ArrayList<>();
            Set<String> stringSet = new HashSet<>();

            for (int j = 0; j < k; j++) {
                stringList.add(distanceList.get(j).getNodeTrain().getNodeClassName());
                stringSet.add(distanceList.get(j).getNodeTrain().getNodeClassName());

                if (distanceList.get(j).getNodeTrain().getNodeClassName().equals(testedNode.getNodeClassName()))
                    correctVectors++;
            }

            int max =0;

            for (String string :stringSet) {
                if (Collections.frequency(stringList,string)>max) {
                    max = Collections.frequency(stringList, string);
                    answer = string;
                }
            }

            if (answer.equals(testedNode.getNodeClassName()))
                correctAnswer++;

            System.out.println("\nK = " + k);
            System.out.println("K-NN ANSWER: " + answer);

            if (showAllInfo) {
                System.out.println("VECTOR COMPATIBILITY:  " + ((correctVectors / k) * 100) + "%");
                System.out.println("CORRECT ANSWER: " + testedNode.getNodeClassName());
            }
        }
        if (showAllInfo)
            System.out.println("----------------------------------------\nTOTAL ACCURACY: "+ (correctAnswer/nodeTestList.size())*100+"%\n----------------------------------------");

        return answer;
    }
}
