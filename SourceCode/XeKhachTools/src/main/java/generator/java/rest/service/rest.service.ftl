package ${root.packagePath};

${root.separators.import.all}

<#assign capName = root.capName>
@Service
@Transactional
@RequiredArgsConstructor
public class ${capName}Service {

${root.separators.body.all}

}
