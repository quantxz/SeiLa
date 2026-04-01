package net.bolinhu.moddoscrias.custom;

public class PlayerProperties implements IPlayerProperties {
    private int resistance;

    public PlayerProperties() {
        this.resistance = 5;
    }

    @Override
    public int getBeerResistence() {
        return resistance;
    }

    @Override
    public void setBeerResistence(int value) {
        this.resistance += value;
    }
}