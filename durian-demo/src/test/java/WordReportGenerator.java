import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WordReportGenerator {

    public static void main(String[] args) throws Exception {
        // 1. 创建配置
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        //cfg.setDirectoryForTemplateLoading(new File("src/main/resources/ftl")); // .ftl 存放目录
        cfg.setClassForTemplateLoading(WordReportGenerator.class, "/ftl");
        cfg.setDefaultEncoding("UTF-8");

        // 2. 准备数据模型
        Map<String, Object> data = new HashMap<>();
        data.put("stationName", "金子山220kV变电站");
        data.put("deviceName", "#1主变");
        data.put("model", "SSZ11-150000/220");
        data.put("capacityMVA", 150);
        data.put("putIntoOperationDate", "2015年1月10日");
        data.put("manufacturer", "特变电工衡阳变压器有限公司");
        data.put("lastEvalDate", "2026年1月20日 09:15:22");
        data.put("healthStatus", "异常");
        data.put("healthIndex", "0.41");
        data.put("totalHydrocarbon", 12.3);
        data.put("weeklyIncrease", 4.7);

        // 列表示例
        List<Map<String, Object>> shortCircuits = new ArrayList<>();
        shortCircuits.add(Map.of("time", "2025-12-15", "ratio", 83.7));
        shortCircuits.add(Map.of("time", "2025-11-02", "ratio", 78.4));
        data.put("shortCircuits", shortCircuits);

        List<String> suggestions = Arrays.asList(
                "设备仍处于异常状态，应加强重点监视，建议2026年二季度前安排C类检修。",
                "针对抗短路能力不足隐患，建议尽快开展短路阻抗及绕组频响测试。",
                "持续跟踪油色谱数据，若增长趋势明显立即停电复测。"
        );
        data.put("suggestions", suggestions);

        // 3. 加载模板
        Template template = cfg.getTemplate("report.ftl");

        // 4. 生成输出
        try (Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream("test-report-2026.docx"), StandardCharsets.UTF_8))) {
            template.process(data, out);
        }

        System.out.println("报告生成完成：test-report-2026.docx");
    }
}