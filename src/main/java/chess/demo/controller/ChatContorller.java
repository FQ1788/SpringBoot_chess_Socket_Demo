package chess.demo.controller;

import chess.demo.bean.RequestMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@CrossOrigin
public class ChatContorller {

    @MessageMapping("/chat/{roomNumber}")
    @SendTo("/out/chat/{roomNumber}")
    public RequestMessage sendMessage(@DestinationVariable("roomNumber") String roomNumber, 
        @RequestBody RequestMessage request){
        System.out.println("sendMessage，roomNumber --> " + roomNumber);
        System.out.println(request.getPlayer() + " : " + request.getMessage());
        return request;
    }
}
