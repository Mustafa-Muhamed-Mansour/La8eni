package com.la8eni.model;

public class ChatMessageModel
{

    private String chatRadnomKey;
    private String chatUserID;
    private String message;
    private String typeMessage;

    public ChatMessageModel()
    {
    }

    public ChatMessageModel(String chatRadnomKey, String chatUserID, String message, String typeMessage)
    {
        this.chatRadnomKey = chatRadnomKey;
        this.chatUserID = chatUserID;
        this.message = message;
        this.typeMessage = typeMessage;
    }

    public String getChatRadnomKey() {
        return chatRadnomKey;
    }

    public String getChatUserID() {
        return chatUserID;
    }

    public String getMessage() {
        return message;
    }

    public String getTypeMessage() {
        return typeMessage;
    }
}
