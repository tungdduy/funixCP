package net.timxekhach.operation.rest.service;

import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.data.entity.*;
import net.timxekhach.operation.data.mapped.BussType_MAPPED;
import net.timxekhach.operation.data.mapped.Buss_MAPPED;
import net.timxekhach.operation.data.mapped.Company_MAPPED;
import net.timxekhach.operation.data.mapped.User_MAPPED;
import net.timxekhach.operation.data.repository.*;
import net.timxekhach.operation.response.ErrorCode;
import net.timxekhach.utility.XeBooleanUtils;
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
    private final UserRepository userRepository;
    private static UserRepository staticUserRepository;
    public static UserRepository getUserRepository() {
        return CommonUpdateService.staticUserRepository;
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
    private final XeLocationRepository xeLocationRepository;
    private static XeLocationRepository staticXeLocationRepository;
    public static XeLocationRepository getXeLocationRepository() {
        return CommonUpdateService.staticXeLocationRepository;
    }
    private final BussScheduleRepository bussScheduleRepository;
    private static BussScheduleRepository staticBussScheduleRepository;
    public static BussScheduleRepository getBussScheduleRepository() {
        return CommonUpdateService.staticBussScheduleRepository;
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
    private final TripUserRepository tripUserRepository;
    private static TripUserRepository staticTripUserRepository;
    public static TripUserRepository getTripUserRepository() {
        return CommonUpdateService.staticTripUserRepository;
    }
    private final BussSchedulePriceRepository bussSchedulePriceRepository;
    private static BussSchedulePriceRepository staticBussSchedulePriceRepository;
    public static BussSchedulePriceRepository getBussSchedulePriceRepository() {
        return CommonUpdateService.staticBussSchedulePriceRepository;
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
    private final BussSchedulePointRepository bussSchedulePointRepository;
    private static BussSchedulePointRepository staticBussSchedulePointRepository;
    public static BussSchedulePointRepository getBussSchedulePointRepository() {
        return CommonUpdateService.staticBussSchedulePointRepository;
    }
    @PostConstruct
    public void postConstruct() {
        CommonUpdateService.staticUserRepository = userRepository;
        CommonUpdateService.staticTripUserSeatRepository = tripUserSeatRepository;
        CommonUpdateService.staticEmployeeRepository = employeeRepository;
        CommonUpdateService.staticCompanyRepository = companyRepository;
        CommonUpdateService.staticXeLocationRepository = xeLocationRepository;
        CommonUpdateService.staticBussScheduleRepository = bussScheduleRepository;
        CommonUpdateService.staticBussEmployeeRepository = bussEmployeeRepository;
        CommonUpdateService.staticBussRepository = bussRepository;
        CommonUpdateService.staticTripUserRepository = tripUserRepository;
        CommonUpdateService.staticBussSchedulePriceRepository = bussSchedulePriceRepository;
        CommonUpdateService.staticBussTypeRepository = bussTypeRepository;
        CommonUpdateService.staticBussPointRepository = bussPointRepository;
        CommonUpdateService.staticTripRepository = tripRepository;
        CommonUpdateService.staticBussSchedulePointRepository = bussSchedulePointRepository;
    }

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
    public TripUserSeat updateTripUserSeat(Map<String, String> data) {
        Long tripUserSeatId = Long.parseLong(data.get("tripUserSeatId"));
        TripUserSeat tripUserSeat = ErrorCode.DATA_NOT_FOUND.throwIfNull(tripUserSeatRepository.findByTripUserSeatId(tripUserSeatId));

        Map<String, String> tripUserData = new HashMap<>();
        data.forEach((fieldName, fieldValue) -> {
            if (fieldName.startsWith("tripUser.")) {
                tripUserData.put(fieldName.substring("tripUser.".length()), fieldValue);
            }
        });
        if (!tripUserData.isEmpty()) {
            tripUserData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateTripUser(tripUserData);
        }

        tripUserSeat.setFieldByName(data);
        tripUserSeatRepository.save(tripUserSeat);
        return tripUserSeat;
    }
    public TripUserSeat insertTripUserSeat(Map<String, String> data) {
        TripUserSeat tripUserSeat = new TripUserSeat();
        tripUserSeat.setFieldByName(data);
        
        if (XeBooleanUtils.isTrue(data.get("newTripUserIfNull")) && (tripUserSeat.getTripUserId() == null || tripUserSeat.getTripUserId() <= 0)) {
            Map<String, String> tripUserData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("tripUser."))
                    .forEach(entry -> tripUserData.put(entry.getKey().substring("tripUser.".length()), entry.getValue()));
            tripUserSeat.setTripUser(this.insertTripUser(tripUserData));
        }

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
        
        if (XeBooleanUtils.isTrue(data.get("newCompanyIfNull")) && (employee.getCompanyId() == null || employee.getCompanyId() <= 0)) {
            Map<String, String> companyData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("company."))
                    .forEach(entry -> companyData.put(entry.getKey().substring("company.".length()), entry.getValue()));
            employee.setCompany(this.insertCompany(companyData));
        }
        if (XeBooleanUtils.isTrue(data.get("newUserIfNull")) && (employee.getUserId() == null || employee.getUserId() <= 0)) {
            Map<String, String> userData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("user."))
                    .forEach(entry -> userData.put(entry.getKey().substring("user.".length()), entry.getValue()));
            employee.setUser(this.insertUser(userData));
        }

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
    public XeLocation updateXeLocation(Map<String, String> data) {
        Long xeLocationId = Long.parseLong(data.get("xeLocationId"));
        XeLocation xeLocation = ErrorCode.DATA_NOT_FOUND.throwIfNull(xeLocationRepository.findByXeLocationId(xeLocationId));


        xeLocation.setFieldByName(data);
        xeLocationRepository.save(xeLocation);
        return xeLocation;
    }
    public XeLocation insertXeLocation(Map<String, String> data) {
        XeLocation xeLocation = new XeLocation();
        xeLocation.setFieldByName(data);
        

        xeLocation = xeLocationRepository.save(xeLocation);
        return xeLocation;
    }
    public List<XeLocation> insertMultiXeLocation(List<Map<String, String>> data) {
        List<XeLocation> result = new ArrayList<>();
        data.forEach(xeLocationData -> result.add(this.insertXeLocation(xeLocationData)));
        return result;
    }
    public void deleteXeLocationByXeLocationIds(Long[] xeLocationIds) {
        xeLocationRepository.deleteAllByXeLocationIdIn(Arrays.asList(xeLocationIds));
    }
    public List<XeLocation> findXeLocation(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return xeLocationRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("XeLocationId")) {
            XeLocation xeLocation = xeLocationRepository.findByXeLocationId(data.get("XeLocationId"));
            if(xeLocation == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(xeLocation);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        return XeReflectionUtils.invokeMethodByName(xeLocationRepository, findMethodName, findMethodParams);
    }
//=================== END OF XeLocation ======================
    public BussSchedule updateBussSchedule(Map<String, String> data) {
        Long bussScheduleId = Long.parseLong(data.get("bussScheduleId"));
        BussSchedule bussSchedule = ErrorCode.DATA_NOT_FOUND.throwIfNull(bussScheduleRepository.findByBussScheduleId(bussScheduleId));

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

        bussSchedule.setFieldByName(data);
        bussScheduleRepository.save(bussSchedule);
        return bussSchedule;
    }
    public BussSchedule insertBussSchedule(Map<String, String> data) {
        BussSchedule bussSchedule = new BussSchedule();
        bussSchedule.setFieldByName(data);
        
        if (XeBooleanUtils.isTrue(data.get("newBussIfNull")) && (bussSchedule.getBussId() == null || bussSchedule.getBussId() <= 0)) {
            Map<String, String> bussData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("buss."))
                    .forEach(entry -> bussData.put(entry.getKey().substring("buss.".length()), entry.getValue()));
            bussSchedule.setBuss(this.insertBuss(bussData));
        }
        if (XeBooleanUtils.isTrue(data.get("newCompanyIfNull")) && (bussSchedule.getCompanyId() == null || bussSchedule.getCompanyId() <= 0)) {
            Map<String, String> companyData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("company."))
                    .forEach(entry -> companyData.put(entry.getKey().substring("company.".length()), entry.getValue()));
            bussSchedule.setCompany(this.insertCompany(companyData));
        }

        bussSchedule = bussScheduleRepository.save(bussSchedule);
        return bussSchedule;
    }
    public List<BussSchedule> insertMultiBussSchedule(List<Map<String, String>> data) {
        List<BussSchedule> result = new ArrayList<>();
        data.forEach(bussScheduleData -> result.add(this.insertBussSchedule(bussScheduleData)));
        return result;
    }
    public void deleteBussScheduleByBussScheduleIds(Long[] bussScheduleIds) {
        bussScheduleRepository.deleteAllByBussScheduleIdIn(Arrays.asList(bussScheduleIds));
    }
    public void deleteBussSchedule(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return;
        }
        if (data.size() == 1 && data.containsKey("BussScheduleId")) {
            bussScheduleRepository.deleteByBussScheduleId(data.get("BussScheduleId"));
            return;
        }
        String deleteMethodName = String.format("deleteBy%s", String.join("And", data.keySet()));
        Object[] deleteMethodParams = data.values().toArray(new Long[0]);
        XeReflectionUtils.invokeMethodByName(bussScheduleRepository, deleteMethodName, deleteMethodParams);
    }
    public List<BussSchedule> findBussSchedule(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return bussScheduleRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("BussScheduleId")) {
            BussSchedule bussSchedule = bussScheduleRepository.findByBussScheduleId(data.get("BussScheduleId"));
            if(bussSchedule == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(bussSchedule);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        return XeReflectionUtils.invokeMethodByName(bussScheduleRepository, findMethodName, findMethodParams);
    }
//=================== END OF BussSchedule ======================
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
        
        if (XeBooleanUtils.isTrue(data.get("newBussIfNull")) && (bussEmployee.getBussId() == null || bussEmployee.getBussId() <= 0)) {
            Map<String, String> bussData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("buss."))
                    .forEach(entry -> bussData.put(entry.getKey().substring("buss.".length()), entry.getValue()));
            bussEmployee.setBuss(this.insertBuss(bussData));
        }
        if (XeBooleanUtils.isTrue(data.get("newEmployeeIfNull")) && (bussEmployee.getEmployeeId() == null || bussEmployee.getEmployeeId() <= 0)) {
            Map<String, String> employeeData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("employee."))
                    .forEach(entry -> employeeData.put(entry.getKey().substring("employee.".length()), entry.getValue()));
            bussEmployee.setEmployee(this.insertEmployee(employeeData));
        }

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
        
        if (XeBooleanUtils.isTrue(data.get("newBussTypeIfNull")) && (buss.getBussTypeId() == null || buss.getBussTypeId() <= 0)) {
            Map<String, String> bussTypeData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("bussType."))
                    .forEach(entry -> bussTypeData.put(entry.getKey().substring("bussType.".length()), entry.getValue()));
            buss.setBussType(this.insertBussType(bussTypeData));
        }
        if (XeBooleanUtils.isTrue(data.get("newCompanyIfNull")) && (buss.getCompanyId() == null || buss.getCompanyId() <= 0)) {
            Map<String, String> companyData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("company."))
                    .forEach(entry -> companyData.put(entry.getKey().substring("company.".length()), entry.getValue()));
            buss.setCompany(this.insertCompany(companyData));
        }

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
//=================== END OF Buss ======================
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
        
        if (XeBooleanUtils.isTrue(data.get("newTripIfNull")) && (tripUser.getTripId() == null || tripUser.getTripId() <= 0)) {
            Map<String, String> tripData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("trip."))
                    .forEach(entry -> tripData.put(entry.getKey().substring("trip.".length()), entry.getValue()));
            tripUser.setTrip(this.insertTrip(tripData));
        }
        if (XeBooleanUtils.isTrue(data.get("newUserIfNull")) && (tripUser.getUserId() == null || tripUser.getUserId() <= 0)) {
            Map<String, String> userData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("user."))
                    .forEach(entry -> userData.put(entry.getKey().substring("user.".length()), entry.getValue()));
            tripUser.setUser(this.insertUser(userData));
        }

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
    public BussSchedulePrice updateBussSchedulePrice(Map<String, String> data) {
        Long bussSchedulePriceId = Long.parseLong(data.get("bussSchedulePriceId"));
        BussSchedulePrice bussSchedulePrice = ErrorCode.DATA_NOT_FOUND.throwIfNull(bussSchedulePriceRepository.findByBussSchedulePriceId(bussSchedulePriceId));

        Map<String, String> bussScheduleData = new HashMap<>();
        data.forEach((fieldName, fieldValue) -> {
            if (fieldName.startsWith("bussSchedule.")) {
                bussScheduleData.put(fieldName.substring("bussSchedule.".length()), fieldValue);
            }
        });
        if (!bussScheduleData.isEmpty()) {
            bussScheduleData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateBussSchedule(bussScheduleData);
        }

        bussSchedulePrice.setFieldByName(data);
        bussSchedulePriceRepository.save(bussSchedulePrice);
        return bussSchedulePrice;
    }
    public BussSchedulePrice insertBussSchedulePrice(Map<String, String> data) {
        BussSchedulePrice bussSchedulePrice = new BussSchedulePrice();
        bussSchedulePrice.setFieldByName(data);
        
        if (XeBooleanUtils.isTrue(data.get("newBussScheduleIfNull")) && (bussSchedulePrice.getBussScheduleId() == null || bussSchedulePrice.getBussScheduleId() <= 0)) {
            Map<String, String> bussScheduleData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("bussSchedule."))
                    .forEach(entry -> bussScheduleData.put(entry.getKey().substring("bussSchedule.".length()), entry.getValue()));
            bussSchedulePrice.setBussSchedule(this.insertBussSchedule(bussScheduleData));
        }

        bussSchedulePrice = bussSchedulePriceRepository.save(bussSchedulePrice);
        return bussSchedulePrice;
    }
    public List<BussSchedulePrice> insertMultiBussSchedulePrice(List<Map<String, String>> data) {
        List<BussSchedulePrice> result = new ArrayList<>();
        data.forEach(bussSchedulePriceData -> result.add(this.insertBussSchedulePrice(bussSchedulePriceData)));
        return result;
    }
    public void deleteBussSchedulePriceByBussSchedulePriceIds(Long[] bussSchedulePriceIds) {
        bussSchedulePriceRepository.deleteAllByBussSchedulePriceIdIn(Arrays.asList(bussSchedulePriceIds));
    }
    public void deleteBussSchedulePrice(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return;
        }
        if (data.size() == 1 && data.containsKey("BussSchedulePriceId")) {
            bussSchedulePriceRepository.deleteByBussSchedulePriceId(data.get("BussSchedulePriceId"));
            return;
        }
        String deleteMethodName = String.format("deleteBy%s", String.join("And", data.keySet()));
        Object[] deleteMethodParams = data.values().toArray(new Long[0]);
        XeReflectionUtils.invokeMethodByName(bussSchedulePriceRepository, deleteMethodName, deleteMethodParams);
    }
    public List<BussSchedulePrice> findBussSchedulePrice(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return bussSchedulePriceRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("BussSchedulePriceId")) {
            BussSchedulePrice bussSchedulePrice = bussSchedulePriceRepository.findByBussSchedulePriceId(data.get("BussSchedulePriceId"));
            if(bussSchedulePrice == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(bussSchedulePrice);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        return XeReflectionUtils.invokeMethodByName(bussSchedulePriceRepository, findMethodName, findMethodParams);
    }
//=================== END OF BussSchedulePrice ======================
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
    public BussType updateBussTypeProfileImage(BussType_MAPPED.Pk pk, MultipartFile profileImage) throws IOException {
        BussType bussType = DATA_NOT_FOUND.throwIfNull(bussTypeRepository.getById(pk));
        bussType.saveProfileImage(profileImage);
        return bussType;
    }
//=================== END OF BussType ======================
    public BussPoint updateBussPoint(Map<String, String> data) {
        Long bussPointId = Long.parseLong(data.get("bussPointId"));
        BussPoint bussPoint = ErrorCode.DATA_NOT_FOUND.throwIfNull(bussPointRepository.findByBussPointId(bussPointId));

        Map<String, String> companyData = new HashMap<>();
        Map<String, String> xeLocationData = new HashMap<>();
        data.forEach((fieldName, fieldValue) -> {
            if (fieldName.startsWith("company.")) {
                companyData.put(fieldName.substring("company.".length()), fieldValue);
            }
            if (fieldName.startsWith("xeLocation.")) {
                xeLocationData.put(fieldName.substring("xeLocation.".length()), fieldValue);
            }
        });
        if (!companyData.isEmpty()) {
            companyData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateCompany(companyData);
        }
        if (!xeLocationData.isEmpty()) {
            xeLocationData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateXeLocation(xeLocationData);
        }

        bussPoint.setFieldByName(data);
        bussPointRepository.save(bussPoint);
        return bussPoint;
    }
    public BussPoint insertBussPoint(Map<String, String> data) {
        BussPoint bussPoint = new BussPoint();
        bussPoint.setFieldByName(data);
        
        if (XeBooleanUtils.isTrue(data.get("newCompanyIfNull")) && (bussPoint.getCompanyId() == null || bussPoint.getCompanyId() <= 0)) {
            Map<String, String> companyData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("company."))
                    .forEach(entry -> companyData.put(entry.getKey().substring("company.".length()), entry.getValue()));
            bussPoint.setCompany(this.insertCompany(companyData));
        }
        if (XeBooleanUtils.isTrue(data.get("newXeLocationIfNull")) && (bussPoint.getXeLocationId() == null || bussPoint.getXeLocationId() <= 0)) {
            Map<String, String> xeLocationData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("xeLocation."))
                    .forEach(entry -> xeLocationData.put(entry.getKey().substring("xeLocation.".length()), entry.getValue()));
            bussPoint.setXeLocation(this.insertXeLocation(xeLocationData));
        }

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

        Map<String, String> bussScheduleData = new HashMap<>();
        data.forEach((fieldName, fieldValue) -> {
            if (fieldName.startsWith("bussSchedule.")) {
                bussScheduleData.put(fieldName.substring("bussSchedule.".length()), fieldValue);
            }
        });
        if (!bussScheduleData.isEmpty()) {
            bussScheduleData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateBussSchedule(bussScheduleData);
        }

        trip.setFieldByName(data);
        tripRepository.save(trip);
        return trip;
    }
    public Trip insertTrip(Map<String, String> data) {
        Trip trip = new Trip();
        trip.setFieldByName(data);
        
        if (XeBooleanUtils.isTrue(data.get("newBussScheduleIfNull")) && (trip.getBussScheduleId() == null || trip.getBussScheduleId() <= 0)) {
            Map<String, String> bussScheduleData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("bussSchedule."))
                    .forEach(entry -> bussScheduleData.put(entry.getKey().substring("bussSchedule.".length()), entry.getValue()));
            trip.setBussSchedule(this.insertBussSchedule(bussScheduleData));
        }

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
    public BussSchedulePoint updateBussSchedulePoint(Map<String, String> data) {
        Long bussSchedulePointId = Long.parseLong(data.get("bussSchedulePointId"));
        BussSchedulePoint bussSchedulePoint = ErrorCode.DATA_NOT_FOUND.throwIfNull(bussSchedulePointRepository.findByBussSchedulePointId(bussSchedulePointId));

        Map<String, String> bussPointData = new HashMap<>();
        Map<String, String> bussScheduleData = new HashMap<>();
        data.forEach((fieldName, fieldValue) -> {
            if (fieldName.startsWith("bussPoint.")) {
                bussPointData.put(fieldName.substring("bussPoint.".length()), fieldValue);
            }
            if (fieldName.startsWith("bussSchedule.")) {
                bussScheduleData.put(fieldName.substring("bussSchedule.".length()), fieldValue);
            }
        });
        if (!bussPointData.isEmpty()) {
            bussPointData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateBussPoint(bussPointData);
        }
        if (!bussScheduleData.isEmpty()) {
            bussScheduleData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateBussSchedule(bussScheduleData);
        }

        bussSchedulePoint.setFieldByName(data);
        bussSchedulePointRepository.save(bussSchedulePoint);
        return bussSchedulePoint;
    }
    public BussSchedulePoint insertBussSchedulePoint(Map<String, String> data) {
        BussSchedulePoint bussSchedulePoint = new BussSchedulePoint();
        bussSchedulePoint.setFieldByName(data);
        
        if (XeBooleanUtils.isTrue(data.get("newBussPointIfNull")) && (bussSchedulePoint.getBussPointId() == null || bussSchedulePoint.getBussPointId() <= 0)) {
            Map<String, String> bussPointData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("bussPoint."))
                    .forEach(entry -> bussPointData.put(entry.getKey().substring("bussPoint.".length()), entry.getValue()));
            bussSchedulePoint.setBussPoint(this.insertBussPoint(bussPointData));
        }
        if (XeBooleanUtils.isTrue(data.get("newBussScheduleIfNull")) && (bussSchedulePoint.getBussScheduleId() == null || bussSchedulePoint.getBussScheduleId() <= 0)) {
            Map<String, String> bussScheduleData = new HashMap<>();
            data.entrySet().stream()
                    .filter(entry -> entry.getKey().startsWith("bussSchedule."))
                    .forEach(entry -> bussScheduleData.put(entry.getKey().substring("bussSchedule.".length()), entry.getValue()));
            bussSchedulePoint.setBussSchedule(this.insertBussSchedule(bussScheduleData));
        }

        bussSchedulePoint = bussSchedulePointRepository.save(bussSchedulePoint);
        return bussSchedulePoint;
    }
    public List<BussSchedulePoint> insertMultiBussSchedulePoint(List<Map<String, String>> data) {
        List<BussSchedulePoint> result = new ArrayList<>();
        data.forEach(bussSchedulePointData -> result.add(this.insertBussSchedulePoint(bussSchedulePointData)));
        return result;
    }
    public void deleteBussSchedulePointByBussSchedulePointIds(Long[] bussSchedulePointIds) {
        bussSchedulePointRepository.deleteAllByBussSchedulePointIdIn(Arrays.asList(bussSchedulePointIds));
    }
    public void deleteBussSchedulePoint(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return;
        }
        if (data.size() == 1 && data.containsKey("BussSchedulePointId")) {
            bussSchedulePointRepository.deleteByBussSchedulePointId(data.get("BussSchedulePointId"));
            return;
        }
        String deleteMethodName = String.format("deleteBy%s", String.join("And", data.keySet()));
        Object[] deleteMethodParams = data.values().toArray(new Long[0]);
        XeReflectionUtils.invokeMethodByName(bussSchedulePointRepository, deleteMethodName, deleteMethodParams);
    }
    public List<BussSchedulePoint> findBussSchedulePoint(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return bussSchedulePointRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("BussSchedulePointId")) {
            BussSchedulePoint bussSchedulePoint = bussSchedulePointRepository.findByBussSchedulePointId(data.get("BussSchedulePointId"));
            if(bussSchedulePoint == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(bussSchedulePoint);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        return XeReflectionUtils.invokeMethodByName(bussSchedulePointRepository, findMethodName, findMethodParams);
    }
//=================== END OF BussSchedulePoint ======================

}
