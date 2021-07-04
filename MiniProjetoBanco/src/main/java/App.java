import java.sql.*;
import java.util.Scanner;

public class App {

    public static void main(String[] args) throws SQLException {

        Usuario usuario= new Usuario();
        RascunhoCache rascunhoCache= new RascunhoCache();
        
        
//Aba de declaração de variáveis usadas no programa//

        ConFactory conFactory = new ConFactory();
        Connection conn= null;
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

        //Trecho de crud, onde o usuario pode se cadastrar ou recuperar rascunho//

        st= conn.createStatement();


        do {
           System.out.println("----------Menu------------");
           System.out.println("Digite 1 para se cadastrar: ");
           System.out.println("Digite 2 se ja possui cadastro e desejar ver seu rascunho salvo:");
           System.out.println("Digite 0 se desejar encerrar");

           n= sc.nextInt();
           sc.nextLine();
           String emailAux;
           String aux2 = null;

           switch (n){

               //o usuario insere seus dados, que são adicionados aos bancos//

               case 1:
                   System.out.println("Informe seu nome: ");
                   nome= sc.nextLine();
                   usuario.setNome(nome);

                   System.out.println("Informe seu email: ");
                   email= sc.nextLine();
                   usuario.setEmail(email);
                   
                   System.out.println("Digite sua publicação");
                   rasc= sc.nextLine();


                        ps= conn.prepareStatement(
                            "INSERT INTO usuarios"
                             + "(nome, email)"
                             + "VALUES"
                             + "(?, ?)");

                        ps.setString(1, usuario.getNome());
                        ps.setString(2, usuario.getEmail());

                       Rascunho rascunho= new Rascunho(usuario.getEmail());
                       rascunho.AdicionarRascunho(rasc);
                       rascunhoCache.AdicionaRascunhoCache(rascunho);
                        

                        ps.executeUpdate();

                        System.out.println("Conta criada com sucesso");
                   break;
//Os usuarios podem recuperar seu rascunho em ate 2 hrs, inserindo seu email;

               case 2:
                   System.out.println("Informe seu email: ");
                   emailBusca= sc.nextLine();

                 Rascunho rascunhoResult= rascunhoCache.buscaRascunho(emailBusca);
                 System.out.println(rascunhoResult);
                   break;
           }


       }while (n!=0);


    }

}
