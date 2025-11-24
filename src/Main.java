import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        System.out.println("Bem vindo ao programa para o EP2 de SO!\n");
        while(keepRunning) {
            // Menu inicial
            System.out.println("Quantidade de Leitores (reader):  ");
            int readerAmount = scanner.nextInt();
            System.out.println("Quantidade de Escritores (writer):  ");
            int writerAmount = scanner.nextInt();

            System.out.println("Escolha a opção que deseja executar:");
            System.out.println("1 - Com diferenciação entre Readers e Writers (writers bloqueiam a base para outros writers).\n2 - Sem diferenciação (qualquer acesso à base a bloqueia).");
            int resposta = scanner.nextInt();
            if(resposta!=1 && resposta!=2) {
                System.out.println("Opção inválida. Tente novamente.");
                continue;
            }
            
            if(resposta==1) {
                Manager.setUp("bd.txt", readerAmount, writerAmount);
                Manager.runWithRoles();
                Manager.displayTimes();
                Manager.cleanUp();
            }
            if(resposta==2) {
                Manager.setUp("bd.txt", readerAmount, writerAmount);
                Manager.runGeneric();
                Manager.displayTimes();
                Manager.cleanUp();
            }

            keepRunning = Manager.isAlive();
        }
        scanner.close();
    }
}
