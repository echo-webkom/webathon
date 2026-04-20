package Model;

// Bishop.java
public class Bishop extends Piece {

    public Bishop(Color color, int row, int col) {
        super(color, row, col, color == Color.WHITE ? "resources/white_bishop.png" : "resources/black_bishop.png");
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endCol - startCol);

        if (rowDiff != colDiff) {
            return false; // Bishops move diagonally
        }

        // Check for obstructions in the path
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

    @Override
    public PieceType getType() {
        return PieceType.BISHOP;
    }
}