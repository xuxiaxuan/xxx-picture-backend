package com.xxx.langchain4j.tools;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import dev.langchain4j.agent.tool.Tool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ImageSearchTools {

    private static final Logger logger = LoggerFactory.getLogger(ImageSearchTools.class);
    private static final String PEXELS_API_URL = "https://api.pexels.com/v1/search";

    @Value("${pexels.api-key}")
    private String apiKey;

    @Tool("搜索高质量免费图片")
    public String searchImages(String query) {
        try {
            // 构建请求
            String url = PEXELS_API_URL + "?query=" + query + "&per_page=5";
            logger.info("Pexels图片搜索请求: {}", url);

            HttpResponse response = HttpUtil.createGet(url)
                    .header("Authorization", apiKey) // 确保API密钥正确传递
                    .timeout(5000) // 缩短超时时间
                    .execute();

            int status = response.getStatus();
            String body = response.body();
            logger.info("API响应状态: {}, 长度: {}", status, body.length());

            // 记录前200个字符用于调试
            String preview = body.length() > 200 ? body.substring(0, 200) : body;
            logger.debug("API响应预览: {}", preview);

            // 检查响应是否为JSON
            if (!body.trim().startsWith("{")) {
                // 尝试检测常见错误类型
                if (body.contains("Invalid API key")) {
                    return "API密钥无效，请检查配置";
                } else if (body.contains("rate limit")) {
                    return "请求过于频繁，请稍后再试";
                } else if (status != 200) {
                    return "API请求失败: HTTP " + status;
                }
                return "API返回了非JSON响应";
            }

            JSONObject jsonObject = JSONUtil.parseObj(body);
            JSONArray photos = jsonObject.getJSONArray("photos");

            if (photos == null || photos.isEmpty()) {
                return "未找到相关图片，请尝试其他关键词";
            }

            StringBuilder imageUrls = new StringBuilder();
            int resultCount = Math.min(5, photos.size());

            for (int i = 0; i < resultCount; i++) {
                JSONObject photo = photos.getJSONObject(i);
                String urlOriginal = photo.getJSONObject("src").getStr("original");
                String photographer = photo.getStr("photographer");

                imageUrls.append(String.format("图片 %d: %s\n摄影师: %s\n\n",
                        i + 1, urlOriginal, photographer));
            }

            return "来源Pexels免费图库（可商用）:\n" + imageUrls.toString().trim();

        } catch (Exception e) {
            logger.error("图片搜索异常", e);
            return "图片搜索失败: " + e.getClass().getSimpleName();
        }
    }
}