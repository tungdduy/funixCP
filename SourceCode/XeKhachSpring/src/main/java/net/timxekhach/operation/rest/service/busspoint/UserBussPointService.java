package net.timxekhach.operation.rest.service.busspoint;

import net.timxekhach.operation.data.entity.BussPoint;
import net.timxekhach.operation.data.repository.BussPointRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserBussPointService extends BussPointAbstractService{

    private BussPointRepository bussPointRepository;
    @Autowired
    public void setBussPointRepository(BussPointRepository bussPointRepository) {
        this.bussPointRepository = bussPointRepository;
    }

    @Override
    public List<BussPoint> getBusPointList() {
        List<BussPoint> bussPointList = new ArrayList<>();
        if (StringUtils.isNotEmpty(desc)){
            return bussPointRepository.findByBussPointDescContains(desc);
        }
        return bussPointList;
    }
}
