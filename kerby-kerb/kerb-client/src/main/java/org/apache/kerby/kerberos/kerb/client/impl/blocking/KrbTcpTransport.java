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
package org.apache.kerby.kerberos.kerb.client.impl.blocking;

import org.apache.kerby.kerberos.kerb.transport.AbstractKrbTransport;
import org.apache.kerby.kerberos.kerb.transport.KrbTransport;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;

/**
 * Default implementation of {@Link KrbTransport} using TCP in block mode.
 */
public class KrbTcpTransport
        extends AbstractKrbTransport implements KrbTransport {
    private Socket socketChannel;
    private DataOutputStream outputStream;
    private DataInputStream inputStream;
    private InetSocketAddress remoteAddress;
    private byte[] headerBuffer; // for message length
    private byte[] messageBuffer; // for message body

    public KrbTcpTransport(InetSocketAddress remoteAddress) throws IOException {
        this.remoteAddress = remoteAddress;
        this.headerBuffer = new byte[4];
        this.messageBuffer = new byte[1024 * 1024]; // TODO.
        doConnect();
    }

    private void doConnect() throws IOException {
        socketChannel = new Socket();
        socketChannel.setSoTimeout(1000);
        socketChannel.connect(remoteAddress);
        outputStream = new DataOutputStream(socketChannel.getOutputStream());
        inputStream = new DataInputStream(socketChannel.getInputStream());
    }

    @Override
    public void sendMessage(ByteBuffer message) throws IOException {
        outputStream.write(message.array());
    }

    @Override
    public ByteBuffer receiveMessage() {
        try {
            int msgLen = inputStream.readInt();
            if (msgLen > 0) {
                inputStream.readFully(messageBuffer, 0, msgLen);
                return ByteBuffer.wrap(messageBuffer, 0, msgLen);
            }
        } catch (IOException e) {
            return null;
        }

        return null;
    }
}