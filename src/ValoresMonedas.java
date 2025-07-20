public class ValoresMonedas {
    public static String siglaMoneda(int opcion) {
        return switch (opcion) {
            case 1 -> "ARS";
            case 2 -> "VES";
            case 3 -> "BOB";
            case 4 -> "USD";
            case 5 -> "EUR";
            case 6 -> "COP";
            case 7 -> "CLP";
            case 8 -> "MXN";
            case 9 -> "BRL";
            case 10 -> "GTQ";
            default -> "Desconocido";
        };
    }
}