package Model;

// Rook.java
public class Rook extends Piece {

    public Rook(Color color, int row, int col) {
        super(color, row, col, color == Color.WHITE ? "resources/white_rook.png" : "resources/black_rook.png");
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        if (startRow != endRow && startCol != endCol) {
            return false; // Rooks move horizontally or vertically
        }

        // Check for obstructions in the path
        if (startRow == endRow) { // Horizontal movement
            int step = (endCol > startCol) ? 1 : -1;
            for (int col = startCol + step; col != endCol; col += step) {
                if (board.getPiece(startRow, col) != null) {
                    return false; // Path is blocked
                }
            }
        } else { // Vertical movement
            int step = (endRow > startRow) ? 1 : -1;
            for (int row = startRow + step; row != endRow; row += step) {
                if (board.getPiece(row, startCol) != null) {
                    return false; // Path is blocked
                }
            }
        }

        // Check if the destination square has a piece of the same color
        Piece destinationPiece = board.getPiece(endRow, endCol);
        if (destinationPiece != null && destinationPiece.getColor() == getColor()) {
            return false;
        }

        return true;
    }

    @Override
    public PieceType getType() {
        return PieceType.ROOK;
    }
}