package com.bolin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 评分结果
 * @TableName scoring_result
 */
@TableName(value ="scoring_result")
@Data
public class ScoringResult implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 结果名称，如物流师
     */
    private String resultname;

    /**
     * 结果描述
     */
    private String resultdesc;

    /**
     * 结果图片
     */
    private String resultpicture;

    /**
     * 结果属性集合 JSON，如 [I,S,T,J]
     */
    private String resultprop;

    /**
     * 结果得分范围，如 80，表示 80及以上的分数命中此结果
     */
    private Integer resultscorerange;

    /**
     * 应用 id
     */
    private Long appid;

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
        ScoringResult other = (ScoringResult) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getResultname() == null ? other.getResultname() == null : this.getResultname().equals(other.getResultname()))
            && (this.getResultdesc() == null ? other.getResultdesc() == null : this.getResultdesc().equals(other.getResultdesc()))
            && (this.getResultpicture() == null ? other.getResultpicture() == null : this.getResultpicture().equals(other.getResultpicture()))
            && (this.getResultprop() == null ? other.getResultprop() == null : this.getResultprop().equals(other.getResultprop()))
            && (this.getResultscorerange() == null ? other.getResultscorerange() == null : this.getResultscorerange().equals(other.getResultscorerange()))
            && (this.getAppid() == null ? other.getAppid() == null : this.getAppid().equals(other.getAppid()))
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
        result = prime * result + ((getResultname() == null) ? 0 : getResultname().hashCode());
        result = prime * result + ((getResultdesc() == null) ? 0 : getResultdesc().hashCode());
        result = prime * result + ((getResultpicture() == null) ? 0 : getResultpicture().hashCode());
        result = prime * result + ((getResultprop() == null) ? 0 : getResultprop().hashCode());
        result = prime * result + ((getResultscorerange() == null) ? 0 : getResultscorerange().hashCode());
        result = prime * result + ((getAppid() == null) ? 0 : getAppid().hashCode());
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
        sb.append(", resultname=").append(resultname);
        sb.append(", resultdesc=").append(resultdesc);
        sb.append(", resultpicture=").append(resultpicture);
        sb.append(", resultprop=").append(resultprop);
        sb.append(", resultscorerange=").append(resultscorerange);
        sb.append(", appid=").append(appid);
        sb.append(", userid=").append(userid);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}