package com.mall.cart.service;

import com.mall.cart.client.GoodsClient;
import com.mall.cart.interceptor.LoginInterceptor;
import com.mall.cart.pojo.Cart;
import com.mall.cart.utils.JsonUtils;
import com.mall.common.pojo.UserInfo;
import com.mall.pojo.Sku;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author pan
 * @create 2020-05-02-10:45
 */
@Service
public class CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private GoodsClient goodsClient;

    static final String KEY_PREFIX = "mall:cart:uid:";

    static final Logger logger = LoggerFactory.getLogger(CartService.class);

    public void addCart(Cart cart) {
        // 获取登录用户
        UserInfo user = LoginInterceptor.getLoginUser();
        // Redis的key
        String key = KEY_PREFIX + user.getId();
        // 获取hash操作对象
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);
        // 查询是否存在
        Long skuId = cart.getSkuId();
        Integer num = cart.getNum();
        Boolean boo = hashOps.hasKey(skuId.toString());
        if (boo) {
            // 存在，则获取购物车数据，修改购物车数量
            String json = hashOps.get(skuId.toString()).toString();
            cart = JsonUtils.parse(json, Cart.class);
            cart.setNum(cart.getNum() + num);
        } else {
            // 不存在，新增购物车数据
            cart.setUserId(user.getId());
            // 因为前台为了节省带宽只传入sku的id，需要通过这 个id查询其它商品信息，组合后加入redis
            Sku sku = this.goodsClient.querySkuById(skuId);
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
            cart.setPrice(sku.getPrice());
            cart.setTitle(sku.getTitle());
            cart.setOwnSpec(sku.getOwnSpec());
        }
        // 将购物车数据写入redis
        hashOps.put(cart.getSkuId().toString(), JsonUtils.serialize(cart));
    }

    public List<Cart> queryCartList() {
        // 获取登录用户
        UserInfo user = LoginInterceptor.getLoginUser();

        // 判断是否存在购物车
        String key = KEY_PREFIX + user.getId();
        if(!this.redisTemplate.hasKey(key)){
            // 不存在，直接返回
            return null;
        }
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);
        List<Object> carts = hashOps.values();
        // 判断是否有数据
        if(CollectionUtils.isEmpty(carts)){
            return null;
        }
        // 查询购物车数据
        return carts.stream().map(o -> JsonUtils.parse(o.toString(), Cart.class)).collect(Collectors.toList());
    }

    public void updateCarts(Cart cart) {
        // 获取登陆信息
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        String key = KEY_PREFIX + userInfo.getId();
        // 获取hash操作对象
        BoundHashOperations<String, Object, Object> hashOperations = this.redisTemplate.boundHashOps(key);
        // 获取购物车信息
        String cartJson = hashOperations.get(cart.getSkuId().toString()).toString();
        Cart cart1 = JsonUtils.parse(cartJson, Cart.class);
        // 更新数量
        cart1.setNum(cart.getNum());
        // 写入购物车
        hashOperations.put(cart.getSkuId().toString(), JsonUtils.serialize(cart1));
    }

    public void deleteCart(String skuId) {
        // 获取登录用户
        UserInfo user = LoginInterceptor.getLoginUser();
        String key = KEY_PREFIX + user.getId();
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);
        hashOps.delete(skuId);
    }
}
