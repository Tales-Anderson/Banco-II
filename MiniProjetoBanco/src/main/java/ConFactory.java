import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConFactory {

    public Connection getConnection() throws ClassNotFoundException, SQLException {

        Class.forName("org.postgresql.Driver");

        return DriverManager.getConnection("jdbc:postgresql://localhost:5433/ProjetoRedis",
                "postgres", "tales");

    }

}
