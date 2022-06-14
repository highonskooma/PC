import java.util.*;

public class menu {
    private int opcao;
    private menulogin menuLogIn;
    private menusignup menuSignUp;

    public static void main(String[] args) {
        new menu().startMenu();
    }

    public menu() {
        this.opcao=0;
        this.menuLogIn = new menulogin();
        this.menuSignUp = new menusignup();
    }

    public int getOpcao() {
        return this.opcao;
    }

    public void setOpcao(int newOpcao) {
        this.opcao = newOpcao;
    }
    
    public int leOpcao() {
        Scanner in = new Scanner(System.in);
        int opcao = -1;

        try {
            opcao = in.nextInt();
        } catch (InputMismatchException e) {
            return Integer.MAX_VALUE;
        }

        return opcao;
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

    public void mostrarMenuInicial() {
        System.out.println("1: Log in");
        System.out.println("2: Sign up");
        System.out.println("3: Sair");
        System.out.println("\nNunca te esqueças que a tua primeira password é sempre 'TrazAqui'!!!");
    
        int opcaoAUX = leOpcao();
        if (opcaoAUX == Integer.MAX_VALUE) {
          clearScreen();
          System.out.println("Introduziu uma opcção inválida. Introduza apenas números.\n\n");
          System.out.print("\tTraz Aqui\n\n");
          mostrarMenuInicial();
        }
        else
          setOpcao(opcaoAUX);
      }
    
    public void startMenu() {
        this.setOpcao(0);
        this.nomeutilizador = new String();
        this.passwd = new String();
        this.logins = new HashMap <>();

        this.opcao=leOpcao();
        
        while (this.getOpcao() != 4) {
            System.out.print("\tBattleRoyale\n\n");
            this.mostrarMenuInicial();

            switch (this.getOpcao()) {
            case 1: // Log in
                this.clearScreen();
                startLogIn();

                break;
            case 2: // Sign up
                this.clearScreen();
                startSignUp();

                break;
            case 3: // Sair

                break;
            default:
                this.clearScreen();
                System.out.print("A opcão que escolheu é inválida.\n\n\n");

                continue;
            }
        }
    }

    public void startLogIn() {
        this.menuLogIn.clearScreen();
        this.menuLogIn.setOpcao(0);
        String Username = new String();
        String password = new String();
    
        //while (this.menuLogIn.getOpcao() != 5 && flagLogIn) {
        this.menuLogIn.clearScreen();
        //try {
            Username = this.menuLogIn.getNomeUtil();
            boolean codigoEX = this.userLogIn(Username);
        /*} catch (exUserDoesNotExist e) {
            this.menusignup.clearScreen();
            System.out.println(e.getMessage());
            System.out.print("A redireciona-lo para o menu inicial...\n\n\n");
            startMenu();
            return;
        }*/
    
        //try {
            password = this.menuLogIn.getPasswd();
            boolean passwordEX = this.pwVerificaC(Username, password);
        /*} catch (exPWIncorrect e) {
            this.menuLogIn.clearScreen();
            System.out.println(e.getMessage());
            System.out.print("A redireciona-lo para o menu inicial...\n\n\n");
            startMenu();
            return;
        }
        */

        this.nomeutilizador = Username;
        this.menuLogIn.clearScreen();
        //startpartida();
    }

    
    public void startSignUp() {
        this.menuSignUp.setOpcao(0);
        this.menuSignUp.clearScreen();
        String username;
        String passwd;
              
        this.menuSignUp.clearScreen();
        this.menuSignUp.mostrarMenuSignUpU();
    
        //try {
            username = this.menuSignUp.getUsername();
            //boolean userEX = ta.usernameSignUp(usename);
        /*} catch (exCodigoAlreadyExists e) {
            this.menuSignUp.clearScreen();
            System.out.println(e.getMessage());
            System.out.print("A redireciona-lo para o menu inicial...\n\n\n");
            startInicial();
            return;
        }*/
    
        //logins.put(u);
    
        menuSignUp.clearScreen();
        System.out.print("Registado com sucesso!!\nA redireciona-lo para página inicial...\n\n\n");
        startMenu();
        
    }
    
    
    public void mostrarMenuLogInC() {
        System.out.println("Username");
        this.nomeutilizador = leString();
    
        System.out.println("\nPassword");
        this.passwd = leString();
    }
    
    public boolean userLogIn(String username){
        for (String nome: this.logins.keySet())
            if (nome==username) //devia ser um equals secalhar
                return true;
        return false;
    }
    
    public boolean pwVerificaC(String username, String pw){
        //String user = this.logins.get(username);
        if (this.logins.get(username).equals(pw))
            return true;
        return false;
        }   

    public void clearScreen() {
        System.out.print('\u000C');
        System.out.flush();
    }
}