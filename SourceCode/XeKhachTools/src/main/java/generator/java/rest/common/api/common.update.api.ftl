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
    
    <#list root.entities as entity>
    <#assign capName = entity.capName>
    <#assign camelName = entity.camelName>
    @PostMapping("/${capName}")
    public ResponseEntity<${capName}> update${capName} (@RequestBody Map<String, String> data) {
        return success(commonUpdateService.update${capName}(data));
    }
    @PutMapping("/${capName}")
    public ResponseEntity<${capName}> insert${capName}(@RequestBody Map<String, String> data) {
        return success(commonUpdateService.insert${capName}(data));
    }
    @PutMapping("/Multi${capName}")
    public ResponseEntity<List<${capName}>> insertMulti${capName}(@RequestBody List<Map<String, String>> data) {
        return success(commonUpdateService.insertMulti${capName}(data));
    }
    <#if entity.hasProfileImage()>
    @PostMapping("/${capName}-profile-image")
    public ResponseEntity<${capName}> update${capName}ProfileImage(
            @RequestParam("${camelName}Id") Long ${camelName}Id,
            <#list entity.primaryKeyIdNames as pkIdName>
            @RequestParam("${pkIdName}") Long ${pkIdName},
            </#list>
            @RequestParam("profileImage") MultipartFile profileImage) throws IOException {
        ${capName}_MAPPED.Pk pk = new ${capName}_MAPPED.Pk(${camelName}Id<#list entity.primaryKeyIdNames as pk>, ${pk}</#list>);
        return success(commonUpdateService.update${capName}ProfileImage(pk, profileImage));
    }
    </#if>
    @DeleteMapping("/${capName}/{${camelName}Ids}")
    public ResponseEntity<Void> delete${capName}By${capName}Ids(@PathVariable Long[] ${camelName}Ids) {
        commonUpdateService.delete${capName}By${capName}Ids(${camelName}Ids);
        return success();
    }
    <#if entity.primaryKeyIdNames?size gt 0>
    @DeleteMapping("/${capName}/{${camelName}Id}<#list entity.primaryKeyIdNames as pk>/{${pk}}</#list>")
    public ResponseEntity<Void> delete${capName}(
        @PathVariable Long ${camelName}Id<#list entity.primaryKeyIdNames as pk>
        , @PathVariable Long ${pk}</#list>) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (${camelName}Id != null && ${camelName}Id > 0) data.put("${capName}Id", ${camelName}Id);
        <#list entity.primaryKeyIdNames as pk>
        if (${pk} != null && ${pk} > 0) data.put("${pk}", ${pk});
        </#list>
        this.commonUpdateService.delete${capName}(data);
        return success();
    }
    </#if>
    @GetMapping("/${capName}/{${camelName}Id}<#list entity.primaryKeyIdNames as pk>/{${pk}}</#list>")
    public ResponseEntity<List<${capName}>> find${capName}(
        @PathVariable Long ${camelName}Id<#list entity.primaryKeyIdNames as pk>
        , @PathVariable Long ${pk}</#list>) {
        TreeMap<String, Long> data = new TreeMap<>();
        if (${camelName}Id != null && ${camelName}Id > 0) data.put("${capName}Id", ${camelName}Id);
        <#list entity.primaryKeyEntities as pk>
        if (${pk.camelName}Id != null && ${pk.camelName}Id > 0) data.put("${pk.capName}Id", ${pk.camelName}Id);
        </#list>
        return success(this.commonUpdateService.find${capName}(data));
    }
    </#list>

}
