package controllers;

public class DetailsView {

    private  String about;
    private  String numbers;

    public  DetailsView (String about, String numbers){
        this.about =about;
        this.numbers =numbers;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
}
