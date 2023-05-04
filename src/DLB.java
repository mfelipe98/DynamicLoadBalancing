import java.util.*;
import java.io.*;

public class DLB {
    
    public static void main(String[] args) throws IOException{

        int requests = 10;
        int simulations = 10;
        ArrayList<Peer> SuperPeerWeight = new ArrayList<Peer>();
        ArrayList<Double> totals = new ArrayList<Double>(Arrays.asList(0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0));
        FileWriter fw = new FileWriter("individuals.txt");
        BufferedWriter bw = new BufferedWriter(fw);

        for (int sims=0; sims<simulations; sims++){
            setUp(SuperPeerWeight);
            for (int request=0; request<requests; request++){
                int newRequest = getSearchLength();
                int assigningPeer = getRandom(0, 10);
                while (newRequest>0){
                    if (newRequest<5){
                        selfAssign(SuperPeerWeight, assigningPeer, newRequest/5.0);
                        newRequest=0;
                    }
                    else {
                        assign(SuperPeerWeight, 1);
                        newRequest-=5;
                    }
                }
            }
            for (int i=0; i<10; i++){
                Peer p = getPeer(SuperPeerWeight, i);
                bw.write(p.weight+"\t");
                totals.add(i,totals.get(i)+p.weight);
            }
            bw.write("\n");
        }
        bw.close();
        fw.close();
        
        fw = new FileWriter("totals.txt");
        bw = new BufferedWriter(fw);
        for (int i=0; i<10; i++){
            double printWeight = totals.get(i);
            bw.write("Super Peer "+i+": "+printWeight+"\n");
        }
        bw.close();
        fw.close();
    }

    public static void setUp(ArrayList<Peer> SuperPeerWeight){
        SuperPeerWeight.clear();
        SuperPeerWeight.add(new Peer(0,0));
        SuperPeerWeight.add(new Peer(1,0));
        SuperPeerWeight.add(new Peer(2,0));
        SuperPeerWeight.add(new Peer(3,0));
        SuperPeerWeight.add(new Peer(4,0));
        SuperPeerWeight.add(new Peer(5,0));
        SuperPeerWeight.add(new Peer(6,0));
        SuperPeerWeight.add(new Peer(7,0));
        SuperPeerWeight.add(new Peer(8,0));
        SuperPeerWeight.add(new Peer(9,0));
    }

    public static Peer getPeer(ArrayList<Peer> SuperPeerWeight, int i){
        for (Peer p : SuperPeerWeight){
            if (p.id == i){
                return p;
            }
        }
        return null;
    }

    public static void selfAssign(ArrayList<Peer> SuperPeerWeight, int assigningPeer, double weight){
        for (Peer p : SuperPeerWeight){
            if (p.id == assigningPeer){
                p.addWeight(weight);
                break;
            }
        }
    }

    public static void assign(ArrayList<Peer> SuperPeerWeight, double weight){
        Collections.sort(SuperPeerWeight);
        SuperPeerWeight.get(0).addWeight(weight);
    }

    public static int getRandom(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    public static double getRandom(double min, double max) {
        return (double) (Math.random() * (max - min) + min);
    }

    public static int getSearchLength() {
        double prob = Math.random();
        if (prob < 10.0/90.0) return 45;
        else if (prob < 20.0/90.0) return 40;
        else if (prob < 30.0/90.0) return 35;
        else if (prob < 40.0/90.0) return 30;
        else if (prob < 50.0/90.0) return 25;
        else if (prob < 60.0/90.0) return 20;
        else if (prob < 70.0/90.0) return 15;
        else if (prob < 80.0/90.0) return 10;
        else return 5;
    }

}

class Peer implements Comparable<Peer>{

    public int id;
    public double weight;

    public Peer(int id, double weight){
        this.id = id;
        this.weight = weight;
    }

    public void addWeight(double weight){
        this.weight += weight;
    }

    @Override
    public int compareTo(Peer p){
        return this.weight > p.weight ? 1 : (this.weight < p.weight ? -1 : 0);
    }
}