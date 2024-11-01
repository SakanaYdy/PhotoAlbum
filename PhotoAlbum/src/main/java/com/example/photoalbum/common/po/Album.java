package com.example.photoalbum.common.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName album
 */
@TableName(value ="album")
@Data
public class Album implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String albumName;

    /**
     * 
     */
    private String owner;

    /**
     * 
     */
    private String photos;

    /**
     * 
     */
    private String avatar_url;

    /**
     * 
     */
    private Integer thumbs;

    /**
     * 
     */
    private Integer favorites;

    /**
     * 
     */
    private Integer recommends;
    /**
     * 相册类别
     */
    private String label;

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
        Album other = (Album) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAlbumName() == null ? other.getAlbumName() == null : this.getAlbumName().equals(other.getAlbumName()))
            && (this.getOwner() == null ? other.getOwner() == null : this.getOwner().equals(other.getOwner()))
            && (this.getPhotos() == null ? other.getPhotos() == null : this.getPhotos().equals(other.getPhotos()))
            && (this.getAvatar_url() == null ? other.getAvatar_url() == null : this.getAvatar_url().equals(other.getAvatar_url()))
            && (this.getThumbs() == null ? other.getThumbs() == null : this.getThumbs().equals(other.getThumbs()))
            && (this.getFavorites() == null ? other.getFavorites() == null : this.getFavorites().equals(other.getFavorites()))
            && (this.getRecommends() == null ? other.getRecommends() == null : this.getRecommends().equals(other.getRecommends()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAlbumName() == null) ? 0 : getAlbumName().hashCode());
        result = prime * result + ((getOwner() == null) ? 0 : getOwner().hashCode());
        result = prime * result + ((getPhotos() == null) ? 0 : getPhotos().hashCode());
        result = prime * result + ((getAvatar_url() == null) ? 0 : getAvatar_url().hashCode());
        result = prime * result + ((getThumbs() == null) ? 0 : getThumbs().hashCode());
        result = prime * result + ((getFavorites() == null) ? 0 : getFavorites().hashCode());
        result = prime * result + ((getRecommends() == null) ? 0 : getRecommends().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", albumName=").append(albumName);
        sb.append(", owner=").append(owner);
        sb.append(", photos=").append(photos);
        sb.append(", avatar_url=").append(avatar_url);
        sb.append(", thumbs=").append(thumbs);
        sb.append(", favorites=").append(favorites);
        sb.append(", recommends=").append(recommends);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}