package Etudiants;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionEtudiants {
    Connection cn;

    public ConnectionEtudiants() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/livre", "root", "");
            System.out.println("Connection Etablie");
        } catch (Exception e) {
            System.out.println("Erreur de connexion");
            e.printStackTrace();
        }
    }
    public Connection maConnection() {
        return cn;
    }
}
