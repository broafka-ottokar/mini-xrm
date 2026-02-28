package com.example.minixrm.backend;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import org.junit.jupiter.api.Tag;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(
		packages = "com.example.minixrm.backend",
		importOptions = {
				com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeJars.class,
				com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeArchives.class,
				com.tngtech.archunit.core.importer.ImportOption.DoNotIncludeTests.class,
		}
)
@Tag(TestTags.ARCH)
public class ArchUnitTest {

	@ArchTest
	static final ArchRule layersShouldBeRespected =
			layeredArchitecture()
			.consideringAllDependencies()
			.layer("Controller").definedBy("..web.controller..")
			.layer("Service").definedBy("..core.service..")
			.layer("Persistence").definedBy("..core.repository..")
			.layer("Entity").definedBy("..core.domain.entity..")
			.layer("DtoFacade").definedBy("..core.facade..")
			.layer("Dto").definedBy("..core.facade.dto..")
			.layer("DtoMapper").definedBy("..core.util.mapper..")
			.layer("ViewMapper").definedBy("..web.util.mapper..")
			.layer("ViewFacade").definedBy("..web.facade..")
			.layer("View").definedBy("..openapi.v1.model..")
			.whereLayer("Controller").mayNotBeAccessedByAnyLayer()
			.whereLayer("DtoMapper").mayOnlyBeAccessedByLayers("DtoFacade")
			.whereLayer("ViewMapper").mayOnlyBeAccessedByLayers("ViewFacade")
			.whereLayer("Service").mayOnlyBeAccessedByLayers("DtoFacade")
			.whereLayer("Dto").mayOnlyBeAccessedByLayers("DtoMapper", "DtoFacade", "ViewMapper")
			.whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service", "DtoFacade")
			.whereLayer("Entity").mayOnlyBeAccessedByLayers("Persistence", "DtoMapper", "Service", "DtoFacade")
			.whereLayer("ViewFacade").mayOnlyBeAccessedByLayers("Controller")
			;
	
	@ArchTest
	static final ArchRule webOnlyUsesDtoFacade = 
			classes().that().resideInAPackage("..web..").should().onlyDependOnClassesThat().resideInAnyPackage(
					"..core.facade..",
					"..web..",
					"..openapi..",
					/* Couldn't find any simpler way, DoNotIncludeJars doesn't work as expected: */
					"java..",
					"javax..",
					"jakarta..",
					"org.springframework..",
					"org.slf4j..",
					"com.fasterxml.jackson..",
					"io.github.resilience4j..",
					"org.hibernate.."
			);
	
	@ArchTest
	static final ArchRule webNotUsedByCore =
			noClasses().that().resideInAPackage("..core..").should().dependOnClassesThat().resideInAPackage("..web..");
	
}
