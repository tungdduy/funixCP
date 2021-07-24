package net.timxekhach.operation.rest.service.busspoint;

public abstract class BussPointAbstractService implements IBussPointService {
    protected String desc;
    public void setDesc(String desc) {
        this.desc = desc;
    }
}
