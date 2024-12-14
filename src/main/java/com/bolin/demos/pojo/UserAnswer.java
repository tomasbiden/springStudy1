package com.bolin.demos.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户答题记录
 * @TableName user_answer
 */
@TableName(value ="user_answer")
@Data
public class UserAnswer implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 应用 id
     */
    private Long appid;

    /**
     * 应用类型（0-得分类，1-角色测评类）
     */
    private Integer apptype;

    /**
     * 评分策略（0-自定义，1-AI）
     */
    private Integer scoringstrategy;

    /**
     * 用户答案（JSON 数组）
     */
    private String choices;

    /**
     * 评分结果 id
     */
    private Long resultid;

    /**
     * 结果名称，如物流师
     */
    private String resultname;

    /**
     * 结果描述
     */
    private String resultdesc;

    /**
     * 结果图标
     */
    private String resultpicture;

    /**
     * 得分
     */
    private Integer resultscore;

    /**
     * 用户 id
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
        UserAnswer other = (UserAnswer) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAppid() == null ? other.getAppid() == null : this.getAppid().equals(other.getAppid()))
            && (this.getApptype() == null ? other.getApptype() == null : this.getApptype().equals(other.getApptype()))
            && (this.getScoringstrategy() == null ? other.getScoringstrategy() == null : this.getScoringstrategy().equals(other.getScoringstrategy()))
            && (this.getChoices() == null ? other.getChoices() == null : this.getChoices().equals(other.getChoices()))
            && (this.getResultid() == null ? other.getResultid() == null : this.getResultid().equals(other.getResultid()))
            && (this.getResultname() == null ? other.getResultname() == null : this.getResultname().equals(other.getResultname()))
            && (this.getResultdesc() == null ? other.getResultdesc() == null : this.getResultdesc().equals(other.getResultdesc()))
            && (this.getResultpicture() == null ? other.getResultpicture() == null : this.getResultpicture().equals(other.getResultpicture()))
            && (this.getResultscore() == null ? other.getResultscore() == null : this.getResultscore().equals(other.getResultscore()))
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
        result = prime * result + ((getAppid() == null) ? 0 : getAppid().hashCode());
        result = prime * result + ((getApptype() == null) ? 0 : getApptype().hashCode());
        result = prime * result + ((getScoringstrategy() == null) ? 0 : getScoringstrategy().hashCode());
        result = prime * result + ((getChoices() == null) ? 0 : getChoices().hashCode());
        result = prime * result + ((getResultid() == null) ? 0 : getResultid().hashCode());
        result = prime * result + ((getResultname() == null) ? 0 : getResultname().hashCode());
        result = prime * result + ((getResultdesc() == null) ? 0 : getResultdesc().hashCode());
        result = prime * result + ((getResultpicture() == null) ? 0 : getResultpicture().hashCode());
        result = prime * result + ((getResultscore() == null) ? 0 : getResultscore().hashCode());
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
        sb.append(", appid=").append(appid);
        sb.append(", apptype=").append(apptype);
        sb.append(", scoringstrategy=").append(scoringstrategy);
        sb.append(", choices=").append(choices);
        sb.append(", resultid=").append(resultid);
        sb.append(", resultname=").append(resultname);
        sb.append(", resultdesc=").append(resultdesc);
        sb.append(", resultpicture=").append(resultpicture);
        sb.append(", resultscore=").append(resultscore);
        sb.append(", userid=").append(userid);
        sb.append(", createtime=").append(createtime);
        sb.append(", updatetime=").append(updatetime);
        sb.append(", isdelete=").append(isdelete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}