package DatabaseFiles;

import Model.Usuario;
import org.neo4j.driver.*;

import java.time.LocalDate;

import static org.neo4j.driver.Values.parameters;

public class RedeSocial {

    public RedeSocial() {
    }

    Driver driver = GraphDatabase.driver("bolt://localhost:7687",
            AuthTokens.basic("neo4j", "654123"));

    Session session= driver.session();

    public void adicionaUsuario(Usuario usuario){

        Result result= session.run(
                "CREATE (p:Usuario{nome:$nome, email:$email})",
                parameters("nome",usuario.getNome(), "email", usuario.getEmail()));
    }

    public void fazerAmizade(String email1, String email2){

        Result result= session.run(
                "MATCH (u1:Usuario{email:$email}) OPTIONAL MATCH (u2:Usuario{email:$email2})" +
                 "CREATE (u1)-[:AMIGO{desde:$desde}]->(u2)",
                parameters("email", email1, "email2", email2,
                "desde", LocalDate.now()));
    }
    public void amigosSugeridos(String email){

        Result result= session.run(
                "MATCH (u1:Usuario{email:$email})-[:AMIGO]->(:Usuario)-[:AMIGO]->(u2:Usuario) RETURN u2.nome",
                parameters("email", email));
        //System.out.println(result.list().get(0));
        result.list().forEach(r -> System.out.println(r.get(0).asString()));
    }




    public void fechaConexao(){
        driver.close();
    }

    }









