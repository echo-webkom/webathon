package Model;

public class Player {
    private String name;
    private Piece.Color color;

    public Player(String name, Piece.Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Piece.Color getColor() {
        return color;
    }
}