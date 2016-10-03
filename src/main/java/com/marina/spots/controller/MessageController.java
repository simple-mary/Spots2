package com.marina.spots.controller;

import com.marina.spots.Game;
import com.marina.spots.dto.DotDTO;
import com.marina.spots.dto.OutputMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
@RequestMapping("/")
public class MessageController {

  private Logger logger = LoggerFactory.getLogger(getClass());
  private Game game = new Game();
  @RequestMapping(method = RequestMethod.GET)
  public String viewApplication() {
    return "index";
  }

  @MessageMapping("/chat")
  @SendTo("/topic/message")
  public OutputMessage sendMessage(DotDTO dotDTO) {
    logger.info("DotDTO sent");
    try {
      if(game.game(dotDTO)) {
        return new OutputMessage(dotDTO, new Date());
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return null;
  }
}
