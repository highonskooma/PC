import java.util.*;

public class menulogin extends menu{
    public String nomeutilizador, passwd;
    private HashMap <String,String> logins = new HashMap <>();

    public menulogin() {
        super();
        this.nomeutilizador = new String();
        this.passwd = new String();
        this.logins = new HashMap <>();
    }

    public menulogin(String nomeutil,String pass) {
        this.nomeutilizador = nomeutil;
        this.passwd = pass;
        this.logins = new HashMap <String,String>();
    }

    public void setNome(String nomeutil) {
        this.nomeutilizador = nomeutil;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setFicheiro(HashMap fic) {
        this.logins=fic;
    }

    public String getNomeUtil() {
        return nomeutilizador;
    }

    public String getPasswd() {
        return this.passwd;
    }

}