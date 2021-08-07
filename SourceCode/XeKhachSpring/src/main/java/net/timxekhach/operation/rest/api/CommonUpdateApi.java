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
    
        @PostMapping("/SeatType")
    public ResponseEntity<SeatType> updateSeatType (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateSeatType(data));
    }
    @PutMapping("/SeatType")
    public ResponseEntity<SeatType> insertSeatType(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insertSeatType(data));
    }
    @PutMapping("/MultiSeatType")
    public ResponseEntity<List<SeatType>> insertMultiSeatType(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMultiSeatType(data));
    }
    @DeleteMapping("/SeatType/{seatTypeIds}")
    public ResponseEntity<Void> deleteSeatTypeBySeatTypeIds(@PathVariable Long[] seatTypeIds) {
        commonUpdateService.deleteSeatTypeBySeatTypeIds(seatTypeIds);
        return success();
    }
    @DeleteMapping("/SeatType/{seatTypeId}/{bussTypeId}")
    public ResponseEntity<Void> deleteSeatType(
        @PathVariable Long seatTypeId
        , @PathVariable Long bussTypeId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (seatTypeId != null && seatTypeId > 0) data.put("SeatTypeId", seatTypeId);
        if (bussTypeId != null && bussTypeId > 0) data.put("bussTypeId", bussTypeId);
        this.commonUpdateService.deleteSeatType(data);
        return success();
    }
    @GetMapping("/SeatType/{seatTypeId}/{bussTypeId}")
    public ResponseEntity<List<SeatType>> findSeatType(
        @PathVariable Long seatTypeId
        , @PathVariable Long bussTypeId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (seatTypeId != null && seatTypeId > 0) data.put("SeatTypeId", seatTypeId);
        if (bussTypeId != null && bussTypeId > 0) data.put("BussTypeId", bussTypeId);
        return success(this.commonUpdateService.findSeatType(data));
    }
    @PostMapping("/BussTrip")
    public ResponseEntity<BussTrip> updateBussTrip (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateBussTrip(data));
    }
    @PutMapping("/BussTrip")
    public ResponseEntity<BussTrip> insertBussTrip(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insertBussTrip(data));
    }
    @PutMapping("/MultiBussTrip")
    public ResponseEntity<List<BussTrip>> insertMultiBussTrip(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMultiBussTrip(data));
    }
    @DeleteMapping("/BussTrip/{bussTripIds}")
    public ResponseEntity<Void> deleteBussTripByBussTripIds(@PathVariable Long[] bussTripIds) {
        commonUpdateService.deleteBussTripByBussTripIds(bussTripIds);
        return success();
    }
    @DeleteMapping("/BussTrip/{bussTripId}/{bussId}/{companyId}")
    public ResponseEntity<Void> deleteBussTrip(
        @PathVariable Long bussTripId
        , @PathVariable Long bussId
        , @PathVariable Long companyId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (bussTripId != null && bussTripId > 0) data.put("BussTripId", bussTripId);
        if (bussId != null && bussId > 0) data.put("bussId", bussId);
        if (companyId != null && companyId > 0) data.put("companyId", companyId);
        this.commonUpdateService.deleteBussTrip(data);
        return success();
    }
    @GetMapping("/BussTrip/{bussTripId}/{bussId}/{companyId}")
    public ResponseEntity<List<BussTrip>> findBussTrip(
        @PathVariable Long bussTripId
        , @PathVariable Long bussId
        , @PathVariable Long companyId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (bussTripId != null && bussTripId > 0) data.put("BussTripId", bussTripId);
        if (bussId != null && bussId > 0) data.put("BussId", bussId);
        if (companyId != null && companyId > 0) data.put("CompanyId", companyId);
        return success(this.commonUpdateService.findBussTrip(data));
    }
    @PostMapping("/User")
    public ResponseEntity<User> updateUser (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateUser(data));
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
    @PostMapping("/Caller")
    public ResponseEntity<Caller> updateCaller (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateCaller(data));
    }
    @PutMapping("/Caller")
    public ResponseEntity<Caller> insertCaller(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insertCaller(data));
    }
    @PutMapping("/MultiCaller")
    public ResponseEntity<List<Caller>> insertMultiCaller(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMultiCaller(data));
    }
    @DeleteMapping("/Caller/{callerIds}")
    public ResponseEntity<Void> deleteCallerByCallerIds(@PathVariable Long[] callerIds) {
        commonUpdateService.deleteCallerByCallerIds(callerIds);
        return success();
    }
    @DeleteMapping("/Caller/{callerId}/{companyId}")
    public ResponseEntity<Void> deleteCaller(
        @PathVariable Long callerId
        , @PathVariable Long companyId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (callerId != null && callerId > 0) data.put("CallerId", callerId);
        if (companyId != null && companyId > 0) data.put("companyId", companyId);
        this.commonUpdateService.deleteCaller(data);
        return success();
    }
    @GetMapping("/Caller/{callerId}/{companyId}")
    public ResponseEntity<List<Caller>> findCaller(
        @PathVariable Long callerId
        , @PathVariable Long companyId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (callerId != null && callerId > 0) data.put("CallerId", callerId);
        if (companyId != null && companyId > 0) data.put("CompanyId", companyId);
        return success(this.commonUpdateService.findCaller(data));
    }
    @PostMapping("/TripUserSeat")
    public ResponseEntity<TripUserSeat> updateTripUserSeat (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateTripUserSeat(data));
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
    @DeleteMapping("/TripUserSeat/{tripUserSeatId}/{seatTypeId}/{tripId}/{userId}")
    public ResponseEntity<Void> deleteTripUserSeat(
        @PathVariable Long tripUserSeatId
        , @PathVariable Long seatTypeId
        , @PathVariable Long tripId
        , @PathVariable Long userId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (tripUserSeatId != null && tripUserSeatId > 0) data.put("TripUserSeatId", tripUserSeatId);
        if (seatTypeId != null && seatTypeId > 0) data.put("seatTypeId", seatTypeId);
        if (tripId != null && tripId > 0) data.put("tripId", tripId);
        if (userId != null && userId > 0) data.put("userId", userId);
        this.commonUpdateService.deleteTripUserSeat(data);
        return success();
    }
    @GetMapping("/TripUserSeat/{tripUserSeatId}/{seatTypeId}/{tripId}/{userId}")
    public ResponseEntity<List<TripUserSeat>> findTripUserSeat(
        @PathVariable Long tripUserSeatId
        , @PathVariable Long seatTypeId
        , @PathVariable Long tripId
        , @PathVariable Long userId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (tripUserSeatId != null && tripUserSeatId > 0) data.put("TripUserSeatId", tripUserSeatId);
        if (seatTypeId != null && seatTypeId > 0) data.put("SeatTypeId", seatTypeId);
        if (tripId != null && tripId > 0) data.put("TripId", tripId);
        if (userId != null && userId > 0) data.put("UserId", userId);
        return success(this.commonUpdateService.findTripUserSeat(data));
    }
    @PostMapping("/Employee")
    public ResponseEntity<Employee> updateEmployee (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateEmployee(data));
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
    @PostMapping("/Company")
    public ResponseEntity<Company> updateCompany (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateCompany(data));
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
    @PostMapping("/TripPoint")
    public ResponseEntity<TripPoint> updateTripPoint (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateTripPoint(data));
    }
    @PutMapping("/TripPoint")
    public ResponseEntity<TripPoint> insertTripPoint(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insertTripPoint(data));
    }
    @PutMapping("/MultiTripPoint")
    public ResponseEntity<List<TripPoint>> insertMultiTripPoint(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMultiTripPoint(data));
    }
    @DeleteMapping("/TripPoint/{tripPointIds}")
    public ResponseEntity<Void> deleteTripPointByTripPointIds(@PathVariable Long[] tripPointIds) {
        commonUpdateService.deleteTripPointByTripPointIds(tripPointIds);
        return success();
    }
    @DeleteMapping("/TripPoint/{tripPointId}/{bussTripId}")
    public ResponseEntity<Void> deleteTripPoint(
        @PathVariable Long tripPointId
        , @PathVariable Long bussTripId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (tripPointId != null && tripPointId > 0) data.put("TripPointId", tripPointId);
        if (bussTripId != null && bussTripId > 0) data.put("bussTripId", bussTripId);
        this.commonUpdateService.deleteTripPoint(data);
        return success();
    }
    @GetMapping("/TripPoint/{tripPointId}/{bussTripId}")
    public ResponseEntity<List<TripPoint>> findTripPoint(
        @PathVariable Long tripPointId
        , @PathVariable Long bussTripId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (tripPointId != null && tripPointId > 0) data.put("TripPointId", tripPointId);
        if (bussTripId != null && bussTripId > 0) data.put("BussTripId", bussTripId);
        return success(this.commonUpdateService.findTripPoint(data));
    }
    @PostMapping("/BussEmployee")
    public ResponseEntity<BussEmployee> updateBussEmployee (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateBussEmployee(data));
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
    @PostMapping("/Buss")
    public ResponseEntity<Buss> updateBuss (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateBuss(data));
    }
    @PutMapping("/Buss")
    public ResponseEntity<Buss> insertBuss(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insertBuss(data));
    }
    @PutMapping("/MultiBuss")
    public ResponseEntity<List<Buss>> insertMultiBuss(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMultiBuss(data));
    }
    @PostMapping("/Buss-profile-image")
    public ResponseEntity<Buss> updateBussProfileImage(
            @RequestParam("bussId") Long bussId,
            @RequestParam("bussTypeId") Long bussTypeId,
            @RequestParam("companyId") Long companyId,
            @RequestParam("profileImage") MultipartFile profileImage) throws IOException {
        Buss_MAPPED.Pk pk = new Buss_MAPPED.Pk(bussId, bussTypeId, companyId);
        return success(commonUpdateService.updateBussProfileImage(pk, profileImage));
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
    @PostMapping("/Location")
    public ResponseEntity<Location> updateLocation (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateLocation(data));
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
    @PostMapping("/TripUser")
    public ResponseEntity<TripUser> updateTripUser (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateTripUser(data));
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
    @PostMapping("/BussType")
    public ResponseEntity<BussType> updateBussType (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateBussType(data));
    }
    @PutMapping("/BussType")
    public ResponseEntity<BussType> insertBussType(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insertBussType(data));
    }
    @PutMapping("/MultiBussType")
    public ResponseEntity<List<BussType>> insertMultiBussType(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMultiBussType(data));
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
    @PostMapping("/BussPoint")
    public ResponseEntity<BussPoint> updateBussPoint (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateBussPoint(data));
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
    @DeleteMapping("/BussPoint/{bussPointId}/{locationId}")
    public ResponseEntity<Void> deleteBussPoint(
        @PathVariable Long bussPointId
        , @PathVariable Long locationId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (bussPointId != null && bussPointId > 0) data.put("BussPointId", bussPointId);
        if (locationId != null && locationId > 0) data.put("locationId", locationId);
        this.commonUpdateService.deleteBussPoint(data);
        return success();
    }
    @GetMapping("/BussPoint/{bussPointId}/{locationId}")
    public ResponseEntity<List<BussPoint>> findBussPoint(
        @PathVariable Long bussPointId
        , @PathVariable Long locationId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (bussPointId != null && bussPointId > 0) data.put("BussPointId", bussPointId);
        if (locationId != null && locationId > 0) data.put("LocationId", locationId);
        return success(this.commonUpdateService.findBussPoint(data));
    }
    @PostMapping("/Trip")
    public ResponseEntity<Trip> updateTrip (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.updateTrip(data));
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
    @DeleteMapping("/Trip/{tripId}/{bussId}")
    public ResponseEntity<Void> deleteTrip(
        @PathVariable Long tripId
        , @PathVariable Long bussId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (tripId != null && tripId > 0) data.put("TripId", tripId);
        if (bussId != null && bussId > 0) data.put("bussId", bussId);
        this.commonUpdateService.deleteTrip(data);
        return success();
    }
    @GetMapping("/Trip/{tripId}/{bussId}")
    public ResponseEntity<List<Trip>> findTrip(
        @PathVariable Long tripId
        , @PathVariable Long bussId) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (tripId != null && tripId > 0) data.put("TripId", tripId);
        if (bussId != null && bussId > 0) data.put("BussId", bussId);
        return success(this.commonUpdateService.findTrip(data));
    }

}
