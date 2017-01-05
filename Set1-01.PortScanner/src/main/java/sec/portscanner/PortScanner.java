package sec.portscanner;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PortScanner {

    final static int MIN_PORT = 1024;
    final static int MAX_PORT = 49151;

    public static void main(String[] args) throws Exception {
        Scanner reader = new Scanner(System.in);

        System.out.println("Which address should I scan?");
        String address = reader.nextLine();
        System.out.println("Start at port?");
        int start = Integer.parseInt(reader.nextLine());
        System.out.println("End at port?");
        int end = Integer.parseInt(reader.nextLine());

        Set<Integer> ports = getAccessiblePorts(address, start, end);
        System.out.println("");

        if (ports.isEmpty()) {
            System.out.println("None found :(");
        } else {
            System.out.println("Found:");
            ports.stream().forEach(p -> System.out.println("\t" + p));
        }
    }

    public static Set<Integer> getAccessiblePorts(String address, int start, int end) {
        Set<Integer> accessiblePorts = new TreeSet<>();
        start = Math.max(start, MIN_PORT);
        end = Math.min(end, MAX_PORT);
       
        for (int check = start; check <= end; check++)
        {
            Socket socket = new Socket();
            try {
                socket.connect(new InetSocketAddress(address, check), 1000);
                socket.close();
                accessiblePorts.add(check);
            } catch (IOException ex) {
                Logger.getLogger(PortScanner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return accessiblePorts;
    }
}
