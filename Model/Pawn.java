package Model;

// Pawn.java
public class Pawn extends Piece {

    public Pawn(Color color, int row, int col) {
        super(color, row, col, color == Color.WHITE ? "resources/white_pawn.png" : "resources/black_pawn.png");
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        int rowDiff = endRow - startRow;
        int colDiff = Math.abs(endCol - startCol);
        Color color = getColor();

        if (colDiff > 1) {
            return false; // Can only move one column to capture
        }

        if (color == Color.WHITE) {
            if (rowDiff > 0) return false; // White pawns move up (decreasing row number)
            if (rowDiff == 0) return false; // White pawns move up (decreasing row number)

            if (colDiff == 0) { // Moving straight
                if (rowDiff == -1 && board.getPiece(endRow, endCol) == null) return true; // One square forward

                if (startRow == 6 && rowDiff == -2 && board.getPiece(endRow, endCol) == null && board.getPiece(endRow + 1, endCol) == null) {
                     //Two squares from start
                    return true;
                }
            } else { // Capturing diagonally
                if (rowDiff == -1 && colDiff == 1 && board.getPiece(endRow, endCol) != null && board.getPiece(endRow, endCol).getColor() == Color.BLACK) return true; // Capture
            }
        } else { // Black pawn
             if (rowDiff < 0) return false; // Black pawns move down (increasing row number)
            if (rowDiff == 0) return false;

            if (colDiff == 0) { // Moving straight
                if (rowDiff == 1 && board.getPiece(endRow, endCol) == null) return true; // One square forward
                if (startRow == 1 && rowDiff == 2 && board.getPiece(endRow, endCol) == null && board.getPiece(endRow - 1, endCol) == null) return true; // Two squares from start
            } else { // Capturing diagonally
                if (rowDiff == 1 && colDiff == 1 && board.getPiece(endRow, endCol) != null && board.getPiece(endRow, endCol).getColor() == Color.WHITE) return true; // Capture
            }
        }

        return false;
    }

    @Override
    public PieceType getType() {
        return PieceType.PAWN;
    }
}