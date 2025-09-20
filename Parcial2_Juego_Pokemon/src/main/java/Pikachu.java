// Este es el mas gallo de los gallos
public class Pikachu extends Pokemon {
    public Pikachu() {
        super("Pikachu", 90, "Eléctrico");
        configurarAtaques();
    }

    @Override
    public void configurarAtaques() {
        agregarAtaque(new Ataque("Impactrueno", 95,
                (atacante, defensor) -> 9 + (int)(Math.random() * 5)));

        agregarAtaque(new Ataque("Rayo", 75,
                (atacante, defensor) -> {
                    int danoBase = 35 + (int)(Math.random() * 11);
                    if (defensor.getTipo().equals("Agua")) {
                        return (int)(danoBase * 1.5);
                    } else if (defensor.getTipo().equals("Planta") || defensor.getTipo().equals("Eléctrico")) {
                        return (int)(danoBase * 0.5);
                    }
                    return danoBase;
                }));
    }
}
