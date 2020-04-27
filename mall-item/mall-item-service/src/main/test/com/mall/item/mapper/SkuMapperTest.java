/*
package com.mall.item.mapper;

import com.mall.ItemServiceApplication;
import com.mall.pojo.Sku;
import com.mall.pojo.Spu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.security.RunAs;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

*/
/**
 * @author pan
 * @create 2020-04-26-20:38
 *//*

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItemServiceApplication.class)
public class SkuMapperTest {
    @Autowired
    SkuMapper skuMapper;

    @Test
    public void changeImgUrl(){
        List<Sku> skus = skuMapper.selectAll();
        for(Sku sku : skus){
            String images = sku.getImages();
            System.out.println(images);
            String replace = images.replace("leyou", "mall");
            System.out.println(replace);
            sku.setImages(replace);
            skuMapper.updateByPrimaryKeySelective(sku);
        }
    }
}*/
