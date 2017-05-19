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
package net.sf.antcontrib.inifile;

import java.io.IOException;
import java.io.Writer;

/**
 * A part of an IniFile that might be written to disk.
 *
 * @author <a href='mailto:mattinger@yahoo.com'>Matthew Inger</a>
 */
public interface IniPart {
    /**
     * Write this part of the IniFile to a writer.
     *
     * @param writer The writer to write to
     * @throws IOException when something goes wrong
     */
    void write(Writer writer) throws IOException;
}
