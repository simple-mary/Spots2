package com.marina.spots.controller;

import com.marina.spots.Dot;
import com.marina.spots.DotValues;
import com.marina.spots.Game;
import com.marina.spots.dto.Command;
import com.marina.spots.dto.InputMessage;
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
    public OutputMessage sendMessage(InputMessage inputMessage) {
        OutputMessage outputMessage = new OutputMessage();
        for (Command command : inputMessage.getManageCommands()) {
            switch (command) {
                case SET_DOT:
                    outputMessage.setFree(DotValues.FREE.equals(game.getField()
                            .fieldPoints[inputMessage.getDotDTO().getX()][inputMessage.getDotDTO().getY()]));
                    if (outputMessage.isFree()
                            && inputMessage.getDotDTO().getDotValues().name().equals(game.getActivePlayer())) {
                        List<Queue<Dot>> cycles = game.game(inputMessage.getDotDTO());
                        outputMessage.setAllCyclesToDraw(cycles);
                    }
                    outputMessage.setFinish(game.isFinish());
                    break;
                case FINISH_GAME:
                    outputMessage.setFinish(true);
                    game.getField().blockAllFreeDots();
                    break;
                case GET_FIELD_STATE:
                    outputMessage.setGameField(game.getField());
                    break;
                case GET_SCORE:
                    outputMessage.setScorePlayer1(game.getField().computeCapturedDots(DotValues.PLAYER1));
                    outputMessage.setScorePlayer2(game.getField().computeCapturedDots(DotValues.PLAYER2));
                    break;
                case NEW_GAME:
                    game.init();
                    outputMessage.setClear(true);
                    break;
                case GET_ACTIVE_PLAYER:
                    outputMessage.setActivePlayer(game.getActivePlayer());
                    break;
                default:
                    throw new IllegalArgumentException("No such command " + command);
            }
        }
        return outputMessage;
    }
}
