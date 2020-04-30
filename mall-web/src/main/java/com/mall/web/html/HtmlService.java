package com.mall.web.html;

import com.mall.web.service.GoodsService;
import com.mall.web.utils.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 用来商品详情页的页面静态化
 *
 * @author pan
 * @create 2020-04-29-16:59
 */
@Service
public class HtmlService {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private TemplateEngine templateEngine;

    private static final Logger LOGGER = LoggerFactory.getLogger(HtmlService.class);

    /**
     * 创建html页面
     *
     * @param spuId
     * @throws Exception
     */
    public void createHtml(Long spuId) {

        PrintWriter writer = null;
        try {
            // 获取页面数据
            Map<String, Object> spuMap = this.goodsService.loadData(spuId);

            // 创建thymeleaf上下文对象
            Context context = new Context();
            // 把数据放入上下文对象
            context.setVariables(spuMap);

            // 创建输出流,把它放在
            File file = new File("F:\\graduation-project\\html\\" + spuId + ".html");
            writer = new PrintWriter(file);
            // 执行页面静态化方法
            templateEngine.process("item", context, writer);
        } catch (Exception e) {
            LOGGER.error("页面静态化出错：{}，" + e, spuId);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * 为提高效率，可以使用异步的方式来创建html页面，提高响应速度
     *
     * @param spuId
     */
    public void asyncExecute(Long spuId) {
        ThreadUtils.execute(() -> createHtml(spuId));
    }

    /**
     * 通过id删除指定路径的html静态资源
     * @param id
     */
    public void deleteHtml(Long id) {
        File file = new File("F:\\graduation-project\\html\\", id + ".html");
        file.deleteOnExit();
    }
}
