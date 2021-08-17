package DatabaseFiles;

import Model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQL {
    public SQL() {
    }

    public void adicionaSQL(Connection conn, Usuario usuario) throws SQLException {
        PreparedStatement ps= null;
        ps= conn.prepareStatement(
                "INSERT INTO usuarios"
                        + "(nome, email)"
                        + "VALUES"
                        + "(?, ?)");

        ps.setString(1, usuario.getNome());
        ps.setString(2, usuario.getEmail());

        ps.executeUpdate();
    }

}
