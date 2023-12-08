package chess.demo.controller;

import chess.demo.bean.RequestUser;
import chess.demo.bean.ResponseRoom;
import chess.demo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class RoomController {

    @Autowired
    private RoomService roomService;
    
    @Autowired
    private SimpMessagingTemplate SimpMessagingTemplate;
    
    @RequestMapping("/addRoom")
    public ResponseRoom addRoom(@RequestBody RequestUser player){
        System.out.println(" addRoom -- start !!");
        System.out.println(" Player -> " + player.getPlayer());
        ResponseRoom room =  roomService.addRoom(player.getPlayer());
        System.out.println(" RoomName -> " + room.getRoomNumber());
        return room;
    }

    @RequestMapping("/inRoom")
    public ResponseRoom inRoom(@RequestBody RequestUser player){
        System.out.println(" inRoom -- start !!");
        System.out.println(" Room -> " + player.getRoomNumber());
        System.out.println(" Player -> " + player.getPlayer());
        ResponseRoom responseRoom = roomService.inRoom(player.getRoomNumber(),player.getPlayer());
        
        //成功加入才發送給其他人
        if("200".equals(responseRoom.getOperateCode())){
            SimpMessagingTemplate.convertAndSend("/out/" + responseRoom.getRoomNumber(), responseRoom);
        }
        
        return responseRoom;
    }
    
    @RequestMapping("/removeRoom")
    public void removeRoom(@RequestBody RequestUser player){
        System.out.println("-- removeRoom -- ");
        
        String roomNumber = player.getRoomNumber();
        System.out.println(" Room -> " + roomNumber);
        System.out.println(" Player -> " + player.getPlayer());
        ResponseRoom responseRoom = roomService.removeRoom(player);
        
        if("100".equals(responseRoom.getOperateCode())){
            SimpMessagingTemplate.convertAndSend("/out/" + roomNumber, responseRoom);
        }
    }

    @RequestMapping("/watchRoom")
    public ResponseRoom watchRoom(@RequestBody RequestUser player){
        System.out.println(" watchRoom -- start !!");
        System.out.println(" Room -> " + player.getRoomNumber());
        System.out.println(" Player -> 觀眾");
        return roomService.watchRoom(player);
    }
}
