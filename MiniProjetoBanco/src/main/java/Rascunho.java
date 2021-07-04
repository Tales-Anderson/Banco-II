public class Rascunho {


    private String email;
    private String texto;

    public Rascunho() {
    }

    public Rascunho(String email) {
        this.email = email;
    }

    public Rascunho(String email, String texto) {
        this.email = email;
        this.texto = texto;
    }

    public String AdicionarRascunho(String texto){
        return this.texto= texto;
    }

    public String getEmail() {
        return email;
    }

    public String getTexto() {
        return texto;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Rascunho{" +
                "email='" + email + '\'' +
                ", texto='" + texto + '\'' +
                '}';
    }
}
