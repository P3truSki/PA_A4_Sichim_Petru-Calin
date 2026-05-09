import lombok.RequiredArgsConstructor;
import java.io.Serializable;

@RequiredArgsConstructor
class Cell implements Serializable {
    final int row, col;
    boolean top = true, right = true, bottom = true, left = true;
}