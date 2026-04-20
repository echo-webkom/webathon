package Model;

// Move.java
public class Move {
    private int startRow;
    private int startCol;
    private int endRow;
    private int endCol;
    private Piece movedPiece;
    private Piece capturedPiece;  //Optional

    public Move(int startRow, int startCol, int endRow, int endCol, Piece movedPiece, Piece capturedPiece) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
    }

      public Move(int startRow, int startCol, int endRow, int endCol, Piece movedPiece) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.movedPiece = movedPiece;
        this.capturedPiece = null;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getEndCol() {
        return endCol;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }
}