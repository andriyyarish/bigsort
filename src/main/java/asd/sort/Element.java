package asd.sort;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Element element = (Element) o;
        return value == element.value &&
                firstLineWhereFounded == element.firstLineWhereFounded;
    }

    @Override
    public int hashCode() {

        return Objects.hash(value);
    }
}
