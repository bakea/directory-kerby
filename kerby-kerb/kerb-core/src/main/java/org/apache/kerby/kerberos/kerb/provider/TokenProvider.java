/**
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.apache.kerby.kerberos.kerb.provider;

/**
 * Token provider for TokenPreauth mechanism. This is needed because JWT token
 * encoding and decoding require various facilities that can be provided by 3rd
 * libraries. We need them but would not allow them to invade into the core.
 */
public interface TokenProvider extends KrbProvider {

    /**
     * Create a token encoder.
     * @return token encoder
     */
    public TokenEncoder createTokenEncoder();

    /**
     * Create a token decoder.
     * @return token decoder
     */
    public TokenDecoder createTokenDecoder();

    /**
     * Create a token factory that can be used to construct concrete token.
     * @return token factory
     */
    public TokenFactory createTokenFactory();

}
