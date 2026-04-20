package Model;

// Knight.java
public class Knight extends Piece {

    public Knight(Color color, int row, int col) {
        super(color, row, col, color == Color.WHITE ? "resources/white_knight.png" : "resources/black_knight.png");
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endCol - startCol);

        if (!((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2))) {
            return false; // Knights move in an L-shape
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
        return PieceType.KNIGHT;
    }
}