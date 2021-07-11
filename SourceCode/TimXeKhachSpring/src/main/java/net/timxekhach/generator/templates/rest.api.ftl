package ${root.packagePath};
${root.importSeparator}
${root.importContent}
${root.importSeparator}

@RestController
@RequiredArgsConstructor
@RequestMapping(path={"/${root.url}"})
public class ${root.capitalizeName}Api {

    private final ${root.capitalizeName}Service ${root.camelName}Service;

${root.bodySeparator}
${root.bodyContent}
${root.bodySeparator}

}
