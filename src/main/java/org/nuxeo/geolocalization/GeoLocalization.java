/*
 * Copyright (c) 2007-2012 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * ldoguin
 * 
 */
package org.nuxeo.geolocalization;

import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.model.Property;

/**
 * @author ldoguin
 */
public class GeoLocalization {

	protected final DocumentModel doc;

	public GeoLocalization(DocumentModel doc) {
		this.doc = doc;
	}

	public String getLatitude() {
		return getStringPropertyValue(GeoLocalizationConstant.LOCALIZATION_LATITUDE_PROPERTY_NAME);
	}

	public String getLongitude() {
		return getStringPropertyValue(GeoLocalizationConstant.LOCALIZATION_LONGITUDE_PROPERTY_NAME);
	}

	protected String getStringPropertyValue(String xPath) {
		try {
			Property p = doc.getProperty(xPath);
			return p.getValue(String.class);
		} catch (ClientException e) {
			throw new RuntimeException(e);
		}
	}
}
