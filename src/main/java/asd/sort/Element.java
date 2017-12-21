package asd.sort;

public class Element {
    long value;
    long firstLineWhereFounded;

    public Element(long value, long firstLineWhereFounded) {
        this.value = value;
        this.firstLineWhereFounded = firstLineWhereFounded;
    }

    public Element(String toStringRepresentation){
        String [] splitted =toStringRepresentation.split("->");
        this.value = Long.valueOf(splitted[0]);
        this.firstLineWhereFounded = Long.valueOf(splitted[1]);
    }

    public long getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public long getFirstLineWhereFounded() {
        return firstLineWhereFounded;
    }

    public void setFirstLineWhereFounded(int firstLineWhereFounded) {
        this.firstLineWhereFounded = firstLineWhereFounded;
    }



    @Override
    public String toString() {
        return value + "->"+ firstLineWhereFounded;
    }
}
