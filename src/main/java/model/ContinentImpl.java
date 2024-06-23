package model;

public class ContinentImpl implements Continent {
    String continentName;
    int continentId;
    int continentArmy;
    String ownership;

    public ContinentImpl(String continentName, int continentId, int continentArmy, String ownership) {
        this.continentName = continentName;
        this.continentId = continentId;
        this.continentArmy = continentArmy;
        this.ownership = ownership;
    }

    public ContinentImpl() {
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public int getContinentId() {
        return continentId;
    }

    public void setContinentId(int continentId) {
        this.continentId = continentId;
    }

    public int getContinentArmy() {
        return continentArmy;
    }

    public void setContinentArmy(int continentArmy) {
        this.continentArmy = continentArmy;
    }

    public String getOwnership() {
        return ownership;
    }

    public void setOwnership(String ownership) {
        this.ownership = ownership;
    }
}

