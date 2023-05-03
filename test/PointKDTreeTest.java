import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.Before;
import org.junit.Test;

/**
 * Test class to test features of KD Tree implementation.
 */
public class PointKDTreeTest {

  Point2D point2D;
  List<Point2D> list1 = new ArrayList<>();
  List<Point2D> listN = new ArrayList<>();

  Comparator comparator;

  Random random;


  @Before
  public void setUp() {
    random = new Random();

    list1.add(new Point2D(40, 45));
    list1.add(new Point2D(15, 70));
    list1.add(new Point2D(70, 10));
    list1.add(new Point2D(69, 50));
    list1.add(new Point2D(66, 85));
    list1.add(new Point2D(85, 90));

    listN.add(new Point2D(51, 75));
    listN.add(new Point2D(25, 40));
    listN.add(new Point2D(70, 70));
    listN.add(new Point2D(10, 30));
    listN.add(new Point2D(35, 90));
    listN.add(new Point2D(55, 1));
    listN.add(new Point2D(60, 80));
    listN.add(new Point2D(1, 10));
    listN.add(new Point2D(50, 50));

    comparator = (Comparator<Point2D>) (o1, o2) -> {
      int xComp = Integer.compare(o1.x, o2.x);
      if (xComp == 0) {
        return Integer.compare(o1.y, o2.y);
      } else {
        return xComp;
      }
    };

  }

  @Test
  public void illegalArgumentWhenNullPointGivenToConstructor() {
    assertThrows(IllegalArgumentException.class, () -> new PointKDTree(null));
  }

  @Test
  public void testEmptyTreeDoesNotThrowException() {
    PointKDTree pointKDTree = new PointKDTree(Collections.emptyList());
    assertEquals(Collections.emptyList(), pointKDTree.getPoints());
  }

  @Test
  public void testToStringForNodesLeftLessThanRootRightGreater() {

    List<Point2D> list = new ArrayList<>();
    list.add(new Point2D(7, 2));
    list.add(new Point2D(5, 4));
    list.add(new Point2D(2, 3));
    list.add(new Point2D(4, 7));
    list.add(new Point2D(4, 4));

    PointKDTree pointKDTree = new PointKDTree(list);
    //InOrder traversal
    String expectedOutput = "[Point2D{x=2, y=3}][Point2D{x=4, y=4}, Point2D{x=4, y=7}, " +
        "Point2D{x=2, y=3}, Point2D{x=5, y=4}, Point2D{x=7, y=2}][Point2D{x=7, y=2}]" +
        "[Point2D{x=5, y=4}, Point2D{x=7, y=2}][]";

    assertEquals(expectedOutput, pointKDTree.toString());
  }

  @Test
  public void testAdd() {
    List<Point2D> expectedList = new ArrayList<>();
    expectedList.add(new Point2D(296, 107));
    expectedList.add(new Point2D(618, 368));

    List<Point2D> inputList = new ArrayList<>();
    inputList.add(new Point2D(296, 107));

    PointKDTree pointKDTree = new PointKDTree(inputList);
    pointKDTree.add(new Point2D(618, 368));

    System.out.println(pointKDTree.getPoints());
    System.out.println(expectedList);
    assertEquals(expectedList, pointKDTree.getPoints());
  }

  @Test
  public void testAddPointsOneByOne() {
    List<Point2D> inputList = new ArrayList<>();
    inputList.add(new Point2D(7, 2));
    inputList.add(new Point2D(2, 3));
    PointKDTree pointKDTree = new PointKDTree(inputList);
    assertEquals(2, pointKDTree.getPoints().size());

    pointKDTree.add(new Point2D(1, 3));
    assertEquals(3, pointKDTree.getPoints().size());

    pointKDTree.add(new Point2D(1, 4));
    assertEquals(4, pointKDTree.getPoints().size());

    pointKDTree.add(null);
    assertEquals(4, pointKDTree.getPoints().size());
  }

  @Test
  public void testAddPointsToAnEmptyKDTree() {
    List<Point2D> inputList = new ArrayList<>();
    PointKDTree pointKDTree = new PointKDTree(inputList);
    assertEquals(0, pointKDTree.getPoints().size());

    pointKDTree.add(new Point2D(1, 3));
    assertEquals(1, pointKDTree.getPoints().size());

    pointKDTree.add(new Point2D(1, 4));
    assertEquals(2, pointKDTree.getPoints().size());

    pointKDTree.add(null);
    assertEquals(2, pointKDTree.getPoints().size());
  }

  @Test
  public void testAddDuplicatePoints() {

    List<Point2D> expectedList = new ArrayList<>();
    expectedList.add(new Point2D(7, 2));
    expectedList.add(new Point2D(2, 3));
    expectedList.add(new Point2D(4, 7));
    expectedList.add(new Point2D(9, 6));
    PointKDTree pointKDTree = new PointKDTree(expectedList);

    pointKDTree.add(new Point2D(7, 2));
    pointKDTree.add(new Point2D(7, 2));
    pointKDTree.add(new Point2D(296, 107));
    pointKDTree.add(new Point2D(618, 368));
    pointKDTree.add(new Point2D(618, 368));

    List<Point2D> actualList = pointKDTree.getPoints();
    expectedList.sort(comparator);
    actualList.sort(comparator);
    assertEquals(expectedList, actualList);
    assertEquals(9, pointKDTree.getPoints().size());

  }

  @Test
  public void getPoints() {
    PointKDTree pointKDTree;

    List<Point2D> list = new ArrayList<>();
    list.add(new Point2D(7, 2));
    list.add(new Point2D(5, 4));
    list.add(new Point2D(2, 3));
    list.add(new Point2D(4, 7));
    list.add(new Point2D(9, 6));
    pointKDTree = new PointKDTree(list);

    List<Point2D> actualList = pointKDTree.getPoints();

    list.sort(comparator);
    actualList.sort(comparator);
    assertEquals(list, actualList);

    list.add(new Point2D(0, 2));
    pointKDTree = new PointKDTree(list);

    actualList = pointKDTree.getPoints();
    list.sort(comparator);
    actualList.sort(comparator);
    assertEquals(list, actualList);
  }

  @Test
  public void checkAllPointsInListAreWithinCircle() {
    Point2D center = new Point2D(0, 0);
    List<Point2D> inputList = new ArrayList<>();
    List<Point2D> expectedList = new ArrayList<>();
    inputList.add(new Point2D(0, 4));
    inputList.add(new Point2D(4, 0));
    inputList.add(new Point2D(0, -4));
    inputList.add(new Point2D(-4, 0));
    expectedList.addAll(inputList);
    inputList.add(new Point2D(-4, -4));
    inputList.add(new Point2D(4, 4));
    inputList.add(new Point2D(4, -4));
    inputList.add(new Point2D(-4, 4));

    PointKDTree pointKDTree = new PointKDTree(inputList);

    List<Point2D> actualList = pointKDTree.allPointsWithinCircle(center, 4.0);
    expectedList.sort(comparator);
    actualList.sort(comparator);
    assertEquals(expectedList, actualList);
  }

  @Test
  public void checkAllPointsInListAreWithinCircleWithDuplicates() {
    Point2D center = new Point2D(0, 0);
    List<Point2D> inputList = new ArrayList<>();
    List<Point2D> expectedList = new ArrayList<>();
    inputList.add(new Point2D(0, 4));
    inputList.add(new Point2D(4, 0));
    inputList.add(new Point2D(0, -4));
    inputList.add(new Point2D(-4, 0));
    inputList.add(new Point2D(4, 0));
    inputList.add(new Point2D(0, -4));
    inputList.add(new Point2D(-4, 0));
    inputList.add(new Point2D(4, 0));
    inputList.add(new Point2D(0, -4));
    inputList.add(new Point2D(-4, 0));
    inputList.add(new Point2D(4, 0));
    expectedList.addAll(inputList);
    inputList.add(new Point2D(-4, -4));
    inputList.add(new Point2D(4, 4));
    inputList.add(new Point2D(4, -4));
    inputList.add(new Point2D(-4, 4));

    PointKDTree pointKDTree = new PointKDTree(inputList);

    List<Point2D> actualList = pointKDTree.allPointsWithinCircle(center, 4.0);
    expectedList.sort(comparator);
    actualList.sort(comparator);
    assertEquals(expectedList, actualList);
  }

  @Test
  public void checkAllIntegralPointsIn4x4SquareAndRadius3Circle() {
    Point2D center = new Point2D(0, 0);
    double radius = 3.0;
    List<Point2D> inputList = new ArrayList<>();
    for (int i = -4; i <= 4; i++) {
      for (int j = -4; j <= 4; j++) {
        inputList.add(new Point2D(i, j));
      }
    }
    List<Point2D> expectedList = new ArrayList<>();
    for (int i = 0; i < inputList.size(); i++) {
      if (center.distance(inputList.get(i)) <= radius) {
        expectedList.add(inputList.get(i));
      }
    }

    PointKDTree pointKDTree = new PointKDTree(inputList);

    List<Point2D> actualList = pointKDTree.allPointsWithinCircle(center, 3.0);
    expectedList.sort(comparator);
    actualList.sort(comparator);
    assertEquals(expectedList, actualList);
  }

  @Test
  public void checkAllPointsInListAreNotWithinCircleWhenNewPointIsAdded() {

    Point2D center = new Point2D(5, 5);

    List<Point2D> list = new ArrayList<>();
    list.add(new Point2D(7, 2));
    list.add(new Point2D(5, 4));
    list.add(new Point2D(2, 3));
    list.add(new Point2D(4, 7));
    list.add(new Point2D(4, 4));

    PointKDTree pointKDTree = new PointKDTree(list);
    List<Point2D> actualList = pointKDTree.allPointsWithinCircle(center, 4.0);

    list.sort(comparator);
    actualList.sort(comparator);
    assertEquals(list, actualList);

    //Below code adds a new point to KD tree that falls in circle
    list.add(new Point2D(3, 3));
    pointKDTree = new PointKDTree(list);
    actualList = pointKDTree.allPointsWithinCircle(center, 4.0);
    list.sort(comparator);
    actualList.sort(comparator);
    assertEquals(list, actualList);

    //Below code adds a new point to KD tree that falls outside circle
    //Below also covers circle intersects KD tree
    list.add(new Point2D(9, 6));
    pointKDTree = new PointKDTree(list);
    actualList = pointKDTree.allPointsWithinCircle(center, 4.0);
    list.sort(comparator);
    actualList.sort(comparator);
    assertNotEquals(list, actualList);
  }

  @Test
  public void returnEmptyListWhenCircleIsOutsideKDTree() {

    List<Point2D> list = new ArrayList<>();
    list.add(new Point2D(7, 2));
    list.add(new Point2D(5, 4));
    list.add(new Point2D(2, 3));
    list.add(new Point2D(4, 7));
    list.add(new Point2D(4, 4));

    PointKDTree pointKDTree = new PointKDTree(list);

    Point2D center = new Point2D(15, 15);
    List<Point2D> actualList = pointKDTree.allPointsWithinCircle(center, 3.0);

    assertEquals(Collections.emptyList(), actualList);
  }

  @Test
  public void circleInsideKDTree() {

    List<Point2D> inputList = new ArrayList<>();
    List<Point2D> expectedList = new ArrayList<>();
    inputList.add(new Point2D(0, 3));
    inputList.add(new Point2D(3, 0));
    inputList.add(new Point2D(0, -3));
    inputList.add(new Point2D(-3, 0));
    expectedList.addAll(inputList);
    inputList.add(new Point2D(-4, -4));
    inputList.add(new Point2D(4, 4));
    inputList.add(new Point2D(4, -4));
    inputList.add(new Point2D(-4, 4));

    PointKDTree pointKDTree = new PointKDTree(inputList);

    Point2D center = new Point2D(0, 0);
    List<Point2D> actualList = pointKDTree.allPointsWithinCircle(center, 3.0);

    expectedList.sort(comparator);
    actualList.sort(comparator);
    assertEquals(expectedList, actualList);
  }

  @Test
  public void kdTreePointsAreOnCircle() {
    List<Point2D> inputList = new ArrayList<>();
    List<Point2D> expectedList = new ArrayList<>();
    inputList.add(new Point2D(0, 3));
    inputList.add(new Point2D(3, 0));
    inputList.add(new Point2D(0, -3));
    inputList.add(new Point2D(-3, 0));
    expectedList.addAll(inputList);

    PointKDTree pointKDTree = new PointKDTree(inputList);

    Point2D center = new Point2D(0, 0);
    List<Point2D> actualList = pointKDTree.allPointsWithinCircle(center, 3.0);

    expectedList.sort(comparator);
    actualList.sort(comparator);
    assertEquals(expectedList, actualList);
  }

  @Test
  public void testTwoClosestPointsReturnsOneOfThem() {
    List<Point2D> inputList = new ArrayList<>();
    inputList.add(new Point2D(0, 3));
    inputList.add(new Point2D(3, 0));
    PointKDTree pointKDTree = new PointKDTree(inputList);
    assertEquals(new Point2D(0, 3), pointKDTree.closestPoint(new Point2D(0, 0)));
    inputList.add(new Point2D(0, -3));
    inputList.add(new Point2D(-3, 0));
    pointKDTree = new PointKDTree(inputList);
    assertEquals(new Point2D(3, 0), pointKDTree.closestPoint(new Point2D(0, 0)));
  }

  @Test
  public void testClosestPointMethodWithRandomGeneratedPoint() {
    Point2D testPoint = generatePointInGivenRange(-10, 10, -10, 10);
    List<Point2D> inputList = new ArrayList<>();
    for (int i = -6; i <= 6; i += 2) {
      for (int j = -6; j <= 6; j += 2) {
        inputList.add(new Point2D(i, j));
      }
    }
    PointKDTree pointKDTree = new PointKDTree(inputList);
    Point2D actualClosestPoint = pointKDTree.closestPoint(testPoint);
    double dist = inputList.get(0).distance(testPoint);
    for (int i = 0; i < inputList.size(); i++) {
      Point2D tempPoint = inputList.get(i);
      if (testPoint.distance(tempPoint) < dist) {
        dist = testPoint.distance(tempPoint);
      }
    }
    Set<Point2D> hashSet = new HashSet<>();
    for (int i = 0; i < inputList.size(); i++) {
      if (testPoint.distance(inputList.get(i)) == dist) {
        hashSet.add(inputList.get(i));
      }
    }
    assertTrue(hashSet.contains(actualClosestPoint));
  }

  @Test
  public void testClosestPointHundredRuns() {
    for (int k = 0; k < 100; k++) {
      Point2D testPoint = generatePointInGivenRange(-10, 10, -10, 10);
      List<Point2D> inputList = new ArrayList<>();
      for (int i = -6; i <= 6; i += 2) {
        for (int j = -6; j <= 6; j += 2) {
          inputList.add(new Point2D(i, j));
        }
      }
      PointKDTree pointKDTree = new PointKDTree(inputList);
      Point2D actualClosestPoint = pointKDTree.closestPoint(testPoint);
      double dist = inputList.get(0).distance(testPoint);
      for (int i = 0; i < inputList.size(); i++) {
        Point2D tempPoint = inputList.get(i);
        if (testPoint.distance(tempPoint) < dist) {
          dist = testPoint.distance(tempPoint);
        }
      }
      Set<Point2D> hashSet = new HashSet<>();
      for (int i = 0; i < inputList.size(); i++) {
        if (testPoint.distance(inputList.get(i)) == dist) {
          hashSet.add(inputList.get(i));
        }
      }
      assertTrue(hashSet.contains(actualClosestPoint));
    }
  }

  @Test
  public void testClosestPointForEmptyTree() {
    PointKDTree pointKDTree = new PointKDTree(Collections.emptyList());
    assertNull(pointKDTree.closestPoint(new Point2D(2, 3)));
  }


  @Test
  public void testDist() {
    point2D = new Point2D(1, 8);
    assertEquals(5.0, point2D.distance(new Point2D(1, 3)), 0.5);
    point2D = new Point2D(5, 6);
    assertEquals(Math.sqrt(2), point2D.distance(new Point2D(4, 7)), 0.5);
    assertEquals(Math.sqrt(4), point2D.sqrDist(new Point2D(4, 7)), 0.5);
  }

  private Point2D generatePointInGivenRange(int xStart, int xEnd, int yStart,
      int yEnd) {
    int randomXVal = ThreadLocalRandom.current().nextInt(xStart, xEnd + 1);
    int randomYVal = ThreadLocalRandom.current().nextInt(yStart, yEnd + 1);
    return new Point2D(randomXVal, randomYVal);
  }

}