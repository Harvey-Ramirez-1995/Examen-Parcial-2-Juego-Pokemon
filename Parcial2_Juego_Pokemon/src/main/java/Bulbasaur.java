// Bulbasaur no es el mejor pero lo ponemos
public class Bulbasaur extends Pokemon {
    public Bulbasaur() {
        super("Bulbasaur", 120, "Planta");
        configurarAtaques();
    }

    @Override
    public void configurarAtaques() {
        agregarAtaque(new Ataque("Latigazo", 80,
                (atacante, defensor) -> 7 + (int)(Math.random() * 4)));

        agregarAtaque(new Ataque("Drenadoras", 85,
                (atacante, defensor) -> {
                    int danoBase = 20 + (int)(Math.random() * 6);
                    if (defensor.getTipo().equals("Agua")) {
                        return (int)(danoBase * 1.5);
                    } else if (defensor.getTipo().equals("Fuego")) {
                        return (int)(danoBase * 0.5);
                    }
                    return danoBase;
                }));
    }
}
