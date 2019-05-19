package com.onaple.wandrously.data.beans;

public class KnockBallBean {
    private int id;
    private String uuid;
    private String casterUuid;

    public KnockBallBean() {
    }

    public KnockBallBean(int id, String uuid, String casterUuid) {
        this.id = id;
        this.uuid = uuid;
        this.casterUuid = casterUuid;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCasterUuid() {
        return casterUuid;
    }
    public void setCasterUuid(String casterUuid) {
        this.casterUuid = casterUuid;
    }
}
