package chess.demo.bean;

import java.util.ArrayList;
import java.util.List;

public class Room {
    
    private String roomNumber;
    
    private String playerOne;
    
    private String playerTwo;
    
    private String operator = "";
    
    private String camp = "";

    private Chess[][] chessboard;

    private Integer[] pick;
    
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Chess[][] getChessboard() {
        return chessboard;
    }

    public void setChessboard(Chess[][] chessboard) {
        this.chessboard = chessboard;
    }

    public Integer[] getPick() {
        return pick;
    }

    public void setPick(Integer[] pick) {
        this.pick = pick;
    }
    
    public String getCamp() {
        return camp;
    }

    public void setCamp(String camp) {
        this.camp = camp;
    }

    public List<List<ResponseChess>> getChessList(){
        List<List<ResponseChess>> chessLists = new ArrayList<>();
        List<ResponseChess> chessList = null;
        ResponseChess responseChess = null;
        for(Chess[] chessArr : this.chessboard){
            chessList = new ArrayList<>();
            for(Chess chess : chessArr){
                responseChess = new ResponseChess();
                if(chess != null){
                    if(chess.getOpen()){
                        responseChess.setChessNo(chess.getChessNo());
                    }else{
                        responseChess.setChessNo("no14");
                    };
                }else{
                    responseChess.setChessNo("no15");
                }
                chessList.add(responseChess);
            }
            chessLists.add(chessList);
        }

        return chessLists;
    }
    
    public void nextPlayer(){
        if(this.operator.equals(this.playerOne)){
            this.operator = this.playerTwo;
        }else{
            this.operator = this.playerOne;
        }
        
        if("RED".equals(this.camp)){
            this.camp = "BLACK";
        }else{
            this.camp = "RED";
        }
    }
}
