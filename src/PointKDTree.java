import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * PointKDTree implements the functionality of KD tree. builds the tree, calculates distance, calls
 * methods in Node classes for specific functionality
 */
public class PointKDTree implements SetOfPoints {

  private final int threshold;
  private Node root;

  /**
   * Constructor throws illegal argument exceptions on empty list and invalid size.
   *
   * @param points list
   * @throws IllegalArgumentException when points are null and sorted points are of incorrect size
   */
  public PointKDTree(List<Point2D> points) throws IllegalArgumentException {
    if (points == null) {
      throw new IllegalArgumentException("points list is null");
    }
    this.threshold = 1;
    List<Integer> pX = argSortList(points, 0);
    List<Integer> pY = argSortList(points, 1);
    if (pX.size() != pY.size()) {
      throw new IllegalArgumentException("pX and pY are not of same size");
    }
    root = buildKDTree(points, pX, pY, 0);
  }

  private Node buildKDTree(List<Point2D> points, List<Integer> pX, List<Integer> pY,
      int nodeLevel) {
    int a;
    int b;
    int c;

    if (pX.size() != pY.size()) {
      throw new IllegalArgumentException("pX and pY are not of same size");
    }
    // TODO: Change code according to the level
    if (pX.size() <= threshold) {
      if (pX.isEmpty()) {
        return new LeafNode(points, new ArrayList<>(), nodeLevel);
      }
      if (nodeLevel % 2 == 0) {
        return new LeafNode(points, pX, nodeLevel);
      } else {
        return new LeafNode(points, pY, nodeLevel);
      }
    }
    if (nodeLevel % 2 == 0) { //use vertical line.
      int medianIndex = pX.get(pX.size() / 2);
      int medianX;
      medianX = points.get(medianIndex).get(0);
      c = medianX * -1;
      //line is ax+by+c
      a = 1;
      b = 0;
    } else {
      int medianIndex = pY.get(pY.size() / 2);
      int medianY;
      medianY = points.get(medianIndex).get(1);
      c = medianY * -1;
      //line is ax+by+c
      a = 0;
      b = 1;
    }

    //divide points into before, after or on the line
    List<Integer> pXBefore = new ArrayList<>();
    List<Integer> pXAfter = new ArrayList<>();
    List<Integer> pYBefore = new ArrayList<>();
    List<Integer> pYAfter = new ArrayList<>();
    List<Integer> on = new ArrayList<>();

    for (int i = 0; i < pX.size(); i++) {
      int signedDist = signedDistance(points.get(pX.get(i)), a, b, c);
      if (signedDist < 0) {
        //insert into pxBefore
        pXBefore.add(pX.get(i));
      } else if (signedDist > 0) {
        //insert into pXAfter
        pXAfter.add(pX.get(i));
      } else {
        //insert in On
        on.add(pX.get(i));
      }
    }

    for (int i = 0; i < pY.size(); i++) {
      int signedDist = signedDistance(points.get(pY.get(i)), a, b, c);
      if (signedDist < 0) {
        //insert into pxBefore
        pYBefore.add(pY.get(i));
      } else if (signedDist > 0) {
        //insert into pXAfter
        pYAfter.add(pY.get(i));
      }
    }

    Node left = buildKDTree(points, pXBefore, pYBefore, nodeLevel + 1);
    Node right = buildKDTree(points, pXAfter, pYAfter, nodeLevel + 1);
    Node node = new NonLeafNode(left, right, points, on, a, b, c, nodeLevel);
    return node;
  }

  private int signedDistance(Point2D point, int a, int b, int c) {
    //find the signed distance of point from line ax+by+c=0
    return a * point.x + b * point.y + c;
  }

  @Override
  public Point2D closestPoint(Point2D point) {
    return root.closestPoint(point);
  }

  @Override
  public void add(Point2D point) {
    if (point == null) {
      return;
    }
    if (root.isLeafNode() && root.getPoints().size() > 0) {
      root = Utility.convertLeafNodeToNonLeafNode(root, point);
    } else {
      root.add(point);
      updatePoints(root, point);
    }
  }

  /**
   * updates points from add method.
   *
   * @param head  head
   * @param point point to add
   */
  private void updatePoints(Node head, Point2D point) {
    List<Point2D> points = head.getPoints();
    if (!points.contains(point)) {
      points.add(point);
    }
    if (!head.isLeafNode()) {
      updatePoints(head.getLeftNode(), point);
      updatePoints(head.getRightNode(), point);
    }
  }

  @Override
  public List<Point2D> getPoints() {
    return root.getPoints();
  }

  /**
   * find all points within the specified circle in the tree rooted at C.
   *
   * @param center of the circle
   * @param radius of the circle
   * @return list of points within circle
   */
  @Override
  public List<Point2D> allPointsWithinCircle(Point2D center, double radius) {
    return root.allPointsWithinCircle(center, radius);
  }

  @Override
  public String toString() {
    StringBuffer ret = new StringBuffer();
    printRecursiveInOrder(ret, root);
    return ret.toString();
  }

  /**
   * Helper method for toString.
   *
   * @param rec  string buffer
   * @param root root value
   */
  private void printRecursiveInOrder(StringBuffer rec, Node root) {
    if (root.isLeafNode()) {
      rec.append(root.getPoints().toString());
      return;
    }
    printRecursiveInOrder(rec, root.getLeftNode());
    rec.append(root.getPoints().toString());
    printRecursiveInOrder(rec, root.getRightNode());
  }


  /**
   * Helper method to sort points list and return x sorted and y sorted indices.
   *
   * @param points    initial point list
   * @param dimension x is 0, y is 1
   * @return indices in x and y sorted order
   */
  private List<Integer> argSortList(List<Point2D> points, int dimension) { //call by reference
    Comparator comp;
    List<Point2D> p = new ArrayList<>(points);
    List<Integer> sortedIndices = new ArrayList<>();
    if (dimension == 0) {
      comp = (Comparator<Point2D>) (o1, o2) -> {
        int xComp = Integer.compare(o1.x, o2.x);
        if (xComp == 0) {
          return Integer.compare(o1.y, o2.y);
        } else {
          return xComp;
        }
      };
    } else if (dimension == 1) {
      comp = (Comparator<Point2D>) (o1, o2) -> {
        int yComp = Integer.compare(o1.y, o2.y);
        if (yComp == 0) {
          return Integer.compare(o1.x, o2.x);
        } else {
          return yComp;
        }
      };
    } else {
      throw new IllegalArgumentException();
    }
    p.sort(comp);
    for (int i = 0; i < p.size(); i++) {
      sortedIndices.add(points.indexOf(p.get(i)));
    }
    return sortedIndices;
  }
}
