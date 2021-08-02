package net.timxekhach.operation.rest.service;

import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.data.entity.*;
import net.timxekhach.operation.data.mapped.Buss_MAPPED;
import net.timxekhach.operation.data.mapped.Company_MAPPED;
import net.timxekhach.operation.data.mapped.User_MAPPED;
import net.timxekhach.operation.data.repository.*;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.utility.XeReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static net.timxekhach.operation.response.ErrorCode.DATA_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class CommonUpdateService {

        private final SeatTypeRepository seatTypeRepository;
    public SeatType updateSeatType(Map<String, String> data) {
        SeatType seatType = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(seatTypeRepository.findById(SeatType.pk(data)));
        seatType.setFieldByName(data);
        seatTypeRepository.save(seatType);
        return seatType;
    }
    public SeatType insertSeatType(Map<String, String> data) {
        SeatType seatType = new SeatType();
        seatType.setFieldByName(data);
        return seatTypeRepository.save(seatType);
    }
    public void deleteSeatTypeBySeatTypeIds(Long[] seatTypeIds) {
        if (seatTypeIds != null) {
            seatTypeRepository.deleteBySeatTypeId(seatTypeIds);
        }
    }
    public void deleteSeatType(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return;
        }
        if (data.size() == 1 && data.containsKey("SeatTypeId")) {
            seatTypeRepository.deleteBySeatTypeId(data.get("SeatTypeId"));
            return;
        }
        String deleteMethodName = String.format("deleteBy%s", String.join("And", data.keySet()));
        Object[] deleteMethodParams = data.values().toArray(new Long[0]);
        XeReflectionUtils.invokeMethodByName(seatTypeRepository, deleteMethodName, deleteMethodParams);
    }
    public List<SeatType> findSeatType(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return seatTypeRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("SeatTypeId")) {
            SeatType seatType = seatTypeRepository.findBySeatTypeId(data.get("SeatTypeId"));
            if(seatType == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(seatType);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        return XeReflectionUtils.invokeMethodByName(seatTypeRepository, findMethodName, findMethodParams);
    }

    private final BussTripRepository bussTripRepository;
    public BussTrip updateBussTrip(Map<String, String> data) {
        BussTrip bussTrip = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(bussTripRepository.findById(BussTrip.pk(data)));
        bussTrip.setFieldByName(data);
        bussTripRepository.save(bussTrip);
        return bussTrip;
    }
    public BussTrip insertBussTrip(Map<String, String> data) {
        BussTrip bussTrip = new BussTrip();
        bussTrip.setFieldByName(data);
        return bussTripRepository.save(bussTrip);
    }
    public void deleteBussTripByBussTripIds(Long[] bussTripIds) {
        if (bussTripIds != null) {
            bussTripRepository.deleteByBussTripId(bussTripIds);
        }
    }
    public void deleteBussTrip(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return;
        }
        if (data.size() == 1 && data.containsKey("BussTripId")) {
            bussTripRepository.deleteByBussTripId(data.get("BussTripId"));
            return;
        }
        String deleteMethodName = String.format("deleteBy%s", String.join("And", data.keySet()));
        Object[] deleteMethodParams = data.values().toArray(new Long[0]);
        XeReflectionUtils.invokeMethodByName(bussTripRepository, deleteMethodName, deleteMethodParams);
    }
    public List<BussTrip> findBussTrip(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return bussTripRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("BussTripId")) {
            BussTrip bussTrip = bussTripRepository.findByBussTripId(data.get("BussTripId"));
            if(bussTrip == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(bussTrip);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        return XeReflectionUtils.invokeMethodByName(bussTripRepository, findMethodName, findMethodParams);
    }

    private final UserRepository userRepository;
    public User updateUser(Map<String, String> data) {
        User user = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(userRepository.findById(User.pk(data)));
        user.setFieldByName(data);
        userRepository.save(user);
        return user;
    }
    public User insertUser(Map<String, String> data) {
        User user = new User();
        user.setFieldByName(data);
        return userRepository.save(user);
    }
    public void deleteUserByUserIds(Long[] userIds) {
        if (userIds != null) {
            userRepository.deleteByUserId(userIds);
        }
    }
    public List<User> findUser(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return userRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("UserId")) {
            User user = userRepository.findByUserId(data.get("UserId"));
            if(user == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(user);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        return XeReflectionUtils.invokeMethodByName(userRepository, findMethodName, findMethodParams);
    }
    public User updateUserProfileImage(User_MAPPED.Pk pk, MultipartFile profileImage) throws IOException {
        User user = DATA_NOT_FOUND.throwIfNull(userRepository.getById(pk));
        user.saveProfileImage(profileImage);
        return user;
    }

    private final TripUserSeatRepository tripUserSeatRepository;
    public TripUserSeat updateTripUserSeat(Map<String, String> data) {
        TripUserSeat tripUserSeat = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(tripUserSeatRepository.findById(TripUserSeat.pk(data)));
        tripUserSeat.setFieldByName(data);
        tripUserSeatRepository.save(tripUserSeat);
        return tripUserSeat;
    }
    public TripUserSeat insertTripUserSeat(Map<String, String> data) {
        TripUserSeat tripUserSeat = new TripUserSeat();
        tripUserSeat.setFieldByName(data);
        return tripUserSeatRepository.save(tripUserSeat);
    }
    public void deleteTripUserSeatByTripUserSeatIds(Long[] tripUserSeatIds) {
        if (tripUserSeatIds != null) {
            tripUserSeatRepository.deleteByTripUserSeatId(tripUserSeatIds);
        }
    }
    public void deleteTripUserSeat(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return;
        }
        if (data.size() == 1 && data.containsKey("TripUserSeatId")) {
            tripUserSeatRepository.deleteByTripUserSeatId(data.get("TripUserSeatId"));
            return;
        }
        String deleteMethodName = String.format("deleteBy%s", String.join("And", data.keySet()));
        Object[] deleteMethodParams = data.values().toArray(new Long[0]);
        XeReflectionUtils.invokeMethodByName(tripUserSeatRepository, deleteMethodName, deleteMethodParams);
    }
    public List<TripUserSeat> findTripUserSeat(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return tripUserSeatRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("TripUserSeatId")) {
            TripUserSeat tripUserSeat = tripUserSeatRepository.findByTripUserSeatId(data.get("TripUserSeatId"));
            if(tripUserSeat == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(tripUserSeat);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        return XeReflectionUtils.invokeMethodByName(tripUserSeatRepository, findMethodName, findMethodParams);
    }

    private final EmployeeRepository employeeRepository;
    public Employee updateEmployee(Map<String, String> data) {
        Employee employee = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(employeeRepository.findById(Employee.pk(data)));
        employee.setFieldByName(data);
        employeeRepository.save(employee);
        return employee;
    }
    public Employee insertEmployee(Map<String, String> data) {
        Employee employee = new Employee();
        employee.setFieldByName(data);
        return employeeRepository.save(employee);
    }
    public void deleteEmployeeByEmployeeIds(Long[] employeeIds) {
        if (employeeIds != null) {
            employeeRepository.deleteByEmployeeId(employeeIds);
        }
    }
    public void deleteEmployee(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return;
        }
        if (data.size() == 1 && data.containsKey("EmployeeId")) {
            employeeRepository.deleteByEmployeeId(data.get("EmployeeId"));
            return;
        }
        String deleteMethodName = String.format("deleteBy%s", String.join("And", data.keySet()));
        Object[] deleteMethodParams = data.values().toArray(new Long[0]);
        XeReflectionUtils.invokeMethodByName(employeeRepository, deleteMethodName, deleteMethodParams);
    }
    public List<Employee> findEmployee(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return employeeRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("EmployeeId")) {
            Employee employee = employeeRepository.findByEmployeeId(data.get("EmployeeId"));
            if(employee == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(employee);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        return XeReflectionUtils.invokeMethodByName(employeeRepository, findMethodName, findMethodParams);
    }

    private final CompanyRepository companyRepository;
    public Company updateCompany(Map<String, String> data) {
        Company company = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(companyRepository.findById(Company.pk(data)));
        company.setFieldByName(data);
        companyRepository.save(company);
        return company;
    }
    public Company insertCompany(Map<String, String> data) {
        Company company = new Company();
        company.setFieldByName(data);
        return companyRepository.save(company);
    }
    public void deleteCompanyByCompanyIds(Long[] companyIds) {
        if (companyIds != null) {
            companyRepository.deleteByCompanyId(companyIds);
        }
    }
    public List<Company> findCompany(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return companyRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("CompanyId")) {
            Company company = companyRepository.findByCompanyId(data.get("CompanyId"));
            if(company == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(company);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        return XeReflectionUtils.invokeMethodByName(companyRepository, findMethodName, findMethodParams);
    }
    public Company updateCompanyProfileImage(Company_MAPPED.Pk pk, MultipartFile profileImage) throws IOException {
        Company company = DATA_NOT_FOUND.throwIfNull(companyRepository.getById(pk));
        company.saveProfileImage(profileImage);
        return company;
    }

    private final TripPointRepository tripPointRepository;
    public TripPoint updateTripPoint(Map<String, String> data) {
        TripPoint tripPoint = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(tripPointRepository.findById(TripPoint.pk(data)));
        tripPoint.setFieldByName(data);
        tripPointRepository.save(tripPoint);
        return tripPoint;
    }
    public TripPoint insertTripPoint(Map<String, String> data) {
        TripPoint tripPoint = new TripPoint();
        tripPoint.setFieldByName(data);
        return tripPointRepository.save(tripPoint);
    }
    public void deleteTripPointByTripPointIds(Long[] tripPointIds) {
        if (tripPointIds != null) {
            tripPointRepository.deleteByTripPointId(tripPointIds);
        }
    }
    public void deleteTripPoint(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return;
        }
        if (data.size() == 1 && data.containsKey("TripPointId")) {
            tripPointRepository.deleteByTripPointId(data.get("TripPointId"));
            return;
        }
        String deleteMethodName = String.format("deleteBy%s", String.join("And", data.keySet()));
        Object[] deleteMethodParams = data.values().toArray(new Long[0]);
        XeReflectionUtils.invokeMethodByName(tripPointRepository, deleteMethodName, deleteMethodParams);
    }
    public List<TripPoint> findTripPoint(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return tripPointRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("TripPointId")) {
            TripPoint tripPoint = tripPointRepository.findByTripPointId(data.get("TripPointId"));
            if(tripPoint == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(tripPoint);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        return XeReflectionUtils.invokeMethodByName(tripPointRepository, findMethodName, findMethodParams);
    }

    private final BussEmployeeRepository bussEmployeeRepository;
    public BussEmployee updateBussEmployee(Map<String, String> data) {
        BussEmployee bussEmployee = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(bussEmployeeRepository.findById(BussEmployee.pk(data)));
        bussEmployee.setFieldByName(data);
        bussEmployeeRepository.save(bussEmployee);
        return bussEmployee;
    }
    public BussEmployee insertBussEmployee(Map<String, String> data) {
        BussEmployee bussEmployee = new BussEmployee();
        bussEmployee.setFieldByName(data);
        return bussEmployeeRepository.save(bussEmployee);
    }
    public void deleteBussEmployeeByBussEmployeeIds(Long[] bussEmployeeIds) {
        if (bussEmployeeIds != null) {
            bussEmployeeRepository.deleteByBussEmployeeId(bussEmployeeIds);
        }
    }
    public void deleteBussEmployee(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return;
        }
        if (data.size() == 1 && data.containsKey("BussEmployeeId")) {
            bussEmployeeRepository.deleteByBussEmployeeId(data.get("BussEmployeeId"));
            return;
        }
        String deleteMethodName = String.format("deleteBy%s", String.join("And", data.keySet()));
        Object[] deleteMethodParams = data.values().toArray(new Long[0]);
        XeReflectionUtils.invokeMethodByName(bussEmployeeRepository, deleteMethodName, deleteMethodParams);
    }
    public List<BussEmployee> findBussEmployee(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return bussEmployeeRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("BussEmployeeId")) {
            BussEmployee bussEmployee = bussEmployeeRepository.findByBussEmployeeId(data.get("BussEmployeeId"));
            if(bussEmployee == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(bussEmployee);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        return XeReflectionUtils.invokeMethodByName(bussEmployeeRepository, findMethodName, findMethodParams);
    }

    private final BussRepository bussRepository;
    public Buss updateBuss(Map<String, String> data) {
        Buss buss = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(bussRepository.findById(Buss.pk(data)));
        buss.setFieldByName(data);
        bussRepository.save(buss);
        return buss;
    }
    public Buss insertBuss(Map<String, String> data) {
        Buss buss = new Buss();
        buss.setFieldByName(data);
        return bussRepository.save(buss);
    }
    public void deleteBussByBussIds(Long[] bussIds) {
        if (bussIds != null) {
            bussRepository.deleteByBussId(bussIds);
        }
    }
    public void deleteBuss(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return;
        }
        if (data.size() == 1 && data.containsKey("BussId")) {
            bussRepository.deleteByBussId(data.get("BussId"));
            return;
        }
        String deleteMethodName = String.format("deleteBy%s", String.join("And", data.keySet()));
        Object[] deleteMethodParams = data.values().toArray(new Long[0]);
        XeReflectionUtils.invokeMethodByName(bussRepository, deleteMethodName, deleteMethodParams);
    }
    public List<Buss> findBuss(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return bussRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("BussId")) {
            Buss buss = bussRepository.findByBussId(data.get("BussId"));
            if(buss == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(buss);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        return XeReflectionUtils.invokeMethodByName(bussRepository, findMethodName, findMethodParams);
    }
    public Buss updateBussProfileImage(Buss_MAPPED.Pk pk, MultipartFile profileImage) throws IOException {
        Buss buss = DATA_NOT_FOUND.throwIfNull(bussRepository.getById(pk));
        buss.saveProfileImage(profileImage);
        return buss;
    }

    private final LocationRepository locationRepository;
    public Location updateLocation(Map<String, String> data) {
        Location location = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(locationRepository.findById(Location.pk(data)));
        location.setFieldByName(data);
        locationRepository.save(location);
        return location;
    }
    public Location insertLocation(Map<String, String> data) {
        Location location = new Location();
        location.setFieldByName(data);
        return locationRepository.save(location);
    }
    public void deleteLocationByLocationIds(Long[] locationIds) {
        if (locationIds != null) {
            locationRepository.deleteByLocationId(locationIds);
        }
    }
    public List<Location> findLocation(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return locationRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("LocationId")) {
            Location location = locationRepository.findByLocationId(data.get("LocationId"));
            if(location == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(location);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        return XeReflectionUtils.invokeMethodByName(locationRepository, findMethodName, findMethodParams);
    }

    private final TripUserRepository tripUserRepository;
    public TripUser updateTripUser(Map<String, String> data) {
        TripUser tripUser = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(tripUserRepository.findById(TripUser.pk(data)));
        tripUser.setFieldByName(data);
        tripUserRepository.save(tripUser);
        return tripUser;
    }
    public TripUser insertTripUser(Map<String, String> data) {
        TripUser tripUser = new TripUser();
        tripUser.setFieldByName(data);
        return tripUserRepository.save(tripUser);
    }
    public void deleteTripUserByTripUserIds(Long[] tripUserIds) {
        if (tripUserIds != null) {
            tripUserRepository.deleteByTripUserId(tripUserIds);
        }
    }
    public void deleteTripUser(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return;
        }
        if (data.size() == 1 && data.containsKey("TripUserId")) {
            tripUserRepository.deleteByTripUserId(data.get("TripUserId"));
            return;
        }
        String deleteMethodName = String.format("deleteBy%s", String.join("And", data.keySet()));
        Object[] deleteMethodParams = data.values().toArray(new Long[0]);
        XeReflectionUtils.invokeMethodByName(tripUserRepository, deleteMethodName, deleteMethodParams);
    }
    public List<TripUser> findTripUser(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return tripUserRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("TripUserId")) {
            TripUser tripUser = tripUserRepository.findByTripUserId(data.get("TripUserId"));
            if(tripUser == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(tripUser);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        return XeReflectionUtils.invokeMethodByName(tripUserRepository, findMethodName, findMethodParams);
    }

    private final BussTypeRepository bussTypeRepository;
    public BussType updateBussType(Map<String, String> data) {
        BussType bussType = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(bussTypeRepository.findById(BussType.pk(data)));
        bussType.setFieldByName(data);
        bussTypeRepository.save(bussType);
        return bussType;
    }
    public BussType insertBussType(Map<String, String> data) {
        BussType bussType = new BussType();
        bussType.setFieldByName(data);
        return bussTypeRepository.save(bussType);
    }
    public void deleteBussTypeByBussTypeIds(Long[] bussTypeIds) {
        if (bussTypeIds != null) {
            bussTypeRepository.deleteByBussTypeId(bussTypeIds);
        }
    }
    public List<BussType> findBussType(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return bussTypeRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("BussTypeId")) {
            BussType bussType = bussTypeRepository.findByBussTypeId(data.get("BussTypeId"));
            if(bussType == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(bussType);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        return XeReflectionUtils.invokeMethodByName(bussTypeRepository, findMethodName, findMethodParams);
    }

    private final BussPointRepository bussPointRepository;
    public BussPoint updateBussPoint(Map<String, String> data) {
        BussPoint bussPoint = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(bussPointRepository.findById(BussPoint.pk(data)));
        bussPoint.setFieldByName(data);
        bussPointRepository.save(bussPoint);
        return bussPoint;
    }
    public BussPoint insertBussPoint(Map<String, String> data) {
        BussPoint bussPoint = new BussPoint();
        bussPoint.setFieldByName(data);
        return bussPointRepository.save(bussPoint);
    }
    public void deleteBussPointByBussPointIds(Long[] bussPointIds) {
        if (bussPointIds != null) {
            bussPointRepository.deleteByBussPointId(bussPointIds);
        }
    }
    public void deleteBussPoint(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return;
        }
        if (data.size() == 1 && data.containsKey("BussPointId")) {
            bussPointRepository.deleteByBussPointId(data.get("BussPointId"));
            return;
        }
        String deleteMethodName = String.format("deleteBy%s", String.join("And", data.keySet()));
        Object[] deleteMethodParams = data.values().toArray(new Long[0]);
        XeReflectionUtils.invokeMethodByName(bussPointRepository, deleteMethodName, deleteMethodParams);
    }
    public List<BussPoint> findBussPoint(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return bussPointRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("BussPointId")) {
            BussPoint bussPoint = bussPointRepository.findByBussPointId(data.get("BussPointId"));
            if(bussPoint == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(bussPoint);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        return XeReflectionUtils.invokeMethodByName(bussPointRepository, findMethodName, findMethodParams);
    }

    private final TripRepository tripRepository;
    public Trip updateTrip(Map<String, String> data) {
        Trip trip = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(tripRepository.findById(Trip.pk(data)));
        trip.setFieldByName(data);
        tripRepository.save(trip);
        return trip;
    }
    public Trip insertTrip(Map<String, String> data) {
        Trip trip = new Trip();
        trip.setFieldByName(data);
        return tripRepository.save(trip);
    }
    public void deleteTripByTripIds(Long[] tripIds) {
        if (tripIds != null) {
            tripRepository.deleteByTripId(tripIds);
        }
    }
    public void deleteTrip(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return;
        }
        if (data.size() == 1 && data.containsKey("TripId")) {
            tripRepository.deleteByTripId(data.get("TripId"));
            return;
        }
        String deleteMethodName = String.format("deleteBy%s", String.join("And", data.keySet()));
        Object[] deleteMethodParams = data.values().toArray(new Long[0]);
        XeReflectionUtils.invokeMethodByName(tripRepository, deleteMethodName, deleteMethodParams);
    }
    public List<Trip> findTrip(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return tripRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("TripId")) {
            Trip trip = tripRepository.findByTripId(data.get("TripId"));
            if(trip == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(trip);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        return XeReflectionUtils.invokeMethodByName(tripRepository, findMethodName, findMethodParams);
    }


}
