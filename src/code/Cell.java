package code;

public class Cell {
    private int size;
    private int value;
    private int iterationAmount=0;

    public Cell(int size, int value) {
        this.size = size;
        this.value = value;
    }
    public Cell(){};

    public void iterationInc(){
        iterationAmount++;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getIterationAmount() {
        return iterationAmount;
    }

    public void setIterationAmount(int iterationAmount) {
        this.iterationAmount = iterationAmount;
    }
}
