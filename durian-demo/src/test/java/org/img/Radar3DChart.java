package org.img;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Radar3DChart {

    public static void main(String[] args) throws Exception {

        int width = 900;
        int height = 800;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // 数据（顺序必须和 labels 对应）
        double[] scores = {10, 180, 150, 100, 150, 50};
        String[] labels = {"重点管控", "在线监控", "缺陷隐患", "停电试验", "带电检测", "运行监控"};

        int cx = 450, cy = 480;      // 中心点（稍微向上偏移，看起来更立体）
        int maxR = 300;              // 最大半径
        double maxValue = 180.0;     // 数据最大值

        // 1. 画3D底座（六边形立体背景）
        draw3DHexagonBase(g, cx, cy, maxR + 60);

        // 2. 画网格（6条射线 + 5层同心六边形）
        g.setStroke(new BasicStroke(1.5f));
        g.setColor(new Color(180, 200, 255));
        for (int level = 1; level <= 5; level++) {
            drawHexagon(g, cx, cy, maxR * level / 5.0, new Color(220, 230, 255));
        }
        // 射线
        g.setColor(new Color(180, 190, 220));
        for (int i = 0; i < 6; i++) {
            double ang = Math.toRadians(60 * i - 90);
            int x2 = cx + (int) (maxR * Math.cos(ang));
            int y2 = cy + (int) (maxR * Math.sin(ang));
            g.drawLine(cx, cy, x2, y2);
        }

        // 3. 计算数据点坐标
        Point[] points = new Point[6];
        for (int i = 0; i < 6; i++) {
            double ang = Math.toRadians(60 * i - 90);
            double r = maxR * scores[i] / maxValue;
            int x = cx + (int) (r * Math.cos(ang));
            int y = cy + (int) (r * Math.sin(ang));
            points[i] = new Point(x, y);
        }

        // 4. 绘制渐变填充多边形（橙红渐变）
        Polygon poly = new Polygon();
        for (Point p : points) poly.addPoint(p.x, p.y);

        GradientPaint gradient = new GradientPaint(
                cx - 100, cy - 150, new Color(255, 100, 100, 200),
                cx + 100, cy + 100, new Color(255, 180, 100, 120));
        g.setPaint(gradient);
        g.fillPolygon(poly);

        // 边框（深蓝粗线）
        g.setStroke(new BasicStroke(4.0f));
        g.setColor(new Color(0, 80, 180));
        g.drawPolygon(poly);

        // 顶点圆点
        g.setColor(Color.WHITE);
        for (Point p : points) {
            g.fillOval(p.x - 9, p.y - 9, 18, 18);
        }
        g.setColor(new Color(0, 70, 160));
        for (Point p : points) {
            g.fillOval(p.x - 6, p.y - 6, 12, 12);
        }

        // 5. 标注数值（在顶点外侧）
        g.setFont(new Font("Microsoft YaHei", Font.BOLD, 22));
        g.setColor(new Color(50, 50, 120));
        for (int i = 0; i < 6; i++) {
            double ang = Math.toRadians(60 * i - 90);
            double r = maxR * scores[i] / maxValue + 38;
            int tx = cx + (int) (r * Math.cos(ang));
            int ty = cy + (int) (r * Math.sin(ang));
            String txt = String.valueOf((int) scores[i]);
            FontMetrics fm = g.getFontMetrics();
            int w = fm.stringWidth(txt);
            g.drawString(txt, tx - w / 2, ty + 8);
        }

        // 6. 指标标签（外围）
        g.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
        g.setColor(new Color(40, 40, 100));
        double labelR = maxR + 100;
        for (int i = 0; i < 6; i++) {
            double ang = Math.toRadians(60 * i - 90);
            int tx = cx + (int) (labelR * Math.cos(ang));
            int ty = cy + (int) (labelR * Math.sin(ang));
            String txt = labels[i];
            FontMetrics fm = g.getFontMetrics();
            int w = fm.stringWidth(txt);
            int offsetX = -w / 2;
            if (i == 0) offsetX = -w / 2;      // 重点管控
            else if (i == 1) offsetX = 10;      // 在线监控（右上）
            else if (i == 2) offsetX = 10;      // 缺陷隐患（右）
            else if (i == 3) offsetX = -w - 10;  // 停电试验（左下）
            else if (i == 4) offsetX = -w - 10;  // 带电检测（左）
            else offsetX = -w / 2;              // 运行监控（下）

            g.drawString(txt, tx + offsetX, ty + 8);
        }

        // 7. 标题
        g.setFont(new Font("Microsoft YaHei", Font.BOLD, 26));
        g.setColor(new Color(30, 60, 120));
        g.drawString("主变健康评估雷达图", 260, 80);

        g.dispose();

        ImageIO.write(img, "png", new File("主变健康评估雷达图_3D.png"));
        System.out.println("生成完成：主变健康评估雷达图_3D.png");
    }

    // 绘制3D六边形底座
    private static void draw3DHexagonBase(Graphics2D g, int cx, int cy, double radius) {
        Graphics2D g2 = (Graphics2D) g.create();
        Polygon hex = new Polygon();
        for (int i = 0; i < 6; i++) {
            double ang = Math.toRadians(60 * i - 30);
            int x = cx + (int) (radius * Math.cos(ang));
            int y = cy + (int) (radius * Math.sin(ang));
            hex.addPoint(x, y);
        }
        // 外层深灰边
        g2.setColor(new Color(150, 170, 200));
        g2.setStroke(new BasicStroke(8f));
        g2.drawPolygon(hex);

        // 内层浅灰填充
        g2.setColor(new Color(220, 230, 245));
        g2.fillPolygon(hex);

        // 立体阴影侧面
        g2.setColor(new Color(180, 190, 210));
        for (int i = 0; i < 6; i++) {
            int i2 = (i + 1) % 6;
            int x1 = hex.xpoints[i];
            int y1 = hex.ypoints[i] + 40;
            int x2 = hex.xpoints[i2];
            int y2 = hex.ypoints[i2] + 40;
            Polygon side = new Polygon(
                    new int[]{hex.xpoints[i], x1, x2, hex.xpoints[i2]},
                    new int[]{hex.ypoints[i], y1, y2, hex.ypoints[i2]}, 4);
            g2.fillPolygon(side);
        }
        g2.dispose();
    }

    // 绘制普通六边形（用于网格）
    private static void drawHexagon(Graphics2D g, int cx, int cy, double radius, Color color) {
        Polygon hex = new Polygon();
        for (int i = 0; i < 6; i++) {
            double ang = Math.toRadians(60 * i - 30);
            int x = cx + (int) (radius * Math.cos(ang));
            int y = cy + (int) (radius * Math.sin(ang));
            hex.addPoint(x, y);
        }
        g.setColor(color);
        g.drawPolygon(hex);
    }
}
