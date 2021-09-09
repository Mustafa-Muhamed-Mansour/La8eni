package com.la8eni.model;

public class GroupModel
{

    private String groupRandomKey;
    private String groupID;
    private String groupName;
    private String groupImage;


    public GroupModel()
    {
    }

    public GroupModel(String groupRandomKey, String groupID, String groupName, String groupImage)
    {
        this.groupRandomKey = groupRandomKey;
        this.groupID = groupID;
        this.groupName = groupName;
        this.groupImage = groupImage;
    }

    public String getGroupRandomKey() {
        return groupRandomKey;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getGroupImage() {
        return groupImage;
    }
}
