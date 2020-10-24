package pl.trollcraft.pvp.ranking.core;

public abstract class Position implements Comparable<Position> {

    protected String name;
    private Position previous;
    private Position next;

    public Position(String name){
        this.name = name;
    }

    public abstract String toString(int ind);

    public String getName() { return name; }
    public Position getPrevious() { return previous; }
    public Position getNext() { return next; }

    public void setPrevious(Position previous) { this.previous = previous; }
    public void setNext(Position next) { this.next = next; }
}
