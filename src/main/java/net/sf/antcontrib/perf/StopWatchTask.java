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
package net.sf.antcontrib.perf;

import java.util.HashMap;
import java.util.Map;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * Assists in timing tasks and/or targets.
 * <p>Developed for use with Antelope, migrated to ant-contrib Oct 2003.</p>
 *
 * @author <a href="mailto:danson@germane-software.com">Dale Anson</a>
 * @version $Revision: 1.5 $
 */
public class StopWatchTask extends Task {
    /**
     * Field name.
     * Storage for stopwatch name
     */
    private String name = null;

    /**
     * Field action.
     * Storage for action
     */
    private String action = null;

    /**
     * Field watches.
     * Storage for watches
     */
    private static Map<String, StopWatch> watches = null;

    // action definitions
    /**
     * Field STOP.
     * (value is ""stop"")
     */
    private static final String STOP = "stop";

    /**
     * Field START.
     * (value is ""start"")
     */
    private static final String START = "start";

    /**
     * Field ELAPSED.
     * (value is ""elapsed"")
     */
    private static final String ELAPSED = "elapsed";

    /**
     * Field TOTAL.
     * (value is ""total"")
     */
    private static final String TOTAL = "total";

    /**
     * Method setName.
     *
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method setAction.
     *
     * @param action String
     */
    public void setAction(String action) {
        action = action.toLowerCase();
        if (action.equals(STOP)
                || action.equals(START)
                || action.equals(ELAPSED)
                || action.equals(TOTAL)) {
            this.action = action;
        } else {
            throw new BuildException("invalid action: " + action);
        }
    }

    /**
     * Method execute.
     */
    public void execute() {
        if (name == null)
            throw new BuildException("name is null");
        if (action == null)
            action = START;
        if (watches == null)
            watches = new HashMap<String, StopWatch>();
        StopWatch sw = watches.get(name);
        if (sw == null && action.equals(START)) {
            sw = new StopWatch(name);
            watches.put(name, sw);
            return;
        }
        if (sw == null)
            return;
        if (action.equals(START)) {
            sw.start();
            return;
        }
        if (action.equals(STOP)) {
            sw.stop();
            return;
        }
        if (action.equals(TOTAL)) {
            String time = sw.format(sw.total());
            log("[" + name + ": " + time + "]");
            getProject().setProperty(name, time);
            return;
        }
        if (action.equals(ELAPSED)) {
            String time = sw.format(sw.elapsed());
            log("[" + name + ": " + time + "]");
            getProject().setProperty(name, time);
            return;
        }
    }
}
