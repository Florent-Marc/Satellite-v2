package be.afhistos.satellitev2.consoleUtils;

import be.afhistos.satellitev2.Satellite;

import java.util.Scanner;

public class ConsoleThread extends Thread{
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void run() {
        while (scanner.hasNext()){
            if(scanner.next().equalsIgnoreCase("stop")){
                System.out.println("Arrêt en cours...");
                scanner.close();
                Satellite.setRunning(false);
                this.interrupt();
                break;
            }
        }
    }
}
