package com.marina.spots.controller;

import com.marina.spots.Dot;
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

  @RequestMapping(method = RequestMethod.GET)
  public String viewApplication() {
    return "index";
  }

  @MessageMapping("/app/chat")
  @SendTo("/topic/message")
  public OutputMessage sendMessage(Dot dot) {
    logger.info("Message sent");
    return new OutputMessage(dot, new Date());
  }
}
