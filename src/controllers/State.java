package controllers;

class State {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    String lat;
    String lon;

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public State(String name, String lat, String lon) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }
}