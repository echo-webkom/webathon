package Model;

// Board.java
public class Board {
    private Piece[][] board;
    private static final int BOARD_SIZE = 8;

    public Board() {
        board = new Piece[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }

    public void initializeBoard() {
        // Initialize white pieces
        board[7][0] = new Rook(Piece.Color.WHITE, 7, 0);
        board[7][1] = new Knight(Piece.Color.WHITE, 7, 1);
        board[7][2] = new Bishop(Piece.Color.WHITE, 7, 2);
        board[7][3] = new Queen(Piece.Color.WHITE, 7, 3);
        board[7][4] = new King(Piece.Color.WHITE, 7, 4);
        board[7][5] = new Bishop(Piece.Color.WHITE, 7, 5);
        board[7][6] = new Knight(Piece.Color.WHITE, 7, 6);
        board[7][7] = new Rook(Piece.Color.WHITE, 7, 7);
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn(Piece.Color.WHITE, 6, i);
        }

        // Initialize black pieces
        board[0][0] = new Rook(Piece.Color.BLACK, 0, 0);
        board[0][1] = new Knight(Piece.Color.BLACK, 0, 1);
        board[0][2] = new Bishop(Piece.Color.BLACK, 0, 2);
        board[0][3] = new Queen(Piece.Color.BLACK, 0, 3);
        board[0][4] = new King(Piece.Color.BLACK, 0, 4);
        board[0][5] = new Bishop(Piece.Color.BLACK, 0, 5);
        board[0][6] = new Knight(Piece.Color.BLACK, 0, 6);
        board[0][7] = new Rook(Piece.Color.BLACK, 0, 7);
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(Piece.Color.BLACK, 1, i);
        }

        // Initialize empty squares
        for (int row = 2; row < 6; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = null;
            }
        }
    }

    public Piece getPiece(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return null; // Out of bounds
        }
        return board[row][col];
    }

    public void setPiece(int row, int col, Piece piece) {
        board[row][col] = piece;
    }

    public void movePiece(int startRow, int startCol, int endRow, int endCol) {
        Piece piece = getPiece(startRow, startCol);
        if (piece != null) {
            board[endRow][endCol] = piece;
            board[startRow][startCol] = null;
            piece.setRow(endRow); // Update the piece's position
            piece.setCol(endCol);
        }
    }

    public static int getBoardSize(){
        return BOARD_SIZE;
    }

    public void printBoard() {
        for (int row = 0; row < BOARD_SIZE; row++) {
            System.out.print(8 - row + " |"); // Print row number for better readability

            for (int col = 0; col < BOARD_SIZE; col++) {
                Piece piece = getPiece(row, col);
                if (piece == null) {
                    System.out.print(" . "); // Empty square
                } else {
                    System.out.print(" " + piece + " "); // Print piece representation
                }
            }
            System.out.println("|");
        }
        System.out.println("   --------------------------------");
        System.out.println("   a  b  c  d  e  f  g  h");  // Print column letters
    }
}