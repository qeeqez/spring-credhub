/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
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

package org.springframework.credhub.support.password;

import org.junit.Test;

import org.springframework.credhub.support.ParametersRequestUnitTestsBase;
import org.springframework.credhub.support.SimpleCredentialName;
import org.springframework.credhub.support.password.PasswordParametersRequest.PasswordGenerateRequestBuilder;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.valid4j.matchers.jsonpath.JsonPathMatchers.hasJsonPath;
import static org.valid4j.matchers.jsonpath.JsonPathMatchers.hasNoJsonPath;

public class PasswordParametersRequestUnitTests extends ParametersRequestUnitTestsBase {
	@Test
	public void serializeWithParameters() throws Exception {
		PasswordGenerateRequestBuilder requestBuilder = PasswordParametersRequest.builder()
				.name(new SimpleCredentialName("example", "credential"))
				.overwrite(true)
				.parameters(PasswordParameters.builder()
						.length(20)
						.excludeLower(true)
						.excludeUpper(false)
						.excludeNumber(true)
						.includeSpecial(false)
						.build());

		String jsonValue = serializeToJson(requestBuilder);

		assertThat(jsonValue,
				allOf(hasJsonPath("$.overwrite", equalTo(true)),
						hasJsonPath("$.name", equalTo("/example/credential")),
						hasJsonPath("$.type", equalTo("password")),
						hasJsonPath("$.parameters.length", equalTo(20)),
						hasJsonPath("$.parameters.exclude_lower", equalTo(true)),
						hasJsonPath("$.parameters.exclude_upper", equalTo(false)),
						hasJsonPath("$.parameters.exclude_number", equalTo(true)),
						hasJsonPath("$.parameters.include_special", equalTo(false))));

		assertThat(jsonValue, hasNoJsonPath("$.additional_permissions"));
	}

	@Test
	public void serializeWithDefaults() throws Exception {
		PasswordGenerateRequestBuilder requestBuilder = PasswordParametersRequest.builder()
				.name(new SimpleCredentialName("example", "credential"))
				.overwrite(true)
				.parameters(new PasswordParameters());

		String jsonValue = serializeToJson(requestBuilder);

		assertThat(jsonValue,
				allOf(hasJsonPath("$.overwrite", equalTo(true)),
						hasJsonPath("$.name", equalTo("/example/credential")),
						hasJsonPath("$.type", equalTo("password")),
						hasNoJsonPath("$.parameters.length"),
						hasNoJsonPath("$.parameters.exclude_lower"),
						hasNoJsonPath("$.parameters.exclude_upper"),
						hasNoJsonPath("$.parameters.exclude_number"),
						hasNoJsonPath("$.parameters.include_special")));

		assertThat(jsonValue, hasNoJsonPath("$.additional_permissions"));
	}
}