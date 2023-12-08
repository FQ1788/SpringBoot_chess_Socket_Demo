package chess.demo.service;

import chess.demo.bean.RequestUser;
import chess.demo.bean.ResponseRoom;
import chess.demo.bean.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
        room.setDieChess(new ArrayList<>());
        rooms.put(roomNumber,room);
        return roomToResponse(room);
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
        
        return roomToResponse(room); //200 OK
    };

    /**
     * 移除房間
     * @param player
     * @return
     */
    public ResponseRoom removeRoom(RequestUser player){
        ResponseRoom responseRoom = new ResponseRoom();
        String code = "404";
        if(rooms.remove(player.getRoomNumber()) != null){
            code = "100";
        };
        responseRoom.setOperateCode(code);
        return responseRoom;
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
        chessService.action(room,player.getCoordinate());
        return roomToResponse(room);
    }

    /**
     * 重新開始
     * @param player
     * @return
     */
    public ResponseRoom restartRoom(RequestUser player){
        Room room = rooms.get(player.getRoomNumber());
        if(room != null){
            chessService.restartGame(room);
        }
        return roomToResponse(room);
    }
    
    public ResponseRoom watchRoom(RequestUser player){
        return roomToResponse(rooms.get(player.getRoomNumber()));
    }
    
    private ResponseRoom roomToResponse(Room room){
        ResponseRoom responseRoom = new ResponseRoom();
        
        if(room != null){
            responseRoom.setChessboard(room.getChessList());
            responseRoom.setRoomNumber(room.getRoomNumber());
            responseRoom.setPlayerOne(room.getPlayerOne());
            responseRoom.setPlayerTwo(room.getPlayerTwo());
            responseRoom.setOperator(room.getOperator());
            responseRoom.setDieChess(room.getDieChess());
            responseRoom.setOperateCode("200");
            if(room.getPick() != null){
                responseRoom.getChessboard().get(room.getPick()[0]).get(room.getPick()[1]).setPush(true);
            }
        }else{
            responseRoom.setOperateCode("404");
        }
        return responseRoom;
    }
}
