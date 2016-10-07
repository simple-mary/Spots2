package com.marina.spots.controller;

import com.marina.spots.Dot;
import com.marina.spots.DotValues;
import com.marina.spots.Game;
import com.marina.spots.dto.DotDTO;
import com.marina.spots.dto.OutputMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Queue;

@Controller
@RequestMapping("/")
public class MessageController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private Game game;

    @RequestMapping(method = RequestMethod.GET)
    public String viewApplication() {
        return "index";
    }

    @MessageMapping("/dots")
    @SendTo("/field/action")
    public OutputMessage sendMessage(DotDTO dotDTO) {
        logger.info("DotDTO sent");
        OutputMessage outputMessage = new OutputMessage();
//        if (isFinish) {
//            outputMessage.setFinish(true);
//            outputMessage.setScorePlayer1(game.getField().computeCapturedDots(DotValues.PLAYER1));
//            outputMessage.setScorePlayer2(game.getField().computeCapturedDots(DotValues.PLAYER2));
//        } else {
            outputMessage.setFree(DotValues.FREE.equals(game.getField().fieldPoints[dotDTO.getX()][dotDTO.getY()]));
            if (outputMessage.isFree()) {
                List<Queue<Dot>> cycles = game.game(dotDTO);
                outputMessage.setAllCyclesToDraw(cycles);
                outputMessage.setGameField(game.getField());
                outputMessage.setScorePlayer1(game.getField().computeCapturedDots(DotValues.PLAYER1));
                outputMessage.setScorePlayer2(game.getField().computeCapturedDots(DotValues.PLAYER2));
            }
//        }
        return outputMessage;
    }

    @MessageMapping("/finish")
    @SendTo("/field/action")
    public OutputMessage sendFinish(boolean isFinish)
    {
        return null;
    }
 }
