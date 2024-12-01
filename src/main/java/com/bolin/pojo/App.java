package com.bolin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 应用
 * @TableName app
 */
@TableName(value ="app")
@Data
public class App implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 应用名
     */
    private String appname;

    /**
     * 应用描述
     */
    private String appdesc;

    /**
     * 应用图标
     */
    private String appicon;

    /**
     * 应用类型（0-得分类，1-测评类）
     */
    private Integer apptype;

    /**
     * 评分策略（0-自定义，1-AI）
     */
    private Integer scoringstrategy;

    /**
     * 审核状态：0-待审核, 1-通过, 2-拒绝
     */
    private Integer reviewstatus;

    /**
     * 审核信息
     */
    private String reviewmessage;

    /**
     * 审核人 id
     */
    private Long reviewerid;

    /**
     * 审核时间
     */
    private Date reviewtime;

    /**
     * 创建用户 id
     */
    private Long userid;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 更新时间
     */
    private Date updatetime;

    /**
     * 是否删除
     */
    private Integer isdelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        App other = (App) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAppname() == null ? other.getAppname() == null : this.getAppname().equals(other.getAppname()))
            && (this.getAppdesc() == null ? other.getAppdesc() == null : this.getAppdesc().equals(other.getAppdesc()))
            && (this.getAppicon() == null ? other.getAppicon() == null : this.getAppicon().equals(other.getAppicon()))
            && (this.getApptype() == null ? other.getApptype() == null : this.getApptype().equals(other.getApptype()))
            && (this.getScoringstrategy() == null ? other.getScoringstrategy() == null : this.getScoringstrategy().equals(other.getScoringstrategy()))
            && (this.getReviewstatus() == null ? other.getReviewstatus() == null : this.getReviewstatus().equals(other.getReviewstatus()))
            && (this.getReviewmessage() == null ? other.getReviewmessage() == null : this.getReviewmessage().equals(other.getReviewmessage()))
            && (this.getReviewerid() == null ? other.getReviewerid() == null : this.getReviewerid().equals(other.getReviewerid()))
            && (this.getReviewtime() == null ? other.getReviewtime() == null : this.getReviewtime().equals(other.getReviewtime()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getCreatetime() == null ? other.getCreatetime() == null : this.getCreatetime().equals(other.getCreatetime()))
            && (this.getUpdatetime() == null ? other.getUpdatetime() == null : this.getUpdatetime().equals(other.getUpdatetime()))
            && (this.getIsdelete() == null ? other.getIsdelete() == null : this.getIsdelete().equals(other.getIsdelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAppname() == null) ? 0 : getAppname().hashCode());
        result = prime * result + ((getAppdesc() == null) ? 0 : getAppdesc().hashCode());
        result = prime * result + ((getAppicon() == null) ? 0 : getAppicon().hashCode());
        result = prime * result + ((getApptype() == null) ? 0 : getApptype().hashCode());
        result = prime * result + ((getScoringstrategy() == null) ? 0 : getScoringstrategy().hashCode());
        result = prime * result + ((getReviewstatus() == null) ? 0 : getReviewstatus().hashCode());
        result = prime * result + ((getReviewmessage() == null) ? 0 : getReviewmessage().hashCode());
        result = prime * result + ((getReviewerid() == null) ? 0 : getReviewerid().hashCode());
        result = prime * result + ((getReviewtime() == null) ? 0 : getReviewtime().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getCreatetime() == null) ? 0 : getCreatetime().hashCode());
        result = prime * result + ((getUpdatetime() == null) ? 0 : getUpdatetime().hashCode());
        result = prime * result + ((getIsdelete() == null) ? 0 : getIsdelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", appname=").append(appname);
        sb.append(", appdesc=").append(appdesc);
        sb.append(", appicon=").append(appicon);
        sb.append(", apptype=").append(apptype);
        sb.append(", scoringstrategy=").append(scoringstrategy);
        sb.append(", reviewstatus=").append(reviewstatus);
        sb.append(", reviewmessage=").append(reviewmessage);
        sb.append(", reviewerid=").append(reviewerid);
        sb.append(", reviewtime=").append(reviewtime);
        sb.append(", userid=").append(userid);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}