import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Conversor {
    private String apiKey;
    private String apiBaseUrl;
    private List<String> historial = new ArrayList<>();

    public Conversor(String apiKey) {
        this.apiKey = apiKey;
        this.apiBaseUrl = "https://v6.exchangerate-api.com/v6/"+apiKey;
    }

    public List<String> getHistorial() {
        return historial;
    }

    public List<Moneda> obtenerMonedasSoportadas() throws IOException, InterruptedException {
        List<Moneda> monedas = new ArrayList<>();

        URI uri = URI.create(apiBaseUrl+"/codes");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

        json.get("supported_codes").getAsJsonArray().forEach(item -> {
            JsonArray itemJsonArray = item.getAsJsonArray();
            Moneda moneda = new Moneda(itemJsonArray.get(0).getAsString(), itemJsonArray.get(1).getAsString());
            monedas.add(moneda);
        });

        return monedas;
    }

    public Double convertir(Moneda monedaBase, Moneda monedaObjetivo, Double cantidad) throws IOException, InterruptedException {
        URI uri = URI.create(apiBaseUrl+"/pair/"+monedaBase.getCodigo()+"/"+monedaObjetivo.getCodigo()+"/"+cantidad);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

        Double resultado = json.get("conversion_result").getAsDouble();

        registrarConversion(monedaBase, monedaObjetivo, cantidad, resultado);

        return resultado;
    }

    public void mostrarMenuConversion() throws IOException, InterruptedException {
        List<Moneda> monedas = obtenerMonedasSoportadas();
        Scanner scanner = new Scanner(System.in);

        listarMonedas(monedas);
        System.out.println("Seleccione la moneda base:");
        int optMonedaBase = scanner.nextInt() - 1;

        listarMonedas(monedas);
        System.out.println("Seleccione la moneda objetivo:");
        int optMonedaObjetivo = scanner.nextInt() - 1;

        System.out.println("Ingrese la cantidad a convertir:");
        Double cantidad = scanner.nextDouble();

        Moneda monedaBase = monedas.get(optMonedaBase);
        Moneda monedaObjetivo = monedas.get(optMonedaObjetivo);
        Double resultadoConversion = convertir(monedaBase, monedaObjetivo, cantidad);

        System.out.println("\n*******************");
        System.out.println(cantidad + " " + monedaBase.getCodigo() + " es igual a " + resultadoConversion + " " + monedaObjetivo.getCodigo());
        System.out.println("*******************\n");
    }

    private static void listarMonedas(List<Moneda> monedas){
        int contadorDeMonedas = 1;
        for (Moneda moneda : monedas) {
            System.out.println(contadorDeMonedas + " - " + moneda.getCodigo() + "("+ moneda.getNombre() +")");
            contadorDeMonedas++;
        }
    }

    private void registrarConversion(Moneda monedaBase, Moneda monedaObjetivo, Double cantidad, Double resultado){
        String time = LocalDateTime.now().toString();
        historial.add(cantidad +" "+ monedaBase.getCodigo() + " = " + resultado + " " + monedaObjetivo.getCodigo() +" ["+time+"]");
    }

    public void listarHistorialDeConversion() {
        List<String> historial = getHistorial();
        if(historial.isEmpty()){
            System.out.println("""
                    ********
                    Nada por aquí, nada por allá, intenta más tarde :P
                    ********
                    """);
        }
        else {
            System.out.println("**** Historial de conversiones *****");
            historial.forEach(System.out::println);
            System.out.println("************************************");
        }
    }

}
