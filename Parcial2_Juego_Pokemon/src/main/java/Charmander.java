//Charmander este si es gallo
public class Charmander extends Pokemon {
    public Charmander() {
        super("Charmander", 100, "Fuego");
        configurarAtaques();
    }

    @Override
    public void configurarAtaques() {
        agregarAtaque(new Ataque("Arañazo", 90,
                (atacante, defensor) -> 10 + (int)(Math.random() * 6)));

        agregarAtaque(new Ataque("Lanzallamas", 75,
                (atacante, defensor) -> {
                    int danoBase = 25 + (int)(Math.random() * 11);
                    if (defensor.getTipo().equals("Planta")) {
                        return (int)(danoBase * 1.5);
                    } else if (defensor.getTipo().equals("Agua")) {
                        return (int)(danoBase * 0.5);
                    }
                    return danoBase;
                }));
    }
}