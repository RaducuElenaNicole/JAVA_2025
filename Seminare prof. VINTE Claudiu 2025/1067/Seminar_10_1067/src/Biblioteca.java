import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Biblioteca {
    // Pentru situatia in care serverul Derby ruleaza in acceasi
    // masina virtuala cu aplicatia utilizator
    // folosim derby.jar
    private static String dbURL = "jdbc:derby:biblioteca;create=true";

    // Pentru situatia in care serverul Derby ruleaza pe o alta masina,
    // respectiv intr-o alta Java VM
    // folosim derbyclient.jar
//    private static String dbURL = "jdbc:derby://127.0.0.1:1527/biblioteca;create=true";

    private static String dbUser = "APP";
    private static String dbPassword = "APP";
    private static String dbName = "biblioteca";
    private static String tableName = "carte";

    // pentru interactiunea cu JDBC
    private static Connection conn;
    private static Statement sqlStmt;

    public static void main(String[] args) {
        Carte c1 = new Carte("Cota-0001", "Cel mai iubit dintre pamanteni",
                "Marin Preda", 1980);
        Carte c2 = new Carte("Cota-0003", "ActiveMQ in Action",
                "Bruce Snyder", 2016);
        Carte c3 = new Carte("Cota-0007", "Un veac de singuratate",
                "Gabriel Garcia Marquez", 1985);

        createConnection();
        dropTable();
        createTable();
        insertCarte(c1);
        insertCarte(c2);
        insertCarte(c3);
        updateCarte(c1);
        deleteCarte(c2);
        selectCarti();
        shutDown();
    }

    private static void createConnection() {
        try {
            conn = DriverManager.getConnection(dbURL);
            //  var conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            System.out.println("Conexiune realizata cu succes!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void dropTable() {
        sqlStmt = null;

        try {
            sqlStmt = conn.createStatement();
            sqlStmt.execute("DROP TABLE " + tableName);
            System.out.println("Tabela stearsa cu succes!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createTable() {
        // TODO
        sqlStmt = null;
        try {
            sqlStmt = conn.createStatement();
            sqlStmt.execute("CREATE TABLE " + tableName + "(cota varchar(16) primary key," +
                    "titlu varchar(64)," + "autori varchar(64)," + "an int)" );
            System.out.println("Tabela creata cu succes!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void insertCarte(Carte c) {

        //todo
        sqlStmt = null;
        try {
            sqlStmt = conn.createStatement();
            sqlStmt.execute("insert into " + tableName + " values ("
                            + "'" +c.getCota() + "'" + "," + "'" + c.getTitlu() + "'" +
                            "," + "'" + c.getAutori() + "'" +"," + c.getAn() +")"
                    );
            System.out.println("Tabela creata cu succes!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void updateCarte(Carte c) {

        // TODO
        sqlStmt = null;
        try {
            sqlStmt = conn.createStatement();
            sqlStmt.execute("update " + tableName +
                    " set cota = 'Cota-0002' where cota = " + "'" + c.getCota() +"'");

            System.out.println("Carte actualizata cu succes!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private static void deleteCarte(Carte c) {

        // TODO
        sqlStmt = null;
        try {
            sqlStmt = conn.createStatement();
            sqlStmt.execute("delete from " + tableName + " where an = " + c.getAn());

            System.out.println("Carte stearsa cu succes!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static  void selectCarti() {
        try {
            sqlStmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet resultSet = sqlStmt.executeQuery("select * from " + tableName +
                    " where cota like '%Cota%'");

            // Initial suntem pozitionati inainte de primul articol

            // Pentru a afla cate articole avem in cursor ne positionam pe ultimul articol
            resultSet.last();
            int nrArticole = resultSet.getRow();
            System.out.println("Au fost selectate " + nrArticole +
                    " articole din tabela " + tableName);

            ResultSetMetaData metaData = resultSet.getMetaData();
            int nrColoane = metaData.getColumnCount();

            // Tiparire la consola a header-ului tabelei carte

            // TODO
            //coloanele sunt numerotate de la 1

            for(int i=1;i<=nrColoane;i++)
                System.out.printf("%-25s", metaData.getColumnName(i));
            System.out.println();
            resultSet.beforeFirst();
            while (resultSet.next()) {

                Carte cNou = new Carte(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString("autori"),
                        resultSet.getInt(4));
                System.out.println(cNou.toString());
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void shutDown() {
        try {
            if (sqlStmt != null)
                sqlStmt.close();
            if (conn != null)
                conn.close();
            System.out.println("Conexiune la DB inchisa cu succes!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
