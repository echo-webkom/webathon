package Model;

// King.java
public class King extends Piece {

    private boolean hasMoved; // Flag to track if the King has moved (for castling)

    public King(Color color, int row, int col) {
        super(color, row, col, color == Color.WHITE ? "resources/white_king.png" : "resources/black_king.png");
        this.hasMoved = false;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endCol - startCol);

        if (rowDiff > 1 || colDiff > 1) {
            //Check for castling
            if (!hasMoved && rowDiff == 0 && colDiff == 2){
                //Check that the spaces are empty
                int direction = (endCol > startCol) ? 1 : -1;
                for (int col = startCol + direction; col != endCol; col += direction){
                    if (board.getPiece(startRow, col) != null){
                        return false;
                    }
                }

                //Check that the rook is there and hasn't moved
                int rookCol = (direction == 1) ? 7 : 0;
                Piece rook = board.getPiece(startRow, rookCol);
                if (rook instanceof Rook){
                    Rook realRook = (Rook) rook;
                    //TODO: Implement hasMoved for rook
                    return true;
                }
                return false;
            }
            return false; // King can only move one square in any direction
        }

        // Check if the destination square has a piece of the same color
        Piece destinationPiece = board.getPiece(endRow, endCol);
        if (destinationPiece != null && destinationPiece.getColor() == getColor()) {
            return false;
        }

        //TODO:  Add Check checking logic

        return true;
    }

    @Override
    public PieceType getType() {
        return PieceType.KING;
    }
}