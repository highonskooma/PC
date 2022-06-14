package com.company;

public class Run {
    private Menu menuInicial;
    private Login menuLogIn;
    private SignUp menuSignUp;

    public Run() {
        this.menuInicial = new Menu();
        this.menuLogIn = new Login();
        this.menuSignUp = new SignUp();
    }

    // Método que lê a opção pretendida do menu inicial e redireciona para essa função (login/signup/leaderbords)
    public void startInicial() {
        this.menuInicial.setOpcao(0);

        while (this.menuInicial.getOpcao() != 4) {
            System.out.print("\tTraz Aqui\n\n");
            this.menuInicial.mostrarMenuInicial();

            switch (this.menuInicial.getOpcao()) {
                case 1: // Log in
                    this.menuLogIn.startLogIn();
                    break;
                case 2: // Sign up
                    this.menuSignUp.startSignUp();
                    break;
                case 3: // Leaderboards
                    break;
                case 4: // Sair
                    break;
                default:
                    System.out.print("A opcão que escolheu é inválida.\n\n\n");
                    continue;
            }
        }
    }

    public static void main(String[] args) {
        new Run().startInicial();
    }
}
