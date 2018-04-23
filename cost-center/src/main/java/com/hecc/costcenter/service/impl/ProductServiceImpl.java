package com.hecc.costcenter.service.impl;

import com.hecc.costcenter.entity.ProductEntity;
import com.hecc.costcenter.mapper.ProductDao;
import com.hecc.costcenter.param.ProductParamInfo;
import com.hecc.costcenter.service.ProductService;
import com.hecc.costcenter.service.feign.IdClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xuhoujun
 * @description: 产品服务实现
 * @date: Created In 下午8:47 on 2018/4/23.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductDao productDao;

    @Autowired
    private IdClient idClient;

    @Override
    public List<ProductEntity> getProductListByParam(ProductParamInfo paramInfo) {
        logger.info("这里是日志，请查收哦");
        return productDao.getProductListByParam(paramInfo);
    }

    @Override
    public Integer getProductTotalByParam(ProductParamInfo paramInfo) {
        return productDao.getProductTotalByParam(paramInfo);
    }

    @Override
    public String create(ProductEntity entity) {
        /**
         * 校验产品名称不能重复
         */
        List<ProductEntity> nameList = this.productDao.getListByName(entity);
        if(!nameList.isEmpty()){
            return "产品名称已存在";
        }
        List<ProductEntity> codeList = this.productDao.getListByCode(entity);
        if(!codeList.isEmpty()){
            return "产品编码已存在";
        }
        entity.setId(idClient.getId("_id"));
        productDao.create(entity);
        return "保存成功";
    }

    @Override
    public void updateShelf(ProductEntity entity) {
        this.productDao.updateShelf(entity);
    }

    @Override
    public ProductEntity getEntityById(Long id) {
        return productDao.getEntityById(id);
    }

    @Override
    public String update(ProductEntity entity) {
        /**
         * 校验产品名称不能重复
         */
        List<ProductEntity> nameList = this.productDao.getListByName(entity);
        if(!nameList.isEmpty()){
            for(ProductEntity info : nameList){
                if(info.getName().equals(entity.getName())){
                    return "产品名称已存在";
                }
            }
        }
        List<ProductEntity> codeList = this.productDao.getListByCode(entity);
        if(!codeList.isEmpty()){
            for(ProductEntity info : nameList){
                if(info.getCode().equals(entity.getCode())){
                    return "产品编码已存在";
                }
            }
        }
        this.productDao.update(entity);
        return "保存成功";
    }
}
