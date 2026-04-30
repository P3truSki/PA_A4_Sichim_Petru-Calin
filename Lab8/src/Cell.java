import java.io.Serializable;

//compulsory
class Cell implements Serializable {
    int row, col;
    boolean top = true, right = true, bottom = true, left = true;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }
}
