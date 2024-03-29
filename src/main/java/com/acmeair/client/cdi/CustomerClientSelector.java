/*******************************************************************************
* Copyright (c) 2017 IBM Corp.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*******************************************************************************/

package com.acmeair.client.cdi;

import com.acmeair.client.CustomerClient;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@SuppressWarnings("all")
@ApplicationScoped
public class CustomerClientSelector {

  /* microprofile-1.1 */
  @Inject 
  @ConfigProperty(name = "CLIENT_TYPE", defaultValue = "jaxrs") 
  private String clientType;

  @Inject
  @Any
  Instance<CustomerClient> clients;

  /**
   * Get a CustomerClient based on CLIENT_TYPE.
   */
  @Produces
  public CustomerClient getCustomerClient() {

    Instance<CustomerClient> found = clients.select(new ClientQualifier(clientType));

    if (!found.isUnsatisfied() && !found.isAmbiguous()) {
      return found.get();
    }

    return null;
  }

  public static class ClientQualifier extends AnnotationLiteral<ClientType> implements ClientType {

    private static final long serialVersionUID = 1L;
    private String value;

    public ClientQualifier(String value) {
      this.value = value;
    }

    public String value() {
      return value;
    }
  }
}
