package Model.soundsystem;

public interface ChessBoardListener {
    void onBoardWon(PieceColor winner, int boardIndex);
    void onMoveMade(int boardIndex); // New method for global turn management

}
