import java.util.*;
import java.util.stream.Collectors;  // ← ¡Este import faltaba!

//Batalla que gane el mejor jajajaja
public class Batalla {
    private List<String> registroBatalla = new ArrayList<>();
    private List<Integer> historialDano = new ArrayList<>();
    private Map<String, Integer> contadorEventos = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    public Batalla() {
        contadorEventos.put("Jugador", 0);
        contadorEventos.put("CPU", 0);
    }

    public Pokemon seleccionarPokemon(String nombreJugador) throws Excepciones.OpcionInvalidaException {
        Map<Integer, Pokemon> mapaPokemon = new HashMap<>();
        mapaPokemon.put(1, new Charmander());
        mapaPokemon.put(2, new Squirtle());
        mapaPokemon.put(3, new Bulbasaur());
        mapaPokemon.put(4, new Pikachu());

        System.out.println("\n" + nombreJugador + ", elige tu Pokémon:");
        System.out.println("1. Charmander (Fuego)");
        System.out.println("2. Squirtle (Agua)");
        System.out.println("3. Bulbasaur (Planta)");
        System.out.println("4. Pikachu (Eléctrico)");
        System.out.print("Tu elección (1-4): ");

        try {
            int opcion = Integer.parseInt(scanner.nextLine());
            if (opcion < 1 || opcion > 4) {
                throw new Excepciones.OpcionInvalidaException("Opción inválida. Debe ser un número entre 1 y 4.");
            }
            return mapaPokemon.get(opcion);
        } catch (NumberFormatException e) {
            throw new Excepciones.OpcionInvalidaException("Entrada inválida. Debe ser un número.");
        }
    }

    public Pokemon seleccionarPokemonCPU(Pokemon pokemonJugador) {
        List<Pokemon> pokemonDisponibles = Arrays.asList(
                new Charmander(), new Squirtle(), new Bulbasaur(), new Pikachu()
        );

        // Filtrar el Pokémon del jugador
        List<Pokemon> opcionesCPU = pokemonDisponibles.stream()
                .filter(p -> !p.getNombre().equals(pokemonJugador.getNombre()))
                .collect(Collectors.toList());  // ← Aquí usa Collectors

        // Selección aleatoria
        Random random = new Random();
        return opcionesCPU.get(random.nextInt(opcionesCPU.size()));
    }

    public void ataqueJugador(String nombreJugador, Pokemon jugador, Pokemon oponente) {
        System.out.println("\n--- Turno de " + nombreJugador + " ---");
        System.out.println("Elige un ataque:");

        // Mostrar ataques ordenados
        List<Ataque> ataquesOrdenados = jugador.getAtaques().stream()
                .sorted(Comparator.comparingInt(Ataque::getPrecision).reversed())
                .collect(Collectors.toList());  // ← Aquí usa Collectors

        for (int i = 0; i < ataquesOrdenados.size(); i++) {
            System.out.println((i + 1) + ". " + ataquesOrdenados.get(i));
        }

        try {
            System.out.print("Tu elección (1-" + ataquesOrdenados.size() + "): ");
            int opcion = Integer.parseInt(scanner.nextLine());

            if (opcion < 1 || opcion > ataquesOrdenados.size()) {
                throw new Excepciones.OpcionInvalidaException("Opción inválida. Intenta de nuevo.");
            }

            Ataque ataqueSeleccionado = ataquesOrdenados.get(opcion - 1);
            ejecutarAtaque(nombreJugador, jugador, oponente, ataqueSeleccionado);
            contadorEventos.put("Jugador", contadorEventos.get("Jugador") + 1);

        } catch (NumberFormatException | Excepciones.OpcionInvalidaException e) {
            System.out.println("Error: " + e.getMessage() + " Pierdes tu turno.");
            registroBatalla.add(nombreJugador + " eligió un ataque inválido y perdió su turno.");
        }
    }

    public void ataqueCPU(Pokemon jugador, Pokemon cpu) {
        System.out.println("\n--- Turno de la CPU ---");

        // La CPU elige el ataque más preciso disponible
        Ataque ataqueCPU = cpu.getAtaques().stream()
                .max(Comparator.comparingInt(Ataque::getPrecision))
                .orElse(cpu.getAtaques().get(0));

        System.out.println("La CPU usa: " + ataqueCPU.getNombre());
        ejecutarAtaque("CPU", cpu, jugador, ataqueCPU);
        contadorEventos.put("CPU", contadorEventos.get("CPU") + 1);
    }

    private void ejecutarAtaque(String nombreAtacante, Pokemon atacante, Pokemon defensor, Ataque ataque) {
        try {
            // Verificar si el ataque falla
            Random random = new Random();
            if (random.nextInt(100) >= ataque.getPrecision()) {
                throw new Excepciones.AtaqueFalladoException("El ataque " + ataque.getNombre() + " ha fallado.");
            }

            // Calcular daño
            int dano = ataque.getReglaDano().calcularDano(atacante, defensor);
            int nuevoHp = defensor.getHpActual() - dano;
            defensor.setHpActual(nuevoHp);

            // Registrar en el historial
            historialDano.add(dano);
            String entradaRegistro = nombreAtacante + " usó " + ataque.getNombre() + " e hizo " + dano + " puntos de daño.";
            registroBatalla.add(entradaRegistro);
            System.out.println(entradaRegistro);

        } catch (Excepciones.AtaqueFalladoException e) {
            String entradaRegistro = nombreAtacante + " usó " + ataque.getNombre() + " pero falló.";
            registroBatalla.add(entradaRegistro);
            System.out.println(entradaRegistro);
        }
    }

    public void determinarGanador(String nombreJugador, Pokemon jugador, Pokemon cpu) {
        System.out.println("\n=== FIN DE LA BATALLA ===");

        if (jugador.estaDebilitado() && cpu.estaDebilitado()) {
            System.out.println("¡Es un empate!");
            registroBatalla.add("La batalla terminó en empate.");
        } else if (jugador.estaDebilitado()) {
            System.out.println("¡" + nombreJugador + " ha perdido! La CPU gana con " + cpu.getNombre());
            registroBatalla.add("La CPU ganó la batalla.");
        } else {
            System.out.println("¡Felicidades " + nombreJugador + "! Has ganado con " + jugador.getNombre());
            registroBatalla.add(nombreJugador + " ganó la batalla.");
        }
    }

    public void mostrarResumenBatalla(String nombreJugador) {
        System.out.println("\n=== RESUMEN DE LA BATALLA ===");

        // Mostrar eventos clave
        System.out.println("\nEventos clave:");
        registroBatalla.forEach(System.out::println);

        // Estadísticas con Streams
        System.out.println("\nEstadísticas:");

        // Total de fallos
        long totalFallos = registroBatalla.stream()
                .filter(evento -> evento.contains("pero falló"))
                .count();
        System.out.println("Total de ataques fallados: " + totalFallos);

        // Top 3 golpes más fuertes
        System.out.println("Top 3 golpes más fuertes:");
        historialDano.stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .forEach(dano -> System.out.println("- " + dano + " puntos de daño"));

        // Promedio de daño
        double promedioDano = historialDano.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
        System.out.println("Promedio de daño: " + String.format("%.2f", promedioDano));

        // Conteo de eventos por actor
        System.out.println("Conteo de eventos:");
        contadorEventos.forEach((actor, conteo) -> {
            String nombre = actor.equals("Jugador") ? nombreJugador : actor;
            System.out.println("- " + nombre + ": " + conteo + " acciones");
        });
    }

    public List<String> getRegistroBatalla() { return registroBatalla; }
    public List<Integer> getHistorialDano() { return historialDano; }
    public Map<String, Integer> getContadorEventos() { return contadorEventos; }
}