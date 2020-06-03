/*
 * Copyright (c) 2019 IBM Corporation and others
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.example.app;

import java.time.Duration;

import org.microshed.testing.SharedContainerConfiguration;
import org.microshed.testing.testcontainers.ApplicationContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.junit.jupiter.Container;

public class AppContainerConfig implements SharedContainerConfiguration {

	@Container
	public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>()
					.withNetworkMode("postgresnet")
					.withNetworkAliases("testpostgres")
					.withExposedPorts(5432)
					.withDatabaseName("testdb");
	
    @Container
    public static ApplicationContainer app = new ApplicationContainer()
                    .withAppContextRoot("/")
					.withNetworkMode("postgresnet")
					.withExposedPorts(7777)
                    .withEnv("POSTGRES_HOSTNAME", "testpostgres")
                    .withReadinessPath("/", 86400)
                    .withEnv("POSTGRES_PORT", "5432")
                    .dependsOn(postgres);
    
}
