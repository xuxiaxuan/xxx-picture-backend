package com.xxx.langchain4j.tools;

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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 改造后的PDF生成工具类，直接返回字节流
 */
@Component
public class PDFGenerationTool {

    private static final Logger logger = LoggerFactory.getLogger(PDFGenerationTool.class);

    @Tool("生成PDF文档（自动处理中文）")
    public ResponseEntity<byte[]> generatePDF(
        @P("文件名（需包含.pdf后缀）") String fileName,
        @P("文档内容") String content
    ) {
        try {
            // 1. 校验文件名
            if (!fileName.toLowerCase().endsWith(".pdf")) {
                throw new IllegalArgumentException("文件名必须以.pdf结尾");
            }

            // 2. 生成PDF字节流
            byte[] pdfBytes = generatePdfBytes(content);

            // 3. 构建响应
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);

        } catch (Exception e) {
            logger.error("PDF生成失败", e);
            throw new RuntimeException("生成失败：" + e.getMessage());
        }
    }

    private byte[] generatePdfBytes(String content) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PdfWriter writer = new PdfWriter(baos);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // 使用系统内置中文字体
            PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H");
            
            // 设置文档属性
            document.setFont(font)
                  .setMargins(50, 50, 50, 50);
            
            // 添加内容段落
            Paragraph paragraph = new Paragraph(content)
                .setFontSize(12)
                .setFirstLineIndent(24);
            
            document.add(paragraph);

            return baos.toByteArray();
        }
    }
}