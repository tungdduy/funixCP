package ${root.packagePath};
${root.importSeparator}
${root.importContent}
${root.importSeparator}

<#assign capName = root.capitalizeName>
@Service
@Transactional
@RequiredArgsConstructor
public class ${capName}Service {

${root.bodySeparator}
${root.bodyContent}
${root.bodySeparator}

}
