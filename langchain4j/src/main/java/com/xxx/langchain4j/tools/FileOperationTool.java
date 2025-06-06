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
 * 文件操作工具类（LangChain4j集成优化版）
 */
@Component
public class FileOperationTool {

    private static final Logger logger = LoggerFactory.getLogger(FileOperationTool.class);
    private final String FILE_DIR = "C:\\Users\\许夏轩\\Desktop\\xxx_picture_tmp\\file";

    @Value("${file.max-size:5485760}") // 默认10MB限制
    private long maxFileSize;

    @Tool("读取指定文件内容")
    public String readFile(
            @P("文件名（含扩展名）") String fileName
    ) {
        try {
            Path filePath = validatePath(fileName);

            if (FileUtil.size(filePath.toFile()) > maxFileSize) {
                logger.warn("文件大小超过限制: {}", fileName);
                return "错误：文件大小超过限制（最大" + (maxFileSize/1024/1024) + "MB）";
            }

            return FileUtil.readString(filePath.toFile(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("文件读取失败: {}", e.getMessage());
            return "错误：" + e.getMessage();
        }
    }

    @Tool("写入内容到文件")
    public String writeFile(
            @P("文件名（含扩展名）") String fileName,
            @P("要写入的内容") String content
    ) {
        try {
            Path filePath = validatePath(fileName);

            if (content.getBytes(StandardCharsets.UTF_8).length > maxFileSize) {
                logger.warn("内容大小超过限制: {}", fileName);
                return "错误：内容大小超过限制（最大" + (maxFileSize/1024/1024) + "MB）";
            }

            FileUtil.mkdir(filePath.getParent().toFile());
            FileUtil.writeString(content, filePath.toFile(), StandardCharsets.UTF_8);
            return "文件写入成功: " + filePath;
        } catch (Exception e) {
            logger.error("文件写入失败: {}", e.getMessage());
            return "错误：" + e.getMessage();
        }
    }

    /**
     * 路径安全校验（防目录遍历攻击）
     */
    private Path validatePath(String fileName) {
        Path baseDir = Paths.get(FILE_DIR).toAbsolutePath().normalize();
        Path resolvedPath = baseDir.resolve(fileName).normalize();

        if (!resolvedPath.startsWith(baseDir)) {
            throw new SecurityException("非法路径访问: " + fileName);
        }
        return resolvedPath;
    }
}