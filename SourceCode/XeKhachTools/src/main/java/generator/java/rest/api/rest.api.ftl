package ${root.packagePath};
${root.separators.import.all}
@RestController
@RequiredArgsConstructor
@RequestMapping(path={"/${root.url}"})
public class ${root.capName}Api {

    private final ${root.capName}Service ${root.camelName}Service;

${root.separators.body.all}

}
