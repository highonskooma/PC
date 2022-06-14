import java.util.*;

public class menusignup extends menu {
  
    public String nomeutilizador, passwd;
    private HashMap <String,String> logins = new HashMap <>();

    public menusignup() {
        super();
        this.nomeutilizador = new String();
        this.passwd = new String();
        this.logins = new HashMap <>();
    }

    public String getUsername() {
        return this.nomeutilizador;
    }

    public void mostrarMenuSignUpU() {
        System.out.println("Nome");
        this.nomeutilizador = leString();
        System.out.println("\npasswd");
        this.passwd = leString();
    
        logins.put(this.nomeutilizador,this.passwd);
    }

    public String leString() {
        Scanner in = new Scanner(System.in);
        String result = new String();
    
        try {
          result = in.nextLine();
        } catch (InputMismatchException e) {
          System.out.println("Não foi detetado nenhum input compatível com o pedido.");
        }
    
        return result;
    }

}
  