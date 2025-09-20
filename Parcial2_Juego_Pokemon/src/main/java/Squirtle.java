//este es gallo igual que charmander pero no estamos listos para esta conversacion.
public class Squirtle extends Pokemon {
    public Squirtle() {
        super("Squirtle", 110, "Agua");
        configurarAtaques();
    }

    @Override
    public void configurarAtaques() {
        agregarAtaque(new Ataque("Burbuja", 85,
                (atacante, defensor) -> 8 + (int)(Math.random() * 5)));

        agregarAtaque(new Ataque("Hidrobomba", 70,
                (atacante, defensor) -> {
                    int danoBase = 30 + (int)(Math.random() * 11);
                    if (defensor.getTipo().equals("Fuego")) {
                        return (int)(danoBase * 1.5);
                    } else if (defensor.getTipo().equals("Planta") || defensor.getTipo().equals("Eléctrico")) {
                        return (int)(danoBase * 0.5);
                    }
                    return danoBase;
                }));
    }
}