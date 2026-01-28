package com.zwx.ai;

import com.zwx.langchain.LangChainApplication;
import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;


@DisplayName("聊天测试-单独使用lang4J的依赖")
@SpringBootTest(classes = LangChainApplication.class)
public class TestChat {

    @Value("${langchain4j.community.dashscope.chat-model.api-key}")
    private String openAiApiKey;

    @Resource
    private QwenChatModel qwenChatModel;


    @Test
    public void testChat01() {
        OpenAiChatModel model = OpenAiChatModel.builder()
                .apiKey("demo")
                .baseUrl("http://langchain4j.dev/demo/openai/v1")
                .modelName("gpt-4o-mini")
                .build();

        String answer = model.chat("你是谁?");
        System.out.println(answer); // Hello World
    }

    /**
     *
     */
    @Test
    public void testChat02() {
//        String deepSeekApiKey = System.getenv("DEEP_SEEK_API_KEY");
//        System.out.println("deepSeekApiKey = " + deepSeekApiKey);
//
//        String qwenApiKey = System.getenv("QWEN_API_KEY");
//        System.out.println("qwenApiKey = " + qwenApiKey);

//        QwenChatModel qwenChatModel = QwenChatModel.builder()
//                .apiKey(openAiApiKey)
//                .modelName("qwen-max")
//                .build();

        String chat = qwenChatModel.chat("你好,你是谁?");

        System.out.println(chat);

    }

    @Test
    public void testChat03() {
        System.out.println(openAiApiKey);
    }
}
