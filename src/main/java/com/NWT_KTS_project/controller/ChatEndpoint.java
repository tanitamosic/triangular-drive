package com.NWT_KTS_project.controller;

import com.NWT_KTS_project.DTO.MessageDTO;
import com.NWT_KTS_project.model.Issue;
import com.NWT_KTS_project.model.Message;
import com.NWT_KTS_project.model.users.User;
import com.NWT_KTS_project.service.IssueService;
import com.NWT_KTS_project.service.MessageService;
import com.NWT_KTS_project.websocket.SpringContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value="/ws/chat/{clientid}/{emailPrefix}")
public class ChatEndpoint {

    private static final Map<Integer, Session[]> allSessions = new ConcurrentHashMap<>();
    private MessageService messageService = SpringContext.getBean(MessageService.class);
    private IssueService issueService = SpringContext.getBean(IssueService.class);

    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatEndpoint.class);


    @OnOpen
    public void onOpen(Session session) {
        try {
            String email = extractPathEmail(session);
            Integer clientid = extractPathClientId(session);

            if (hasOpenSession(clientid, email)) {
                replaceSession(session, clientid, email);
            } else {
                placeSession(session, clientid);
            }
        } catch (Exception e) {
            LOGGER.error("Error on open web socket", e);
        }
    }

    private void placeSession(Session newSession, Integer clientid) {
        Session[] sessions = allSessions.get(clientid);
        for (int i = 0; i< sessions.length; i++ ) {
            if (null == sessions[i]) {
                sessions[i] = newSession;
                break;
            }
        }

    }

    private void replaceSession(Session newSession, Integer clientid, String email) {
        Session[] sessions = allSessions.get(clientid);
        for (int i = 0; i< sessions.length; i++ ) {
            if (null == sessions[i]) {
                continue;
            } else if (email.equals(extractPathEmail(sessions[i]))) {
                sessions[i] = newSession;
            }
        }

    }

    private boolean hasOpenSession(Integer clientid, String email) {
        if (null == allSessions.get(clientid)) {
            allSessions.put(clientid, new Session[] {null, null});
            return false;
        }
        Session[] sessions = allSessions.get(clientid);
        for (Session session : sessions) {
            if (null == session) {
                continue;
            } else if (email.equals(extractPathEmail(session))) {
                return true;
            }
        }
        return false;
    }

    private String extractPathEmail(Session session) {
        URI v1 = session.getRequestURI();
        String[] pathParts = v1.getPath().split("/");
        return pathParts[pathParts.length - 1];
    }
    private Integer extractPathClientId(Session session) {
        URI v1 = session.getRequestURI();
        String[] pathParts = v1.getPath().split("/");
        return Integer.valueOf(pathParts[pathParts.length - 2]);
    }

    private void removeSession(Integer clientid, String email) {
        Session[] sessions = allSessions.get(clientid);
        for (int i = 0; i< sessions.length; i++ ) {
            if (email.equals(extractPathEmail(sessions[i]))) {
                sessions[i] = null;
            }
        }
    }



    @OnMessage
    public void handleMessage(Session session, String messageJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {


            MessageDTO dto = mapper.readValue(messageJson, MessageDTO.class);
            System.out.println(dto);
            LocalDate date = LocalDate.parse(dto.getTime(), FORMATTER);
            System.out.println(date);

            LocalDateTime datetime = date.atStartOfDay();
            User sender = issueService.getSender(dto.getSender());
            User receiver = issueService.getReceiver(dto.getReceiver());
            Message msg = new Message(dto, sender, receiver, datetime);

            Issue i = issueService.getIssueById(dto.getIssueId());
            issueService.addMessage(i, msg);

            String email = extractPathEmail(session);
            Integer clientId = extractPathClientId(session);

            if (hasOpenSession(clientId, email)) {
                postMessageText(msg.getMessage(), session, clientId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void postMessageText(String text, Session session, Integer clientId) {
        Session[] sessions = allSessions.get(clientId);
        for (Session value : sessions) {
            try {
                if (null == value) {
                    continue;
                }
                if (value.hashCode() != session.hashCode()) {
                    value.getBasicRemote().sendText(text);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    @OnClose
    public void onClose(Session session) {
        LOGGER.info("{} disconnected", session.getId());
        URI v1 = session.getRequestURI();
        String[] pathParts = v1.getPath().split("/");
        String email = pathParts[pathParts.length - 1];
        Integer clientid = Integer.parseInt(pathParts[pathParts.length-2]);
        removeSession(clientid, email);
    }

    public void setIssueService(IssueService is) {this.issueService = is;}
    public void setMessageService(MessageService ms) {this.messageService = ms;}
}
