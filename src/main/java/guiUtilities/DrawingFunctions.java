package guiUtilities;

import graphStructure.Edge;
import graphStructure.Vertex;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import java.util.LinkedHashSet;

public class DrawingFunctions
{
    public static double sceneX, sceneY, layoutX, layoutY;

    /**
     * Builds the single directional line with pointing arrows at each end.
     * @param startDot Pane for considering start point
     * @param endDot   Pane for considering end point
     * @param parent Parent container
     * @param hasEndArrow Specifies whether to show arrow towards end
     * @param hasStartArrow Specifies whether to show arrow towards start
     */
    public static void buildSingleDirectionalLine(StackPane startDot, StackPane endDot, Pane parent, StackPane colorLabel, boolean hasEndArrow, boolean hasStartArrow, long color) {
        Line line = getLine(startDot, endDot);
        StackPane arrowAB = getArrow(true, line, startDot, endDot);
        if (!hasEndArrow) {
            arrowAB.setOpacity(0);
        }
        StackPane arrowBA = getArrow(false, line, startDot, endDot);
        if (!hasStartArrow) {
            arrowBA.setOpacity(0);
        }

        colorLabel = getWeight(line);
        colorLabel.getChildren().add(new Label(String.valueOf(color)));

        if (color == 0)
        {
            colorLabel.setOpacity(0);
        }

        line.toBack();
        parent.getChildren().addAll(line, colorLabel, arrowBA, arrowAB);
    }

    /**
     * Builds the bi directional line with pointing arrow at specified end.
     * @param isEnd Specifies whether the line is towards end or not. If false then the line is towards start.
     * @param startDot Pane for considering start point
     * @param endDot   Pane for considering end point
     * @param parent Parent container
     */
    public static void buildBiDirectionalLine(boolean isEnd, StackPane startDot, StackPane endDot, Pane parent) {
        Line virtualCenterLine = getLine(startDot, endDot);
        virtualCenterLine.setOpacity(0);
        StackPane centerLineArrowAB = getArrow(true, virtualCenterLine, startDot, endDot);
        centerLineArrowAB.setOpacity(0);
        StackPane centerLineArrowBA = getArrow(false, virtualCenterLine, startDot, endDot);
        centerLineArrowBA.setOpacity(0);

        Line directedLine = new Line();
        directedLine.setStroke(Color.RED);
        directedLine.setStrokeWidth(2);

        double diff = isEnd ? -centerLineArrowAB.getPrefWidth() / 2 : centerLineArrowAB.getPrefWidth() / 2;
        final ChangeListener<Number> listener = (obs, old, newVal) -> {
            Rotate r = new Rotate();
            r.setPivotX(virtualCenterLine.getStartX());
            r.setPivotY(virtualCenterLine.getStartY());
            r.setAngle(centerLineArrowAB.getRotate());
            Point2D point = r.transform(new Point2D(virtualCenterLine.getStartX(), virtualCenterLine.getStartY() + diff));
            directedLine.setStartX(point.getX());
            directedLine.setStartY(point.getY());

            Rotate r2 = new Rotate();
            r2.setPivotX(virtualCenterLine.getEndX());
            r2.setPivotY(virtualCenterLine.getEndY());
            r2.setAngle(centerLineArrowBA.getRotate());
            Point2D point2 = r2.transform(new Point2D(virtualCenterLine.getEndX(), virtualCenterLine.getEndY() - diff));
            directedLine.setEndX(point2.getX());
            directedLine.setEndY(point2.getY());
        };
        centerLineArrowAB.rotateProperty().addListener(listener);
        centerLineArrowBA.rotateProperty().addListener(listener);
        virtualCenterLine.startXProperty().addListener(listener);
        virtualCenterLine.startYProperty().addListener(listener);
        virtualCenterLine.endXProperty().addListener(listener);
        virtualCenterLine.endYProperty().addListener(listener);

        StackPane mainArrow = getArrow(isEnd, directedLine, startDot, endDot);
        parent.getChildren().addAll(virtualCenterLine, centerLineArrowAB, centerLineArrowBA, directedLine);
    }

    /**
     * Builds a line between the provided start and end panes center point.
     *
     * @param startDot Pane for considering start point
     * @param endDot   Pane for considering end point
     * @return Line joining the layout center points of the provided panes.
     */
    public static Line getLine(StackPane startDot, StackPane endDot) {
        Line line = new Line();
        line.setStroke(Color.BLUE);
        line.setStrokeWidth(2);
        line.startXProperty().bind(startDot.layoutXProperty().add(startDot.translateXProperty()).add(startDot.widthProperty().divide(2)));
        line.startYProperty().bind(startDot.layoutYProperty().add(startDot.translateYProperty()).add(startDot.heightProperty().divide(2)));
        line.endXProperty().bind(endDot.layoutXProperty().add(endDot.translateXProperty()).add(endDot.widthProperty().divide(2)));
        line.endYProperty().bind(endDot.layoutYProperty().add(endDot.translateYProperty()).add(endDot.heightProperty().divide(2)));
        return line;
    }

    /**
     * Builds an arrow on the provided line pointing towards the specified pane.
     *
     * @param toLineEnd Specifies whether the arrow to point towards end pane or start pane.
     * @param line      Line joining the layout center points of the provided panes.
     * @param startDot  Pane which is considered as start point of line
     * @param endDot    Pane which is considered as end point of line
     * @return Arrow towards the specified pane.
     */
    public static StackPane getArrow(boolean toLineEnd, Line line, StackPane startDot, StackPane endDot) {
        double size = 12; // Arrow size
        StackPane arrow = new StackPane();
        arrow.setStyle("-fx-background-color:#333333;-fx-border-width:1px;-fx-border-color:black;-fx-shape: \"M0,-4L4,0L0,4Z\"");//
        arrow.setPrefSize(size, size);
        arrow.setMaxSize(size, size);
        arrow.setMinSize(size, size);

        // Determining the arrow visibility unless there is enough space between dots.
        DoubleBinding xDiff = line.endXProperty().subtract(line.startXProperty());
        DoubleBinding yDiff = line.endYProperty().subtract(line.startYProperty());
        BooleanBinding visible = (xDiff.lessThanOrEqualTo(size).and(xDiff.greaterThanOrEqualTo(-size)).and(yDiff.greaterThanOrEqualTo(-size)).and(yDiff.lessThanOrEqualTo(size))).not();
        arrow.visibleProperty().bind(visible);

        // Determining the x point on the line which is at a certain distance.
        DoubleBinding tX = Bindings.createDoubleBinding(() -> {
            double xDiffSqu = (line.getEndX() - line.getStartX()) * (line.getEndX() - line.getStartX());
            double yDiffSqu = (line.getEndY() - line.getStartY()) * (line.getEndY() - line.getStartY());
            double lineLength = Math.sqrt(xDiffSqu + yDiffSqu);
            double dt;
            if (toLineEnd) {
                // When determining the point towards end, the required distance is total length minus (radius + arrow half width)
                dt = lineLength - (endDot.getWidth() / 2) - (arrow.getWidth() / 2);
            } else {
                // When determining the point towards start, the required distance is just (radius + arrow half width)
                dt = (startDot.getWidth() / 2) + (arrow.getWidth() / 2);
            }

            double t = dt / lineLength;
            double dx = ((1 - t) * line.getStartX()) + (t * line.getEndX());
            return dx;
        }, line.startXProperty(), line.endXProperty(), line.startYProperty(), line.endYProperty());

        // Determining the y point on the line which is at a certain distance.
        DoubleBinding tY = Bindings.createDoubleBinding(() -> {
            double xDiffSqu = (line.getEndX() - line.getStartX()) * (line.getEndX() - line.getStartX());
            double yDiffSqu = (line.getEndY() - line.getStartY()) * (line.getEndY() - line.getStartY());
            double lineLength = Math.sqrt(xDiffSqu + yDiffSqu);
            double dt;
            if (toLineEnd) {
                dt = lineLength - (endDot.getHeight() / 2) - (arrow.getHeight() / 2);
            } else {
                dt = (startDot.getHeight() / 2) + (arrow.getHeight() / 2);
            }
            double t = dt / lineLength;
            double dy = ((1 - t) * line.getStartY()) + (t * line.getEndY());
            return dy;
        }, line.startXProperty(), line.endXProperty(), line.startYProperty(), line.endYProperty());

        arrow.layoutXProperty().bind(tX.subtract(arrow.widthProperty().divide(2)));
        arrow.layoutYProperty().bind(tY.subtract(arrow.heightProperty().divide(2)));

        DoubleBinding endArrowAngle = Bindings.createDoubleBinding(() -> {
            double stX = toLineEnd ? line.getStartX() : line.getEndX();
            double stY = toLineEnd ? line.getStartY() : line.getEndY();
            double enX = toLineEnd ? line.getEndX() : line.getStartX();
            double enY = toLineEnd ? line.getEndY() : line.getStartY();
            double angle = Math.toDegrees(Math.atan2(enY - stY, enX - stX));
            if (angle < 0) {
                angle += 360;
            }
            return angle;
        }, line.startXProperty(), line.endXProperty(), line.startYProperty(), line.endYProperty());
        arrow.rotateProperty().bind(endArrowAngle);

        return arrow;
    }

    /**
     * Builds a pane at the center of the provided line.
     *
     * @param line Line on which the pane need to be set.
     * @return Pane located at the center of the provided line.
     */
    public static StackPane getWeight(Line line) {
        double size = 20;
        StackPane weight = new StackPane();
        weight.setStyle("-fx-background-color:grey;-fx-border-width:1px;-fx-border-color:black;");
        weight.setPrefSize(size, size);
        weight.setMaxSize(size, size);
        weight.setMinSize(size, size);

        DoubleBinding wgtSqrHalfWidth = weight.widthProperty().divide(2);
        DoubleBinding wgtSqrHalfHeight = weight.heightProperty().divide(2);
        DoubleBinding lineXHalfLength = line.endXProperty().subtract(line.startXProperty()).divide(2);
        DoubleBinding lineYHalfLength = line.endYProperty().subtract(line.startYProperty()).divide(2);

        weight.layoutXProperty().bind(line.startXProperty().add(lineXHalfLength.subtract(wgtSqrHalfWidth)));
        weight.layoutYProperty().bind(line.startYProperty().add(lineYHalfLength.subtract(wgtSqrHalfHeight)));
        return weight;
    }

    /**
     * Builds a pane consisting of circle with the provided specifications.
     *
     * @param color Color of the circle
     * @param text  Text inside the circle
     * @return Draggable pane consisting a circle.
     */
    public static StackPane getDot(String color, String text) {
        double radius = 20;
        double paneSize = 2 * radius;
        StackPane dotPane = new StackPane();
        Circle dot = new Circle();
        dot.setRadius(radius);
        dot.setStyle("-fx-fill:" + color + ";-fx-stroke-width:2px;-fx-stroke:black;");

        Label txt = new Label(text);
        txt.setStyle("-fx-font-size:18px;-fx-font-weight:bold;");
        dotPane.getChildren().addAll(dot, txt);
        dotPane.setPrefSize(paneSize, paneSize);
        dotPane.setMaxSize(paneSize, paneSize);
        dotPane.setMinSize(paneSize, paneSize);
        dotPane.setOnMousePressed(e -> {
            sceneX = e.getSceneX();
            sceneY = e.getSceneY();
            layoutX = dotPane.getLayoutX();
            layoutY = dotPane.getLayoutY();
        });

        EventHandler<MouseEvent> dotOnMouseDraggedEventHandler = e -> {
            // Offset of drag
            double offsetX = e.getSceneX() - sceneX;
            double offsetY = e.getSceneY() - sceneY;

            // Taking parent bounds
            Bounds parentBounds = dotPane.getParent().getLayoutBounds();

            // Drag node bounds
            double currPaneLayoutX = dotPane.getLayoutX();
            double currPaneWidth = dotPane.getWidth();
            double currPaneLayoutY = dotPane.getLayoutY();
            double currPaneHeight = dotPane.getHeight();

            if ((currPaneLayoutX + offsetX < parentBounds.getWidth() - currPaneWidth) && (currPaneLayoutX + offsetX > -1)) {
                // If the dragNode bounds is within the parent bounds, then you can set the offset value.
                dotPane.setTranslateX(offsetX);
            } else if (currPaneLayoutX + offsetX < 0) {
                // If the sum of your offset and current layout position is negative, then you ALWAYS update your translate to negative layout value
                // which makes the final layout position to 0 in mouse released event.
                dotPane.setTranslateX(-currPaneLayoutX);
            } else {
                // If your dragNode bounds are outside parent bounds,ALWAYS setting the translate value that fits your node at end.
                dotPane.setTranslateX(parentBounds.getWidth() - currPaneLayoutX - currPaneWidth);
            }

            if ((currPaneLayoutY + offsetY < parentBounds.getHeight() - currPaneHeight) && (currPaneLayoutY + offsetY > -1)) {
                dotPane.setTranslateY(offsetY);
            } else if (currPaneLayoutY + offsetY < 0) {
                dotPane.setTranslateY(-currPaneLayoutY);
            } else {
                dotPane.setTranslateY(parentBounds.getHeight() - currPaneLayoutY - currPaneHeight);
            }
        };
        dotPane.setOnMouseDragged(dotOnMouseDraggedEventHandler);
        dotPane.setOnMouseReleased(e -> {
            // Updating the new layout positions
            dotPane.setLayoutX(layoutX + dotPane.getTranslateX());
            dotPane.setLayoutY(layoutY + dotPane.getTranslateY());

            // Resetting the translate positions
            dotPane.setTranslateX(0);
            dotPane.setTranslateY(0);
        });
        return dotPane;
    }

    public static void drawVertex(Pane graphPane, StackPane dot)
    {
        dot.toFront();
        graphPane.getChildren().add(dot);
    }

    public static void drawGraph(Pane graphPane, LinkedHashSet<Vertex> vertices, LinkedHashSet<Edge> edges)
    {
        for(Vertex v : vertices)
        {
            drawVertex(graphPane, v.getDot());
        }

        for(Edge e : edges)
        {
            buildSingleDirectionalLine(e.getFirstVertex().getDot(),
                    e.getSecondVertex().getDot(),
                    graphPane,
                    e.getColorLabel(),
                    false,
                    false,
                    e.getColorFromVar());
        }

        for(Vertex v : vertices)
        {
            v.getDot().toFront();
        }
    }
}