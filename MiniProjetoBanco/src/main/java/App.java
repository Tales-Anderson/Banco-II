
import java.sql.*;
import java.util.Scanner;



public class App {

    public static void main(String[] args) throws SQLException {

//Aba de declaração de variáveis usadas no programa//
        Usuario usuario= new Usuario();
        RascunhoCache rascunhoCache= new RascunhoCache();
        ConFactory conFactory = new ConFactory();
        Connection conn= null;
        MongoConnection mongoConnection= new MongoConnection();
        SQL sql= new SQL();
        int n;
        String nome;
        String email;
        String emailBusca;
        String rasc;
        Scanner sc= new Scanner(System.in);
        Statement st= null;
        PreparedStatement ps= null;

//Trecho de conexão ao PostgreSql//

        try {
            conn = conFactory.getConnection();

            if(conn != null){
                System.out.println("Conectado!");
            }else{
                System.out.println("Não conectado!");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        //Trecho de crud, onde o usuario pode se cadastrar, recuperar rascunho e fazer postagens//

        do {
           System.out.println("----------Menu------------");
           System.out.println("Digite 1 para se cadastrar: ");
           System.out.println("Digite 2 se ja possui cadastro e desejar ver seu rascunho salvo:");
           System.out.println("Digite 3 para realizar uma postagem: ");
           System.out.println("Digite 4 para ver suas postagens: ");
           System.out.println("Digite 0 se desejar encerrar");

           n= sc.nextInt();
           sc.nextLine();
           String emailAux;
           String aux2 = null;
           String titulo;
           String texto;

         switch (n){

               //o usuario insere seus dados, que são adicionados aos bancos//

               case 1:
                   System.out.println("Informe seu nome: ");
                   nome= sc.nextLine();
                   usuario.setNome(nome);

                   System.out.println("Informe seu email: ");
                   email= sc.nextLine();
                   usuario.setEmail(email);

                   System.out.println("Digite seu rascunho a ser salvo");
                   rasc= sc.nextLine();


                        sql.AdicionaSQL(conn, usuario);

                        Rascunho rascunho= new Rascunho(usuario.getEmail());
                        rascunho.AdicionarRascunho(rasc);
                        rascunhoCache.AdicionaRascunhoCache(rascunho);

                        System.out.println("Conta criada com sucesso");
                   break;
//Os usuarios podem recuperar seu rascunho em ate 2 hrs, inserindo seu email;

               case 2:
                   System.out.println("Informe seu email: ");
                   emailBusca= sc.nextLine();

                 Rascunho rascunhoResult= rascunhoCache.buscaRascunho(emailBusca);
                 System.out.println(rascunhoResult);
                   break;

             case 3:
                 System.out.println("Informe seu email: ");
                 email= sc.nextLine();

                 System.out.println("Carregando...");

                 System.out.println("Digite o titulo da sua postagem: ");
                 titulo= sc.nextLine();
                 System.out.println("Escreva usa postagem");
                 texto= sc.nextLine();

                 Postagem postagem= new Postagem(titulo, texto, email);
                 mongoConnection.AdicionaPostagem(postagem);

                 break;

             case 4:
                 System.out.println("Informe seu email: ");
                 email= sc.nextLine();
                 mongoConnection.BuscaPostagem(email);
                 break;
               }

       }while (n!=0);

        mongoConnection.Encerra();
    }

}
