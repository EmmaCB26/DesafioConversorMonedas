# Desafio Conversor de Monedas — Proyecto en Java

Hola. Este es un proyecto hecho en Java que permite convertir entre distintas monedas latinoamericanas y algunas internacionales, utilizando la API de ExchangeRate.

Nació como parte de un desafío de programación, pero quise llevarlo un poco más lejos. Me gusta que incluso las herramientas en consola tengan personalidad. Cada parte del menú, las validaciones, los mensajes, están pensados para acompañar al usuario sin perder claridad.

## ¿Qué hace?

- Pide al usuario su API key de ExchangeRate
- Permite elegir una moneda base y una moneda destino de una lista bien definida
- Solicita el monto a convertir
- Se conecta a la API y muestra el valor final con dos decimales

## Estructura

- El `main()` contiene el flujo completo de interacción con el usuario
- Una clase externa (`ValoresMonedas`) gestiona las siglas oficiales de cada moneda
- Se usa `Scanner` para capturar entradas del usuario
- Los datos JSON se deserializan con `Gson` en un `record` llamado `InformationAPI`, que tiene métodos para mostrar los resultados formateados

## Pruebas

Durante el desarrollo se simularon errores y entradas inválidas, se verificaron respuestas reales de la API, y se añadieron validaciones para asegurar que el campo `"result"` sea `"success"` antes de mostrar resultados. También se comprobó el manejo correcto de decimales en los resultados de conversión.

## Monedas incluidas

- ARS — Peso argentino  
- BOB — Boliviano boliviano  
- BRL — Real brasileño  
- CLP — Peso chileno  
- COP — Peso colombiano  
- USD — Dólar estadounidense  
- Más algunas que completan el catálogo funcional del proyecto

## ¿Por qué está hecho así?

Porque creo que incluso una herramienta de consola puede tener narrativa. Preferí mantener la lógica en un mismo flujo antes que fragmentarla sin necesidad. Cada parte está colocada donde tiene sentido dentro del recorrido del usuario. Simple, directo y conversacional.

## Requisitos

- API key válida de ExchangeRate  
- Java 17 o superior (por el uso de `record`)  
- Conexión a internet  

## Comentarios

Si lo probaste, encontraste algún detalle que mejorar o simplemente te pareció útil, siempre es bien recibido el feedback.
