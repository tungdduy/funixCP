package net.timxekhach.operation.rest.service;

import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.data.entity.*;
import net.timxekhach.operation.data.mapped.*;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
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
    private final EmployeeRepository employeeRepository;
    private static EmployeeRepository staticEmployeeRepository;
    public static EmployeeRepository getEmployeeRepository() {
        return CommonUpdateService.staticEmployeeRepository;
    }
    private final PathPointRepository pathPointRepository;
    private static PathPointRepository staticPathPointRepository;
    public static PathPointRepository getPathPointRepository() {
        return CommonUpdateService.staticPathPointRepository;
    }
    private final CompanyRepository companyRepository;
    private static CompanyRepository staticCompanyRepository;
    public static CompanyRepository getCompanyRepository() {
        return CommonUpdateService.staticCompanyRepository;
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
    private final SeatGroupRepository seatGroupRepository;
    private static SeatGroupRepository staticSeatGroupRepository;
    public static SeatGroupRepository getSeatGroupRepository() {
        return CommonUpdateService.staticSeatGroupRepository;
    }
    private final BussTypeRepository bussTypeRepository;
    private static BussTypeRepository staticBussTypeRepository;
    public static BussTypeRepository getBussTypeRepository() {
        return CommonUpdateService.staticBussTypeRepository;
    }
    private final TripRepository tripRepository;
    private static TripRepository staticTripRepository;
    public static TripRepository getTripRepository() {
        return CommonUpdateService.staticTripRepository;
    }
    private final PathRepository pathRepository;
    private static PathRepository staticPathRepository;
    public static PathRepository getPathRepository() {
        return CommonUpdateService.staticPathRepository;
    }
    private final BussSchedulePointRepository bussSchedulePointRepository;
    private static BussSchedulePointRepository staticBussSchedulePointRepository;
    public static BussSchedulePointRepository getBussSchedulePointRepository() {
        return CommonUpdateService.staticBussSchedulePointRepository;
    }
    public static final Map<Class<? extends XeEntity>, Object> repoMap = new HashMap<>();
    @PostConstruct
    public void postConstruct() {
        CommonUpdateService.staticUserRepository = userRepository;
        repoMap.put(User.class, userRepository);
        CommonUpdateService.staticEmployeeRepository = employeeRepository;
        repoMap.put(Employee.class, employeeRepository);
        CommonUpdateService.staticPathPointRepository = pathPointRepository;
        repoMap.put(PathPoint.class, pathPointRepository);
        CommonUpdateService.staticCompanyRepository = companyRepository;
        repoMap.put(Company.class, companyRepository);
        CommonUpdateService.staticBussScheduleRepository = bussScheduleRepository;
        repoMap.put(BussSchedule.class, bussScheduleRepository);
        CommonUpdateService.staticBussEmployeeRepository = bussEmployeeRepository;
        repoMap.put(BussEmployee.class, bussEmployeeRepository);
        CommonUpdateService.staticBussRepository = bussRepository;
        repoMap.put(Buss.class, bussRepository);
        CommonUpdateService.staticLocationRepository = locationRepository;
        repoMap.put(Location.class, locationRepository);
        CommonUpdateService.staticTripUserRepository = tripUserRepository;
        repoMap.put(TripUser.class, tripUserRepository);
        CommonUpdateService.staticSeatGroupRepository = seatGroupRepository;
        repoMap.put(SeatGroup.class, seatGroupRepository);
        CommonUpdateService.staticBussTypeRepository = bussTypeRepository;
        repoMap.put(BussType.class, bussTypeRepository);
        CommonUpdateService.staticTripRepository = tripRepository;
        repoMap.put(Trip.class, tripRepository);
        CommonUpdateService.staticPathRepository = pathRepository;
        repoMap.put(Path.class, pathRepository);
        CommonUpdateService.staticBussSchedulePointRepository = bussSchedulePointRepository;
        repoMap.put(BussSchedulePoint.class, bussSchedulePointRepository);
    }

    public User updateUser(Map<String, String> data) {
        Long userId = Long.parseLong(data.get("userId"));
        User user = ErrorCode.DATA_NOT_FOUND.throwIfNull(userRepository.findByUserId(userId));


        user.setFieldByName(data);
        user.preUpdateAction();
        userRepository.save(user);
        return user;
    }
        
    public List<User> updateMultiUser(List<Map<String, String>> multiData) {
        List<User> userParseList = new ArrayList<>();
        multiData.forEach(data -> {
            userParseList.add(this.updateUser(data));
        });
        userRepository.flush();
        return userParseList;
    }
        
    public User insertUser(Map<String, String> data) {
        User user = new User();
        user.setFieldByName(data);
        
        user.preSaveAction();
        user = userRepository.save(user);
        return user;
    }
    public List<User> insertMultiUser(List<Map<String, String>> data) {
        List<User> result = new ArrayList<>();
        data.forEach(userData -> result.add(this.insertUser(userData)));
        return result;
    }
    public void deleteUserByUserIds(Long[] userIds) {
        List<User> deletingList = userRepository.findByUserIdIn(Arrays.asList(userIds));
        deletingList.forEach(XeEntity::preRemoveAction);
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
        String orderExpression = "";
        return XeReflectionUtils.invokeMethodByName(userRepository, findMethodName + orderExpression, findMethodParams);
    }
    public User updateUserProfileImage(User_MAPPED.Pk pk, MultipartFile profileImage) throws IOException {
        User user = DATA_NOT_FOUND.throwIfNull(userRepository.getById(pk));
        user.saveProfileImage(profileImage);
        return user;
    }
//=================== END OF User ======================
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
        employee.preUpdateAction();
        employeeRepository.save(employee);
        return employee;
    }
        
    public List<Employee> updateMultiEmployee(List<Map<String, String>> multiData) {
        List<Employee> employeeParseList = new ArrayList<>();
        multiData.forEach(data -> {
            employeeParseList.add(this.updateEmployee(data));
        });
        employeeRepository.flush();
        return employeeParseList;
    }
        
    public Employee insertEmployee(Map<String, String> data) {
        Employee employee = new Employee();
        employee.setFieldByName(data);
        
        if (employee.getCompanyId() == null || employee.getCompanyId() <= 0) {
            if (XeBooleanUtils.isTrue(data.get("newCompanyIfNull"))) {
                Map<String, String> companyData = new HashMap<>();
                data.entrySet().stream()
                        .filter(entry -> entry.getKey().startsWith("company."))
                        .forEach(entry -> companyData.put(entry.getKey().substring("company.".length()), entry.getValue()));
                employee.setCompany(this.insertCompany(companyData));
            }
        } else {
            employee.setCompany(this.companyRepository.findByCompanyId(employee.getCompanyId()));
        }
        if (employee.getUserId() == null || employee.getUserId() <= 0) {
            if (XeBooleanUtils.isTrue(data.get("newUserIfNull"))) {
                Map<String, String> userData = new HashMap<>();
                data.entrySet().stream()
                        .filter(entry -> entry.getKey().startsWith("user."))
                        .forEach(entry -> userData.put(entry.getKey().substring("user.".length()), entry.getValue()));
                employee.setUser(this.insertUser(userData));
            }
        } else {
            employee.setUser(this.userRepository.findByUserId(employee.getUserId()));
        }
        employee.preSaveAction();
        employee = employeeRepository.save(employee);
        return employee;
    }
    public List<Employee> insertMultiEmployee(List<Map<String, String>> data) {
        List<Employee> result = new ArrayList<>();
        data.forEach(employeeData -> result.add(this.insertEmployee(employeeData)));
        return result;
    }
    public void deleteEmployeeByEmployeeIds(Long[] employeeIds) {
        List<Employee> deletingList = employeeRepository.findByEmployeeIdIn(Arrays.asList(employeeIds));
        deletingList.forEach(XeEntity::preRemoveAction);
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
        String orderExpression = "";
        return XeReflectionUtils.invokeMethodByName(employeeRepository, findMethodName + orderExpression, findMethodParams);
    }
//=================== END OF Employee ======================
    public PathPoint updatePathPoint(Map<String, String> data) {
        Long pathPointId = Long.parseLong(data.get("pathPointId"));
        PathPoint pathPoint = ErrorCode.DATA_NOT_FOUND.throwIfNull(pathPointRepository.findByPathPointId(pathPointId));

        Map<String, String> locationData = new HashMap<>();
        Map<String, String> pathData = new HashMap<>();
        data.forEach((fieldName, fieldValue) -> {
            if (fieldName.startsWith("location.")) {
                locationData.put(fieldName.substring("location.".length()), fieldValue);
            }
            if (fieldName.startsWith("path.")) {
                pathData.put(fieldName.substring("path.".length()), fieldValue);
            }
        });
        if (!locationData.isEmpty()) {
            locationData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateLocation(locationData);
        }
        if (!pathData.isEmpty()) {
            pathData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updatePath(pathData);
        }

        pathPoint.setFieldByName(data);
        pathPoint.preUpdateAction();
        pathPointRepository.save(pathPoint);
        return pathPoint;
    }
        
    public List<PathPoint> updateMultiPathPoint(List<Map<String, String>> multiData) {
        List<PathPoint> pathPointParseList = new ArrayList<>();
        multiData.forEach(data -> {
            pathPointParseList.add(this.updatePathPoint(data));
        });
        pathPointRepository.flush();
        return pathPointParseList;
    }
        
    public PathPoint insertPathPoint(Map<String, String> data) {
        PathPoint pathPoint = new PathPoint();
        pathPoint.setFieldByName(data);
        
        if (pathPoint.getLocationId() == null || pathPoint.getLocationId() <= 0) {
            if (XeBooleanUtils.isTrue(data.get("newLocationIfNull"))) {
                Map<String, String> locationData = new HashMap<>();
                data.entrySet().stream()
                        .filter(entry -> entry.getKey().startsWith("location."))
                        .forEach(entry -> locationData.put(entry.getKey().substring("location.".length()), entry.getValue()));
                pathPoint.setLocation(this.insertLocation(locationData));
            }
        } else {
            pathPoint.setLocation(this.locationRepository.findByLocationId(pathPoint.getLocationId()));
        }
        if (pathPoint.getPathId() == null || pathPoint.getPathId() <= 0) {
            if (XeBooleanUtils.isTrue(data.get("newPathIfNull"))) {
                Map<String, String> pathData = new HashMap<>();
                data.entrySet().stream()
                        .filter(entry -> entry.getKey().startsWith("path."))
                        .forEach(entry -> pathData.put(entry.getKey().substring("path.".length()), entry.getValue()));
                pathPoint.setPath(this.insertPath(pathData));
            }
        } else {
            pathPoint.setPath(this.pathRepository.findByPathId(pathPoint.getPathId()));
        }
        pathPoint.preSaveAction();
        pathPoint = pathPointRepository.save(pathPoint);
        return pathPoint;
    }
    public List<PathPoint> insertMultiPathPoint(List<Map<String, String>> data) {
        List<PathPoint> result = new ArrayList<>();
        data.forEach(pathPointData -> result.add(this.insertPathPoint(pathPointData)));
        return result;
    }
    public void deletePathPointByPathPointIds(Long[] pathPointIds) {
        List<PathPoint> deletingList = pathPointRepository.findByPathPointIdIn(Arrays.asList(pathPointIds));
        deletingList.forEach(XeEntity::preRemoveAction);
        pathPointRepository.deleteAllByPathPointIdIn(Arrays.asList(pathPointIds));
    }
    public void deletePathPoint(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return;
        }
        if (data.size() == 1 && data.containsKey("PathPointId")) {
            pathPointRepository.deleteByPathPointId(data.get("PathPointId"));
            return;
        }
        String deleteMethodName = String.format("deleteBy%s", String.join("And", data.keySet()));
        Object[] deleteMethodParams = data.values().toArray(new Long[0]);
        XeReflectionUtils.invokeMethodByName(pathPointRepository, deleteMethodName, deleteMethodParams);
    }
    public List<PathPoint> findPathPoint(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return pathPointRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("PathPointId")) {
            PathPoint pathPoint = pathPointRepository.findByPathPointId(data.get("PathPointId"));
            if(pathPoint == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(pathPoint);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        String orderExpression = "OrderByPointOrderAsc";
        return XeReflectionUtils.invokeMethodByName(pathPointRepository, findMethodName + orderExpression, findMethodParams);
    }
//=================== END OF PathPoint ======================
    public Company updateCompany(Map<String, String> data) {
        Long companyId = Long.parseLong(data.get("companyId"));
        Company company = ErrorCode.DATA_NOT_FOUND.throwIfNull(companyRepository.findByCompanyId(companyId));


        company.setFieldByName(data);
        company.preUpdateAction();
        companyRepository.save(company);
        return company;
    }
        
    public List<Company> updateMultiCompany(List<Map<String, String>> multiData) {
        List<Company> companyParseList = new ArrayList<>();
        multiData.forEach(data -> {
            companyParseList.add(this.updateCompany(data));
        });
        companyRepository.flush();
        return companyParseList;
    }
        
    public Company insertCompany(Map<String, String> data) {
        Company company = new Company();
        company.setFieldByName(data);
        
        company.preSaveAction();
        company = companyRepository.save(company);
        return company;
    }
    public List<Company> insertMultiCompany(List<Map<String, String>> data) {
        List<Company> result = new ArrayList<>();
        data.forEach(companyData -> result.add(this.insertCompany(companyData)));
        return result;
    }
    public void deleteCompanyByCompanyIds(Long[] companyIds) {
        List<Company> deletingList = companyRepository.findByCompanyIdIn(Arrays.asList(companyIds));
        deletingList.forEach(XeEntity::preRemoveAction);
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
        String orderExpression = "";
        return XeReflectionUtils.invokeMethodByName(companyRepository, findMethodName + orderExpression, findMethodParams);
    }
    public Company updateCompanyProfileImage(Company_MAPPED.Pk pk, MultipartFile profileImage) throws IOException {
        Company company = DATA_NOT_FOUND.throwIfNull(companyRepository.getById(pk));
        company.saveProfileImage(profileImage);
        return company;
    }
//=================== END OF Company ======================
    public BussSchedule updateBussSchedule(Map<String, String> data) {
        Long bussScheduleId = Long.parseLong(data.get("bussScheduleId"));
        BussSchedule bussSchedule = ErrorCode.DATA_NOT_FOUND.throwIfNull(bussScheduleRepository.findByBussScheduleId(bussScheduleId));

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

        bussSchedule.setFieldByName(data);
        bussSchedule.preUpdateAction();
        bussScheduleRepository.save(bussSchedule);
        return bussSchedule;
    }
        
    public List<BussSchedule> updateMultiBussSchedule(List<Map<String, String>> multiData) {
        List<BussSchedule> bussScheduleParseList = new ArrayList<>();
        multiData.forEach(data -> {
            bussScheduleParseList.add(this.updateBussSchedule(data));
        });
        bussScheduleRepository.flush();
        return bussScheduleParseList;
    }
        
    public BussSchedule insertBussSchedule(Map<String, String> data) {
        BussSchedule bussSchedule = new BussSchedule();
        bussSchedule.setFieldByName(data);
        
        if (bussSchedule.getBussId() == null || bussSchedule.getBussId() <= 0) {
            if (XeBooleanUtils.isTrue(data.get("newBussIfNull"))) {
                Map<String, String> bussData = new HashMap<>();
                data.entrySet().stream()
                        .filter(entry -> entry.getKey().startsWith("buss."))
                        .forEach(entry -> bussData.put(entry.getKey().substring("buss.".length()), entry.getValue()));
                bussSchedule.setBuss(this.insertBuss(bussData));
            }
        } else {
            bussSchedule.setBuss(this.bussRepository.findByBussId(bussSchedule.getBussId()));
        }
        bussSchedule.preSaveAction();
        bussSchedule = bussScheduleRepository.save(bussSchedule);
        return bussSchedule;
    }
    public List<BussSchedule> insertMultiBussSchedule(List<Map<String, String>> data) {
        List<BussSchedule> result = new ArrayList<>();
        data.forEach(bussScheduleData -> result.add(this.insertBussSchedule(bussScheduleData)));
        return result;
    }
    public void deleteBussScheduleByBussScheduleIds(Long[] bussScheduleIds) {
        List<BussSchedule> deletingList = bussScheduleRepository.findByBussScheduleIdIn(Arrays.asList(bussScheduleIds));
        deletingList.forEach(XeEntity::preRemoveAction);
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
        String orderExpression = "";
        return XeReflectionUtils.invokeMethodByName(bussScheduleRepository, findMethodName + orderExpression, findMethodParams);
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
        bussEmployee.preUpdateAction();
        bussEmployeeRepository.save(bussEmployee);
        return bussEmployee;
    }
        
    public List<BussEmployee> updateMultiBussEmployee(List<Map<String, String>> multiData) {
        List<BussEmployee> bussEmployeeParseList = new ArrayList<>();
        multiData.forEach(data -> {
            bussEmployeeParseList.add(this.updateBussEmployee(data));
        });
        bussEmployeeRepository.flush();
        return bussEmployeeParseList;
    }
        
    public BussEmployee insertBussEmployee(Map<String, String> data) {
        BussEmployee bussEmployee = new BussEmployee();
        bussEmployee.setFieldByName(data);
        
        if (bussEmployee.getBussId() == null || bussEmployee.getBussId() <= 0) {
            if (XeBooleanUtils.isTrue(data.get("newBussIfNull"))) {
                Map<String, String> bussData = new HashMap<>();
                data.entrySet().stream()
                        .filter(entry -> entry.getKey().startsWith("buss."))
                        .forEach(entry -> bussData.put(entry.getKey().substring("buss.".length()), entry.getValue()));
                bussEmployee.setBuss(this.insertBuss(bussData));
            }
        } else {
            bussEmployee.setBuss(this.bussRepository.findByBussId(bussEmployee.getBussId()));
        }
        if (bussEmployee.getEmployeeId() == null || bussEmployee.getEmployeeId() <= 0) {
            if (XeBooleanUtils.isTrue(data.get("newEmployeeIfNull"))) {
                Map<String, String> employeeData = new HashMap<>();
                data.entrySet().stream()
                        .filter(entry -> entry.getKey().startsWith("employee."))
                        .forEach(entry -> employeeData.put(entry.getKey().substring("employee.".length()), entry.getValue()));
                bussEmployee.setEmployee(this.insertEmployee(employeeData));
            }
        } else {
            bussEmployee.setEmployee(this.employeeRepository.findByEmployeeId(bussEmployee.getEmployeeId()));
        }
        bussEmployee.preSaveAction();
        bussEmployee = bussEmployeeRepository.save(bussEmployee);
        return bussEmployee;
    }
    public List<BussEmployee> insertMultiBussEmployee(List<Map<String, String>> data) {
        List<BussEmployee> result = new ArrayList<>();
        data.forEach(bussEmployeeData -> result.add(this.insertBussEmployee(bussEmployeeData)));
        return result;
    }
    public void deleteBussEmployeeByBussEmployeeIds(Long[] bussEmployeeIds) {
        List<BussEmployee> deletingList = bussEmployeeRepository.findByBussEmployeeIdIn(Arrays.asList(bussEmployeeIds));
        deletingList.forEach(XeEntity::preRemoveAction);
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
        String orderExpression = "";
        return XeReflectionUtils.invokeMethodByName(bussEmployeeRepository, findMethodName + orderExpression, findMethodParams);
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
        buss.preUpdateAction();
        bussRepository.save(buss);
        return buss;
    }
        
    public List<Buss> updateMultiBuss(List<Map<String, String>> multiData) {
        List<Buss> bussParseList = new ArrayList<>();
        multiData.forEach(data -> {
            bussParseList.add(this.updateBuss(data));
        });
        bussRepository.flush();
        return bussParseList;
    }
        
    public Buss insertBuss(Map<String, String> data) {
        Buss buss = new Buss();
        buss.setFieldByName(data);
        
        if (buss.getBussTypeId() == null || buss.getBussTypeId() <= 0) {
            if (XeBooleanUtils.isTrue(data.get("newBussTypeIfNull"))) {
                Map<String, String> bussTypeData = new HashMap<>();
                data.entrySet().stream()
                        .filter(entry -> entry.getKey().startsWith("bussType."))
                        .forEach(entry -> bussTypeData.put(entry.getKey().substring("bussType.".length()), entry.getValue()));
                buss.setBussType(this.insertBussType(bussTypeData));
            }
        } else {
            buss.setBussType(this.bussTypeRepository.findByBussTypeId(buss.getBussTypeId()));
        }
        if (buss.getCompanyId() == null || buss.getCompanyId() <= 0) {
            if (XeBooleanUtils.isTrue(data.get("newCompanyIfNull"))) {
                Map<String, String> companyData = new HashMap<>();
                data.entrySet().stream()
                        .filter(entry -> entry.getKey().startsWith("company."))
                        .forEach(entry -> companyData.put(entry.getKey().substring("company.".length()), entry.getValue()));
                buss.setCompany(this.insertCompany(companyData));
            }
        } else {
            buss.setCompany(this.companyRepository.findByCompanyId(buss.getCompanyId()));
        }
        buss.preSaveAction();
        buss = bussRepository.save(buss);
        return buss;
    }
    public List<Buss> insertMultiBuss(List<Map<String, String>> data) {
        List<Buss> result = new ArrayList<>();
        data.forEach(bussData -> result.add(this.insertBuss(bussData)));
        return result;
    }
    public void deleteBussByBussIds(Long[] bussIds) {
        List<Buss> deletingList = bussRepository.findByBussIdIn(Arrays.asList(bussIds));
        deletingList.forEach(XeEntity::preRemoveAction);
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
        String orderExpression = "";
        return XeReflectionUtils.invokeMethodByName(bussRepository, findMethodName + orderExpression, findMethodParams);
    }
//=================== END OF Buss ======================
    public Location updateLocation(Map<String, String> data) {
        Long locationId = Long.parseLong(data.get("locationId"));
        Location location = ErrorCode.DATA_NOT_FOUND.throwIfNull(locationRepository.findByLocationId(locationId));


        location.setFieldByName(data);
        location.preUpdateAction();
        locationRepository.save(location);
        return location;
    }
        
    public List<Location> updateMultiLocation(List<Map<String, String>> multiData) {
        List<Location> locationParseList = new ArrayList<>();
        multiData.forEach(data -> {
            locationParseList.add(this.updateLocation(data));
        });
        locationRepository.flush();
        return locationParseList;
    }
        
    public Location insertLocation(Map<String, String> data) {
        Location location = new Location();
        location.setFieldByName(data);
        
        location.preSaveAction();
        location = locationRepository.save(location);
        return location;
    }
    public List<Location> insertMultiLocation(List<Map<String, String>> data) {
        List<Location> result = new ArrayList<>();
        data.forEach(locationData -> result.add(this.insertLocation(locationData)));
        return result;
    }
    public void deleteLocationByLocationIds(Long[] locationIds) {
        List<Location> deletingList = locationRepository.findByLocationIdIn(Arrays.asList(locationIds));
        deletingList.forEach(XeEntity::preRemoveAction);
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
        String orderExpression = "";
        return XeReflectionUtils.invokeMethodByName(locationRepository, findMethodName + orderExpression, findMethodParams);
    }
//=================== END OF Location ======================
    public TripUser updateTripUser(Map<String, String> data) {
        Long tripUserId = Long.parseLong(data.get("tripUserId"));
        TripUser tripUser = ErrorCode.DATA_NOT_FOUND.throwIfNull(tripUserRepository.findByTripUserId(tripUserId));

        Map<String, String> tripData = new HashMap<>();
        data.forEach((fieldName, fieldValue) -> {
            if (fieldName.startsWith("trip.")) {
                tripData.put(fieldName.substring("trip.".length()), fieldValue);
            }
        });
        if (!tripData.isEmpty()) {
            tripData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateTrip(tripData);
        }

        tripUser.setFieldByName(data);
        tripUser.preUpdateAction();
        tripUserRepository.save(tripUser);
        return tripUser;
    }
        
    public List<TripUser> updateMultiTripUser(List<Map<String, String>> multiData) {
        List<TripUser> tripUserParseList = new ArrayList<>();
        multiData.forEach(data -> {
            tripUserParseList.add(this.updateTripUser(data));
        });
        tripUserRepository.flush();
        return tripUserParseList;
    }
        
    public TripUser insertTripUser(Map<String, String> data) {
        TripUser tripUser = new TripUser();
        tripUser.setFieldByName(data);
        
        if (tripUser.getTripId() == null || tripUser.getTripId() <= 0) {
            if (XeBooleanUtils.isTrue(data.get("newTripIfNull"))) {
                Map<String, String> tripData = new HashMap<>();
                data.entrySet().stream()
                        .filter(entry -> entry.getKey().startsWith("trip."))
                        .forEach(entry -> tripData.put(entry.getKey().substring("trip.".length()), entry.getValue()));
                tripUser.setTrip(this.insertTrip(tripData));
            }
        } else {
            tripUser.setTrip(this.tripRepository.findByTripId(tripUser.getTripId()));
        }
        tripUser.preSaveAction();
        tripUser = tripUserRepository.save(tripUser);
        return tripUser;
    }
    public List<TripUser> insertMultiTripUser(List<Map<String, String>> data) {
        List<TripUser> result = new ArrayList<>();
        data.forEach(tripUserData -> result.add(this.insertTripUser(tripUserData)));
        return result;
    }
    public void deleteTripUserByTripUserIds(Long[] tripUserIds) {
        List<TripUser> deletingList = tripUserRepository.findByTripUserIdIn(Arrays.asList(tripUserIds));
        deletingList.forEach(XeEntity::preRemoveAction);
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
        String orderExpression = "";
        return XeReflectionUtils.invokeMethodByName(tripUserRepository, findMethodName + orderExpression, findMethodParams);
    }
//=================== END OF TripUser ======================
    public SeatGroup updateSeatGroup(Map<String, String> data) {
        Long seatGroupId = Long.parseLong(data.get("seatGroupId"));
        SeatGroup seatGroup = ErrorCode.DATA_NOT_FOUND.throwIfNull(seatGroupRepository.findBySeatGroupId(seatGroupId));

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

        seatGroup.setFieldByName(data);
        seatGroup.preUpdateAction();
        seatGroupRepository.save(seatGroup);
        return seatGroup;
    }
        
    public List<SeatGroup> updateMultiSeatGroup(List<Map<String, String>> multiData) {
        List<SeatGroup> seatGroupParseList = new ArrayList<>();
        multiData.forEach(data -> {
            seatGroupParseList.add(this.updateSeatGroup(data));
        });
        seatGroupRepository.flush();
        return seatGroupParseList;
    }
        
    public SeatGroup insertSeatGroup(Map<String, String> data) {
        SeatGroup seatGroup = new SeatGroup();
        seatGroup.setFieldByName(data);
        
        if (seatGroup.getBussTypeId() == null || seatGroup.getBussTypeId() <= 0) {
            if (XeBooleanUtils.isTrue(data.get("newBussTypeIfNull"))) {
                Map<String, String> bussTypeData = new HashMap<>();
                data.entrySet().stream()
                        .filter(entry -> entry.getKey().startsWith("bussType."))
                        .forEach(entry -> bussTypeData.put(entry.getKey().substring("bussType.".length()), entry.getValue()));
                seatGroup.setBussType(this.insertBussType(bussTypeData));
            }
        } else {
            seatGroup.setBussType(this.bussTypeRepository.findByBussTypeId(seatGroup.getBussTypeId()));
        }
        seatGroup.preSaveAction();
        seatGroup = seatGroupRepository.save(seatGroup);
        return seatGroup;
    }
    public List<SeatGroup> insertMultiSeatGroup(List<Map<String, String>> data) {
        List<SeatGroup> result = new ArrayList<>();
        data.forEach(seatGroupData -> result.add(this.insertSeatGroup(seatGroupData)));
        return result;
    }
    public void deleteSeatGroupBySeatGroupIds(Long[] seatGroupIds) {
        List<SeatGroup> deletingList = seatGroupRepository.findBySeatGroupIdIn(Arrays.asList(seatGroupIds));
        deletingList.forEach(XeEntity::preRemoveAction);
        seatGroupRepository.deleteAllBySeatGroupIdIn(Arrays.asList(seatGroupIds));
    }
    public void deleteSeatGroup(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return;
        }
        if (data.size() == 1 && data.containsKey("SeatGroupId")) {
            seatGroupRepository.deleteBySeatGroupId(data.get("SeatGroupId"));
            return;
        }
        String deleteMethodName = String.format("deleteBy%s", String.join("And", data.keySet()));
        Object[] deleteMethodParams = data.values().toArray(new Long[0]);
        XeReflectionUtils.invokeMethodByName(seatGroupRepository, deleteMethodName, deleteMethodParams);
    }
    public List<SeatGroup> findSeatGroup(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return seatGroupRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("SeatGroupId")) {
            SeatGroup seatGroup = seatGroupRepository.findBySeatGroupId(data.get("SeatGroupId"));
            if(seatGroup == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(seatGroup);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        String orderExpression = "";
        return XeReflectionUtils.invokeMethodByName(seatGroupRepository, findMethodName + orderExpression, findMethodParams);
    }
//=================== END OF SeatGroup ======================
    public BussType updateBussType(Map<String, String> data) {
        Long bussTypeId = Long.parseLong(data.get("bussTypeId"));
        BussType bussType = ErrorCode.DATA_NOT_FOUND.throwIfNull(bussTypeRepository.findByBussTypeId(bussTypeId));


        bussType.setFieldByName(data);
        bussType.preUpdateAction();
        bussTypeRepository.save(bussType);
        return bussType;
    }
        
    public List<BussType> updateMultiBussType(List<Map<String, String>> multiData) {
        List<BussType> bussTypeParseList = new ArrayList<>();
        multiData.forEach(data -> {
            bussTypeParseList.add(this.updateBussType(data));
        });
        bussTypeRepository.flush();
        return bussTypeParseList;
    }
        
    public BussType insertBussType(Map<String, String> data) {
        BussType bussType = new BussType();
        bussType.setFieldByName(data);
        
        bussType.preSaveAction();
        bussType = bussTypeRepository.save(bussType);
        return bussType;
    }
    public List<BussType> insertMultiBussType(List<Map<String, String>> data) {
        List<BussType> result = new ArrayList<>();
        data.forEach(bussTypeData -> result.add(this.insertBussType(bussTypeData)));
        return result;
    }
    public void deleteBussTypeByBussTypeIds(Long[] bussTypeIds) {
        List<BussType> deletingList = bussTypeRepository.findByBussTypeIdIn(Arrays.asList(bussTypeIds));
        deletingList.forEach(XeEntity::preRemoveAction);
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
        String orderExpression = "";
        return XeReflectionUtils.invokeMethodByName(bussTypeRepository, findMethodName + orderExpression, findMethodParams);
    }
    public BussType updateBussTypeProfileImage(BussType_MAPPED.Pk pk, MultipartFile profileImage) throws IOException {
        BussType bussType = DATA_NOT_FOUND.throwIfNull(bussTypeRepository.getById(pk));
        bussType.saveProfileImage(profileImage);
        return bussType;
    }
//=================== END OF BussType ======================
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
        trip.preUpdateAction();
        tripRepository.save(trip);
        return trip;
    }
        
    public List<Trip> updateMultiTrip(List<Map<String, String>> multiData) {
        List<Trip> tripParseList = new ArrayList<>();
        multiData.forEach(data -> {
            tripParseList.add(this.updateTrip(data));
        });
        tripRepository.flush();
        return tripParseList;
    }
        
    public Trip insertTrip(Map<String, String> data) {
        Trip trip = new Trip();
        trip.setFieldByName(data);
        
        if (trip.getBussScheduleId() == null || trip.getBussScheduleId() <= 0) {
            if (XeBooleanUtils.isTrue(data.get("newBussScheduleIfNull"))) {
                Map<String, String> bussScheduleData = new HashMap<>();
                data.entrySet().stream()
                        .filter(entry -> entry.getKey().startsWith("bussSchedule."))
                        .forEach(entry -> bussScheduleData.put(entry.getKey().substring("bussSchedule.".length()), entry.getValue()));
                trip.setBussSchedule(this.insertBussSchedule(bussScheduleData));
            }
        } else {
            trip.setBussSchedule(this.bussScheduleRepository.findByBussScheduleId(trip.getBussScheduleId()));
        }
        trip.preSaveAction();
        trip = tripRepository.save(trip);
        return trip;
    }
    public List<Trip> insertMultiTrip(List<Map<String, String>> data) {
        List<Trip> result = new ArrayList<>();
        data.forEach(tripData -> result.add(this.insertTrip(tripData)));
        return result;
    }
    public void deleteTripByTripIds(Long[] tripIds) {
        List<Trip> deletingList = tripRepository.findByTripIdIn(Arrays.asList(tripIds));
        deletingList.forEach(XeEntity::preRemoveAction);
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
        String orderExpression = "";
        return XeReflectionUtils.invokeMethodByName(tripRepository, findMethodName + orderExpression, findMethodParams);
    }
//=================== END OF Trip ======================
    public Path updatePath(Map<String, String> data) {
        Long pathId = Long.parseLong(data.get("pathId"));
        Path path = ErrorCode.DATA_NOT_FOUND.throwIfNull(pathRepository.findByPathId(pathId));

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

        path.setFieldByName(data);
        path.preUpdateAction();
        pathRepository.save(path);
        return path;
    }
        
    public List<Path> updateMultiPath(List<Map<String, String>> multiData) {
        List<Path> pathParseList = new ArrayList<>();
        multiData.forEach(data -> {
            pathParseList.add(this.updatePath(data));
        });
        pathRepository.flush();
        return pathParseList;
    }
        
    public Path insertPath(Map<String, String> data) {
        Path path = new Path();
        path.setFieldByName(data);
        
        if (path.getCompanyId() == null || path.getCompanyId() <= 0) {
            if (XeBooleanUtils.isTrue(data.get("newCompanyIfNull"))) {
                Map<String, String> companyData = new HashMap<>();
                data.entrySet().stream()
                        .filter(entry -> entry.getKey().startsWith("company."))
                        .forEach(entry -> companyData.put(entry.getKey().substring("company.".length()), entry.getValue()));
                path.setCompany(this.insertCompany(companyData));
            }
        } else {
            path.setCompany(this.companyRepository.findByCompanyId(path.getCompanyId()));
        }
        path.preSaveAction();
        path = pathRepository.save(path);
        return path;
    }
    public List<Path> insertMultiPath(List<Map<String, String>> data) {
        List<Path> result = new ArrayList<>();
        data.forEach(pathData -> result.add(this.insertPath(pathData)));
        return result;
    }
    public void deletePathByPathIds(Long[] pathIds) {
        List<Path> deletingList = pathRepository.findByPathIdIn(Arrays.asList(pathIds));
        deletingList.forEach(XeEntity::preRemoveAction);
        pathRepository.deleteAllByPathIdIn(Arrays.asList(pathIds));
    }
    public void deletePath(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return;
        }
        if (data.size() == 1 && data.containsKey("PathId")) {
            pathRepository.deleteByPathId(data.get("PathId"));
            return;
        }
        String deleteMethodName = String.format("deleteBy%s", String.join("And", data.keySet()));
        Object[] deleteMethodParams = data.values().toArray(new Long[0]);
        XeReflectionUtils.invokeMethodByName(pathRepository, deleteMethodName, deleteMethodParams);
    }
    public List<Path> findPath(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return pathRepository.findAll();
        }
        if (data.size() == 1 && data.containsKey("PathId")) {
            Path path = pathRepository.findByPathId(data.get("PathId"));
            if(path == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(path);
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        String orderExpression = "";
        return XeReflectionUtils.invokeMethodByName(pathRepository, findMethodName + orderExpression, findMethodParams);
    }
//=================== END OF Path ======================
    public BussSchedulePoint updateBussSchedulePoint(Map<String, String> data) {
        Long bussSchedulePointId = Long.parseLong(data.get("bussSchedulePointId"));
        BussSchedulePoint bussSchedulePoint = ErrorCode.DATA_NOT_FOUND.throwIfNull(bussSchedulePointRepository.findByBussSchedulePointId(bussSchedulePointId));

        Map<String, String> bussScheduleData = new HashMap<>();
        Map<String, String> pathPointData = new HashMap<>();
        data.forEach((fieldName, fieldValue) -> {
            if (fieldName.startsWith("bussSchedule.")) {
                bussScheduleData.put(fieldName.substring("bussSchedule.".length()), fieldValue);
            }
            if (fieldName.startsWith("pathPoint.")) {
                pathPointData.put(fieldName.substring("pathPoint.".length()), fieldValue);
            }
        });
        if (!bussScheduleData.isEmpty()) {
            bussScheduleData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updateBussSchedule(bussScheduleData);
        }
        if (!pathPointData.isEmpty()) {
            pathPointData.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.updatePathPoint(pathPointData);
        }

        bussSchedulePoint.setFieldByName(data);
        bussSchedulePoint.preUpdateAction();
        bussSchedulePointRepository.save(bussSchedulePoint);
        return bussSchedulePoint;
    }
        
    public List<BussSchedulePoint> updateMultiBussSchedulePoint(List<Map<String, String>> multiData) {
        List<BussSchedulePoint> bussSchedulePointParseList = new ArrayList<>();
        multiData.forEach(data -> {
            bussSchedulePointParseList.add(this.updateBussSchedulePoint(data));
        });
        bussSchedulePointRepository.flush();
        return bussSchedulePointParseList;
    }
        
    public BussSchedulePoint insertBussSchedulePoint(Map<String, String> data) {
        BussSchedulePoint bussSchedulePoint = new BussSchedulePoint();
        bussSchedulePoint.setFieldByName(data);
        
        if (bussSchedulePoint.getBussScheduleId() == null || bussSchedulePoint.getBussScheduleId() <= 0) {
            if (XeBooleanUtils.isTrue(data.get("newBussScheduleIfNull"))) {
                Map<String, String> bussScheduleData = new HashMap<>();
                data.entrySet().stream()
                        .filter(entry -> entry.getKey().startsWith("bussSchedule."))
                        .forEach(entry -> bussScheduleData.put(entry.getKey().substring("bussSchedule.".length()), entry.getValue()));
                bussSchedulePoint.setBussSchedule(this.insertBussSchedule(bussScheduleData));
            }
        } else {
            bussSchedulePoint.setBussSchedule(this.bussScheduleRepository.findByBussScheduleId(bussSchedulePoint.getBussScheduleId()));
        }
        if (bussSchedulePoint.getPathPointId() == null || bussSchedulePoint.getPathPointId() <= 0) {
            if (XeBooleanUtils.isTrue(data.get("newPathPointIfNull"))) {
                Map<String, String> pathPointData = new HashMap<>();
                data.entrySet().stream()
                        .filter(entry -> entry.getKey().startsWith("pathPoint."))
                        .forEach(entry -> pathPointData.put(entry.getKey().substring("pathPoint.".length()), entry.getValue()));
                bussSchedulePoint.setPathPoint(this.insertPathPoint(pathPointData));
            }
        } else {
            bussSchedulePoint.setPathPoint(this.pathPointRepository.findByPathPointId(bussSchedulePoint.getPathPointId()));
        }
        bussSchedulePoint.preSaveAction();
        bussSchedulePoint = bussSchedulePointRepository.save(bussSchedulePoint);
        return bussSchedulePoint;
    }
    public List<BussSchedulePoint> insertMultiBussSchedulePoint(List<Map<String, String>> data) {
        List<BussSchedulePoint> result = new ArrayList<>();
        data.forEach(bussSchedulePointData -> result.add(this.insertBussSchedulePoint(bussSchedulePointData)));
        return result;
    }
    public void deleteBussSchedulePointByBussSchedulePointIds(Long[] bussSchedulePointIds) {
        List<BussSchedulePoint> deletingList = bussSchedulePointRepository.findByBussSchedulePointIdIn(Arrays.asList(bussSchedulePointIds));
        deletingList.forEach(XeEntity::preRemoveAction);
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
        String orderExpression = "";
        return XeReflectionUtils.invokeMethodByName(bussSchedulePointRepository, findMethodName + orderExpression, findMethodParams);
    }
//=================== END OF BussSchedulePoint ======================

}
