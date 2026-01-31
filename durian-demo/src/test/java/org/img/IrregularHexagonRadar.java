package org.img;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class IrregularHexagonRadar extends Application {

    // 6个指标的值（0 ~ maxValue）
    private final double[] values = {10, 180, 150, 100, 150, 50}; // 示例数据，可替换
    private final String[] labels = {"重点管控", "在线监控", "缺陷隐患", "停电试验", "带电检测", "运行监控"};
    private final double maxValue = 200;          // 每个指标的最大值（满分）
    private final double centerX = 300;
    private final double centerY = 300;
    private final double radius = 200;            // 雷达图半径

    @Override
    public void start(Stage primaryStage) {
        Pane pane = new Pane();

        // 1. 画背景网格（6条半径线 + 3圈同心圆）
        drawBackgroundGrid(pane);

        // 2. 画6个标签
        drawLabels(pane);

        // 3. 画不规则多边形（核心部分）
        Polygon polygon = createDataPolygon();
        polygon.setFill(Color.rgb(100, 149, 237, 0.4));   // 半透明填充
        polygon.setStroke(Color.BLUE);
        polygon.setStrokeWidth(2.5);
        pane.getChildren().add(polygon);

        // 4. 可选：画数据点小圆圈
        for (int i = 0; i < values.length; i++) {
            double angle = Math.toRadians(60 * i - 90); // 从顶部开始，顺时针
            double r = radius * (values[i] / maxValue);
            double x = centerX + r * Math.cos(angle);
            double y = centerY + r * Math.sin(angle);

            javafx.scene.shape.Circle dot = new javafx.scene.shape.Circle(x, y, 5);
            dot.setFill(Color.BLUE);
            dot.setStroke(Color.WHITE);
            dot.setStrokeWidth(1.5);
            pane.getChildren().add(dot);
        }

        Scene scene = new Scene(pane, 600, 600);
        primaryStage.setTitle("6指标不规则六边形 - 雷达图");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawBackgroundGrid(Pane pane) {
        // 画6条从中心到顶点的半径线
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i - 90);
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            Line line = new Line(centerX, centerY, x, y);
            line.setStroke(Color.LIGHTGRAY);
            line.setStrokeWidth(1);
            pane.getChildren().add(line);
        }

        // 画3圈同心圆（30%、60%、100%）
        for (int level = 1; level <= 3; level++) {
            double r = radius * (level / 3.0);
            javafx.scene.shape.Circle circle = new javafx.scene.shape.Circle(centerX, centerY, r);
            circle.setFill(Color.TRANSPARENT);
            circle.setStroke(Color.LIGHTGRAY);
            circle.setStrokeWidth(0.8);
            circle.getStrokeDashArray().addAll(4.0, 4.0); // 虚线
            pane.getChildren().add(circle);
        }
    }

    private void drawLabels(Pane pane) {
        double labelRadius = radius + 30; // 标签稍微往外一点
        for (int i = 0; i < labels.length; i++) {
            double angle = Math.toRadians(60 * i - 90);
            double x = centerX + labelRadius * Math.cos(angle);
            double y = centerY + labelRadius * Math.sin(angle);

            Text text = new Text(x, y, labels[i]);
            text.setFill(Color.DARKBLUE);
            text.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

            // 微调文字位置（避免重叠或偏离）
            if (i == 0) {
                text.setTranslateY(-10);
            }     // 上
            else if (i == 3) {
                text.setTranslateY(15);
            } // 下
            else if (i == 1 || i == 2) {
                text.setTranslateX(10);
            }  // 右上/右下
            else {
                text.setTranslateX(-text.getLayoutBounds().getWidth() - 10);
            } // 左

            pane.getChildren().add(text);
        }
    }

    private Polygon createDataPolygon() {
        Polygon polygon = new Polygon();
        for (int i = 0; i < values.length; i++) {
            double angle = Math.toRadians(60 * i - 90); // 从顶部开始
            double r = radius * (values[i] / maxValue);
            double x = centerX + r * Math.cos(angle);
            double y = centerY + r * Math.sin(angle);
            polygon.getPoints().addAll(x, y);
        }
        return polygon;
    }

    public static void main(String[] args) {
        launch(args);
    }
}