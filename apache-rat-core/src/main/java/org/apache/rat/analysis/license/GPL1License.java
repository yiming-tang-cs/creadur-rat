/*
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 */ 
package org.apache.rat.analysis.license;

import org.apache.rat.api.MetaData;

/**
 * Licence matches GPL1 or later.
 */
public class GPL1License extends SimplePatternBasedLicense {
	public static final String FIRST_LICENSE_LINE = "This program is free software; you can redistribute it and/or modify\n" +
            " it under the terms of the GNU General Public License as published by\n" +
            " the Free Software Foundation; either version 1, or (at your option)\n" +
            " any later version.";

    public static final String LICENSE_REFERENCE_LINE = "You should have received a copy of the GNU General Public License\n" +
            " along with this program; if not, write to the Free Software\n" +
            " Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.";

	public GPL1License() {
		super(MetaData.RAT_LICENSE_FAMILY_CATEGORY_DATUM_GPL1,
                MetaData.RAT_LICENSE_FAMILY_NAME_DATUM_GPL_VERSION_1,
		        "", new String[]{FIRST_LICENSE_LINE, LICENSE_REFERENCE_LINE});
	}
}