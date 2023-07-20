package chess.demo.enums;

public enum ChessEnum {
    no0(0,"RED","兵"),
    no1(1,"RED","砲"),
    no2(2,"RED","傌"),
    no3(3,"RED","俥"),
    no4(4,"RED","像"),
    no5(5,"RED","仕"),
    no6(6,"RED","帥"),
    no7(0,"BLACK","卒"),
    no8(1,"BLACK","砲"),
    no9(2,"BLACK","馬"),
    no10(3,"BLACK","車"),
    no11(4,"BLACK","象"),
    no12(5,"BLACK","士"),
    no13(6,"BLACK","將");
    //no14 未翻開、 no15 空的。 

    private Integer level;

    private String camp;

    private String chess;

    ChessEnum(Integer level, String camp, String chess) {
        this.level = level;
        this.camp = camp;
        this.chess = chess;
    }

    public Integer getLevel() {
        return level;
    }

    public String getCamp() {
        return camp;
    }

    public String getChess() {
        return chess;
    }
}
