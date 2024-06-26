package chess.demo.bean;

import chess.demo.enums.ChessEnum;

public class Chess {

    private String chessNo;
 
    private String camp;

    private Integer level;

    private boolean open;

    public Chess(String chessNo,boolean open){
        ChessEnum chess = ChessEnum.valueOf(chessNo);
        this.open = open;
        this.chessNo = chessNo;
        this.camp = chess.getCamp();
        this.level = chess.getLevel();
    }

    public String getChessNo() {
        return chessNo;
    }

    public void setChessNo(String chessNo) {
        this.chessNo = chessNo;
    }

    public String getCamp() {
        return camp;
    }

    public void setCamp(String camp) {
        this.camp = camp;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }
}
