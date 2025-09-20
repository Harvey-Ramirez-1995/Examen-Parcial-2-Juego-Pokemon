import java.util.ArrayList;
import java.util.List;

// Clase abstracta para Pokémon
public abstract class Pokemon {
    private String nombre;
    private int hpMaximo;
    private int hpActual;
    private String tipo;
    private List<Ataque> ataques;

    public Pokemon(String nombre, int hpMaximo, String tipo) {
        this.nombre = nombre;
        this.hpMaximo = hpMaximo;
        this.hpActual = hpMaximo;
        this.tipo = tipo;
        this.ataques = new ArrayList<>();
    }

    public String getNombre() { return nombre; }
    public int getHpMaximo() { return hpMaximo; }
    public int getHpActual() { return hpActual; }
    public String getTipo() { return tipo; }
    public List<Ataque> getAtaques() { return ataques; }

    public void setHpActual(int hpActual) {
        this.hpActual = Math.max(0, Math.min(hpMaximo, hpActual));
    }

    public void agregarAtaque(Ataque ataque) {
        ataques.add(ataque);
    }

    public boolean estaDebilitado() {
        return hpActual <= 0;
    }

    public abstract void configurarAtaques();

    @Override
    public String toString() {
        return nombre + " (" + tipo + ") - HP: " + hpActual + "/" + hpMaximo;
    }
}