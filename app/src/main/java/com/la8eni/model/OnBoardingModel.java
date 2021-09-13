package com.la8eni.model;

public class OnBoardingModel
{

    private int imageOnBoarding;
    private String titleOnBoarding;
    private String descriptionOnBoarding;

    public OnBoardingModel()
    {
    }

    public OnBoardingModel(int imageOnBoarding, String titleOnBoarding, String descriptionOnBoarding)
    {
        this.imageOnBoarding = imageOnBoarding;
        this.titleOnBoarding = titleOnBoarding;
        this.descriptionOnBoarding = descriptionOnBoarding;
    }

    public int getImageOnBoarding() {
        return imageOnBoarding;
    }

    public String getTitleOnBoarding() {
        return titleOnBoarding;
    }

    public String getDescriptionOnBoarding() {
        return descriptionOnBoarding;
    }
}
