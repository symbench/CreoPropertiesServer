package org.symbench.creointerferenceserver.creo;

public class InterferenceDetails {
    private String part1Name;
    private String part2Name;
    private double interferenceVolume;

    public InterferenceDetails(String part1Name, String part2Name, double interferenceVolume) {
        this.part1Name = part1Name;
        this.part2Name = part2Name;
        this.interferenceVolume = interferenceVolume;
    }

    public String getPart1Name() {
        return part1Name;
    }

    public void setPart1Name(String part1Name) {
        this.part1Name = part1Name;
    }

    public String getPart2Name() {
        return part2Name;
    }

    public void setPart2Name(String part2Name) {
        this.part2Name = part2Name;
    }

    public double getInterferenceVolume() {
        return interferenceVolume;
    }

    public void setInterferenceVolume(double interferenceVolume) {
        this.interferenceVolume = interferenceVolume;
    }
}