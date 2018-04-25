package com.hecc.costcenter.controller;

import com.hecc.costcenter.entity.ProductEntity;
import com.hecc.costcenter.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xuhoujun
 * @description: 产品控制器
 * @date: Created In 下午10:55 on 2018/4/24.
 */
@Controller
@RequestMapping(value = "/back/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    /**
     * 新增产品
     * @return
     */
    @ApiOperation(value = "获取卡列表")
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public String create(@RequestBody ProductEntity productInfo) {
        try {
            if(productInfo==null || StringUtils.isEmpty(productInfo.getName())){
                return "产品名称不能为空";
            }
            if(StringUtils.isEmpty(productInfo.getCode())){
                return "产品编码不能为空";
            }
            productInfo.setCreateName("admin");
            productInfo.setModifyName("admin");
            return this.productService.create(productInfo);
        } catch (Exception e) {
            logger.error("新增商品异常", e);
        }
        return "保存失败";
    }

    /**
     * 产品--上架
     * @return
     */
    @RequestMapping(value = "upShelf", method = RequestMethod.POST)
    @ResponseBody
    public String upShelf(Long id) {
        try {
            if(id == null){
                return "参数错误";
            }

            ProductEntity productInfo = new ProductEntity();
            productInfo.setId(id);
            productInfo.setShelf(1);
            productInfo.setModifyName("admin");
            this.productService.updateShelf(productInfo);
            return "上架成功";
        } catch (Exception e) {
            logger.error("商品上架异常", e);
        }
        return "上架失败";
    }

    /**
     * 产品--下架
     * @return
     */
    @RequestMapping(value = "downShelf", method = RequestMethod.POST)
    @ResponseBody
    public String downShelf(Long id) {
        try {
            if(id == null){
                return "参数错误";
            }
            ProductEntity productInfo = new ProductEntity();
            productInfo.setId(id);
            productInfo.setShelf(0);
            productInfo.setModifyName("admin");
            this.productService.updateShelf(productInfo);
            return "下架成功";
        } catch (Exception e) {
            logger.error("商品下架异常", e);
        }
        return "下架失败";
    }

    /**
     * 产品--详情
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    @ResponseBody
    public ProductEntity getEntityById(Long id) {
        return this.productService.getEntityById(id);
    }

    /**
     * 产品--修改
     * @return
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public String update(@RequestBody ProductEntity productInfo) {
        try {
            if(productInfo == null){
                return "参数错误";
            }
            productInfo.setModifyName("admin");
            return this.productService.update(productInfo);
        } catch (Exception e) {
            logger.error("修改产品异常", e);
        }
        return "保存失败";
    }
}
