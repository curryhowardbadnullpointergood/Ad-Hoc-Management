package soton.ac.uk.seg.backend;

public class makeDatabase {

    public static void main(String[] args) {
        ClicksDatabase cdb = new ClicksDatabase(); 
        cdb.start();
        ServerDatabase sdb = new ServerDatabase(); 
        sdb.start();
        ImpressionDatabase idb = new ImpressionDatabase(); 
        idb.start();
      
    }
    
}