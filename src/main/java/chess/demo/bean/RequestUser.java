package chess.demo.bean;

public class RequestUser {
    
    private String roomNumber;
    
    private String player;
    
    private Integer[] coordinate;

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Integer[] getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Integer[] coordinate) {
        this.coordinate = coordinate;
    }
}
