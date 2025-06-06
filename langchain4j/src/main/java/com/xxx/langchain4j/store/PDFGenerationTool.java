package com.xxx.langchain4j.store;

import cn.hutool.core.io.FileUtil;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * LangChain4j规范的PDF生成工具
 */
@Component
public class PDFGenerationTool {

    private static final Logger logger = LoggerFactory.getLogger(PDFGenerationTool.class);
    private final String BASE_DIR = "C:\\Users\\许夏轩\\Desktop\\xxx_picture_tmp\\file";

    @Value("${pdf.max-size:5485760}") // 默认10MB限制
    private long maxContentSize;

    @Tool("生成PDF文档（自动处理中文）")
    public String generatePDF(
        @P("文件名（需包含.pdf后缀）") String fileName,
        @P("文档内容") String content
    ) {
        try {
            // 1. 路径安全校验
            Path filePath = validateAndResolvePath(fileName);

            // 2. 内容长度检查
            if (content.getBytes().length > maxContentSize) {
                String msg = String.format("内容超过大小限制（最大%.1fMB）", maxContentSize/1024f/1024f);
                logger.warn(msg);
                return msg;
            }

            // 3. 创建目录结构
            FileUtil.mkdir(filePath.getParent().toFile());

            // 4. 生成PDF（带中文支持）
            generatePdfDocument(filePath, content);

            return "PDF生成成功：" + filePath;
        } catch (Exception e) {
            logger.error("PDF生成失败", e);
            return "生成失败：" + e.getMessage();
        }
    }

    private void generatePdfDocument(Path filePath, String content) throws IOException {
        try (PdfWriter writer = new PdfWriter(filePath.toString());
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // 使用系统内置中文字体（无需额外字体文件）
            PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H");

            // 设置文档属性
            document.setFont(font)
                  .setMargins(50, 50, 50, 50);

            // 添加内容段落
            Paragraph paragraph = new Paragraph(content)
                .setFontSize(12)
                .setFirstLineIndent(24);

            document.add(paragraph);
        }
    }

    /**
     * 路径安全校验（防目录遍历攻击）
     */
    private Path validateAndResolvePath(String fileName) {
        Path baseDir = Paths.get(BASE_DIR).toAbsolutePath().normalize();
        Path resolvedPath = baseDir.resolve(fileName).normalize();

        if (!resolvedPath.startsWith(baseDir)) {
            throw new SecurityException("非法路径访问: " + fileName);
        }

        if (!fileName.toLowerCase().endsWith(".pdf")) {
            throw new IllegalArgumentException("文件名必须以.pdf结尾");
        }

        return resolvedPath;
    }
}