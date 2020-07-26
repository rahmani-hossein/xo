package logic;

import model.Board;

public class BoardManager {



    public BoardManager() {

    }



    public boolean win(Board board,String name){
        for (int i = 3; i < board.getPieces()[0].length-3; i++) {
            for (int j = 3; j <board.getPieces()[1].length-3 ; j++) {
                if (checkAmodi(board,i,j,name)||checkOfoghi(board,i,j,name)||checkGhotri(board,i,j,name)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkOfoghi(Board board ,int i,int j,String name){
        if (board.getPieces()[i][j]!=null&&board.getPieces()[i][j].getName().equals(name)
                &&board.getPieces()[i][j-1]!=null&&board.getPieces()[i][j-1].getName().equals(name)
                &&board.getPieces()[i][j-2]!=null&&board.getPieces()[i][j-2].getName().equals(name)
                &&board.getPieces()[i][j-3]!=null&&board.getPieces()[i][j-3].getName().equals(name)){
            return true;
        }
        if (board.getPieces()[i][j]!=null&&board.getPieces()[i][j].getName().equals(name)
                &&board.getPieces()[i][j-1]!=null&&board.getPieces()[i][j-1].getName().equals(name)
                &&board.getPieces()[i][j-2]!=null&&board.getPieces()[i][j-2].getName().equals(name)
                &&board.getPieces()[i][j+1]!=null&&board.getPieces()[i][j+1].getName().equals(name)){
            return true;
        }
        if (board.getPieces()[i][j]!=null&&board.getPieces()[i][j].getName().equals(name)
                &&board.getPieces()[i][j-1]!=null&&board.getPieces()[i][j-1].getName().equals(name)
                &&board.getPieces()[i][j+2]!=null&&board.getPieces()[i][j+2].getName().equals(name)
                &&board.getPieces()[i][j+1]!=null&&board.getPieces()[i][j+1].getName().equals(name)){
            return true;
        }
        if (board.getPieces()[i][j]!=null&&board.getPieces()[i][j].getName().equals(name)
                &&board.getPieces()[i][j+3]!=null&&board.getPieces()[i][j+3].getName().equals(name)
                &&board.getPieces()[i][j+2]!=null&&board.getPieces()[i][j+2].getName().equals(name)
                &&board.getPieces()[i][j+1]!=null&&board.getPieces()[i][j+1].getName().equals(name)){
            return true;
        }
        return false;
    }
    private boolean checkAmodi(Board board ,int i,int j,String name){
        if (board.getPieces()[i][j]!=null&&board.getPieces()[i][j].getName().equals(name)
                &&board.getPieces()[i-1][j]!=null&&board.getPieces()[i-1][j].getName().equals(name)
                &&board.getPieces()[i-2][j]!=null&&board.getPieces()[i-2][j].getName().equals(name)
                &&board.getPieces()[i-3][j]!=null&&board.getPieces()[i-3][j].getName().equals(name)){
            return true;
        }
        if (board.getPieces()[i][j]!=null&&board.getPieces()[i][j].getName().equals(name)
                &&board.getPieces()[i-1][j]!=null&&board.getPieces()[i-1][j].getName().equals(name)
                &&board.getPieces()[i-2][j]!=null&&board.getPieces()[i-2][j].getName().equals(name)
                &&board.getPieces()[i+1][j]!=null&&board.getPieces()[i+1][j].getName().equals(name)){
            return true;
        }
        if (board.getPieces()[i][j]!=null&&board.getPieces()[i][j].getName().equals(name)
                &&board.getPieces()[i-1][j]!=null&&board.getPieces()[i-1][j].getName().equals(name)
                &&board.getPieces()[i+2][j]!=null&&board.getPieces()[i+2][j].getName().equals(name)
                &&board.getPieces()[i+1][j]!=null&&board.getPieces()[i+1][j].getName().equals(name)){
            return true;
        }
        if (board.getPieces()[i][j]!=null&&board.getPieces()[i][j].getName().equals(name)
                &&board.getPieces()[i+3][j]!=null&&board.getPieces()[i+3][j].getName().equals(name)
                &&board.getPieces()[i+2][j]!=null&&board.getPieces()[i+2][j].getName().equals(name)
                &&board.getPieces()[i+1][j]!=null&&board.getPieces()[i+1][j].getName().equals(name)){
            return true;
        }
        return false;
    }
    private boolean checkGhotri(Board board ,int i,int j,String name){
        if (board.getPieces()[i][j]!=null&&board.getPieces()[i][j].getName().equals(name)
                &&board.getPieces()[i-1][j]!=null&&board.getPieces()[i-1][j-1].getName().equals(name)
                &&board.getPieces()[i-2][j]!=null&&board.getPieces()[i-2][j-2].getName().equals(name)
                &&board.getPieces()[i-3][j]!=null&&board.getPieces()[i-3][j-3].getName().equals(name)){
            return true;
        }
        if (board.getPieces()[i][j]!=null&&board.getPieces()[i][j].getName().equals(name)
                &&board.getPieces()[i-1][j]!=null&&board.getPieces()[i-1][j].getName().equals(name)
                &&board.getPieces()[i-2][j]!=null&&board.getPieces()[i-2][j].getName().equals(name)
                &&board.getPieces()[i+1][j]!=null&&board.getPieces()[i+1][j].getName().equals(name)){
            return true;
        }
        if (board.getPieces()[i][j]!=null&&board.getPieces()[i][j].getName().equals(name)
                &&board.getPieces()[i-1][j]!=null&&board.getPieces()[i-1][j-1].getName().equals(name)
                &&board.getPieces()[i+2][j]!=null&&board.getPieces()[i+2][j+2].getName().equals(name)
                &&board.getPieces()[i+1][j]!=null&&board.getPieces()[i+1][j+1].getName().equals(name)){
            return true;
        }
        if (board.getPieces()[i][j]!=null&&board.getPieces()[i][j].getName().equals(name)
                &&board.getPieces()[i+3][j]!=null&&board.getPieces()[i+3][j+3].getName().equals(name)
                &&board.getPieces()[i+2][j]!=null&&board.getPieces()[i+2][j+2].getName().equals(name)
                &&board.getPieces()[i+1][j]!=null&&board.getPieces()[i+1][j+1].getName().equals(name)){
            return true;
        }
        return false;
    }

}
