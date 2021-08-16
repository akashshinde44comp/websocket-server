package com.andy.websocket.controller;

import com.andy.websocket.domain.Message;
import com.andy.websocket.service.SessionManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;


@Controller
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private SessionManagementService sessionManagementService;

    @Autowired
    public UserController(SessionManagementService sessionManagementService) {
        this.sessionManagementService = sessionManagementService;
    }

    @MessageMapping("/wss-chat/user")
    @SendTo("/wss-user-events/messages")
    public Message sendMessage(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
        logger.info("Received msg here ");
        logger.info(headerAccessor.toString());
        logger.info(message.toString());
        return message;
    }

    //TODO create sessionn here
    @MessageMapping("/wss-chat/message/send/{sessionId}")
    @SendTo("/wss-user-events/messages")
    public Message newUser(@Payload Message message, @DestinationVariable String sessionId,
                           SimpMessageHeaderAccessor headerAccessor) {
        logger.info("Session Id : " + headerAccessor.getSessionId());
      //  headerAccessor.getSessionAttributes().put("username", message.getUserName());
        logger.info(message.toString());
        return message;
    }

}

