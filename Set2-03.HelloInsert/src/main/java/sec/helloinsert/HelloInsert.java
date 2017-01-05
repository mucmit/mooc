package sec.helloinsert;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import org.h2.tools.RunScript;

public class HelloInsert {

    public static void main(String[] args) throws Exception {
        // Open connection to a database -- do not alter this code
        String databaseAddress = "jdbc:h2:file:./database";
        if (args.length > 0) {
            databaseAddress = args[0];
        }

        Connection connection = DriverManager.getConnection(databaseAddress, "sa", "");

        try {
            // If database has not yet been created, insert content
            RunScript.execute(connection, new FileReader("sql/database-schema.sql"));
            RunScript.execute(connection, new FileReader("sql/database-import.sql"));
        } catch (Throwable t) {
            System.err.println(t.getMessage());
        }

        // Add the code that first reads the agents from the database, then
        // asks for an agent (id and name) and stores the agent to the database.
        // Finally, the program prints the agents in the database again.

        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Agent");

        // Do something with the results
        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            System.out.println("Agents in database:");
            System.out.println(id + "\t" + name);
        }
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println(" ");
        System.out.println("Add one:");
        System.out.print("What id?");
        String id = scanner.nextLine();
        System.out.print("What name?");
        String name = scanner.nextLine();
        
        String query = "INSERT INTO Agent (id, name) VALUES ('" + id + "', '" + name + "')";
        connection.createStatement().executeUpdate(query);

        // Close the connection
        connection.close();
    }
}
