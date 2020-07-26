package model;

public class Board {

    private Piece[][] pieces =new Piece[7+6][7+6];

    public Board(){

    }

    public Piece[][] getPieces() {
        return pieces;
    }

    public void setPieces(Piece[][] pieces) {
        this.pieces = pieces;
    }
}
