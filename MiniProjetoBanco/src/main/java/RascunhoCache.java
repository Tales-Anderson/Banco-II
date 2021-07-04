import com.google.gson.Gson;
import redis.clients.jedis.Jedis;

public class RascunhoCache {

    Jedis jedis= new Jedis();
    Gson gson= new Gson();

    public boolean AdicionaRascunhoCache(Rascunho rascunho){
        if(jedis.setex(rascunho.getEmail(), 7200l,gson.toJson(rascunho)).equals("OK")) return true;
        return false;
    }

    public Rascunho buscaRascunho(String email){
        String json= jedis.get(email);
        if(json==null){

            System.out.println("Usuario inexistente!");
            return null;
        }
        else{
            return gson.fromJson(json, Rascunho.class);
        }

    }
}
