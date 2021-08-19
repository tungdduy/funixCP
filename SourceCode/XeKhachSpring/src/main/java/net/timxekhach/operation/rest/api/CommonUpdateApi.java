package net.timxekhach.operation.rest.api;

import lombok.RequiredArgsConstructor;
import net.timxekhach.operation.data.entity.*;
import net.timxekhach.operation.data.mapped.*;

import net.timxekhach.operation.rest.service.CommonUpdateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static net.timxekhach.utility.XeResponseUtils.success;

@RestController
@RequiredArgsConstructor
@RequestMapping(path={"/common-update"})
public class CommonUpdateApi {

    private final CommonUpdateService commonUpdateService;
    
        @PostMapping("/User")
    public ResponseEntity<User> updateUser (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateUser(data));
    }
    @PostMapping("/MultiUser")
    public ResponseEntity<List<User>> updateMultiUser (@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.updateMultiUser(data));
    }
    @PutMapping("/User")
    public ResponseEntity<User> insertUser(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insertUser(data));
    }
    @PutMapping("/MultiUser")
    public ResponseEntity<List<User>> insertMultiUser(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMultiUser(data));
    }
    @PostMapping("/User-profile-image")
    public ResponseEntity<User> updateUserProfileImage(
            @RequestParam("userId") Long userId,
            @RequestParam("profileImage") MultipartFile profileImage) throws IOException {
        User_MAPPED.Pk pk = new User_MAPPED.Pk(userId);
        return success(commonUpdateService.updateUserProfileImage(pk, profileImage));
    }
    @DeleteMapping("/User/{userIds}")
    public ResponseEntity<Void> deleteUserByUserIds(@PathVariable Long[] userIds) {
        commonUpdateService.deleteUserByUserIds(userIds);
        return success();
    }
    @GetMapping("/User/{userId}")
    public ResponseEntity<List<User>> findUser(
        @PathVariable Long userId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (userId != null && userId > 0) data.put("UserId", userId);
        return success(this.commonUpdateService.findUser(data));
    }
    //_____________ END OF User __________________________            
    @PostMapping("/TripUserSeat")
    public ResponseEntity<TripUserSeat> updateTripUserSeat (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateTripUserSeat(data));
    }
    @PostMapping("/MultiTripUserSeat")
    public ResponseEntity<List<TripUserSeat>> updateMultiTripUserSeat (@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.updateMultiTripUserSeat(data));
    }
    @PutMapping("/TripUserSeat")
    public ResponseEntity<TripUserSeat> insertTripUserSeat(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insertTripUserSeat(data));
    }
    @PutMapping("/MultiTripUserSeat")
    public ResponseEntity<List<TripUserSeat>> insertMultiTripUserSeat(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMultiTripUserSeat(data));
    }
    @DeleteMapping("/TripUserSeat/{tripUserSeatIds}")
    public ResponseEntity<Void> deleteTripUserSeatByTripUserSeatIds(@PathVariable Long[] tripUserSeatIds) {
        commonUpdateService.deleteTripUserSeatByTripUserSeatIds(tripUserSeatIds);
        return success();
    }
    @DeleteMapping("/TripUserSeat/{tripUserSeatId}/{tripUserId}")
    public ResponseEntity<Void> deleteTripUserSeat(
        @PathVariable Long tripUserSeatId
        , @PathVariable Long tripUserId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (tripUserSeatId != null && tripUserSeatId > 0) data.put("TripUserSeatId", tripUserSeatId);
        if (tripUserId != null && tripUserId > 0) data.put("tripUserId", tripUserId);
        this.commonUpdateService.deleteTripUserSeat(data);
        return success();
    }
    @GetMapping("/TripUserSeat/{tripUserSeatId}/{tripUserId}")
    public ResponseEntity<List<TripUserSeat>> findTripUserSeat(
        @PathVariable Long tripUserSeatId
        , @PathVariable Long tripUserId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (tripUserSeatId != null && tripUserSeatId > 0) data.put("TripUserSeatId", tripUserSeatId);
        if (tripUserId != null && tripUserId > 0) data.put("TripUserId", tripUserId);
        return success(this.commonUpdateService.findTripUserSeat(data));
    }
    //_____________ END OF TripUserSeat __________________________            
    @PostMapping("/Employee")
    public ResponseEntity<Employee> updateEmployee (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateEmployee(data));
    }
    @PostMapping("/MultiEmployee")
    public ResponseEntity<List<Employee>> updateMultiEmployee (@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.updateMultiEmployee(data));
    }
    @PutMapping("/Employee")
    public ResponseEntity<Employee> insertEmployee(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insertEmployee(data));
    }
    @PutMapping("/MultiEmployee")
    public ResponseEntity<List<Employee>> insertMultiEmployee(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMultiEmployee(data));
    }
    @DeleteMapping("/Employee/{employeeIds}")
    public ResponseEntity<Void> deleteEmployeeByEmployeeIds(@PathVariable Long[] employeeIds) {
        commonUpdateService.deleteEmployeeByEmployeeIds(employeeIds);
        return success();
    }
    @DeleteMapping("/Employee/{employeeId}/{companyId}/{userId}")
    public ResponseEntity<Void> deleteEmployee(
        @PathVariable Long employeeId
        , @PathVariable Long companyId
        , @PathVariable Long userId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (employeeId != null && employeeId > 0) data.put("EmployeeId", employeeId);
        if (companyId != null && companyId > 0) data.put("companyId", companyId);
        if (userId != null && userId > 0) data.put("userId", userId);
        this.commonUpdateService.deleteEmployee(data);
        return success();
    }
    @GetMapping("/Employee/{employeeId}/{companyId}/{userId}")
    public ResponseEntity<List<Employee>> findEmployee(
        @PathVariable Long employeeId
        , @PathVariable Long companyId
        , @PathVariable Long userId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (employeeId != null && employeeId > 0) data.put("EmployeeId", employeeId);
        if (companyId != null && companyId > 0) data.put("CompanyId", companyId);
        if (userId != null && userId > 0) data.put("UserId", userId);
        return success(this.commonUpdateService.findEmployee(data));
    }
    //_____________ END OF Employee __________________________            
    @PostMapping("/Company")
    public ResponseEntity<Company> updateCompany (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateCompany(data));
    }
    @PostMapping("/MultiCompany")
    public ResponseEntity<List<Company>> updateMultiCompany (@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.updateMultiCompany(data));
    }
    @PutMapping("/Company")
    public ResponseEntity<Company> insertCompany(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insertCompany(data));
    }
    @PutMapping("/MultiCompany")
    public ResponseEntity<List<Company>> insertMultiCompany(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMultiCompany(data));
    }
    @PostMapping("/Company-profile-image")
    public ResponseEntity<Company> updateCompanyProfileImage(
            @RequestParam("companyId") Long companyId,
            @RequestParam("profileImage") MultipartFile profileImage) throws IOException {
        Company_MAPPED.Pk pk = new Company_MAPPED.Pk(companyId);
        return success(commonUpdateService.updateCompanyProfileImage(pk, profileImage));
    }
    @DeleteMapping("/Company/{companyIds}")
    public ResponseEntity<Void> deleteCompanyByCompanyIds(@PathVariable Long[] companyIds) {
        commonUpdateService.deleteCompanyByCompanyIds(companyIds);
        return success();
    }
    @GetMapping("/Company/{companyId}")
    public ResponseEntity<List<Company>> findCompany(
        @PathVariable Long companyId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (companyId != null && companyId > 0) data.put("CompanyId", companyId);
        return success(this.commonUpdateService.findCompany(data));
    }
    //_____________ END OF Company __________________________            
    @PostMapping("/BussSchedule")
    public ResponseEntity<BussSchedule> updateBussSchedule (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateBussSchedule(data));
    }
    @PostMapping("/MultiBussSchedule")
    public ResponseEntity<List<BussSchedule>> updateMultiBussSchedule (@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.updateMultiBussSchedule(data));
    }
    @PutMapping("/BussSchedule")
    public ResponseEntity<BussSchedule> insertBussSchedule(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insertBussSchedule(data));
    }
    @PutMapping("/MultiBussSchedule")
    public ResponseEntity<List<BussSchedule>> insertMultiBussSchedule(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMultiBussSchedule(data));
    }
    @DeleteMapping("/BussSchedule/{bussScheduleIds}")
    public ResponseEntity<Void> deleteBussScheduleByBussScheduleIds(@PathVariable Long[] bussScheduleIds) {
        commonUpdateService.deleteBussScheduleByBussScheduleIds(bussScheduleIds);
        return success();
    }
    @DeleteMapping("/BussSchedule/{bussScheduleId}/{bussId}")
    public ResponseEntity<Void> deleteBussSchedule(
        @PathVariable Long bussScheduleId
        , @PathVariable Long bussId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (bussScheduleId != null && bussScheduleId > 0) data.put("BussScheduleId", bussScheduleId);
        if (bussId != null && bussId > 0) data.put("bussId", bussId);
        this.commonUpdateService.deleteBussSchedule(data);
        return success();
    }
    @GetMapping("/BussSchedule/{bussScheduleId}/{bussId}")
    public ResponseEntity<List<BussSchedule>> findBussSchedule(
        @PathVariable Long bussScheduleId
        , @PathVariable Long bussId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (bussScheduleId != null && bussScheduleId > 0) data.put("BussScheduleId", bussScheduleId);
        if (bussId != null && bussId > 0) data.put("BussId", bussId);
        return success(this.commonUpdateService.findBussSchedule(data));
    }
    //_____________ END OF BussSchedule __________________________            
    @PostMapping("/BussEmployee")
    public ResponseEntity<BussEmployee> updateBussEmployee (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateBussEmployee(data));
    }
    @PostMapping("/MultiBussEmployee")
    public ResponseEntity<List<BussEmployee>> updateMultiBussEmployee (@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.updateMultiBussEmployee(data));
    }
    @PutMapping("/BussEmployee")
    public ResponseEntity<BussEmployee> insertBussEmployee(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insertBussEmployee(data));
    }
    @PutMapping("/MultiBussEmployee")
    public ResponseEntity<List<BussEmployee>> insertMultiBussEmployee(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMultiBussEmployee(data));
    }
    @DeleteMapping("/BussEmployee/{bussEmployeeIds}")
    public ResponseEntity<Void> deleteBussEmployeeByBussEmployeeIds(@PathVariable Long[] bussEmployeeIds) {
        commonUpdateService.deleteBussEmployeeByBussEmployeeIds(bussEmployeeIds);
        return success();
    }
    @DeleteMapping("/BussEmployee/{bussEmployeeId}/{bussId}/{employeeId}")
    public ResponseEntity<Void> deleteBussEmployee(
        @PathVariable Long bussEmployeeId
        , @PathVariable Long bussId
        , @PathVariable Long employeeId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (bussEmployeeId != null && bussEmployeeId > 0) data.put("BussEmployeeId", bussEmployeeId);
        if (bussId != null && bussId > 0) data.put("bussId", bussId);
        if (employeeId != null && employeeId > 0) data.put("employeeId", employeeId);
        this.commonUpdateService.deleteBussEmployee(data);
        return success();
    }
    @GetMapping("/BussEmployee/{bussEmployeeId}/{bussId}/{employeeId}")
    public ResponseEntity<List<BussEmployee>> findBussEmployee(
        @PathVariable Long bussEmployeeId
        , @PathVariable Long bussId
        , @PathVariable Long employeeId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (bussEmployeeId != null && bussEmployeeId > 0) data.put("BussEmployeeId", bussEmployeeId);
        if (bussId != null && bussId > 0) data.put("BussId", bussId);
        if (employeeId != null && employeeId > 0) data.put("EmployeeId", employeeId);
        return success(this.commonUpdateService.findBussEmployee(data));
    }
    //_____________ END OF BussEmployee __________________________            
    @PostMapping("/Buss")
    public ResponseEntity<Buss> updateBuss (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateBuss(data));
    }
    @PostMapping("/MultiBuss")
    public ResponseEntity<List<Buss>> updateMultiBuss (@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.updateMultiBuss(data));
    }
    @PutMapping("/Buss")
    public ResponseEntity<Buss> insertBuss(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insertBuss(data));
    }
    @PutMapping("/MultiBuss")
    public ResponseEntity<List<Buss>> insertMultiBuss(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMultiBuss(data));
    }
    @DeleteMapping("/Buss/{bussIds}")
    public ResponseEntity<Void> deleteBussByBussIds(@PathVariable Long[] bussIds) {
        commonUpdateService.deleteBussByBussIds(bussIds);
        return success();
    }
    @DeleteMapping("/Buss/{bussId}/{bussTypeId}/{companyId}")
    public ResponseEntity<Void> deleteBuss(
        @PathVariable Long bussId
        , @PathVariable Long bussTypeId
        , @PathVariable Long companyId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (bussId != null && bussId > 0) data.put("BussId", bussId);
        if (bussTypeId != null && bussTypeId > 0) data.put("bussTypeId", bussTypeId);
        if (companyId != null && companyId > 0) data.put("companyId", companyId);
        this.commonUpdateService.deleteBuss(data);
        return success();
    }
    @GetMapping("/Buss/{bussId}/{bussTypeId}/{companyId}")
    public ResponseEntity<List<Buss>> findBuss(
        @PathVariable Long bussId
        , @PathVariable Long bussTypeId
        , @PathVariable Long companyId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (bussId != null && bussId > 0) data.put("BussId", bussId);
        if (bussTypeId != null && bussTypeId > 0) data.put("BussTypeId", bussTypeId);
        if (companyId != null && companyId > 0) data.put("CompanyId", companyId);
        return success(this.commonUpdateService.findBuss(data));
    }
    //_____________ END OF Buss __________________________            
    @PostMapping("/Location")
    public ResponseEntity<Location> updateLocation (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateLocation(data));
    }
    @PostMapping("/MultiLocation")
    public ResponseEntity<List<Location>> updateMultiLocation (@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.updateMultiLocation(data));
    }
    @PutMapping("/Location")
    public ResponseEntity<Location> insertLocation(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insertLocation(data));
    }
    @PutMapping("/MultiLocation")
    public ResponseEntity<List<Location>> insertMultiLocation(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMultiLocation(data));
    }
    @DeleteMapping("/Location/{locationIds}")
    public ResponseEntity<Void> deleteLocationByLocationIds(@PathVariable Long[] locationIds) {
        commonUpdateService.deleteLocationByLocationIds(locationIds);
        return success();
    }
    @GetMapping("/Location/{locationId}")
    public ResponseEntity<List<Location>> findLocation(
        @PathVariable Long locationId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (locationId != null && locationId > 0) data.put("LocationId", locationId);
        return success(this.commonUpdateService.findLocation(data));
    }
    //_____________ END OF Location __________________________            
    @PostMapping("/TripUser")
    public ResponseEntity<TripUser> updateTripUser (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateTripUser(data));
    }
    @PostMapping("/MultiTripUser")
    public ResponseEntity<List<TripUser>> updateMultiTripUser (@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.updateMultiTripUser(data));
    }
    @PutMapping("/TripUser")
    public ResponseEntity<TripUser> insertTripUser(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insertTripUser(data));
    }
    @PutMapping("/MultiTripUser")
    public ResponseEntity<List<TripUser>> insertMultiTripUser(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMultiTripUser(data));
    }
    @DeleteMapping("/TripUser/{tripUserIds}")
    public ResponseEntity<Void> deleteTripUserByTripUserIds(@PathVariable Long[] tripUserIds) {
        commonUpdateService.deleteTripUserByTripUserIds(tripUserIds);
        return success();
    }
    @DeleteMapping("/TripUser/{tripUserId}/{tripId}/{userId}")
    public ResponseEntity<Void> deleteTripUser(
        @PathVariable Long tripUserId
        , @PathVariable Long tripId
        , @PathVariable Long userId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (tripUserId != null && tripUserId > 0) data.put("TripUserId", tripUserId);
        if (tripId != null && tripId > 0) data.put("tripId", tripId);
        if (userId != null && userId > 0) data.put("userId", userId);
        this.commonUpdateService.deleteTripUser(data);
        return success();
    }
    @GetMapping("/TripUser/{tripUserId}/{tripId}/{userId}")
    public ResponseEntity<List<TripUser>> findTripUser(
        @PathVariable Long tripUserId
        , @PathVariable Long tripId
        , @PathVariable Long userId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (tripUserId != null && tripUserId > 0) data.put("TripUserId", tripUserId);
        if (tripId != null && tripId > 0) data.put("TripId", tripId);
        if (userId != null && userId > 0) data.put("UserId", userId);
        return success(this.commonUpdateService.findTripUser(data));
    }
    //_____________ END OF TripUser __________________________            
    @PostMapping("/SeatGroup")
    public ResponseEntity<SeatGroup> updateSeatGroup (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateSeatGroup(data));
    }
    @PostMapping("/MultiSeatGroup")
    public ResponseEntity<List<SeatGroup>> updateMultiSeatGroup (@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.updateMultiSeatGroup(data));
    }
    @PutMapping("/SeatGroup")
    public ResponseEntity<SeatGroup> insertSeatGroup(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insertSeatGroup(data));
    }
    @PutMapping("/MultiSeatGroup")
    public ResponseEntity<List<SeatGroup>> insertMultiSeatGroup(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMultiSeatGroup(data));
    }
    @DeleteMapping("/SeatGroup/{seatGroupIds}")
    public ResponseEntity<Void> deleteSeatGroupBySeatGroupIds(@PathVariable Long[] seatGroupIds) {
        commonUpdateService.deleteSeatGroupBySeatGroupIds(seatGroupIds);
        return success();
    }
    @DeleteMapping("/SeatGroup/{seatGroupId}/{bussTypeId}")
    public ResponseEntity<Void> deleteSeatGroup(
        @PathVariable Long seatGroupId
        , @PathVariable Long bussTypeId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (seatGroupId != null && seatGroupId > 0) data.put("SeatGroupId", seatGroupId);
        if (bussTypeId != null && bussTypeId > 0) data.put("bussTypeId", bussTypeId);
        this.commonUpdateService.deleteSeatGroup(data);
        return success();
    }
    @GetMapping("/SeatGroup/{seatGroupId}/{bussTypeId}")
    public ResponseEntity<List<SeatGroup>> findSeatGroup(
        @PathVariable Long seatGroupId
        , @PathVariable Long bussTypeId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (seatGroupId != null && seatGroupId > 0) data.put("SeatGroupId", seatGroupId);
        if (bussTypeId != null && bussTypeId > 0) data.put("BussTypeId", bussTypeId);
        return success(this.commonUpdateService.findSeatGroup(data));
    }
    //_____________ END OF SeatGroup __________________________            
    @PostMapping("/BussSchedulePrice")
    public ResponseEntity<BussSchedulePrice> updateBussSchedulePrice (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateBussSchedulePrice(data));
    }
    @PostMapping("/MultiBussSchedulePrice")
    public ResponseEntity<List<BussSchedulePrice>> updateMultiBussSchedulePrice (@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.updateMultiBussSchedulePrice(data));
    }
    @PutMapping("/BussSchedulePrice")
    public ResponseEntity<BussSchedulePrice> insertBussSchedulePrice(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insertBussSchedulePrice(data));
    }
    @PutMapping("/MultiBussSchedulePrice")
    public ResponseEntity<List<BussSchedulePrice>> insertMultiBussSchedulePrice(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMultiBussSchedulePrice(data));
    }
    @DeleteMapping("/BussSchedulePrice/{bussSchedulePriceIds}")
    public ResponseEntity<Void> deleteBussSchedulePriceByBussSchedulePriceIds(@PathVariable Long[] bussSchedulePriceIds) {
        commonUpdateService.deleteBussSchedulePriceByBussSchedulePriceIds(bussSchedulePriceIds);
        return success();
    }
    @DeleteMapping("/BussSchedulePrice/{bussSchedulePriceId}/{bussScheduleId}")
    public ResponseEntity<Void> deleteBussSchedulePrice(
        @PathVariable Long bussSchedulePriceId
        , @PathVariable Long bussScheduleId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (bussSchedulePriceId != null && bussSchedulePriceId > 0) data.put("BussSchedulePriceId", bussSchedulePriceId);
        if (bussScheduleId != null && bussScheduleId > 0) data.put("bussScheduleId", bussScheduleId);
        this.commonUpdateService.deleteBussSchedulePrice(data);
        return success();
    }
    @GetMapping("/BussSchedulePrice/{bussSchedulePriceId}/{bussScheduleId}")
    public ResponseEntity<List<BussSchedulePrice>> findBussSchedulePrice(
        @PathVariable Long bussSchedulePriceId
        , @PathVariable Long bussScheduleId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (bussSchedulePriceId != null && bussSchedulePriceId > 0) data.put("BussSchedulePriceId", bussSchedulePriceId);
        if (bussScheduleId != null && bussScheduleId > 0) data.put("BussScheduleId", bussScheduleId);
        return success(this.commonUpdateService.findBussSchedulePrice(data));
    }
    //_____________ END OF BussSchedulePrice __________________________            
    @PostMapping("/BussType")
    public ResponseEntity<BussType> updateBussType (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateBussType(data));
    }
    @PostMapping("/MultiBussType")
    public ResponseEntity<List<BussType>> updateMultiBussType (@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.updateMultiBussType(data));
    }
    @PutMapping("/BussType")
    public ResponseEntity<BussType> insertBussType(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insertBussType(data));
    }
    @PutMapping("/MultiBussType")
    public ResponseEntity<List<BussType>> insertMultiBussType(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMultiBussType(data));
    }
    @PostMapping("/BussType-profile-image")
    public ResponseEntity<BussType> updateBussTypeProfileImage(
            @RequestParam("bussTypeId") Long bussTypeId,
            @RequestParam("profileImage") MultipartFile profileImage) throws IOException {
        BussType_MAPPED.Pk pk = new BussType_MAPPED.Pk(bussTypeId);
        return success(commonUpdateService.updateBussTypeProfileImage(pk, profileImage));
    }
    @DeleteMapping("/BussType/{bussTypeIds}")
    public ResponseEntity<Void> deleteBussTypeByBussTypeIds(@PathVariable Long[] bussTypeIds) {
        commonUpdateService.deleteBussTypeByBussTypeIds(bussTypeIds);
        return success();
    }
    @GetMapping("/BussType/{bussTypeId}")
    public ResponseEntity<List<BussType>> findBussType(
        @PathVariable Long bussTypeId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (bussTypeId != null && bussTypeId > 0) data.put("BussTypeId", bussTypeId);
        return success(this.commonUpdateService.findBussType(data));
    }
    //_____________ END OF BussType __________________________            
    @PostMapping("/BussPoint")
    public ResponseEntity<BussPoint> updateBussPoint (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateBussPoint(data));
    }
    @PostMapping("/MultiBussPoint")
    public ResponseEntity<List<BussPoint>> updateMultiBussPoint (@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.updateMultiBussPoint(data));
    }
    @PutMapping("/BussPoint")
    public ResponseEntity<BussPoint> insertBussPoint(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insertBussPoint(data));
    }
    @PutMapping("/MultiBussPoint")
    public ResponseEntity<List<BussPoint>> insertMultiBussPoint(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMultiBussPoint(data));
    }
    @DeleteMapping("/BussPoint/{bussPointIds}")
    public ResponseEntity<Void> deleteBussPointByBussPointIds(@PathVariable Long[] bussPointIds) {
        commonUpdateService.deleteBussPointByBussPointIds(bussPointIds);
        return success();
    }
    @DeleteMapping("/BussPoint/{bussPointId}/{companyId}/{locationId}")
    public ResponseEntity<Void> deleteBussPoint(
        @PathVariable Long bussPointId
        , @PathVariable Long companyId
        , @PathVariable Long locationId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (bussPointId != null && bussPointId > 0) data.put("BussPointId", bussPointId);
        if (companyId != null && companyId > 0) data.put("companyId", companyId);
        if (locationId != null && locationId > 0) data.put("locationId", locationId);
        this.commonUpdateService.deleteBussPoint(data);
        return success();
    }
    @GetMapping("/BussPoint/{bussPointId}/{companyId}/{locationId}")
    public ResponseEntity<List<BussPoint>> findBussPoint(
        @PathVariable Long bussPointId
        , @PathVariable Long companyId
        , @PathVariable Long locationId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (bussPointId != null && bussPointId > 0) data.put("BussPointId", bussPointId);
        if (companyId != null && companyId > 0) data.put("CompanyId", companyId);
        if (locationId != null && locationId > 0) data.put("LocationId", locationId);
        return success(this.commonUpdateService.findBussPoint(data));
    }
    //_____________ END OF BussPoint __________________________            
    @PostMapping("/Trip")
    public ResponseEntity<Trip> updateTrip (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateTrip(data));
    }
    @PostMapping("/MultiTrip")
    public ResponseEntity<List<Trip>> updateMultiTrip (@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.updateMultiTrip(data));
    }
    @PutMapping("/Trip")
    public ResponseEntity<Trip> insertTrip(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insertTrip(data));
    }
    @PutMapping("/MultiTrip")
    public ResponseEntity<List<Trip>> insertMultiTrip(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMultiTrip(data));
    }
    @DeleteMapping("/Trip/{tripIds}")
    public ResponseEntity<Void> deleteTripByTripIds(@PathVariable Long[] tripIds) {
        commonUpdateService.deleteTripByTripIds(tripIds);
        return success();
    }
    @DeleteMapping("/Trip/{tripId}/{bussScheduleId}")
    public ResponseEntity<Void> deleteTrip(
        @PathVariable Long tripId
        , @PathVariable Long bussScheduleId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (tripId != null && tripId > 0) data.put("TripId", tripId);
        if (bussScheduleId != null && bussScheduleId > 0) data.put("bussScheduleId", bussScheduleId);
        this.commonUpdateService.deleteTrip(data);
        return success();
    }
    @GetMapping("/Trip/{tripId}/{bussScheduleId}")
    public ResponseEntity<List<Trip>> findTrip(
        @PathVariable Long tripId
        , @PathVariable Long bussScheduleId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (tripId != null && tripId > 0) data.put("TripId", tripId);
        if (bussScheduleId != null && bussScheduleId > 0) data.put("BussScheduleId", bussScheduleId);
        return success(this.commonUpdateService.findTrip(data));
    }
    //_____________ END OF Trip __________________________            
    @PostMapping("/BussSchedulePoint")
    public ResponseEntity<BussSchedulePoint> updateBussSchedulePoint (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateBussSchedulePoint(data));
    }
    @PostMapping("/MultiBussSchedulePoint")
    public ResponseEntity<List<BussSchedulePoint>> updateMultiBussSchedulePoint (@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.updateMultiBussSchedulePoint(data));
    }
    @PutMapping("/BussSchedulePoint")
    public ResponseEntity<BussSchedulePoint> insertBussSchedulePoint(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insertBussSchedulePoint(data));
    }
    @PutMapping("/MultiBussSchedulePoint")
    public ResponseEntity<List<BussSchedulePoint>> insertMultiBussSchedulePoint(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMultiBussSchedulePoint(data));
    }
    @DeleteMapping("/BussSchedulePoint/{bussSchedulePointIds}")
    public ResponseEntity<Void> deleteBussSchedulePointByBussSchedulePointIds(@PathVariable Long[] bussSchedulePointIds) {
        commonUpdateService.deleteBussSchedulePointByBussSchedulePointIds(bussSchedulePointIds);
        return success();
    }
    @DeleteMapping("/BussSchedulePoint/{bussSchedulePointId}/{bussPointId}/{bussScheduleId}")
    public ResponseEntity<Void> deleteBussSchedulePoint(
        @PathVariable Long bussSchedulePointId
        , @PathVariable Long bussPointId
        , @PathVariable Long bussScheduleId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (bussSchedulePointId != null && bussSchedulePointId > 0) data.put("BussSchedulePointId", bussSchedulePointId);
        if (bussPointId != null && bussPointId > 0) data.put("bussPointId", bussPointId);
        if (bussScheduleId != null && bussScheduleId > 0) data.put("bussScheduleId", bussScheduleId);
        this.commonUpdateService.deleteBussSchedulePoint(data);
        return success();
    }
    @GetMapping("/BussSchedulePoint/{bussSchedulePointId}/{bussPointId}/{bussScheduleId}")
    public ResponseEntity<List<BussSchedulePoint>> findBussSchedulePoint(
        @PathVariable Long bussSchedulePointId
        , @PathVariable Long bussPointId
        , @PathVariable Long bussScheduleId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (bussSchedulePointId != null && bussSchedulePointId > 0) data.put("BussSchedulePointId", bussSchedulePointId);
        if (bussPointId != null && bussPointId > 0) data.put("BussPointId", bussPointId);
        if (bussScheduleId != null && bussScheduleId > 0) data.put("BussScheduleId", bussScheduleId);
        return success(this.commonUpdateService.findBussSchedulePoint(data));
    }
    //_____________ END OF BussSchedulePoint __________________________            

}
