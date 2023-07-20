package chess.demo.bean;

import java.util.List;

public class ResponseRoom {
    
    private String roomNumber;
    
    private List<List<ResponseChess>> chessboard;
    
    private String playerOne;
    
    private String playerTwo;
    
    private String operateCode;
    
    private String operator;
    
    private List<String> dieChess;

    public List<String> getDieChess() {
        return dieChess;
    }

    public void setDieChess(List<String> dieChess) {
        this.dieChess = dieChess;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public List<List<ResponseChess>> getChessboard() {
        return chessboard;
    }

    public void setChessboard(List<List<ResponseChess>> chessboard) {
        this.chessboard = chessboard;
    }

    public String getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(String playerOne) {
        this.playerOne = playerOne;
    }

    public String getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(String playerTwo) {
        this.playerTwo = playerTwo;
    }

    public String getOperateCode() {
        return operateCode;
    }

    public void setOperateCode(String operateCode) {
        this.operateCode = operateCode;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
