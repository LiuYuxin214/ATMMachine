public class State {
    boolean result;
    String message;

    public State(boolean state, String message) {
        this.result = state;
        this.message = message;
    }

    public boolean getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
