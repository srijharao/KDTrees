import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Tests for Utility class.
 */
public class UtilityTest {

  @Test
  public void testConvertLeafNodeToNonLeafNodeLesserValueInsert() {
    List<Point2D> points = new ArrayList<>();
    points.add(new Point2D(5, 5));
    List<Integer> on = new ArrayList<>();
    on.add(0);
    Node leafNode = new LeafNode(points, on, 0);
    Point2D pointToAdd = new Point2D(1, 2);
    Node newNode = Utility.convertLeafNodeToNonLeafNode(leafNode, pointToAdd);

    assertFalse(newNode.isLeafNode());
    assertEquals(1, newNode.getPointsIndices().size());
    assertEquals(0, (int) newNode.getPointsIndices().get(0));
    assertTrue(newNode.getLeftNode().isLeafNode());
    assertTrue(newNode.getRightNode().isLeafNode());
    assertEquals(1, newNode.getLeftNode().getPoints().size());
    assertEquals(1, (int) newNode.getLeftNode().getPointsIndices().get(0));
    assertEquals(0, newNode.getRightNode().getPoints().size());
  }

  @Test
  public void testConvertLeafNodeToNonLeafNodeGreaterValueInsert() {
    List<Point2D> points = new ArrayList<>();
    points.add(new Point2D(5, 5));
    List<Integer> on = new ArrayList<>();
    on.add(0);
    Node leafNode = new LeafNode(points, on, 0);
    Point2D pointToAdd = new Point2D(8, 2);
    Node newNode = Utility.convertLeafNodeToNonLeafNode(leafNode, pointToAdd);

    assertFalse(newNode.isLeafNode());
    assertEquals(1, newNode.getPointsIndices().size());
    assertEquals(0, (int) newNode.getPointsIndices().get(0));
    assertTrue(newNode.getLeftNode().isLeafNode());
    assertTrue(newNode.getRightNode().isLeafNode());
    assertEquals(1, newNode.getRightNode().getPoints().size());
    assertEquals(1, (int) newNode.getRightNode().getPointsIndices().get(0));
    assertEquals(0, newNode.getLeftNode().getPoints().size());
  }

  @Test
  public void testConvertLeafNodeToNonLeafNodeSameValueInsert() {
    List<Point2D> points = new ArrayList<>();
    points.add(new Point2D(5, 5));
    List<Integer> on = new ArrayList<>();
    on.add(0);
    Node leafNode = new LeafNode(points, on, 0);
    Point2D pointToAdd = new Point2D(5, 5);
    Node newNode = Utility.convertLeafNodeToNonLeafNode(leafNode, pointToAdd);

    assertFalse(newNode.isLeafNode());
    assertEquals(2, newNode.getPointsIndices().size());
    assertEquals(0, (int) newNode.getPointsIndices().get(0));
    assertEquals(1, (int) newNode.getPointsIndices().get(1));
    assertTrue(newNode.getLeftNode().isLeafNode());
    assertTrue(newNode.getRightNode().isLeafNode());
    assertEquals(0, newNode.getRightNode().getPoints().size());
    assertEquals(0, newNode.getLeftNode().getPoints().size());
  }

}
