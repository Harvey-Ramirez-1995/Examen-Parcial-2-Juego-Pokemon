// Ataque para los pokemones
public class Ataque {
    private String nombre;
    private int precision;
    private ReglaDano reglaDano;

    public Ataque(String nombre, int precision, ReglaDano reglaDano) {
        this.nombre = nombre;
        this.precision = precision;
        this.reglaDano = reglaDano;
    }

    public String getNombre() { return nombre; }
    public int getPrecision() { return precision; }
    public ReglaDano getReglaDano() { return reglaDano; }

    @Override
    public String toString() {
        return nombre + " (Precisión: " + precision + "%)";
    }
}