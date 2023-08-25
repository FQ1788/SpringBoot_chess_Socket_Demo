package chess.demo.service;

import chess.demo.bean.Chess;
import chess.demo.bean.Room;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
public class ChessService {
    
    private Random random = new Random();

    /**
     * 開始遊戲(新建遊戲)
     * @return Chess[][] 目前棋盤的座標，
     */
    public Chess[][] startGame(){
        Chess[][] checkerboard = new Chess[4][8];
        String[] piece = {
            "no1","no2","no3","no4","no5", 
            "no1","no2","no3","no4","no5",
            "no8","no9","no10","no11","no12",
            "no8","no9","no10","no11","no12",
            "no0","no0","no0","no0","no0",
            "no7","no7","no7","no7","no7",
            "no6","no13"
        };
        
        String temporaryStr = null;
        
        for(int y = 0,rnumber = 0, start = piece.length-1; y < checkerboard.length; y++ ) {
            for(int x = 0; x < checkerboard[y].length; x++,start--) {
                if(start != 0) {
                    rnumber = random.nextInt(start);
                }else {
                    rnumber = 0;
                }
                temporaryStr = piece[start];
                piece[start] = piece[rnumber];
                piece[rnumber] = temporaryStr;
                checkerboard[y][x] = new Chess(piece[start],false);
            }
        }
        return checkerboard;
    };
    
    public Room action(Room room,Integer[] coordinate){
        Integer[] pick = room.getPick();

        if(pick == null){
            oneClick(room,coordinate);
        }else{
            twoClick(room,coordinate);
        }
        
        return room;
    }

    /**
     * 玩家第一次點擊
     * @param room 遊玩的房間
     * @param coordinate 玩家點擊的座標(Y,X)
     */
    private void oneClick(Room room, Integer[] coordinate){
        Chess chess = room.getChessboard()[coordinate[0]][coordinate[1]];
        String camp = room.getCamp();

        //沒有棋子，直接返回。
        if(chess == null){
            return;
        }
        
        //翻開棋子
        if(!chess.getOpen()){
            //第一次選擇的棋子來分陣營
            if("".equals(camp)){
                room.setCamp(chess.getCamp());
            }
            chess.setOpen(true);
            room.nextPlayer();
            return;
        }
        
        //已翻開的棋子
        if(camp.equals(chess.getCamp())){
            room.setPick(coordinate);
            return;
        }
    }

    /**
     * 玩家第二次點擊
     * @param room
     * @param coordinate
     */
    private void twoClick(Room room, Integer[] coordinate){
        Integer[] pick = room.getPick();
        Chess[][] chessboard = room.getChessboard();
        boolean checkMove = checkMove(pick,chessboard,coordinate,room.getCamp());
        
        if(checkMove){
            Chess oneChess = chessboard[pick[0]][pick[1]];
            Chess twoChess = chessboard[coordinate[0]][coordinate[1]];
            
            if(twoChess != null){
                room.getDieChess().add(twoChess.getChessNo());
            }
            chessboard[coordinate[0]][coordinate[1]] = oneChess;
            chessboard[pick[0]][pick[1]] = null;
            room.nextPlayer();
        }

        room.setPick(null);
        return;
    }

    /**
     * 判斷棋子是否可以移動
     * @param pick 第一次點擊的座標
     * @param chessboard 當前棋盤內容
     * @param coordinate 第二次點擊的座標
     * @param roomCamp 操作者陣營
     * @return boolean 移動成功與否
     */
    public boolean checkMove(Integer[] pick,Chess[][] chessboard,Integer[] coordinate,String roomCamp){
        Chess oneChess = chessboard[pick[0]][pick[1]];
        Chess twoChess = chessboard[coordinate[0]][coordinate[1]];
        int distanceY = Math.abs(pick[0] - coordinate[0]);
        int distanceX = Math.abs(pick[1] - coordinate[1]);
        
        //目標位置為空。
        if(twoChess == null){
            //移動步數異常，無效操作。
            if(distanceY + distanceX != 1){
                return false;
            }else{
                return true;
            }
        }else{
            //除了砲以外移動異常、目標位置沒翻開、目標位置同陣營，無效操作。
            if(oneChess.getLevel() != 1 && distanceY + distanceX != 1 || !twoChess.getOpen() || twoChess.getCamp().equals(roomCamp)){
                return false;
            }

            if(oneChess.getLevel() == 1){ //砲的移動 比較特別 
                if(distanceX == 0 && distanceY != 0){
                    return checkY(chessboard,pick[0],coordinate[0],pick[1]);
                }else if(distanceY == 0 && distanceX != 0){
                    return checkX(chessboard,pick[1],coordinate[1],pick[0]);
                }
            }else{
                return checkEat(oneChess,twoChess);
            }
        }
        return false;
    }

    /**
     * 檢查能不能吃
     * @param oneChess 要吃人的棋子
     * @param twoChess 被吃的棋子
     * @return boolean 是否能吃
     */
    private boolean checkEat(Chess oneChess, Chess twoChess){
        boolean result = false;
        
        if(oneChess.getLevel() == 6 && twoChess.getLevel() == 0){
            //將or帥 不能吃 兵or卒
        }else if(oneChess.getLevel() >= twoChess.getLevel()){
            //一般吃吃
            result = true;
        }else if(oneChess.getLevel() == 0 && twoChess.getLevel() == 6){
            //兵or卒 吃 將or帥
            result = true;
        }else if(oneChess.getLevel() == 1){
            result = true;
        }
        
        return result;
    }

    /**
     * 判斷 '砲' 的X軸(橫向)移動
     * @param chessboard 棋盤
     * @param oneX X起始點
     * @param twoX X終點
     * @param y Y軸(直)座標
     * @return 是否成功移動
     */
    private boolean checkX(Chess[][] chessboard,int oneX,int twoX,int y){
        int checkChess = 0;
        int start = 0;
        int end = 0;
        
        if(oneX > twoX){
            start = twoX;
            end = oneX;
        }else {
            start = oneX;
            end = twoX;
        }
        
        while(start <= end){
            if(chessboard[y][start] != null){
                checkChess++;
            }
            start++;
        }
        
        if(checkChess != 3){
            return false;       
        }
        
        return checkEat(chessboard[y][oneX],chessboard[y][twoX]);
    }

    /**
     * 判斷 '砲' 的Y軸(直行)移動
     * @param chessboard 棋盤
     * @param oneY Y起始點
     * @param twoY Y終點
     * @param X X軸(橫)座標
     * @return 是否成功移動
     */
    private boolean checkY(Chess[][] chessboard,int oneY,int twoY,int X){
        int checkChess = 0;
        int start = 0;
        int end = 0;

        if(oneY > twoY){
            start = twoY;
            end = oneY;
        }else {
            start = oneY;
            end = twoY;
        }

        while(start <= end){
            if(chessboard[start][X] != null){
                checkChess++;
            }
            start++;
        }

        if(checkChess != 3){
            return false;
        }

        return checkEat(chessboard[oneY][X],chessboard[twoY][X]);
    }

    /**
     * 重新開始
     * @param room
     * @return
     */
    public Room restartGame(Room room){
        room.setChessboard(startGame());
        room.setOperator("");
        room.setCamp("");
        room.setPick(null);
        room.setDieChess(new ArrayList<>());
        return room;
    }
}
