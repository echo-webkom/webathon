package Model;

// Piece.java
import java.awt.Image;
import javax.swing.ImageIcon;

public abstract class Piece {
    private Color color;
    private int row;
    private int col;
    private String imagePath; // Path to the image file (e.g., "resources/white_pawn.png")


    public Piece(Color color, int row, int col, String imagePath) {
        this.color = color;
        this.row = row;
        this.col = col;
        this.imagePath = imagePath;
    }

    public Color getColor() {
        return color;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

     public String getImagePath() {
        return imagePath;
    }

    public Image getImage() {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(imagePath));
        return icon.getImage();
    }

    // Abstract method to define valid moves for each piece type
    public abstract boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board);

    public abstract PieceType getType();


    public enum Color {
        WHITE,
        BLACK
    }

    public enum PieceType {
        PAWN,
        ROOK,
        KNIGHT,
        BISHOP,
        QUEEN,
        KING
    }


    @Override
    public String toString() {
        return color.toString().charAt(0) + getType().toString().substring(0, 1); // e.g., WP for White Pawn
    }
}