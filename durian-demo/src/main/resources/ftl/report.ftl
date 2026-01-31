<#-- report.ftl - FreeMarker Template for Word Document -->
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<w:document xmlns:wpc="http://schemas.microsoft.com/office/word/2010/wordprocessingCanvas" xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships" xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math" xmlns:v="urn:schemas-microsoft-com:vml" xmlns:wp14="http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml" xmlns:w10="urn:schemas-microsoft-com:office:word" xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml" xmlns:wpg="http://schemas.microsoft.com/office/word/2010/wordprocessingGroup" xmlns:wpi="http://schemas.microsoft.com/office/word/2010/wordprocessingInk" xmlns:wne="http://schemas.microsoft.com/office/word/2006/wordml" xmlns:wps="http://schemas.microsoft.com/office/word/2010/wordprocessingShape" xmlns:wpsCustomData="http://www.wps.cn/officeDocument/2013/wpsCustomData" mc:Ignorable="w14 w15 wp14">

    <w:body>

        <#-- Title -->
        <w:p>
            <w:pPr><w:jc w:val="center"/><w:rPr><w:b/><w:sz w:val="48"/></w:rPr></w:pPr>
            <w:r><w:t>${substationName!"金子山"}${voltage!"220kV"}变电站#${transformerNumber!"1"}主变设备健康评估报告</w:t></w:r>
        </w:p>

        <#-- Empty lines -->
        <w:p><w:r><w:t xml:space="preserve"> </w:t></w:r></w:p>
        <w:p><w:r><w:t xml:space="preserve"> </w:t></w:r></w:p>

        <#-- Section 1: 设备基本情况 -->
        <w:p>
            <w:pPr><w:pStyle w:val="Heading1"/><w:ind w:left="568"/><w:outlineLvl w:val="1"/><w:rPr><w:b/><w:sz w:val="28"/></w:rPr></w:pPr>
            <w:r><w:t>一 、设备基本情况</w:t></w:r>
        </w:p>

        <w:p>
            <w:pPr><w:ind w:left="5" w:firstLine="559"/><w:jc w:val="both"/><w:rPr><w:sz w:val="28"/></w:rPr></w:pPr>
            <w:r><w:t>${substationName!"金子山"}${voltage!"220kV"}变电站#${transformerNumber!"1"}主变设备型号为${model!"SSZ11-150000/220"}，设备容量为 ${capacity!"150MW"}，投运日期是${commissioningDate!"2015 年1月10日"}，${company!"国网宜春供电公司"}，生产厂家为${manufacturer!"特变电工衡阳变压器有限公司"}。抗短路校核结果为${shortCircuitResult!"不合格"}。重过载分级为${overloadLevel!"一级"}（详细情况见附件 ${attachmentOverload!"7"}）。</w:t></w:r>
        </w:p>

        <#-- Section 2: 设备健康评估结果 -->
        <w:p>
            <w:pPr><w:ind w:left="568"/><w:outlineLvl w:val="1"/><w:rPr><w:b/><w:sz w:val="28"/></w:rPr></w:pPr>
            <w:r><w:t>二、 设备健康评估结果</w:t></w:r>
        </w:p>

        <w:p>
            <w:pPr><w:ind w:left="5" w:firstLine="559"/><w:jc w:val="both"/><w:rPr><w:sz w:val="28"/></w:rPr></w:pPr>
            <w:r><w:t>综合在线监测、运行监控、带电检测、停电试验、隐患缺陷以及重点管控等6个维度，构建主变健康综合评价模型。根据PMS系统评价结果，其最新状态评价日期为${evaluationDate!"2022年11月04日 18:23:12"}，评价结果为${status!"异常状态"}，健康指数为${healthIndex!"0.32"}。（详细情况见附件 ${attachmentEval!"1"}）</w:t></w:r>
        </w:p>

        <#-- Section 3: 关键健康指标 -->
        <w:p>
            <w:pPr><w:ind w:left="568"/><w:outlineLvl w:val="1"/><w:rPr><w:b/><w:sz w:val="28"/></w:rPr></w:pPr>
            <w:r><w:t>三、关键健康指标</w:t></w:r>
        </w:p>

        <#-- Subsection (一) 在线监测 -->
        <w:p>
            <w:pPr><w:ind w:left="5" w:firstLine="559"/><w:jc w:val="both"/><w:rPr><w:sz w:val="28"/></w:rPr></w:pPr>
            <w:r><w:t>(一) 在线监测</w:t></w:r>
        </w:p>
        <w:p>
            <w:pPr><w:ind w:left="5" w:firstLine="559"/><w:jc w:val="both"/><w:rPr><w:sz w:val="28"/></w:rPr></w:pPr>
            <w:r><w:t>油色谱智能分析  (在线)  ： 油色谱在线监测装置已接入，有实时数据，依据最新油色谱在线监测数据 ，诊断结果为${oilChromDiagnosis!"正常"}。详细情况见附件 ${attachmentOil!"2"}。</w:t></w:r>
        </w:p>
        <w:p>
            <w:pPr><w:ind w:left="5" w:firstLine="559"/><w:jc w:val="both"/><w:rPr><w:sz w:val="28"/></w:rPr></w:pPr>
            <w:r><w:t>铁芯接地电流  (在线)  ： 在线监测装置已接入，无实时数据。</w:t></w:r>
        </w:p>

        <#-- Add other subsections similarly -->
        <#-- (二) 运行监控 -->
        <w:p>
            <w:pPr><w:ind w:left="5" w:firstLine="559"/><w:jc w:val="both"/><w:rPr><w:sz w:val="28"/></w:rPr></w:pPr>
            <w:r><w:t>(二) 运行监控</w:t></w:r>
        </w:p>
        <w:p>
            <w:pPr><w:ind w:left="5" w:firstLine="559"/><w:jc w:val="both"/><w:rPr><w:sz w:val="28"/></w:rPr></w:pPr>
            <w:r><w:t>短路冲击:  本检修周期内共遭受短路冲击${shortCircuitImpacts!"5"}次，本检修周期内最大短路冲击电流比超过70%且次数超过3次。详细情况见附件 ${attachmentShortCircuit!"3"}。</w:t></w:r>
        </w:p>
        <w:p>
            <w:pPr><w:ind w:left="5" w:firstLine="559"/><w:jc w:val="both"/><w:rPr><w:sz w:val="28"/></w:rPr></w:pPr>
            <w:r><w:t>油温 ： ${oilTempDate!"2022年04月2日18:23:15"} 装置离线，待处理。</w:t></w:r>
        </w:p>

        <#-- (三) 带电检测 -->
        <w:p>
            <w:pPr><w:ind w:left="5" w:firstLine="559"/><w:jc w:val="both"/><w:rPr><w:sz w:val="28"/></w:rPr></w:pPr>
            <w:r><w:t>(三) 带电检测</w:t></w:r>
        </w:p>
        <w:p>
            <w:pPr><w:ind w:left="5" w:firstLine="559"/><w:jc w:val="both"/><w:rPr><w:sz w:val="28"/></w:rPr></w:pPr>
            <w:r><w:t>铁芯夹件接地电流检测：上次检测时间${coreGroundDate!"2022年04月2日"}，接地电流：${coreGroundCurrent!"0.1A"}，色谱无异常。</w:t></w:r>
        </w:p>
        <w:p>
            <w:pPr><w:ind w:left="5" w:firstLine="559"/><w:jc w:val="both"/><w:rPr><w:sz w:val="28"/></w:rPr></w:pPr>
            <w:r><w:t>红外热成像检测：上次检测时间${irDate!"2022年04月2日"}，红外图谱识别结果：${irResult!"发现温度异常"}。</w:t></w:r>
        </w:p>
        <w:p>
            <w:pPr><w:ind w:left="5" w:firstLine="559"/><w:jc w:val="both"/><w:rPr><w:sz w:val="28"/></w:rPr></w:pPr>
            <w:r><w:t>高频局部放电检测： ${hfpdRecord!"暂无该项记录"}。</w:t></w:r>
        </w:p>

        <#-- (四) 隐患缺陷 -->
        <w:p>
            <w:pPr><w:ind w:left="5" w:firstLine="559"/><w:jc w:val="both"/><w:rPr><w:sz w:val="28"/></w:rPr></w:pPr>
            <w:r><w:t>(四) 隐患缺陷</w:t></w:r>
        </w:p>
        <w:p>
            <w:pPr><w:ind w:left="5" w:firstLine="559"/><w:jc w:val="both"/><w:rPr><w:sz w:val="28"/></w:rPr></w:pPr>
            <w:r><w:t>${defectPeriodStart!"2019"}年到${defectPeriodEnd!"2022"} 年，未发现缺陷；发现隐患 ${hiddenDangers!"1"} 项 ，全部未完成整改 ，详见附件${attachmentDefects!"4"}。</w:t></w:r>
        </w:p>

        <#-- (五) 检测试验是否超期情况 -->
        <w:p>
            <w:pPr><w:ind w:left="5" w:firstLine="559"/><w:jc w:val="both"/><w:rPr><w:sz w:val="28"/></w:rPr></w:pPr>
            <w:r><w:t>(五) 检测试验是否超期情况</w:t></w:r>
        </w:p>
        <#-- Subsubsections for electrical, chemical, live tests -->
        <w:p>
            <w:pPr><w:ind w:left="5" w:firstLine="559"/><w:jc w:val="both"/><w:rPr><w:sz w:val="28"/></w:rPr></w:pPr>
            <w:r><w:t>（1）电气试验（例行）：</w:t></w:r>
        </w:p>
        <#-- List each test with placeholders -->
        <w:p><w:r><w:t>直流电阻：上次试验时间为${dcResistanceDate!"2022年01月12日"}，按周期要求正常开展。</w:t></w:r></w:p>
        <#-- ... Add similar for others ... -->

        <#-- For tables in attachments, you can use <w:tbl> with <#list> for rows -->

        <#-- Continue for other attachments similarly -->

    </w:body>
</w:document>