import java.util.*;

/**
 * Created by Georg on 21/12/2016.
 */
public class AStar {
    //muutujad
    private char[][] labürint;
    private List<Node> closeSet;
    private List<Node> openSet;

    //konstruktor
    public AStar(char[][] labürint) {
        this.labürint = labürint;
        this.closeSet = new ArrayList<>();
        this.openSet = new ArrayList<>();
    }

    //meetodid
    public char[][] aStarFind(){
        char[][] lab = labürint.clone();
        int k = labürint.length; //kõrgus
        int l = labürint[0].length; //laius
        List<Node> tipud = new ArrayList<>();
        Node aNode = new Node();
        Node lNode = new Node();
        boolean algusolemas = false;
        boolean loppolemas = false;

        for (int i = 0; i < k; i++){    //kogu välja punktide otsimne ja lisamine suured listi, otsin ja leian ka alguse ja lõpu
            for (int j = 0; j < l; j++){
                if (labürint[i][j] == 'A'){
                    aNode.setX(j);
                    aNode.setY(i);
                    aNode.setNimi(labürint[i][j]);
                    tipud.add(aNode);
                    algusolemas = true;
                }else if (labürint[i][j] == 'L'){
                    lNode.setX(j);
                    lNode.setY(i);
                    lNode.setNimi(labürint[i][j]);
                    tipud.add(lNode);
                    loppolemas = true;
                }else if (labürint[i][j] == 'X'){
                    tipud.add(new Node(j,i, labürint[i][j]));
                }else {
                    tipud.add(new Node(j,i, labürint[i][j]));
                }
            }
        }

        if (algusolemas && loppolemas){ //algus ja lopp on olemas
            for (int i = aNode.getY() - 1; i < aNode.getY() + 2; i++){ //esmane punktide panemine openSet-i
                for (int j = aNode.getX() - 1; j < aNode.getX() + 2; j++){
                    if (i >= 0 && j >= 0 && i < k && j < l){
                        if (i != aNode.getY() || j != aNode.getX()){
                            if (labürint[i][j] != 'X'){
                                tipud.get(i * l + j).setG(Math.sqrt(Math.pow(aNode.getY() - i, 2)
                                        + Math.pow(aNode.getX() - j, 2)));
                                tipud.get(i * l + j).setH(Math.sqrt(Math.pow(lNode.getY() - i, 2)
                                        + Math.pow(lNode.getX() - j, 2)));
                                tipud.get(i * l + j).setEelane(aNode);
                                openSet.add(tipud.get(i * l + j));
                            }
                        }
                    }
                }
            }

            Collections.sort(openSet, new OpenSetComparator());
            closeSet.add(aNode);

            while (!openSet.isEmpty()){
                Node lähim = openSet.remove(0);

                if (lähim == lNode){    //lõpptingumus
                    break;
                }else {
                    if (!closeSet.contains(lähim)){//ei ole juba käidud punkt
                        for (int i = lähim.getY() - 1; i < lähim.getY() + 2; i++){
                            for (int j = lähim.getX() - 1; j < lähim.getX() + 2; j++){
                                if (i >= 0 && j >= 0 && i < k && j < l){//tabeli äär
                                    if (i != lähim.getY() || j != lähim.getX()){//lähim ise, hetke asukoht
                                        if (labürint[i][j] != 'X'){ //ei ole sein
                                            if (tipud.get(i * l + j).getG() == 0){ //varem külastamata
                                                tipud.get(i * l + j).setG(Math.sqrt(Math.pow(lähim.getY() - i, 2)
                                                        + Math.pow(lähim.getX() - j, 2)));
                                                tipud.get(i * l + j).setH(Math.sqrt(Math.pow(lNode.getY() - i, 2)
                                                        + Math.pow(lNode.getX() - j, 2)));
                                                tipud.get(i * l + j).setEelane(lähim);
                                                openSet.add(tipud.get(i * l + j));
                                            }else {
                                                double g = Math.sqrt(Math.pow(lähim.getY() - i, 2)
                                                        + Math.pow(lähim.getX() - j, 2));
                                                if (tipud.get(i * l + j).getG() > lähim.getG() + g){    //punkt on leitud, aga selle g on suurem kui lähima g + kaugus nende vahel
                                                    openSet.remove(tipud.get(i * l + j));   //eemaldan, et ei jääks tööjärjekorda nismo
                                                    tipud.get(i * l + j).setG(lähim.getG() + g);    //annan uue g väärtuse
                                                    tipud.get(i * l + j).setEelane(lähim);
                                                    openSet.add(tipud.get(i * l + j));  //panen tagasi tööjärjekorda
                                                }
                                            }
                                            Collections.sort(openSet, new OpenSetComparator());//sordin
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                closeSet.add(lähim);//lisan läbitud punkti
            }

            //Kuvamine
            Node otsinEelast = lNode;
            while (otsinEelast != aNode){
                //System.out.println(otsinEelast);
                otsinEelast = otsinEelast.getEelane();
                if (otsinEelast != aNode){
                    lab[otsinEelast.getY()][otsinEelast.getX()] = 'O';
                }
            }
            for (char[] c: lab){
                System.out.println(c);
            }
            System.out.println(closeSet.size());
        }

        return lab;
    }


    public char[][] breadthFirstFind(){
        char[][] lab = labürint.clone();
        int k = labürint.length; //kõrgus
        int l = labürint[0].length; //laius
        closeSet = new ArrayList<>();
        openSet = new ArrayList<>();
        List<Node> tipud = new ArrayList<>();
        Node aNode = new Node();
        Node lNode = new Node();
        boolean algusolemas = false;
        boolean loppolemas = false;

        for (int i = 0; i < k; i++){    //kogu välja punktide otsimne ja lisamine suured listi, otsin ja leian ka alguse ja lõpu
            for (int j = 0; j < l; j++){
                if (labürint[i][j] == 'A'){
                    aNode.setX(j);
                    aNode.setY(i);
                    aNode.setNimi(labürint[i][j]);
                    tipud.add(aNode);
                    algusolemas = true;
                }else if (labürint[i][j] == 'L'){
                    lNode.setX(j);
                    lNode.setY(i);
                    lNode.setNimi(labürint[i][j]);
                    tipud.add(lNode);
                    loppolemas = true;
                }else if (labürint[i][j] == 'X'){
                    tipud.add(new Node(j,i, labürint[i][j]));
                }else {
                    tipud.add(new Node(j,i, labürint[i][j]));
                }
            }
        }

        if (algusolemas && loppolemas){ //algus ja lopp on olemas
            for (int i = aNode.getY() - 1; i < aNode.getY() + 2; i++){ //esmane punktide panemine openSet-i
                for (int j = aNode.getX() - 1; j < aNode.getX() + 2; j++){
                    if (i >= 0 && j >= 0 && i < k && j < l){
                        if (i != aNode.getY() || j != aNode.getX()){
                            if (labürint[i][j] != 'X'){
                                tipud.get(i * l + j).setG(Math.sqrt(Math.pow(aNode.getY() - i, 2)
                                        + Math.pow(aNode.getX() - j, 2)));
                                tipud.get(i * l + j).setH(Math.sqrt(Math.pow(lNode.getY() - i, 2)
                                        + Math.pow(lNode.getX() - j, 2)));
                                tipud.get(i * l + j).setEelane(aNode);
                                openSet.add(tipud.get(i * l + j));
                            }
                        }
                    }
                }
            }

            closeSet.add(aNode);

            while (!openSet.isEmpty()){
                Node lähim = openSet.remove(0);
                if (lähim == lNode){    //lõpptingumus
                    break;
                }else {
                    if (!closeSet.contains(lähim)){//ei ole juba käidud punkt
                        for (int i = lähim.getY() - 1; i < lähim.getY() + 2; i++){
                            for (int j = lähim.getX() - 1; j < lähim.getX() + 2; j++){
                                if (i >= 0 && j >= 0 && i < k && j < l){//tabeli äär
                                    if (i != lähim.getY() || j != lähim.getX()){//lähim ise, hetke asukoht
                                        if (labürint[i][j] != 'X'){ //ei ole sein
                                            if (tipud.get(i * l + j).getG() == 0){ //varem külastamata
                                                tipud.get(i * l + j).setG(Math.sqrt(Math.pow(lähim.getY() - i, 2)
                                                        + Math.pow(lähim.getX() - j, 2)));
                                                tipud.get(i * l + j).setH(Math.sqrt(Math.pow(lNode.getY() - i, 2)
                                                        + Math.pow(lNode.getX() - j, 2)));
                                                tipud.get(i * l + j).setEelane(lähim);
                                                openSet.add(tipud.get(i * l + j));
                                            }/*else {
                                                double g = Math.sqrt(Math.pow(lähim.getY() - i, 2)
                                                        + Math.pow(lähim.getX() - j, 2));
                                                if (tipud.get(i * l + j).getG() > lähim.getG() + g){    //punkt on leitud, aga selle g on suurem kui lähima g + kaugus nende vahel
                                                    openSet.remove(tipud.get(i * l + j));   //eemaldan, et ei jääks tööjärjekorda nismo
                                                    tipud.get(i * l + j).setG(lähim.getG() + g);    //annan uue g väärtuse
                                                    tipud.get(i * l + j).setEelane(lähim);
                                                    openSet.add(tipud.get(i * l + j));  //panen tagasi tööjärjekorda
                                                }
                                            }*/
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                closeSet.add(lähim);//lisan läbitud punkti
            }
            System.out.println(closeSet.size());

        }
        return lab;
    }
    public char[][] depthFirstFind(){
        char[][] lab = labürint.clone();
        int k = labürint.length; //kõrgus
        int l = labürint[0].length; //laius
        closeSet = new ArrayList<>();
        openSet = new ArrayList<>();
        List<Node> tipud = new ArrayList<>();
        Node aNode = new Node();
        Node lNode = new Node();
        boolean algusolemas = false;
        boolean loppolemas = false;

        for (int i = 0; i < k; i++){    //kogu välja punktide otsimne ja lisamine suured listi, otsin ja leian ka alguse ja lõpu
            for (int j = 0; j < l; j++){
                if (labürint[i][j] == 'A'){
                    aNode.setX(j);
                    aNode.setY(i);
                    aNode.setNimi(labürint[i][j]);
                    tipud.add(aNode);
                    algusolemas = true;
                }else if (labürint[i][j] == 'L'){
                    lNode.setX(j);
                    lNode.setY(i);
                    lNode.setNimi(labürint[i][j]);
                    tipud.add(lNode);
                    loppolemas = true;
                }else if (labürint[i][j] == 'X'){
                    tipud.add(new Node(j,i, labürint[i][j]));
                }else {
                    tipud.add(new Node(j,i, labürint[i][j]));
                }
            }
        }

        if (algusolemas && loppolemas){ //algus ja lopp on olemas
            for (int i = aNode.getY() - 1; i < aNode.getY() + 2; i++){ //esmane punktide panemine openSet-i
                for (int j = aNode.getX() - 1; j < aNode.getX() + 2; j++){
                    if (i >= 0 && j >= 0 && i < k && j < l){
                        if (i != aNode.getY() || j != aNode.getX()){
                            if (labürint[i][j] != 'X'){
                                tipud.get(i * l + j).setG(Math.sqrt(Math.pow(aNode.getY() - i, 2)
                                        + Math.pow(aNode.getX() - j, 2)));
                                tipud.get(i * l + j).setH(Math.sqrt(Math.pow(lNode.getY() - i, 2)
                                        + Math.pow(lNode.getX() - j, 2)));
                                tipud.get(i * l + j).setEelane(aNode);
                                openSet.add(tipud.get(i * l + j));
                            }
                        }
                    }
                }
            }
            closeSet.add(aNode);

            while (!openSet.isEmpty()) {
                Node lähim = openSet.remove(0);

                if (lähim == lNode) {    //lõpptingumus
                    break;
                } else {
                    if (!closeSet.contains(lähim)) {//ei ole juba käidud punkt
                        for (int i = lähim.getY() - 1; i < lähim.getY() + 2; i++) {
                            for (int j = lähim.getX() - 1; j < lähim.getX() + 2; j++) {
                                if (i >= 0 && j >= 0 && i < k && j < l) {//tabeli äär
                                    if (i != lähim.getY() || j != lähim.getX()) {//lähim ise, hetke asukoht
                                        if (labürint[i][j] != 'X') { //ei ole sein
                                            if (tipud.get(i * l + j).getG() == 0) { //varem külastamata
                                                tipud.get(i * l + j).setG(Math.sqrt(Math.pow(lähim.getY() - i, 2)
                                                        + Math.pow(lähim.getX() - j, 2)));
                                                tipud.get(i * l + j).setH(Math.sqrt(Math.pow(lNode.getY() - i, 2)
                                                        + Math.pow(lNode.getX() - j, 2)));
                                                tipud.get(i * l + j).setEelane(lähim);
                                                openSet.add(0,tipud.get(i * l + j));
                                            } else {
                                                double g = Math.sqrt(Math.pow(lähim.getY() - i, 2)
                                                        + Math.pow(lähim.getX() - j, 2));
                                                if (tipud.get(i * l + j).getG() > lähim.getG() + g) {    //punkt on leitud, aga selle g on suurem kui lähima g + kaugus nende vahel
                                                    openSet.remove(tipud.get(i * l + j));   //eemaldan, et ei jääks tööjärjekorda nismo
                                                    tipud.get(i * l + j).setG(lähim.getG() + g);    //annan uue g väärtuse
                                                    tipud.get(i * l + j).setEelane(lähim);
                                                    openSet.add(0,tipud.get(i * l + j));  //panen tagasi tööjärjekorda
                                                }
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                closeSet.add(lähim);//lisan läbitud punkti
            }
            System.out.println(closeSet.size());
        }
        return lab;
    }

}

class OpenSetComparator implements Comparator<Node>{ //komparaator lähima leidmiseks

    @Override
    public int compare(Node n1, Node n2) {
        if (n1.getF() < n2.getF()){
            return -1;
        }else {
            if (n1.getF() > n2.getF()){
                return 1;
            }else {
                if (n1.getH() < n2.getH()){
                    return -1;
                }else {
                    if (n1.getH() > n2.getH()){
                        return 1;
                    }else {
                        return 0;
                    }
                }
            }
        }
    }
}
