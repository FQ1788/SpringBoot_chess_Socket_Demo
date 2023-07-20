package chess.demo.controller;

import chess.demo.bean.RequestUser;
import chess.demo.bean.ResponseRoom;
import chess.demo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@CrossOrigin
public class ChessController {
    
    @Autowired
    RoomService roomService;

    @MessageMapping("/playerAction/{roomNumber}")
    @SendTo("/out/{roomNumber}")
    public ResponseRoom playerAction(@DestinationVariable("roomNumber") String roomNumber, @RequestBody RequestUser requestUser){
        System.out.println(" playerAction -- start!!");
        System.out.println(" roomNumber --> " + roomNumber);
        System.out.println(" requestUser --> " + requestUser.getPlayer());
        return roomService.playerAction(requestUser);
    }

    @MessageMapping("/restartGame/{roomNumber}")
    @SendTo("/out/{roomNumber}")
    public ResponseRoom restartGame(@DestinationVariable("roomNumber") String roomNumber, @RequestBody RequestUser requestUser){
        System.out.println(" playerAction -- start!!");
        System.out.println(" roomNumber --> " + roomNumber);
        System.out.println(" requestUser --> " + requestUser.getPlayer());
        return roomService.restartRoom(requestUser);
    }
}
