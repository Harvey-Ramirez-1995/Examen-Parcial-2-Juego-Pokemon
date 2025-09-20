import java.util.Scanner;

public class Juego {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Batalla batalla = new Batalla();

        System.out.println("¡Bienvenido al juego de Pokémon!");
        System.out.print("Por favor, ingresa tu nombre y apellido: ");
        String nombreJugador = scanner.nextLine();

        try {
            // Selección de Pokémon
            Pokemon pokemonJugador = batalla.seleccionarPokemon(nombreJugador);
            Pokemon pokemonCPU = batalla.seleccionarPokemonCPU(pokemonJugador);

            batalla.getRegistroBatalla().add(nombreJugador + " ha elegido a " + pokemonJugador.getNombre());
            batalla.getRegistroBatalla().add("La CPU ha elegido a " + pokemonCPU.getNombre());

            System.out.println("\n¡Comienza la batalla!");
            System.out.println(nombreJugador + ": " + pokemonJugador);
            System.out.println("CPU: " + pokemonCPU);

            // Bucle de batalla
            boolean turnoJugador = true;
            while (!pokemonJugador.estaDebilitado() && !pokemonCPU.estaDebilitado()) {
                if (turnoJugador) {
                    batalla.ataqueJugador(nombreJugador, pokemonJugador, pokemonCPU);
                } else {
                    batalla.ataqueCPU(pokemonJugador, pokemonCPU);
                }

                turnoJugador = !turnoJugador;

                System.out.println("\nEstado actual:");
                System.out.println(nombreJugador + ": " + pokemonJugador);
                System.out.println("CPU: " + pokemonCPU);
            }

            // Determinar resultado
            batalla.determinarGanador(nombreJugador, pokemonJugador, pokemonCPU);

            // Mostrar resumen
            batalla.mostrarResumenBatalla(nombreJugador);

        } catch (Excepciones.OpcionInvalidaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}