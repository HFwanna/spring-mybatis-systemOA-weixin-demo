package com.hf.springbootmvn.demo.entity;

import java.io.Serializable;

/**
 * @author
 */
public class Permision implements Serializable {
    private Integer id;
    //用來給前端是否显示uri打勾的标识
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    private String uri;

    private String name;

    private String action;

    private Byte c;

    private Byte u;

    private Byte r;

    private Byte d;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Byte getC() {
        return c;
    }

    public void setC(Byte c) {
        this.c = c;
    }

    public Byte getU() {
        return u;
    }

    public void setU(Byte u) {
        this.u = u;
    }

    public Byte getR() {
        return r;
    }

    public void setR(Byte r) {
        this.r = r;
    }

    public Byte getD() {
        return d;
    }

    public void setD(Byte d) {
        this.d = d;
    }

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
        Permision other = (Permision) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUri() == null ? other.getUri() == null : this.getUri().equals(other.getUri()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getAction() == null ? other.getAction() == null : this.getAction().equals(other.getAction()))
                && (this.getC() == null ? other.getC() == null : this.getC().equals(other.getC()))
                && (this.getU() == null ? other.getU() == null : this.getU().equals(other.getU()))
                && (this.getR() == null ? other.getR() == null : this.getR().equals(other.getR()))
                && (this.getD() == null ? other.getD() == null : this.getD().equals(other.getD()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUri() == null) ? 0 : getUri().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getAction() == null) ? 0 : getAction().hashCode());
        result = prime * result + ((getC() == null) ? 0 : getC().hashCode());
        result = prime * result + ((getU() == null) ? 0 : getU().hashCode());
        result = prime * result + ((getR() == null) ? 0 : getR().hashCode());
        result = prime * result + ((getD() == null) ? 0 : getD().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", uri=").append(uri);
        sb.append(", name=").append(name);
        sb.append(", action=").append(action);
        sb.append(", c=").append(c);
        sb.append(", u=").append(u);
        sb.append(", r=").append(r);
        sb.append(", d=").append(d);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}