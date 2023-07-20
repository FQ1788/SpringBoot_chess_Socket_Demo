package chess.demo.service;

import chess.demo.bean.RequestUser;
import chess.demo.bean.ResponseChess;
import chess.demo.bean.ResponseRoom;
import chess.demo.bean.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class RoomService {
    
    @Autowired
    ChessService chessService;
    
    private static Map<String, Room> rooms = new HashMap();

    /**
     * 建立房間 
     * @return ResponseRoom 房間
     */
    public ResponseRoom addRoom(String playerName){
        String roomNumber = UUID.randomUUID().toString();
        Room room = new Room();
        room.setChessboard(chessService.startGame());
        room.setRoomNumber(roomNumber);
        room.setPlayerOne(playerName);
        rooms.put(roomNumber,room);
        ResponseRoom responseRoom = new ResponseRoom();
        responseRoom.setRoomNumber(roomNumber);
        responseRoom.setPlayerOne(playerName);
        responseRoom.setChessboard(room.getChessList());
        responseRoom.setOperateCode("200");
        return responseRoom;
    };

    /**
     * 進入房間
     * @param roomNumber 房號
     * @param playerName 玩家名稱
     * @return ResponseRoom 房間內容
     */
    public ResponseRoom inRoom(String roomNumber,String playerName){
        ResponseRoom responRoom = new ResponseRoom();
        Room room = rooms.get(roomNumber);
        
        if(room == null){
            responRoom.setOperateCode("404"); //404 找不到房間
            return responRoom; 
        }
        
        if(room.getPlayerOne() != null && room.getPlayerTwo() != null){
            responRoom.setOperateCode("300"); //300 滿人
            return responRoom;
        }

        if(room.getPlayerOne() == null){
            room.setPlayerOne(playerName);
        }else{
            room.setPlayerTwo(playerName);
        }
        
        responRoom.setRoomNumber(roomNumber);
        responRoom.setOperator(room.getOperator());
        responRoom.setPlayerOne(room.getPlayerOne());
        responRoom.setPlayerTwo(room.getPlayerTwo());
        responRoom.setChessboard(room.getChessList());
        responRoom.setOperateCode("200"); //200 OK
        return responRoom;
    };

    /**
     * 移除房間
     * @param roomNumber
     * @return
     */
    public String removeRoom(String roomNumber){
        String result = "NO";
        if(rooms.remove(roomNumber) != null){
            result = "OK";
        };
        return result;
    }

    /**
     * 玩家操作
     * @param player 來自玩家的訊息
     * @return 目前房間狀態
     */
    public ResponseRoom playerAction(RequestUser player){
        ResponseRoom responRoom = new ResponseRoom();
        Room room = rooms.get(player.getRoomNumber());
        
        if(room == null){
            responRoom.setOperateCode("404");
            return responRoom;
        }

        if(room.getPlayerOne() == null || room.getPlayerTwo() == null){
            responRoom.setOperateCode("301"); //301 玩家沒到齊
            return responRoom;
        }
        
        if(!"".equals(room.getOperator()) && !player.getPlayer().equals(room.getOperator())){
            responRoom.setOperateCode("302"); //302 還沒輪到該玩家
            return responRoom;
        }
        
        //玩家順序
        if("".equals(room.getOperator())){
            room.setOperator(player.getPlayer());
        }

        responRoom.setChessboard(chessService.action(room,player.getCoordinate()).getChessList());
        responRoom.setRoomNumber(room.getRoomNumber());
        responRoom.setPlayerOne(room.getPlayerOne());
        responRoom.setPlayerTwo(room.getPlayerTwo());
        responRoom.setOperator(room.getOperator());
        responRoom.setOperateCode("200");
        responRoom.getChessboard().get(room.getPick()[0]).get(room.getPick()[1]).setPush(true);
        return responRoom;
    }
}
