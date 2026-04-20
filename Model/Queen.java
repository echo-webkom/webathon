package Model;

// Queen.java
public class Queen extends Piece {

    public Queen(Color color, int row, int col) {
        super(color, row, col, color == Color.WHITE ? "resources/white_queen.png" : "resources/black_queen.png");
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        // Queen combines Rook and Bishop movement

        // Rook-like movement
        if (startRow == endRow || startCol == endCol) {
            // Check for obstructions (same as Rook)
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

        // Bishop-like movement
        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endCol - startCol);

        if (rowDiff == colDiff) {
            // Check for obstructions (same as Bishop)
            int rowStep = (endRow > startRow) ? 1 : -1;
            int colStep = (endCol > startCol) ? 1 : -1;
            int row = startRow + rowStep;
            int col = startCol + colStep;

            while (row != endRow) {
                if (board.getPiece(row, col) != null) {
                    return false; // Path is blocked
                }
                row += rowStep;
                col += colStep;
            }
             // Check if the destination square has a piece of the same color
            Piece destinationPiece = board.getPiece(endRow, endCol);
            if (destinationPiece != null && destinationPiece.getColor() == getColor()) {
                return false;
            }
            return true;
        }

        return false;
    }

    @Override
    public PieceType getType() {
        return PieceType.QUEEN;
    }
}