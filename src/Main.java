import java.util.Scanner;

import manager.Manager;

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

            for(int i=1; i<=50; i++) {
                long tempoInicial = System.currentTimeMillis();
                Manager.setUp("./database/bd.txt", readerAmount, writerAmount);
                Manager.run();
                Manager.cleanUp();
                long tempoFinal = System.currentTimeMillis();
                
                System.out.println("Tempo de execução (" + i + "): " + (tempoFinal - tempoInicial) + " ms\n");
            }
            System.out.println("Deseja executar novamente? (1 - Sim / 0 - Não)");
            int resposta = scanner.nextInt();
            if(resposta == 0)
                keepRunning = false;
        }
        scanner.close();
    }
}
