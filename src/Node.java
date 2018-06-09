/**
 * Created by Georg on 21/12/2016.
 */
public class Node {
    //muutujad
    private double g; //movement cost
    private double h; //distant from target node
    private double f; //f näitab mis on järgmine, kõige lühem maa "avastatud" ruutudest, f = g + h
    private Node eelane;
    private int x; //x cord
    private int y; //y cord
    private char nimi;

    //konstuktorid

    public Node() {
    }

    public Node(int x, int y, char nimi) {
        this.x = x;
        this.y = y;
        this.nimi = nimi;

    }

    //Get, set

    public double getG() {
        return g;
    }

    public double getH() {
        return h;
    }

    public double getF() {
        f = g + h;
        return f;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Node getEelane() {
        return eelane;
    }

    public void setG(double g) {
        this.g = g;
    }

    public void setH(double h) {
        this.h = h;
    }

    public void setEelane(Node eelane) {
        this.eelane = eelane;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setNimi(char nimi) {
        this.nimi = nimi;
    }

    //toString
    @Override
    public String toString() {

        if (eelane == null){
            return nimi + ", g=" + g + ", h=" + h + ", f=" + f + ", eelane=null";
        }else {
            return nimi + ", g=" + g + ", h=" + h + ", f=" + f + ", eelane=(" + eelane.getX() + "," + eelane.getY() + ")";
        }


    }
}
