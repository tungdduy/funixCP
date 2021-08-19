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
<#list root.entities as entity>
    private final ${entity.capName}Repository ${entity.camelName}Repository;
    private static ${entity.capName}Repository static${entity.capName}Repository;
    public static ${entity.capName}Repository get${entity.capName}Repository() {
        return CommonUpdateService.static${entity.capName}Repository;
    }
</#list>
    public static final Map<Class<? extends XeEntity>, Object> repoMap = new HashMap<>();
    @PostConstruct
    public void postConstruct() {
<#list root.entities as entity>
        CommonUpdateService.static${entity.capName}Repository = ${entity.camelName}Repository;
        repoMap.put(${entity.capName}.class, ${entity.camelName}Repository);
</#list>
    }

    <#list root.entities as entity>
    <#assign capName = entity.capName>
    <#assign camelName = entity.camelName>
    public ${capName} update${capName}(Map<String, String> data) {
        Long ${camelName}Id = Long.parseLong(data.get("${camelName}Id"));
        ${capName} ${camelName} = ErrorCode.DATA_NOT_FOUND.throwIfNull(${camelName}Repository.findBy${capName}Id(${camelName}Id));

    <#list entity.primaryKeyEntities as pk>
        Map<String, String> ${pk.camelName}Data = new HashMap<>();
    </#list>
    <#if entity.primaryKeyEntities?size gt 0>
        data.forEach((fieldName, fieldValue) -> {
        <#list entity.primaryKeyEntities as pk>
            if (fieldName.startsWith("${pk.camelName}.")) {
                ${pk.camelName}Data.put(fieldName.substring("${pk.camelName}.".length()), fieldValue);
            }
        </#list>
        });
    </#if>
    <#list entity.primaryKeyEntities as pk>
        if (!${pk.camelName}Data.isEmpty()) {
            ${pk.camelName}Data.forEach((fieldName, fieldValue) -> data.remove(fieldName));

            this.update${pk.capName}(${pk.camelName}Data);
        }
    </#list>

        ${camelName}.setFieldByName(data);
        ${camelName}.preUpdateAction();
        ${camelName}Repository.save(${camelName});
        return ${camelName};
    }
        
    public List<${capName}> updateMulti${capName}(List<Map<String, String>> multiData) {
        List<${capName}> ${camelName}ParseList = new ArrayList<>();
        multiData.forEach(data -> {
            ${camelName}ParseList.add(this.update${capName}(data));
        });
        ${camelName}Repository.flush();
        return ${camelName}ParseList;
    }
        
    public ${capName} insert${capName}(Map<String, String> data) {
        ${capName} ${camelName} = new ${capName}();
        ${camelName}.setFieldByName(data);
        
        <#list entity.primaryKeyEntities as pkEntity>
        if (${camelName}.get${pkEntity.capName}Id() == null || ${camelName}.get${pkEntity.capName}Id() <= 0) {
            if (XeBooleanUtils.isTrue(data.get("new${pkEntity.capName}IfNull"))) {
                Map<String, String> ${pkEntity.camelName}Data = new HashMap<>();
                data.entrySet().stream()
                        .filter(entry -> entry.getKey().startsWith("${pkEntity.camelName}."))
                        .forEach(entry -> ${pkEntity.camelName}Data.put(entry.getKey().substring("${pkEntity.camelName}.".length()), entry.getValue()));
                ${camelName}.set${pkEntity.capName}(this.insert${pkEntity.capName}(${pkEntity.camelName}Data));
            }
        } else {
            ${camelName}.set${pkEntity.capName}(this.${pkEntity.camelName}Repository.findBy${pkEntity.capName}Id(${camelName}.get${pkEntity.capName}Id()));
        }
        </#list>
        ${camelName}.preSaveAction();
        ${camelName} = ${camelName}Repository.save(${camelName});
        return ${camelName};
    }
    public List<${capName}> insertMulti${capName}(List<Map<String, String>> data) {
        List<${capName}> result = new ArrayList<>();
        data.forEach(${camelName}Data -> result.add(this.insert${capName}(${camelName}Data)));
        return result;
    }
    public void delete${capName}By${capName}Ids(Long[] ${camelName}Ids) {
        List<${capName}> deletingList = ${camelName}Repository.findBy${capName}IdIn(Arrays.asList(${camelName}Ids));
        deletingList.forEach(XeEntity::preRemoveAction);
        ${camelName}Repository.deleteAllBy${capName}IdIn(Arrays.asList(${camelName}Ids));
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
//=================== END OF ${capName} ======================
    </#list>

}
