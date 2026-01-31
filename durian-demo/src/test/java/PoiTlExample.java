import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.PictureType;
import com.deepoove.poi.data.Pictures;
import org.img.utils.RadarChartGenerator;

import java.util.*;

public class PoiTlExample {
    public static void main(String[] args) throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("stationName", "金子山220kV变电站");
        data.put("deviceName", "#1主变");
        data.put("model", "SSZ11-150000/220");
        data.put("capacityMVA", 150);
        data.put("putIntoOperationDate", "2015年1月10日");
        data.put("healthStatus", "异常");
        data.put("healthIndex", 0.41);
        data.put("totalHydrocarbon", 12.3);
        data.put("weeklyIncrease", 4.7);

        // 表格数据示例
        List<Map<String, Object>> shortCircuits = new ArrayList<>();
        shortCircuits.add(Map.of("time", "2025-12-15", "ratio", "83.7%"));
        shortCircuits.add(Map.of("time", "2025-11-02", "ratio", "78.4%"));
        data.put("shortCircuits", shortCircuits);

        // 建议列表
        data.put("suggestions", Arrays.asList(
                "加强重点监视，建议2026年二季度安排C类检修",
                "尽快开展短路阻抗及绕组频响测试"
        ));

        // 图片流
        double[] scores = {10, 180, 150, 100, 150, 50};
        String[] labels = {"重点管控", "在线监控", "缺陷隐患", "停电试验", "带电检测", "运行监控"};
        data.put("img_1", Pictures.ofStream(RadarChartGenerator.generateRadarChartAsFileInputStream(scores, labels, "/Users/zwx/developer/backend/Durian/durian-demo/radar.png")
                        , PictureType.JPEG)
                .size(400, 400).create());

        // 加载模板并渲染
        //XWPFTemplate template = XWPFTemplate.compile("templates/template.docx").render(data);

        XWPFTemplate template = XWPFTemplate.compile(
                Objects.requireNonNull(PoiTlExample.class.getClassLoader().getResource("templates/template.docx")).getPath()
        ).render(data);

        template.writeToFile("金子山#1主变健康评估报告-2026-1.docx");
        template.close();

        System.out.println("生成完成，双击可正常打开");
    }
}