/*
 * Copyright (c) 2001-2004 Ant-Contrib project.  All rights reserved.
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
package net.sf.antcontrib.antserver.server;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

/**
 * @author <a href="mailto:mattinger@yahoo.com">Matthew Inger</a>
 */
public class Server implements Runnable {
    /**
     * Field task.
     */
    private final ServerTask task;

    /**
     * Field port.
     */
    private int port = 17000;

    /**
     * Field running.
     */
    private boolean running = false;

    /**
     * Constructor for Server.
     *
     * @param task ServerTask
     * @param port int
     */
    public Server(ServerTask task, int port) {
        super();
        this.task = task;
        this.port = port;
    }

    /**
     * Method start.
     *
     * @throws InterruptedException if thread is interrupted
     */
    public void start() throws InterruptedException {
        Thread thread = new Thread(this);
        thread.start();
        thread.join();
    }

    /**
     * Method stop.
     */
    public void stop() {
        running = false;
    }

    /**
     * Method run.
     *
     * @see java.lang.Runnable#run()
     */
    public void run() {
        ServerSocket server = null;
        running = true;
        try {
            task.getProject().log("Starting server on port: " + port,
                    Project.MSG_DEBUG);
            try {
                server = new ServerSocket(port);
                server.setSoTimeout(500);
            } catch (IOException e) {
                throw new BuildException(e);
            }

            while (running) {
                try {
                    Socket clientSocket = server.accept();
                    task.getProject().log("Got a client connection. Starting Handler.",
                            Project.MSG_DEBUG);
                    ConnectionHandler handler = new ConnectionHandler(task,
                            clientSocket);
                    handler.start();
                } catch (InterruptedIOException e) {
                    // gulp, no socket connection
                } catch (IOException e) {
                    task.getProject().log(e.getMessage(), Project.MSG_ERR);
                }
            }
        } finally {
            if (server != null) {
                try {
                    server.close();
                    server = null;
                } catch (IOException e) {
                    // gulp
                }
            }
        }
        running = false;
    }
}
