package net.timxekhach.operation.data.entity;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import net.timxekhach.operation.data.mapped.TripUser_MAPPED;
import net.timxekhach.operation.data.mapped.PathPoint_MAPPED;
import net.timxekhach.operation.data.enumeration.TripUserStatus;
import net.timxekhach.operation.data.mapped.abstracts.XeEntity;
import net.timxekhach.operation.rest.service.CommonUpdateService;
import net.timxekhach.utility.Xe;
import net.timxekhach.utility.XeNumberUtils;
import net.timxekhach.utility.XeStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import javax.persistence.Transient;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.stream.Collectors;
// ____________________ ::IMPORT_SEPARATOR:: ____________________ //

@Entity @Getter @Setter
public class TripUser extends TripUser_MAPPED {

    public TripUser() {}
    public TripUser(Trip trip, User user) {
        super(trip, user);
    }

// ____________________ ::BODY_SEPARATOR:: ____________________ //
    @Transient
    private List<Integer> seats;

    public static TripUser prepareTripUser(Trip trip, Long price, List<PathPoint> pathPoints) {
        TripUser tripUser = new TripUser();
        tripUser.setTrip(trip);
        tripUser.setTripUserPoints(pathPoints);
        tripUser.unitPrice = price;
        tripUser.getStartPoint();
        tripUser.getEndPoint();
        return tripUser;
    }

    public void setTripUserPoints(List<PathPoint> pathPoints) {
        this.tripUserPoints = pathPoints;
        this.tripUserPointsString = pathPoints.stream()
                .map(PathPoint_MAPPED::getPathPointId)
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    @Transient
    List<PathPoint> tripUserPoints;
    public List<PathPoint> getTripUserPoints() {
        if(this.tripUserPoints == null) {
            tripUserPoints = new ArrayList<>();
            List<Long> pathPointIds = XeStringUtils.commaGt0ToStream(this.tripUserPointsString)
                    .map(Integer::longValue)
                    .collect(Collectors.toList());
            if (!pathPointIds.isEmpty()) {
                tripUserPoints = CommonUpdateService.getPathPointRepository().findByPathPointIdIn(pathPointIds);
            }
        }
        return this.tripUserPoints;
    }

    public Boolean isApproved() {
        return this.status != null && this.status == TripUserStatus.APPROVED;
    }

    public static void main(String[] args) {
        List<String> cacheBuilderString = new ArrayList<>();
        findExtendsClassesInPackage(XeEntity.class, "net.timxekhach.operation.data.entity").forEach(clazz -> {
            if(clazz.getName().contains("_MAPPED")) return;
            Map<String, String> cacheFields = new HashMap<>();
            for (Method declaredMethod : clazz.getDeclaredMethods()) {
                String name = declaredMethod.getName();
                String returnType = declaredMethod.getReturnType().getName();
                if (declaredMethod.getReturnType().isAssignableFrom(List.class)) {
                    returnType = ((ParameterizedTypeImpl) declaredMethod.getGenericReturnType()).getActualTypeArguments()[0].getTypeName();
                }
                if(!returnType.startsWith("net.timxekhach.operation.data.entity.")) continue;
                if(declaredMethod.getParameters().length > 0 || !name.startsWith("get")) continue;

                String fieldName = name.substring(3).substring(0, 1).toLowerCase() + name.substring(3).substring(1);
                String fieldType = returnType.replace("net.timxekhach.operation.data.entity.", "");
                cacheFields.put(fieldName, fieldType);

            }
            String className = clazz.getSimpleName();
            List<String> buildCache = new ArrayList<>();
            cacheFields.forEach((fieldName, fieldType) -> {
                buildCache.add(String.format("%s: EntityUtil.metas.%s", fieldName, fieldType));
            });
            String result = String.format("%s: {%s}", className, String.join(", ", buildCache));
            cacheBuilderString.add(result);
        });
        Xe.staticLogger.info("\n" + String.join(",\n", cacheBuilderString));

    }

    public static <E, T extends E> Set<Class<? extends E>> findExtendsClassesInPackage(Class<E> clazz, String packageName) {

        List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
        classLoadersList.add(ClasspathHelper.contextClassLoader());
        classLoadersList.add(ClasspathHelper.staticClassLoader());

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(packageName))));

        return reflections.getSubTypesOf(clazz);
    }

    public Integer getTotalTripUserPoints(){
        return this.getTripUserPoints() == null ? null : tripUserPoints.size();
    }

    @Transient
    PathPoint startPoint;

    public PathPoint getStartPoint() {
        startPoint = this.getTripUserPoints() == null || tripUserPoints.isEmpty() ? null : tripUserPoints.get(0);
        return startPoint;
    }

    @Transient
    PathPoint endPoint;

    public PathPoint getEndPoint() {
        endPoint = getTripUserPoints() == null || tripUserPoints.isEmpty() ? null : tripUserPoints.get(tripUserPoints.size() - 1);
        return endPoint;
    }

    public boolean isDeleted() {
        return this.status != null && this.status == TripUserStatus.DELETED;
    }

    public Trip getTrip(){
        return this.trip.getTripId() != null ? this.trip : null;
    }

    boolean isOverlapPoint(TripUser tripUser) {
        if(tripUser == null || tripUser.isDeleted()) return false;
        return this.getStartPoint() != null && this.getEndPoint() != null
                && tripUser.getStartPoint() != null && tripUser.getEndPoint() != null
                && XeNumberUtils.anyNullOrOverlap(
                tripUser.getStartPoint().getPointOrder(),
                tripUser.getEndPoint().getPointOrder(),
                startPoint.getPointOrder(),
                endPoint.getPointOrder());
    }

    public List<Integer> getSeats() {
        if (this.seats == null) {
            this.seats = XeStringUtils.commaGt0ToStream(this.seatsString)
                    .filter(seat -> seat <= this.getTrip()
                            .getBussSchedule()
                            .getBuss()
                            .getBussType()
                            .getTotalSeats())
                    .collect(Collectors.toList());
        }
        return this.seats;
    }
// ____________________ ::BODY_SEPARATOR:: ____________________ //

}

