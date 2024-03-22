public class Main {
    public static void main (String [] args) throws Exception {
        // Just pass control to Camel Main class.
        org.apache.camel.main.Main main = new org.apache.camel.main.Main (Main.class);
        main.run (args);
    }
}
