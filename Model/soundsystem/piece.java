package Model.soundsystem;

enum PieceType { KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN, NONE }
enum PieceColor { WHITE, BLACK, NONE }

public class piece {
    PieceType type;
    PieceColor color;
    
    public piece(PieceType type, PieceColor color) {
        this.type = type;
        this.color = color;
    }
    
    public piece() {
        this.type = PieceType.NONE;
        this.color = PieceColor.NONE;
    }
    
    // Returns a Unicode symbol for the piece.
    public String getSymbol() {
        if (color == PieceColor.WHITE) {
            switch(type) {
                case KING:   return "\u2654";
                case QUEEN:  return "\u2655";
                case ROOK:   return "\u2656";
                case BISHOP: return "\u2657";
                case KNIGHT: return "\u2658";
                case PAWN:   return "\u2659";
                default:     return "";
            }
        } else if (color == PieceColor.BLACK) {
            switch(type) {
                case KING:   return "\u265A";
                case QUEEN:  return "\u265B";
                case ROOK:   return "\u265C";
                case BISHOP: return "\u265D";
                case KNIGHT: return "\u265E";
                case PAWN:   return "\u265F";
                default:     return "";
            }
        }
        return "";
    }
}
