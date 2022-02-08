import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Main {

    public static List<List<Integer>> sea = List.of(
            List.of(1, 0, 0, 0, 0, 0),
            List.of(1, 1, 0, 0, 1, 0),
            List.of(0, 0, 0, 0, 0, 1),
            List.of(1, 1, 0, 0, 0, 0),
            List.of(1, 1, 1, 0, 1, 1)
    );

    public static void main(String[] args) {

        Set<Point> points = new HashSet<>(extractPointsOfIcebergs(sea));
        List<Iceberg> icebergs = new ArrayList<>();

        if (!points.isEmpty()) {
            icebergs.addAll(extractIcebergsFromPoints(
                    points.stream().findAny().get(),
                    points,
                    new HashSet<>()
            ));
        }

        icebergs.forEach(i -> System.out.println("ICEBERG, points of iceberg - " + i.getPoints()));

        System.out.println("Number of icebergs - " + icebergs.size());
    }

    public static Set<Iceberg> extractIcebergsFromPoints(Point pointToSearch, Set<Point> points, Set<Iceberg> icebergs) {

        Set<Point> pointsOfIceberg = findHierarchyNeighboringPoints(
                points,
                pointToSearch,
                new HashSet<>()
        );
        pointsOfIceberg.add(pointToSearch);

        icebergs.add(Iceberg.builder()
                .points(pointsOfIceberg)
                .build()
        );

        points.removeAll(pointsOfIceberg);

        if (!points.isEmpty()) {
            icebergs.addAll(extractIcebergsFromPoints(
                    points.stream().findAny().get(),
                    points,
                    icebergs
            ));
        }

        return icebergs;
    }

    public static Set<Point> findHierarchyNeighboringPoints(
            Set<Point> pointsToSearchBy,
            Point pointToFindNeighbours,
            Set<Point> neighbours
    ) {

        Set<Point> neighboringPoints = pointsToSearchBy.stream().filter(p ->
                (p.getY().equals(pointToFindNeighbours.getY()) && Math.abs(pointToFindNeighbours.getX() - p.getX()) == 1)
                        ||
                        (p.getX().equals(pointToFindNeighbours.getX()) && Math.abs(pointToFindNeighbours.getY() - p.getY()) == 1)
        ).collect(Collectors.toSet());

        neighbours.addAll(neighboringPoints);

        neighboringPoints.forEach(np -> neighbours.addAll(findHierarchyNeighboringPoints(
                pointsToSearchBy.stream().filter(p -> !neighbours.contains(p)).collect(Collectors.toSet()),
                np,
                neighbours
        )));

        return neighbours;
    }

    public static Set<Point> extractPointsOfIcebergs(List<List<Integer>> sea) {
        Set<Point> icebergPoints = new HashSet<>();

        for (int i = sea.size() - 1; i >= 0; i--) {
            int y = sea.size() - i;
            List<Integer> latitude = sea.get(i);

            for (int j = 0; j < latitude.size(); j++) {
                int x = j + 1;
                Integer longitude = latitude.get(j);

                if (longitude == 1) {
                    icebergPoints.add(
                            Point.builder()
                                    .x(x)
                                    .y(y)
                                    .build()
                    );
                }
            }
        }

        return icebergPoints;
    }


}
