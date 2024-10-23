package com.example.photoalbum.common.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @TableName userToAlbum
 */
@TableName(value ="userToAlbum")
@Data
@AllArgsConstructor
public class Usertoalbum implements Serializable {
    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String albumName;

    /**
     * 
     */
    private Integer like;

    /**
     * 
     */
    private Integer favorite;

    /**
     * 
     */
    private Integer recommend;

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
        Usertoalbum other = (Usertoalbum) that;
        return (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getAlbumName() == null ? other.getAlbumName() == null : this.getAlbumName().equals(other.getAlbumName()))
            && (this.getLike() == null ? other.getLike() == null : this.getLike().equals(other.getLike()))
            && (this.getFavorite() == null ? other.getFavorite() == null : this.getFavorite().equals(other.getFavorite()))
            && (this.getRecommend() == null ? other.getRecommend() == null : this.getRecommend().equals(other.getRecommend()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getAlbumName() == null) ? 0 : getAlbumName().hashCode());
        result = prime * result + ((getLike() == null) ? 0 : getLike().hashCode());
        result = prime * result + ((getFavorite() == null) ? 0 : getFavorite().hashCode());
        result = prime * result + ((getRecommend() == null) ? 0 : getRecommend().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", username=").append(username);
        sb.append(", albumName=").append(albumName);
        sb.append(", like=").append(like);
        sb.append(", favorite=").append(favorite);
        sb.append(", recommend=").append(recommend);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}