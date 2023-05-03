import java.util.List;

/**
 * Interface that has operations of KDTree.
 */
public interface SetOfPoints {

  void add(Point2D point);

  List<Point2D> getPoints();

  List<Point2D> allPointsWithinCircle(Point2D center, double radius);

  Point2D closestPoint(Point2D point);

}
