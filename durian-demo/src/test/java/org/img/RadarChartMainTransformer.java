package org.img;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class RadarChartMainTransformer {
    public static void main(String[] args) throws Exception {
        int w = 900, h = 900;
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int cx = w / 2, cy = h / 2;
        int rMax = 320;

        // 背景
        g.setColor(new Color(250, 252, 255));
        g.fillRect(0, 0, w, h);

        // 网格圆 + 射线
        g.setColor(new Color(210, 215, 230));
        g.setStroke(new BasicStroke(1.1f));
        for (int lv = 1; lv <= 5; lv++) {
            int rr = rMax * lv / 5;
            g.drawOval(cx - rr, cy - rr, rr * 2, rr * 2);
        }
        for (int i = 0; i < 6; i++) {
            double ang = Math.toRadians(60 * i - 90);
            int ex = cx + (int) (rMax * Math.cos(ang));
            int ey = cy + (int) (rMax * Math.sin(ang));
            g.drawLine(cx, cy, ex, ey);
        }

        // 数据
        double[] scores = {10, 180, 150, 100, 150, 50};
        double max = 180.0;
        int[] px = new int[6];
        int[] py = new int[6];
        String[] labels = {"重点管控", "在线监控", "缺陷隐患", "停电试验", "带电检测", "运行监控"};

        for (int i = 0; i < 6; i++) {
            double ang = Math.toRadians(60 * i - 90);
            double ratio = scores[i] / max;
            double len = rMax * ratio;
            px[i] = cx + (int) (len * Math.cos(ang));
            py[i] = cy + (int) (len * Math.sin(ang));
        }

        // 填充 + 边框
        g.setColor(new Color(220, 80, 80, 70));   // 橙红半透明
        g.fillPolygon(px, py, 6);
        g.setColor(new Color(180, 30, 30));
        g.setStroke(new BasicStroke(3.5f));
        g.drawPolygon(px, py, 6);

        // 顶点圆点
        g.setColor(new Color(140, 0, 0));
        for (int i = 0; i < 6; i++) {
            g.fillOval(px[i] - 8, py[i] - 8, 16, 16);
        }

        // 标签 + 分数
        g.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
        g.setColor(new Color(30, 30, 60));
        double labelR = rMax + 65;
        for (int i = 0; i < 6; i++) {
            double ang = Math.toRadians(60 * i - 90);
            int lx = cx + (int) (labelR * Math.cos(ang));
            int ly = cy + (int) (labelR * Math.sin(ang)) + 6;

            String txt = labels[i] + "  " + (int) scores[i];
            FontMetrics fm = g.getFontMetrics();
            int tw = fm.stringWidth(txt);

            int ox = -tw / 2;
            if (Math.cos(ang) > 0.7) ox = 10;          // 右边
            else if (Math.cos(ang) < -0.7) ox = -tw - 10; // 左边

            g.drawString(txt, lx + ox, ly);
        }

        // 标题
        g.setFont(new Font("Microsoft YaHei", Font.BOLD, 22));
        g.setColor(new Color(40, 60, 140));
        //g.drawString("主变健康评估 - 6维度雷达图", cx - 220, 70);

        g.dispose();
        ImageIO.write(img, "png", new File("radar_main_transformer.png"));
        System.out.println("已生成：radar_main_transformer.png");
    }
}
