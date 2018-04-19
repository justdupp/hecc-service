package com.hecc.framework.entity;

/**
 * @author xuhoujun
 * @description: id片段对象（线程安全）
 * @date: Created In 下午11:49 on 2018/4/19.
 */
public final class IdSegment {

    /**
     * id片断开始值
     */
    private long start;
    /**
     * id片断结束值
     */
    private long end;

    public IdSegment(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object other) {
        final IdSegment otherIdSegment = this.safeCast(other);
        return otherIdSegment != null
                && otherIdSegment.getStart() == this.getStart()
                && otherIdSegment.getEnd() == this.getEnd();
    }

    /**
     * 安全类型转换
     *
     * @param source 源类型实例
     * @return IdSegment 否则返回null
     */
    private IdSegment safeCast(Object source) {
        if (source == null) {
            return null;
        }
        if (IdSegment.class.isInstance(source)) {
            return IdSegment.class.cast(source);
        }
        return null;
    }
}
