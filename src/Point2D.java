import java.util.Objects;

/**
 * This class represents a single, immutable 2D point represented using Cartesian coordinates. The
 * coordinates of this point are integral values.
 */
public final class Point2D {

  public final int x;
  public final int y;

  /**
   * Create a point given its x and y coordinates.
   *
   * @param x the x-coordinate of the point
   * @param y the y-coordinate of the point
   */
  public Point2D(int x, int y) {
    this.x = x;
    this.y = y;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  /**
   * Return the coordinate of the appropriate dimension.
   *
   * @param dimension the dimension number (0:x, 1:y)
   * @return the value of the dimension, if it is valid.
   * @throws IllegalArgumentException if the dimension is not valid (anything other than 0 or 1)
   */
  public int get(int dimension) throws IllegalArgumentException {
    if (dimension == 0) {
      return this.x;
    } else if (dimension == 1) {
      return this.y;
    }
    throw new IllegalArgumentException("Invalid dimension for point!");
  }

  /**
   * Return the square of the distance between this point and another.
   *
   * @param other the other point
   * @return the square of the distance between this point and the other, as an integer
   */
  public int sqrDist(Point2D other) {
    return (this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y);
  }

  /**
   * Return the euclidean distance between from this point and another.
   *
   * @param other the other point
   * @return the euclidean distance, as a double
   */
  public double distance(Point2D other) {
    return Math.sqrt(sqrDist(other));
  }

  @Override
  public String toString() {
    return "Point2D{"
        + "x=" + x
        + ", y=" + y
        + '}';
  }

  /**
   * Return if this point is the same as another. Two points are the same if they have the same x
   * and y coordinates
   *
   * @param o the other point
   * @return true if this==o, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Point2D)) {
      return false;
    }
    Point2D other = (Point2D) o;
    return this.x == other.x && this.y == other.y;
  }

  /**
   * Return the hashcode for this point, which depends on its coordinates.
   *
   * @return the hashcode for this point
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }
}
