import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Cell {
    final int r, c;
    boolean top = true, right = true, bottom = true, left = true;
}