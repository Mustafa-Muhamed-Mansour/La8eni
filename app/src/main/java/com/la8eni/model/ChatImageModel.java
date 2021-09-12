package com.la8eni.model;

public class ChatImageModel
{

    private String chatRadnomKey;
    private String chatUserID;
    private String image;
    private String typeMessage;

    public ChatImageModel()
    {
    }

    public ChatImageModel(String chatRadnomKey, String chatUserID, String image, String typeMessage)
    {
        this.chatRadnomKey = chatRadnomKey;
        this.chatUserID = chatUserID;
        this.image = image;
        this.typeMessage = typeMessage;
    }

    public String getChatRadnomKey() {
        return chatRadnomKey;
    }

    public String getChatUserID() {
        return chatUserID;
    }

    public String getImage() {
        return image;
    }

    public String getTypeMessage() {
        return typeMessage;
    }
}
