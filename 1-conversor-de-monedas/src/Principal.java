import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) throws IOException, InterruptedException {
        Conversor conversor = new Conversor("0e99cb5393758d10dbb181e0");
        int optSeleccionada = -1;

        while (optSeleccionada != 0){
            System.out.println("""
                ¡Hola, bienvenido al conversor de monedas!
                Selecciona una opción.
                
                1 - Convertir moneda.
                2 - Ver historial de conversiones.
                0 - Salir.
                """);
            Scanner scanner = new Scanner(System.in);
            optSeleccionada = scanner.nextInt();

            switch (optSeleccionada){
                case 1 :
                    conversor.mostrarMenuConversion();
                    break;
                case 2 :
                    conversor.listarHistorialDeConversion();
                    break;
                case 0 :
                    System.out.println("¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }
}
