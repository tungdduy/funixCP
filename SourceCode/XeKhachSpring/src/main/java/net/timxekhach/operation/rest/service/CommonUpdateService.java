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

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

import static net.timxekhach.operation.response.ErrorCode.DATA_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class CommonUpdateService {
    private final SeatTypeRepository seatTypeRepository;
    private static SeatTypeRepository staticSeatTypeRepository;
    public static SeatTypeRepository getSeatTypeRepository() {
        return CommonUpdateService.staticSeatTypeRepository;
    }
    private final BussTripRepository bussTripRepository;
    private static BussTripRepository staticBussTripRepository;
    public static BussTripRepository getBussTripRepository() {
        return CommonUpdateService.staticBussTripRepository;
    }
    private final UserRepository userRepository;
    private static UserRepository staticUserRepository;
    public static UserRepository getUserRepository() {
        return CommonUpdateService.staticUserRepository;
    }
    private final CallerRepository callerRepository;
    private static CallerRepository staticCallerRepository;
    public static CallerRepository getCallerRepository() {
        return CommonUpdateService.staticCallerRepository;
    }
    private final TripUserSeatRepository tripUserSeatRepository;
    private static TripUserSeatRepository staticTripUserSeatRepository;
    public static TripUserSeatRepository getTripUserSeatRepository() {
        return CommonUpdateService.staticTripUserSeatRepository;
    }
    private final EmployeeRepository employeeRepository;
    private static EmployeeRepository staticEmployeeRepository;
    public static EmployeeRepository getEmployeeRepository() {
        return CommonUpdateService.staticEmployeeRepository;
    }
    private final CompanyRepository companyRepository;
    private static CompanyRepository staticCompanyRepository;
    public static CompanyRepository getCompanyRepository() {
        return CommonUpdateService.staticCompanyRepository;
    }
    private final TripPointRepository tripPointRepository;
    private static TripPointRepository staticTripPointRepository;
    public static TripPointRepository getTripPointRepository() {
        return CommonUpdateService.staticTripPointRepository;
    }
    private final BussEmployeeRepository bussEmployeeRepository;
    private static BussEmployeeRepository staticBussEmployeeRepository;
    public static BussEmployeeRepository getBussEmployeeRepository() {
        return CommonUpdateService.staticBussEmployeeRepository;
    }
    private final BussRepository bussRepository;
    private static BussRepository staticBussRepository;
    public static BussRepository getBussRepository() {
        return CommonUpdateService.staticBussRepository;
    }
    private final LocationRepository locationRepository;
    private static LocationRepository staticLocationRepository;
    public static LocationRepository getLocationRepository() {
        return CommonUpdateService.staticLocationRepository;
    }
    private final TripUserRepository tripUserRepository;
    private static TripUserRepository staticTripUserRepository;
    public static TripUserRepository getTripUserRepository() {
        return CommonUpdateService.staticTripUserRepository;
    }
    private final BussTypeRepository bussTypeRepository;
    private static BussTypeRepository staticBussTypeRepository;
    public static BussTypeRepository getBussTypeRepository() {
        return CommonUpdateService.staticBussTypeRepository;
    }
    private final BussPointRepository bussPointRepository;
    private static BussPointRepository staticBussPointRepository;
    public static BussPointRepository getBussPointRepository() {
        return CommonUpdateService.staticBussPointRepository;
    }
    private final TripRepository tripRepository;
    private static TripRepository staticTripRepository;
    public static TripRepository getTripRepository() {
        return CommonUpdateService.staticTripRepository;
    }
    @PostConstruct
    public void postConstruct() {
        CommonUpdateService.staticSeatTypeRepository = seatTypeRepository;
        CommonUpdateService.staticBussTripRepository = bussTripRepository;
        CommonUpdateService.staticUserRepository = userRepository;
        CommonUpdateService.staticCallerRepository = callerRepository;
        CommonUpdateService.staticTripUserSeatRepository = tripUserSeatRepository;
        CommonUpdateService.staticEmployeeRepository = employeeRepository;
        CommonUpdateService.staticCompanyRepository = companyRepository;
        CommonUpdateService.staticTripPointRepository = tripPointRepository;
        CommonUpdateService.staticBussEmployeeRepository = bussEmployeeRepository;
        CommonUpdateService.staticBussRepository = bussRepository;
        CommonUpdateService.staticLocationRepository = locationRepository;
        CommonUpdateService.staticTripUserRepository = tripUserRepository;
        CommonUpdateService.staticBussTypeRepository = bussTypeRepository;
        CommonUpdateService.staticBussPointRepository = bussPointRepository;
        CommonUpdateService.staticTripRepository = tripRepository;
    }
    public SeatType updateSeatType(Map<String, String> data) {
        Long seatTypeId = Long.parseLong(data.get("seatTypeId"));
        SeatType seatType = ErrorCode.DATA_NOT_FOUND.throwIfNull(seatTypeRepository.findBySeatTypeId(seatTypeId));

        Map<String, String> bussTypeData = new HashMap<>();
        data.forEach((fieldName, fieldValue) -> {
            if (fieldName.startsWith("bussType.")) {
                bussTypeData.put(fieldName.substring("bussType.".length()), fieldValue);
            }
        });
        if (!bussTypeData.isEmpty()) {
            bussTypeData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateBussType(bussTypeData);
        }

        seatType.setFieldByName(data);
        seatTypeRepository.save(seatType);
        return seatType;
    }
    public SeatType insertSeatType(Map<String, String> data) {
        SeatType seatType = new SeatType();
        seatType.setFieldByName(data);
        
        if (seatType.getBussTypeId() == null || seatType.getBussTypeId() <= 0) {
            Map<String, String> bussTypeData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("bussType."))
                    .forEach(entry -> bussTypeData.put(entry.getKey().substring("bussType.".length()), entry.getValue()));
            seatType.setBussType(this.insertBussType(bussTypeData));
        }
        ErrorCode.DATA_EXISTED.throwIfNotEmpty(seatTypeRepository.findByBussTypeId(seatType.getBussTypeId()));

        seatType = seatTypeRepository.save(seatType);
        return seatType;
    }
    public List<SeatType> insertMultiSeatType(List<Map<String, String>> data) {
        List<SeatType> result = new ArrayList<>();
        data.forEach(seatTypeData -> result.add(this.insertSeatType(seatTypeData)));
        return result;
    }
    public void deleteSeatTypeBySeatTypeIds(Long[] seatTypeIds) {
        seatTypeRepository.deleteAllBySeatTypeIdIn(Arrays.asList(seatTypeIds));
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
//=================== END OF SeatType ======================
    public BussTrip updateBussTrip(Map<String, String> data) {
        Long bussTripId = Long.parseLong(data.get("bussTripId"));
        BussTrip bussTrip = ErrorCode.DATA_NOT_FOUND.throwIfNull(bussTripRepository.findByBussTripId(bussTripId));

        Map<String, String> bussData = new HashMap<>();
        Map<String, String> companyData = new HashMap<>();
        data.forEach((fieldName, fieldValue) -> {
            if (fieldName.startsWith("buss.")) {
                bussData.put(fieldName.substring("buss.".length()), fieldValue);
            }
            if (fieldName.startsWith("company.")) {
                companyData.put(fieldName.substring("company.".length()), fieldValue);
            }
        });
        if (!bussData.isEmpty()) {
            bussData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateBuss(bussData);
        }
        if (!companyData.isEmpty()) {
            companyData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateCompany(companyData);
        }

        bussTrip.setFieldByName(data);
        bussTripRepository.save(bussTrip);
        return bussTrip;
    }
    public BussTrip insertBussTrip(Map<String, String> data) {
        BussTrip bussTrip = new BussTrip();
        bussTrip.setFieldByName(data);
        
        if (bussTrip.getBussId() == null || bussTrip.getBussId() <= 0) {
            Map<String, String> bussData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("buss."))
                    .forEach(entry -> bussData.put(entry.getKey().substring("buss.".length()), entry.getValue()));
            bussTrip.setBuss(this.insertBuss(bussData));
        }
        if (bussTrip.getCompanyId() == null || bussTrip.getCompanyId() <= 0) {
            Map<String, String> companyData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("company."))
                    .forEach(entry -> companyData.put(entry.getKey().substring("company.".length()), entry.getValue()));
            bussTrip.setCompany(this.insertCompany(companyData));
        }
        ErrorCode.DATA_EXISTED.throwIfNotEmpty(bussTripRepository.findByBussIdAndCompanyId(bussTrip.getBussId(), bussTrip.getCompanyId()));

        bussTrip = bussTripRepository.save(bussTrip);
        return bussTrip;
    }
    public List<BussTrip> insertMultiBussTrip(List<Map<String, String>> data) {
        List<BussTrip> result = new ArrayList<>();
        data.forEach(bussTripData -> result.add(this.insertBussTrip(bussTripData)));
        return result;
    }
    public void deleteBussTripByBussTripIds(Long[] bussTripIds) {
        bussTripRepository.deleteAllByBussTripIdIn(Arrays.asList(bussTripIds));
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
//=================== END OF BussTrip ======================
    public User updateUser(Map<String, String> data) {
        Long userId = Long.parseLong(data.get("userId"));
        User user = ErrorCode.DATA_NOT_FOUND.throwIfNull(userRepository.findByUserId(userId));


        user.setFieldByName(data);
        userRepository.save(user);
        return user;
    }
    public User insertUser(Map<String, String> data) {
        User user = new User();
        user.setFieldByName(data);
        

        user = userRepository.save(user);
        return user;
    }
    public List<User> insertMultiUser(List<Map<String, String>> data) {
        List<User> result = new ArrayList<>();
        data.forEach(userData -> result.add(this.insertUser(userData)));
        return result;
    }
    public void deleteUserByUserIds(Long[] userIds) {
        userRepository.deleteAllByUserIdIn(Arrays.asList(userIds));
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
//=================== END OF User ======================
    public Caller updateCaller(Map<String, String> data) {
        Long callerId = Long.parseLong(data.get("callerId"));
        Caller caller = ErrorCode.DATA_NOT_FOUND.throwIfNull(callerRepository.findByCallerId(callerId));

        Map<String, String> companyData = new HashMap<>();
        data.forEach((fieldName, fieldValue) -> {
            if (fieldName.startsWith("company.")) {
                companyData.put(fieldName.substring("company.".length()), fieldValue);
            }
        });
        if (!companyData.isEmpty()) {
            companyData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateCompany(companyData);
        }

        caller.setFieldByName(data);
        callerRepository.save(caller);
        return caller;
    }
    public Caller insertCaller(Map<String, String> data) {
        Caller caller = new Caller();
        caller.setFieldByName(data);
        
        if (caller.getCompanyId() == null || caller.getCompanyId() <= 0) {
            Map<String, String> companyData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("company."))
                    .forEach(entry -> companyData.put(entry.getKey().substring("company.".length()), entry.getValue()));
            caller.setCompany(this.insertCompany(companyData));
        }
        ErrorCode.DATA_EXISTED.throwIfNotEmpty(callerRepository.findByCompanyId(caller.getCompanyId()));

        caller = callerRepository.save(caller);
        return caller;
    }
    public List<Caller> insertMultiCaller(List<Map<String, String>> data) {
        List<Caller> result = new ArrayList<>();
        data.forEach(callerData -> result.add(this.insertCaller(callerData)));
        return result;
    }
    public void deleteCallerByCallerIds(Long[] callerIds) {
        callerRepository.deleteAllByCallerIdIn(Arrays.asList(callerIds));
    }
    public void deleteCaller(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return;
        }
        if (data.size() == 1 && data.containsKey("CallerId")) {
            callerRepository.deleteByCallerId(data.get("CallerId"));
            return;
        }
        String deleteMethodName = String.format("deleteBy%s", String.join("And", data.keySet()));
        Object[] deleteMethodParams = data.values().toArray(new Long[0]);
        XeReflectionUtils.invokeMethodByName(callerRepository, deleteMethodName, deleteMethodParams);
    }
    public List<Caller> findCaller(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return callerRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("CallerId")) {
            Caller caller = callerRepository.findByCallerId(data.get("CallerId"));
            if(caller == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(caller);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        return XeReflectionUtils.invokeMethodByName(callerRepository, findMethodName, findMethodParams);
    }
//=================== END OF Caller ======================
    public TripUserSeat updateTripUserSeat(Map<String, String> data) {
        Long tripUserSeatId = Long.parseLong(data.get("tripUserSeatId"));
        TripUserSeat tripUserSeat = ErrorCode.DATA_NOT_FOUND.throwIfNull(tripUserSeatRepository.findByTripUserSeatId(tripUserSeatId));

        Map<String, String> seatTypeData = new HashMap<>();
        Map<String, String> tripData = new HashMap<>();
        Map<String, String> userData = new HashMap<>();
        data.forEach((fieldName, fieldValue) -> {
            if (fieldName.startsWith("seatType.")) {
                seatTypeData.put(fieldName.substring("seatType.".length()), fieldValue);
            }
            if (fieldName.startsWith("trip.")) {
                tripData.put(fieldName.substring("trip.".length()), fieldValue);
            }
            if (fieldName.startsWith("user.")) {
                userData.put(fieldName.substring("user.".length()), fieldValue);
            }
        });
        if (!seatTypeData.isEmpty()) {
            seatTypeData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateSeatType(seatTypeData);
        }
        if (!tripData.isEmpty()) {
            tripData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateTrip(tripData);
        }
        if (!userData.isEmpty()) {
            userData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateUser(userData);
        }

        tripUserSeat.setFieldByName(data);
        tripUserSeatRepository.save(tripUserSeat);
        return tripUserSeat;
    }
    public TripUserSeat insertTripUserSeat(Map<String, String> data) {
        TripUserSeat tripUserSeat = new TripUserSeat();
        tripUserSeat.setFieldByName(data);
        
        if (tripUserSeat.getSeatTypeId() == null || tripUserSeat.getSeatTypeId() <= 0) {
            Map<String, String> seatTypeData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("seatType."))
                    .forEach(entry -> seatTypeData.put(entry.getKey().substring("seatType.".length()), entry.getValue()));
            tripUserSeat.setSeatType(this.insertSeatType(seatTypeData));
        }
        if (tripUserSeat.getTripId() == null || tripUserSeat.getTripId() <= 0) {
            Map<String, String> tripData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("trip."))
                    .forEach(entry -> tripData.put(entry.getKey().substring("trip.".length()), entry.getValue()));
            tripUserSeat.setTrip(this.insertTrip(tripData));
        }
        if (tripUserSeat.getUserId() == null || tripUserSeat.getUserId() <= 0) {
            Map<String, String> userData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("user."))
                    .forEach(entry -> userData.put(entry.getKey().substring("user.".length()), entry.getValue()));
            tripUserSeat.setUser(this.insertUser(userData));
        }
        ErrorCode.DATA_EXISTED.throwIfNotEmpty(tripUserSeatRepository.findBySeatTypeIdAndTripIdAndUserId(tripUserSeat.getSeatTypeId(), tripUserSeat.getTripId(), tripUserSeat.getUserId()));

        tripUserSeat = tripUserSeatRepository.save(tripUserSeat);
        return tripUserSeat;
    }
    public List<TripUserSeat> insertMultiTripUserSeat(List<Map<String, String>> data) {
        List<TripUserSeat> result = new ArrayList<>();
        data.forEach(tripUserSeatData -> result.add(this.insertTripUserSeat(tripUserSeatData)));
        return result;
    }
    public void deleteTripUserSeatByTripUserSeatIds(Long[] tripUserSeatIds) {
        tripUserSeatRepository.deleteAllByTripUserSeatIdIn(Arrays.asList(tripUserSeatIds));
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
//=================== END OF TripUserSeat ======================
    public Employee updateEmployee(Map<String, String> data) {
        Long employeeId = Long.parseLong(data.get("employeeId"));
        Employee employee = ErrorCode.DATA_NOT_FOUND.throwIfNull(employeeRepository.findByEmployeeId(employeeId));

        Map<String, String> companyData = new HashMap<>();
        Map<String, String> userData = new HashMap<>();
        data.forEach((fieldName, fieldValue) -> {
            if (fieldName.startsWith("company.")) {
                companyData.put(fieldName.substring("company.".length()), fieldValue);
            }
            if (fieldName.startsWith("user.")) {
                userData.put(fieldName.substring("user.".length()), fieldValue);
            }
        });
        if (!companyData.isEmpty()) {
            companyData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateCompany(companyData);
        }
        if (!userData.isEmpty()) {
            userData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateUser(userData);
        }

        employee.setFieldByName(data);
        employeeRepository.save(employee);
        return employee;
    }
    public Employee insertEmployee(Map<String, String> data) {
        Employee employee = new Employee();
        employee.setFieldByName(data);
        
        if (employee.getCompanyId() == null || employee.getCompanyId() <= 0) {
            Map<String, String> companyData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("company."))
                    .forEach(entry -> companyData.put(entry.getKey().substring("company.".length()), entry.getValue()));
            employee.setCompany(this.insertCompany(companyData));
        }
        if (employee.getUserId() == null || employee.getUserId() <= 0) {
            Map<String, String> userData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("user."))
                    .forEach(entry -> userData.put(entry.getKey().substring("user.".length()), entry.getValue()));
            employee.setUser(this.insertUser(userData));
        }
        ErrorCode.DATA_EXISTED.throwIfNotEmpty(employeeRepository.findByCompanyIdAndUserId(employee.getCompanyId(), employee.getUserId()));

        employee = employeeRepository.save(employee);
        return employee;
    }
    public List<Employee> insertMultiEmployee(List<Map<String, String>> data) {
        List<Employee> result = new ArrayList<>();
        data.forEach(employeeData -> result.add(this.insertEmployee(employeeData)));
        return result;
    }
    public void deleteEmployeeByEmployeeIds(Long[] employeeIds) {
        employeeRepository.deleteAllByEmployeeIdIn(Arrays.asList(employeeIds));
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
//=================== END OF Employee ======================
    public Company updateCompany(Map<String, String> data) {
        Long companyId = Long.parseLong(data.get("companyId"));
        Company company = ErrorCode.DATA_NOT_FOUND.throwIfNull(companyRepository.findByCompanyId(companyId));


        company.setFieldByName(data);
        companyRepository.save(company);
        return company;
    }
    public Company insertCompany(Map<String, String> data) {
        Company company = new Company();
        company.setFieldByName(data);
        

        company = companyRepository.save(company);
        return company;
    }
    public List<Company> insertMultiCompany(List<Map<String, String>> data) {
        List<Company> result = new ArrayList<>();
        data.forEach(companyData -> result.add(this.insertCompany(companyData)));
        return result;
    }
    public void deleteCompanyByCompanyIds(Long[] companyIds) {
        companyRepository.deleteAllByCompanyIdIn(Arrays.asList(companyIds));
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
//=================== END OF Company ======================
    public TripPoint updateTripPoint(Map<String, String> data) {
        Long tripPointId = Long.parseLong(data.get("tripPointId"));
        TripPoint tripPoint = ErrorCode.DATA_NOT_FOUND.throwIfNull(tripPointRepository.findByTripPointId(tripPointId));

        Map<String, String> bussTripData = new HashMap<>();
        data.forEach((fieldName, fieldValue) -> {
            if (fieldName.startsWith("bussTrip.")) {
                bussTripData.put(fieldName.substring("bussTrip.".length()), fieldValue);
            }
        });
        if (!bussTripData.isEmpty()) {
            bussTripData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateBussTrip(bussTripData);
        }

        tripPoint.setFieldByName(data);
        tripPointRepository.save(tripPoint);
        return tripPoint;
    }
    public TripPoint insertTripPoint(Map<String, String> data) {
        TripPoint tripPoint = new TripPoint();
        tripPoint.setFieldByName(data);
        
        if (tripPoint.getBussTripId() == null || tripPoint.getBussTripId() <= 0) {
            Map<String, String> bussTripData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("bussTrip."))
                    .forEach(entry -> bussTripData.put(entry.getKey().substring("bussTrip.".length()), entry.getValue()));
            tripPoint.setBussTrip(this.insertBussTrip(bussTripData));
        }
        ErrorCode.DATA_EXISTED.throwIfNotEmpty(tripPointRepository.findByBussTripId(tripPoint.getBussTripId()));

        tripPoint = tripPointRepository.save(tripPoint);
        return tripPoint;
    }
    public List<TripPoint> insertMultiTripPoint(List<Map<String, String>> data) {
        List<TripPoint> result = new ArrayList<>();
        data.forEach(tripPointData -> result.add(this.insertTripPoint(tripPointData)));
        return result;
    }
    public void deleteTripPointByTripPointIds(Long[] tripPointIds) {
        tripPointRepository.deleteAllByTripPointIdIn(Arrays.asList(tripPointIds));
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
//=================== END OF TripPoint ======================
    public BussEmployee updateBussEmployee(Map<String, String> data) {
        Long bussEmployeeId = Long.parseLong(data.get("bussEmployeeId"));
        BussEmployee bussEmployee = ErrorCode.DATA_NOT_FOUND.throwIfNull(bussEmployeeRepository.findByBussEmployeeId(bussEmployeeId));

        Map<String, String> bussData = new HashMap<>();
        Map<String, String> employeeData = new HashMap<>();
        data.forEach((fieldName, fieldValue) -> {
            if (fieldName.startsWith("buss.")) {
                bussData.put(fieldName.substring("buss.".length()), fieldValue);
            }
            if (fieldName.startsWith("employee.")) {
                employeeData.put(fieldName.substring("employee.".length()), fieldValue);
            }
        });
        if (!bussData.isEmpty()) {
            bussData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateBuss(bussData);
        }
        if (!employeeData.isEmpty()) {
            employeeData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateEmployee(employeeData);
        }

        bussEmployee.setFieldByName(data);
        bussEmployeeRepository.save(bussEmployee);
        return bussEmployee;
    }
    public BussEmployee insertBussEmployee(Map<String, String> data) {
        BussEmployee bussEmployee = new BussEmployee();
        bussEmployee.setFieldByName(data);
        
        if (bussEmployee.getBussId() == null || bussEmployee.getBussId() <= 0) {
            Map<String, String> bussData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("buss."))
                    .forEach(entry -> bussData.put(entry.getKey().substring("buss.".length()), entry.getValue()));
            bussEmployee.setBuss(this.insertBuss(bussData));
        }
        if (bussEmployee.getEmployeeId() == null || bussEmployee.getEmployeeId() <= 0) {
            Map<String, String> employeeData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("employee."))
                    .forEach(entry -> employeeData.put(entry.getKey().substring("employee.".length()), entry.getValue()));
            bussEmployee.setEmployee(this.insertEmployee(employeeData));
        }
        ErrorCode.DATA_EXISTED.throwIfNotEmpty(bussEmployeeRepository.findByBussIdAndEmployeeId(bussEmployee.getBussId(), bussEmployee.getEmployeeId()));

        bussEmployee = bussEmployeeRepository.save(bussEmployee);
        return bussEmployee;
    }
    public List<BussEmployee> insertMultiBussEmployee(List<Map<String, String>> data) {
        List<BussEmployee> result = new ArrayList<>();
        data.forEach(bussEmployeeData -> result.add(this.insertBussEmployee(bussEmployeeData)));
        return result;
    }
    public void deleteBussEmployeeByBussEmployeeIds(Long[] bussEmployeeIds) {
        bussEmployeeRepository.deleteAllByBussEmployeeIdIn(Arrays.asList(bussEmployeeIds));
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
//=================== END OF BussEmployee ======================
    public Buss updateBuss(Map<String, String> data) {
        Long bussId = Long.parseLong(data.get("bussId"));
        Buss buss = ErrorCode.DATA_NOT_FOUND.throwIfNull(bussRepository.findByBussId(bussId));

        Map<String, String> bussTypeData = new HashMap<>();
        Map<String, String> companyData = new HashMap<>();
        data.forEach((fieldName, fieldValue) -> {
            if (fieldName.startsWith("bussType.")) {
                bussTypeData.put(fieldName.substring("bussType.".length()), fieldValue);
            }
            if (fieldName.startsWith("company.")) {
                companyData.put(fieldName.substring("company.".length()), fieldValue);
            }
        });
        if (!bussTypeData.isEmpty()) {
            bussTypeData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateBussType(bussTypeData);
        }
        if (!companyData.isEmpty()) {
            companyData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateCompany(companyData);
        }

        buss.setFieldByName(data);
        bussRepository.save(buss);
        return buss;
    }
    public Buss insertBuss(Map<String, String> data) {
        Buss buss = new Buss();
        buss.setFieldByName(data);
        
        if (buss.getBussTypeId() == null || buss.getBussTypeId() <= 0) {
            Map<String, String> bussTypeData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("bussType."))
                    .forEach(entry -> bussTypeData.put(entry.getKey().substring("bussType.".length()), entry.getValue()));
            buss.setBussType(this.insertBussType(bussTypeData));
        }
        if (buss.getCompanyId() == null || buss.getCompanyId() <= 0) {
            Map<String, String> companyData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("company."))
                    .forEach(entry -> companyData.put(entry.getKey().substring("company.".length()), entry.getValue()));
            buss.setCompany(this.insertCompany(companyData));
        }
        ErrorCode.DATA_EXISTED.throwIfNotEmpty(bussRepository.findByBussTypeIdAndCompanyId(buss.getBussTypeId(), buss.getCompanyId()));

        buss = bussRepository.save(buss);
        return buss;
    }
    public List<Buss> insertMultiBuss(List<Map<String, String>> data) {
        List<Buss> result = new ArrayList<>();
        data.forEach(bussData -> result.add(this.insertBuss(bussData)));
        return result;
    }
    public void deleteBussByBussIds(Long[] bussIds) {
        bussRepository.deleteAllByBussIdIn(Arrays.asList(bussIds));
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
//=================== END OF Buss ======================
    public Location updateLocation(Map<String, String> data) {
        Long locationId = Long.parseLong(data.get("locationId"));
        Location location = ErrorCode.DATA_NOT_FOUND.throwIfNull(locationRepository.findByLocationId(locationId));


        location.setFieldByName(data);
        locationRepository.save(location);
        return location;
    }
    public Location insertLocation(Map<String, String> data) {
        Location location = new Location();
        location.setFieldByName(data);
        

        location = locationRepository.save(location);
        return location;
    }
    public List<Location> insertMultiLocation(List<Map<String, String>> data) {
        List<Location> result = new ArrayList<>();
        data.forEach(locationData -> result.add(this.insertLocation(locationData)));
        return result;
    }
    public void deleteLocationByLocationIds(Long[] locationIds) {
        locationRepository.deleteAllByLocationIdIn(Arrays.asList(locationIds));
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
//=================== END OF Location ======================
    public TripUser updateTripUser(Map<String, String> data) {
        Long tripUserId = Long.parseLong(data.get("tripUserId"));
        TripUser tripUser = ErrorCode.DATA_NOT_FOUND.throwIfNull(tripUserRepository.findByTripUserId(tripUserId));

        Map<String, String> tripData = new HashMap<>();
        Map<String, String> userData = new HashMap<>();
        data.forEach((fieldName, fieldValue) -> {
            if (fieldName.startsWith("trip.")) {
                tripData.put(fieldName.substring("trip.".length()), fieldValue);
            }
            if (fieldName.startsWith("user.")) {
                userData.put(fieldName.substring("user.".length()), fieldValue);
            }
        });
        if (!tripData.isEmpty()) {
            tripData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateTrip(tripData);
        }
        if (!userData.isEmpty()) {
            userData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateUser(userData);
        }

        tripUser.setFieldByName(data);
        tripUserRepository.save(tripUser);
        return tripUser;
    }
    public TripUser insertTripUser(Map<String, String> data) {
        TripUser tripUser = new TripUser();
        tripUser.setFieldByName(data);
        
        if (tripUser.getTripId() == null || tripUser.getTripId() <= 0) {
            Map<String, String> tripData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("trip."))
                    .forEach(entry -> tripData.put(entry.getKey().substring("trip.".length()), entry.getValue()));
            tripUser.setTrip(this.insertTrip(tripData));
        }
        if (tripUser.getUserId() == null || tripUser.getUserId() <= 0) {
            Map<String, String> userData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("user."))
                    .forEach(entry -> userData.put(entry.getKey().substring("user.".length()), entry.getValue()));
            tripUser.setUser(this.insertUser(userData));
        }
        ErrorCode.DATA_EXISTED.throwIfNotEmpty(tripUserRepository.findByTripIdAndUserId(tripUser.getTripId(), tripUser.getUserId()));

        tripUser = tripUserRepository.save(tripUser);
        return tripUser;
    }
    public List<TripUser> insertMultiTripUser(List<Map<String, String>> data) {
        List<TripUser> result = new ArrayList<>();
        data.forEach(tripUserData -> result.add(this.insertTripUser(tripUserData)));
        return result;
    }
    public void deleteTripUserByTripUserIds(Long[] tripUserIds) {
        tripUserRepository.deleteAllByTripUserIdIn(Arrays.asList(tripUserIds));
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
//=================== END OF TripUser ======================
    public BussType updateBussType(Map<String, String> data) {
        Long bussTypeId = Long.parseLong(data.get("bussTypeId"));
        BussType bussType = ErrorCode.DATA_NOT_FOUND.throwIfNull(bussTypeRepository.findByBussTypeId(bussTypeId));


        bussType.setFieldByName(data);
        bussTypeRepository.save(bussType);
        return bussType;
    }
    public BussType insertBussType(Map<String, String> data) {
        BussType bussType = new BussType();
        bussType.setFieldByName(data);
        

        bussType = bussTypeRepository.save(bussType);
        return bussType;
    }
    public List<BussType> insertMultiBussType(List<Map<String, String>> data) {
        List<BussType> result = new ArrayList<>();
        data.forEach(bussTypeData -> result.add(this.insertBussType(bussTypeData)));
        return result;
    }
    public void deleteBussTypeByBussTypeIds(Long[] bussTypeIds) {
        bussTypeRepository.deleteAllByBussTypeIdIn(Arrays.asList(bussTypeIds));
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
//=================== END OF BussType ======================
    public BussPoint updateBussPoint(Map<String, String> data) {
        Long bussPointId = Long.parseLong(data.get("bussPointId"));
        BussPoint bussPoint = ErrorCode.DATA_NOT_FOUND.throwIfNull(bussPointRepository.findByBussPointId(bussPointId));

        Map<String, String> locationData = new HashMap<>();
        data.forEach((fieldName, fieldValue) -> {
            if (fieldName.startsWith("location.")) {
                locationData.put(fieldName.substring("location.".length()), fieldValue);
            }
        });
        if (!locationData.isEmpty()) {
            locationData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateLocation(locationData);
        }

        bussPoint.setFieldByName(data);
        bussPointRepository.save(bussPoint);
        return bussPoint;
    }
    public BussPoint insertBussPoint(Map<String, String> data) {
        BussPoint bussPoint = new BussPoint();
        bussPoint.setFieldByName(data);
        
        if (bussPoint.getLocationId() == null || bussPoint.getLocationId() <= 0) {
            Map<String, String> locationData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("location."))
                    .forEach(entry -> locationData.put(entry.getKey().substring("location.".length()), entry.getValue()));
            bussPoint.setLocation(this.insertLocation(locationData));
        }
        ErrorCode.DATA_EXISTED.throwIfNotEmpty(bussPointRepository.findByLocationId(bussPoint.getLocationId()));

        bussPoint = bussPointRepository.save(bussPoint);
        return bussPoint;
    }
    public List<BussPoint> insertMultiBussPoint(List<Map<String, String>> data) {
        List<BussPoint> result = new ArrayList<>();
        data.forEach(bussPointData -> result.add(this.insertBussPoint(bussPointData)));
        return result;
    }
    public void deleteBussPointByBussPointIds(Long[] bussPointIds) {
        bussPointRepository.deleteAllByBussPointIdIn(Arrays.asList(bussPointIds));
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
//=================== END OF BussPoint ======================
    public Trip updateTrip(Map<String, String> data) {
        Long tripId = Long.parseLong(data.get("tripId"));
        Trip trip = ErrorCode.DATA_NOT_FOUND.throwIfNull(tripRepository.findByTripId(tripId));

        Map<String, String> bussData = new HashMap<>();
        data.forEach((fieldName, fieldValue) -> {
            if (fieldName.startsWith("buss.")) {
                bussData.put(fieldName.substring("buss.".length()), fieldValue);
            }
        });
        if (!bussData.isEmpty()) {
            bussData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateBuss(bussData);
        }

        trip.setFieldByName(data);
        tripRepository.save(trip);
        return trip;
    }
    public Trip insertTrip(Map<String, String> data) {
        Trip trip = new Trip();
        trip.setFieldByName(data);
        
        if (trip.getBussId() == null || trip.getBussId() <= 0) {
            Map<String, String> bussData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("buss."))
                    .forEach(entry -> bussData.put(entry.getKey().substring("buss.".length()), entry.getValue()));
            trip.setBuss(this.insertBuss(bussData));
        }
        ErrorCode.DATA_EXISTED.throwIfNotEmpty(tripRepository.findByBussId(trip.getBussId()));

        trip = tripRepository.save(trip);
        return trip;
    }
    public List<Trip> insertMultiTrip(List<Map<String, String>> data) {
        List<Trip> result = new ArrayList<>();
        data.forEach(tripData -> result.add(this.insertTrip(tripData)));
        return result;
    }
    public void deleteTripByTripIds(Long[] tripIds) {
        tripRepository.deleteAllByTripIdIn(Arrays.asList(tripIds));
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
//=================== END OF Trip ======================

}
