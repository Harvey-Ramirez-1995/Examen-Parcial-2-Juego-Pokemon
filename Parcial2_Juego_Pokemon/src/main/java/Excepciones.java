// Excepciones
public class Excepciones {
    public static class OpcionInvalidaException extends Exception {
        public OpcionInvalidaException(String mensaje) {
            super(mensaje);
        }
    }

    public static class AtaqueFalladoException extends Exception {
        public AtaqueFalladoException(String mensaje) {
            super(mensaje);
        }
    }
}