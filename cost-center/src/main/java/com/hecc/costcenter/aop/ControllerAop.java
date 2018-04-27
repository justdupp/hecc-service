package com.hecc.costcenter.aop;

import com.hecc.costcenter.entity.ResultBean;
import com.hecc.costcenter.param.BaseParamInfo;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author xuhoujun
 * @description: aop 切面
 * @date: Created In 下午10:33 on 2018/4/26.
 */
@Aspect
@Component
public class ControllerAop {

    private static Logger logger = LoggerFactory.getLogger(ControllerAop.class);
    public static final int defaultPage = 10000;

    @Around(value = "execution(public com.hecc.costcenter.entity.ResultBean com.hecc.costcenter.controller..*.*(..))")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) {
        ResultBean<?> result;
        try {
            result = (ResultBean<?>) pjp.proceed();
            result = setPaging(result, pjp);
        } catch (Throwable throwable) {
            result = handlerException(pjp, throwable);
        }
        return result;
    }


    public ResultBean<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        if (e instanceof NullPointerException) {
            e.printStackTrace();
        }
        logger.error("查询异常 - 类:{} - 方法:{}, errMsg:{}", pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName(), e);
        return new ResultBean<>(e);
    }


    /**
     * 分页逻辑
     *
     * @param result 结果
     * @param pjp    切点
     * @return
     * @throws Throwable
     */
    private ResultBean<?> setPaging(ResultBean<?> result, ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        if (args[0] != null && args[0] instanceof BaseParamInfo) {
            BaseParamInfo paramInfo = ((BaseParamInfo) args[0]);
            Integer currentPage = paramInfo.getPage();
            Integer pageSize = paramInfo.getRows();
            Boolean isTotal = paramInfo.getTotal();
            if (result.getRows() != null && currentPage != null && pageSize != null) {
                Integer listSize = result.getRows().size();
                Integer prevPage = currentPage - 1;
                if (isTotal && currentPage * pageSize == defaultPage) {
                    Integer newTotal = result.getTotal();
                    if (newTotal > 0) {
                        int newPage = (int) Math.ceil(newTotal * 1.0D / pageSize);
                        ((BaseParamInfo) args[0]).setPage(newPage);
                        ((BaseParamInfo) args[0]).setTotal(false);
                        result = (ResultBean<?>) pjp.proceed(args);
                        result.setTotal(newTotal);
                    }
                } else {
                    if (currentPage == 1) {
                        if (listSize < pageSize) {
                            result.setTotal(listSize);
                        } else if (listSize.equals(pageSize)) {
                            result.setTotal(defaultPage);
                        }
                    } else if (currentPage > 1) {
                        if (listSize == 0) {
                            ((BaseParamInfo) args[0]).setPage(prevPage);
                            result = (ResultBean<?>) pjp.proceed(args);
                            result.setTotal(prevPage * pageSize);
                        } else if (listSize < pageSize) {
                            result.setTotal(prevPage * pageSize + listSize);
                        } else if (listSize.equals(pageSize)) {
                            result.setTotal(defaultPage);
                        }
                    }
                }
            }
        }
        return result;
    }
}
