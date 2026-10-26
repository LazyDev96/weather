package org.gradle.accessors.dm;

import org.gradle.api.NonNullApi;
import org.gradle.api.artifacts.ProjectDependency;
import org.gradle.api.internal.artifacts.dependencies.ProjectDependencyInternal;
import org.gradle.api.internal.artifacts.DefaultProjectDependencyFactory;
import org.gradle.api.internal.artifacts.dsl.dependencies.ProjectFinder;
import org.gradle.api.internal.catalog.DelegatingProjectDependency;
import org.gradle.api.internal.catalog.TypeSafeProjectDependencyFactory;
import javax.inject.Inject;

@NonNullApi
public class CoreProjectDependency extends DelegatingProjectDependency {

    @Inject
    public CoreProjectDependency(TypeSafeProjectDependencyFactory factory, ProjectDependencyInternal delegate) {
        super(factory, delegate);
    }

    /**
     * Creates a project dependency on the project at path ":core:common"
     */
    public Core_CommonProjectDependency getCommon() { return new Core_CommonProjectDependency(getFactory(), create(":core:common")); }

    /**
     * Creates a project dependency on the project at path ":core:data"
     */
    public Core_DataProjectDependency getData() { return new Core_DataProjectDependency(getFactory(), create(":core:data")); }

    /**
     * Creates a project dependency on the project at path ":core:database"
     */
    public Core_DatabaseProjectDependency getDatabase() { return new Core_DatabaseProjectDependency(getFactory(), create(":core:database")); }

    /**
     * Creates a project dependency on the project at path ":core:network"
     */
    public Core_NetworkProjectDependency getNetwork() { return new Core_NetworkProjectDependency(getFactory(), create(":core:network")); }

    /**
     * Creates a project dependency on the project at path ":core:service"
     */
    public Core_ServiceProjectDependency getService() { return new Core_ServiceProjectDependency(getFactory(), create(":core:service")); }

    /**
     * Creates a project dependency on the project at path ":core:testing"
     */
    public Core_TestingProjectDependency getTesting() { return new Core_TestingProjectDependency(getFactory(), create(":core:testing")); }

    /**
     * Creates a project dependency on the project at path ":core:ui"
     */
    public Core_UiProjectDependency getUi() { return new Core_UiProjectDependency(getFactory(), create(":core:ui")); }

}
