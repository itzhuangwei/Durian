package org.img.utils;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class RadarChartGenerator {

    private RadarChartGenerator() {
        // 禁止实例化
    }

    public static void main(String[] args) throws IOException {
        double[] scores = {10, 180, 150, 100, 150, 50};
        String[] labels = {"重点管控", "在线监控", "缺陷隐患", "停电试验", "带电检测", "运行监控"};
        FileInputStream fileInputStream = RadarChartGenerator.generateRadarChartAsFileInputStream(scores, labels, "/Users/zwx/developer/backend/Durian/durian-demo/radar.png");
    }

    /**
     * 生成雷達圖並返回 FileInputStream（先寫入臨時檔案，再開啟流）
     * 注意：不推薦大量呼叫，因為會產生臨時檔案
     *
     * @param scores       6個指標分數
     * @param labels       6個指標名稱
     * @param tempFilePath 臨時檔案完整路徑（例如 "C:/temp/radar.png"），必須有寫入權限
     * @return FileInputStream（呼叫方負責關閉）
     * @throws IOException 如果寫檔或讀檔失敗
     */
    public static FileInputStream generateRadarChartAsFileInputStream(
            double[] scores, String[] labels, String tempFilePath) throws IOException {

        BufferedImage image = createRadarImage(scores, labels);

        // 写入指定路徑
        ImageIO.write(image, "png", new java.io.File(tempFilePath));

        // 開啟檔案輸入流
        return new FileInputStream(tempFilePath);
    }

    private static BufferedImage createRadarImage(double[] scores, String[] labels) {
        int width = 900;
        int height = 900;
        int centerX = width / 2;
        int centerY = height / 2;
        int maxRadius = 320;

        double maxValue = 0;
        for (double v : scores) {
            if (v > maxValue) maxValue = v;
        }
        if (maxValue == 0) maxValue = 1.0; // 避免除以0

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // 背景
        g.setColor(new Color(250, 252, 255));
        g.fillRect(0, 0, width, height);

        // 網格圓 + 射線
        g.setColor(new Color(210, 215, 230));
        g.setStroke(new BasicStroke(1.1f));
        for (int lv = 1; lv <= 5; lv++) {
            int rr = maxRadius * lv / 5;
            g.drawOval(centerX - rr, centerY - rr, rr * 2, rr * 2);
        }

        for (int i = 0; i < 6; i++) {
            double ang = Math.toRadians(60 * i - 90);
            int ex = centerX + (int) (maxRadius * Math.cos(ang));
            int ey = centerY + (int) (maxRadius * Math.sin(ang));
            g.drawLine(centerX, centerY, ex, ey);
        }

        // 資料多邊形座標
        int[] px = new int[6];
        int[] py = new int[6];
        for (int i = 0; i < 6; i++) {
            double ang = Math.toRadians(60 * i - 90);
            double ratio = scores[i] / maxValue;
            double len = maxRadius * ratio;
            px[i] = centerX + (int) (len * Math.cos(ang));
            py[i] = centerY + (int) (len * Math.sin(ang));
        }

        // 填充 + 邊框
        g.setColor(new Color(220, 80, 80, 70)); // 橙紅半透明
        g.fillPolygon(px, py, 6);
        g.setColor(new Color(180, 30, 30));
        g.setStroke(new BasicStroke(3.5f));
        g.drawPolygon(px, py, 6);

        // 頂點圓點
        g.setColor(new Color(140, 0, 0));
        for (int i = 0; i < 6; i++) {
            g.fillOval(px[i] - 8, py[i] - 8, 16, 16);
        }

        // 標籤 + 分數
        g.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
        g.setColor(new Color(30, 30, 60));
        double labelR = maxRadius + 65;
        for (int i = 0; i < 6; i++) {
            double ang = Math.toRadians(60 * i - 90);
            int lx = centerX + (int) (labelR * Math.cos(ang));
            int ly = centerY + (int) (labelR * Math.sin(ang)) + 6;

            String txt = labels[i] + "  " + (int) scores[i];
            FontMetrics fm = g.getFontMetrics();
            int tw = fm.stringWidth(txt);

            int ox = -tw / 2;
            if (Math.cos(ang) > 0.7) ox = 10;
            else if (Math.cos(ang) < -0.7) ox = -tw - 10;

            g.drawString(txt, lx + ox, ly);
        }

        // 標題
        g.setFont(new Font("Microsoft YaHei", Font.BOLD, 22));
        g.setColor(new Color(40, 60, 140));
        //g.drawString("主變健康評估 - 6維度雷達圖", centerX - 220, 70);

        g.dispose();
        return image;
    }
}
