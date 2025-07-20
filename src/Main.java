import com.google.gson.*;

import java.io.FileWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();
        List<Object> historialConversiones = new ArrayList<>();
        System.out.println("""
                ¡Hola usuario, vamos a convertir las monedas!\s
                Para convertir las monedas, necesitamos que ingrese su API key de ExchangeRate:\s""");
        Scanner scanner = new Scanner(System.in);
        String apikey = scanner.nextLine();
        if (apikey.isBlank()) {
            System.out.println("API key vacía. Por favor ingrésala nuevamente.");
            return;
        }
        String menu = """
    Ingrese el tipo de moneda base:
    1) Peso Argentino (ARS)
    2) Bolívar Venezolano (VES)
    3) Boliviano boliviano (BOB)
    4) Dólar Estadounidense (USD)
    5) Euro (EUR)
    6) Peso Colombiano (COP)
    7) Peso Chileno (CLP)
    8) Peso Mexicano (MXN)
    9) Real Brasileño (BRL)
    10) Quetzal (GTQ)""";
        while (true){
            System.out.println(menu);
            System.out.println("11) Salir");
            int opcionBase;
            int opcionDestino;
            if (scanner.hasNextInt()) {
                opcionBase = scanner.nextInt();
                // seguir flujo
            } else {
                System.out.println("Entrada no válida. Por favor ingrese un número.");
                scanner.next(); // descarta la entrada incorrecta
                continue;
            }
            String valorBase = "";
            String valorDestino = "";
            double cantidadBase = 0.0;

            if (opcionBase == 11) {
                System.out.println("\nHasta luego usuario. :)");
                break;
            } else if (opcionBase < 1 || opcionBase > 11) {
                System.out.println("\nOpción no válida.");
            } else {
                valorBase = ValoresMonedas.siglaMoneda(opcionBase);
                System.out.println("\nSeleccionaste como base: " + valorBase);
                System.out.println("\nIngrese el valor a convertir:");
                cantidadBase = scanner.nextDouble();
                System.out.println("\nIngrese el tipo de moneda destino:");
                System.out.println(menu);
                if (scanner.hasNextInt()) {
                    opcionDestino = scanner.nextInt();
                } else {
                    System.out.println("Entrada no válida. Por favor ingrese un número.");
                    scanner.next();
                    continue;
                }
                if (opcionDestino < 1 || opcionDestino > 11) {
                    System.out.println("\nOpción no válida.");
                } else {
                    valorDestino = ValoresMonedas.siglaMoneda(opcionDestino);
                    if (valorBase.equals(valorDestino)) {
                        System.out.println("Seleccionaste la misma moneda como origen y destino. El resultado será el mismo.");
                    }
                    System.out.println("\nConvertirás de " + valorBase + " a " + valorDestino);
                }
            }
            String direccion = "https://v6.exchangerate-api.com/v6/"+ apikey +"/pair/" + valorBase + "/" + valorDestino + "/" + cantidadBase;
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(direccion))
                        .build();

                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();
                JsonObject jsonObj = JsonParser.parseString(json).getAsJsonObject();
                if (!jsonObj.get("result").getAsString().equals("success")) {
                    System.out.println("Error en la conversión. Verifica tu API key o monedas.");
                    continue;
                }
                InformationAPI respuesta = gson.fromJson(json, InformationAPI.class);
                String resultadoFinal = "Tasa de cambio de " + valorBase + ": " + respuesta.conversionRateDecimales() + " " + valorDestino +
                                        "\nResultado: " + respuesta.conversionResultDecimales() + " " + valorDestino + "\n";
                System.out.println(resultadoFinal);
                LocalDateTime momento = LocalDateTime.now();
                DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                String marcaTiempo = momento.format(formato);
                String registro = marcaTiempo + " — " + cantidadBase + " " + valorBase + " → " + respuesta.conversionResultDecimales() + " " + valorDestino;
                historialConversiones.add(registro);
            } catch (Exception e){
                System.out.println("Error, URL no encontrada o errónea");
                System.out.println("Detalles técnicos: " + e.getMessage());
            }
        }
        if (historialConversiones != null && !historialConversiones.isEmpty()){
            System.out.println("\nHistorial de conversiones:");
            for (Object h : historialConversiones) {
                System.out.println(h);
            }
        } else {
            System.out.println("\nNo se realizaron conversiones.\n");
        }
    }
}
