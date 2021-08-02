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

    <#list root.entities as entity>
    <#assign capName = entity.capName>
    <#assign camelName = entity.camelName>
    private final ${capName}Repository ${camelName}Repository;
    public ${capName} update${capName}(Map<String, String> data) {
        ${capName} ${camelName} = ErrorCode.DATA_NOT_FOUND.throwIfNotPresent(${camelName}Repository.findById(${capName}.pk(data)));
        ${camelName}.setFieldByName(data);
        ${camelName}Repository.save(${camelName});
        return ${camelName};
    }
    public ${capName} insert${capName}(Map<String, String> data) {
        ${capName} ${camelName} = new ${capName}();
        ${camelName}.setFieldByName(data);
        return ${camelName}Repository.save(${camelName});
    }
    public void delete${capName}By${capName}Ids(Long[] ${camelName}Ids) {
        if (${camelName}Ids != null) {
            ${camelName}Repository.deleteBy${capName}Id(${camelName}Ids);
        }
    }
    <#if entity.primaryKeyClasses?size gt 0>
    public void delete${capName}(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return;
        }
        if (data.size() == 1 && data.containsKey("${capName}Id")) {
            ${camelName}Repository.deleteBy${capName}Id(data.get("${capName}Id"));
            return;
        }
        String deleteMethodName = String.format("deleteBy%s", String.join("And", data.keySet()));
        Object[] deleteMethodParams = data.values().toArray(new Long[0]);
        XeReflectionUtils.invokeMethodByName(${camelName}Repository, deleteMethodName, deleteMethodParams);
    }
    </#if>
    public List<${capName}> find${capName}(TreeMap<String, Long> data) {
        if (data.isEmpty()) {
            return ${camelName}Repository.findAll();
        }
        if (data.size() == 1 && data.containsKey("${capName}Id")) {
            ${capName} ${camelName} = ${camelName}Repository.findBy${capName}Id(data.get("${capName}Id"));
            if(${camelName} == null) {
                return new ArrayList<>();
            } else {
                return Collections.singletonList(${camelName});
            }
        }
        String findMethodName = String.format("findBy%s", String.join("And", data.keySet()));
        Object[] findMethodParams = data.values().toArray(new Long[0]);
        return XeReflectionUtils.invokeMethodByName(${camelName}Repository, findMethodName, findMethodParams);
    }
    <#if entity.hasProfileImage()>
    public ${capName} update${capName}ProfileImage(${capName}_MAPPED.Pk pk, MultipartFile profileImage) throws IOException {
        ${capName} ${camelName} = DATA_NOT_FOUND.throwIfNull(${camelName}Repository.getById(pk));
        ${camelName}.saveProfileImage(profileImage);
        return ${camelName};
    }
    </#if>

    </#list>

}
