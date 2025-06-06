package com.xxx.langchain4j.tools;

import cn.hutool.core.io.FileUtil;
import com.xxx.langchain4j.constant.FileConstant;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * LangChain4j规范的HTML生成工具
 */
@Component
public class HTMLGenerationTool {

    private static final Logger logger = LoggerFactory.getLogger(HTMLGenerationTool.class);
    private final String BASE_DIR = "C:\\Users\\许夏轩\\Desktop\\xxx_picture_tmp\\file";

    @Value("${html.max-size:5242880}") // 默认5MB限制
    private long maxContentSize;

    @Tool("生成HTML文件（自动添加完整结构）")
    public String generateHTML(
        @P("文件名（需包含.html或.htm后缀）") String fileName,
        @P("正文内容") String content,
        @P(value = "页面标题", required = false) String title
    ) {
        try {
            // 1. 路径安全校验
            Path filePath = validateAndResolvePath(fileName);
            
            // 2. 内容长度检查
            if (content.getBytes(StandardCharsets.UTF_8).length > maxContentSize) {
                String msg = String.format("内容超过大小限制（最大%.1fMB）", maxContentSize/1024f/1024f);
                logger.warn(msg);
                return msg;
            }

            // 3. 创建目录结构
            FileUtil.mkdir(filePath.getParent().toFile());

            // 4. 生成完整HTML文档
            String htmlContent = buildHtmlDocument(content, title);
            FileUtil.writeString(htmlContent, filePath.toFile(), StandardCharsets.UTF_8);
            
            return "HTML文件生成成功：" + filePath;
        } catch (Exception e) {
            logger.error("HTML生成失败", e);
            return "生成失败：" + e.getMessage();
        }
    }

    /**
     * 构建完整的HTML文档结构
     */
    private String buildHtmlDocument(String content, String title) {
        return String.format("""
            <!DOCTYPE html>
            <html lang="zh-CN">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>%s</title>
                <style>
                    body { font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, sans-serif; line-height: 1.6; }
                    .container { max-width: 800px; margin: 0 auto; padding: 20px; }
                </style>
            </head>
            <body>
                <div class="container">
                    %s
                </div>
            </body>
            </html>
            """, 
            title != null ? escapeHtml(title) : "AI生成文档",
            content
        );
    }

    /**
     * HTML特殊字符转义
     */
    private String escapeHtml(String input) {
        return input.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;");
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
        
        if (!fileName.toLowerCase().matches(".*\\.(html|htm)$")) {
            throw new IllegalArgumentException("文件名必须以.html或.htm结尾");
        }
        
        return resolvedPath;
    }
}