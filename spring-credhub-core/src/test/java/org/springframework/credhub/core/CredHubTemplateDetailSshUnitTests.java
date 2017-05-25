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

package org.springframework.credhub.core;

import java.util.List;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import org.springframework.credhub.support.CredentialDetails;
import org.springframework.credhub.support.CredentialDetailsData;
import org.springframework.credhub.support.CredentialRequest;
import org.springframework.credhub.support.CredentialType;
import org.springframework.credhub.support.ssh.SshCredential;
import org.springframework.credhub.support.ssh.SshCredentialRequest;
import org.springframework.http.ResponseEntity;

@RunWith(Theories.class)
public class CredHubTemplateDetailSshUnitTests
		extends CredHubTemplateDetailUnitTestsBase<SshCredential> {
	private static final SshCredential CREDENTIAL = new SshCredential("public-key", "private-key");

	@DataPoints("detail-responses")
	public static List<ResponseEntity<CredentialDetails<SshCredential>>> buildDetailResponses() {
		return buildDetailResponses(CredentialType.RSA, CREDENTIAL);
	}

	@DataPoints("data-responses")
	public static List<ResponseEntity<CredentialDetailsData<SshCredential>>> buildDataResponses() {
		return buildDataResponses(CredentialType.RSA, CREDENTIAL);
	}

	@Override
	public CredentialRequest<SshCredential> getRequest() {
		return SshCredentialRequest.builder()
				.name(NAME)
				.value(CREDENTIAL)
				.build();
	}

	@Override
	public Class<SshCredential> getType() {
		return SshCredential.class;
	}

	@Theory
	public void write(@FromDataPoints("detail-responses")
					  ResponseEntity<CredentialDetails<SshCredential>> expectedResponse) {
		verifyWrite(expectedResponse);
	}

	@Theory
	public void getById(@FromDataPoints("detail-responses")
						ResponseEntity<CredentialDetails<SshCredential>> expectedResponse) {
		verifyGetById(expectedResponse);
	}

	@Theory
	public void getByNameWithString(@FromDataPoints("data-responses")
									ResponseEntity<CredentialDetailsData<SshCredential>> expectedResponse) {
		verifyGetByNameWithString(expectedResponse);
	}

	@Theory
	public void getByNameWithCredentialName(@FromDataPoints("data-responses")
											ResponseEntity<CredentialDetailsData<SshCredential>> expectedResponse) {
		verifyGetByNameWithCredentialName(expectedResponse);
	}
}