import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Point {

    private Integer x;
    private Integer y;

    @Override
    public String toString() {
        return "(x=" + x + ", y=" + y + ")";
    }
}
