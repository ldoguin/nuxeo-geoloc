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

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.FacesMessages;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.nuxeo.ecm.core.api.ClientException;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.api.NuxeoPrincipal;
import org.nuxeo.ecm.platform.ui.web.api.NavigationContext;
import org.nuxeo.ecm.platform.ui.web.invalidations.AutomaticDocumentBasedInvalidation;
import org.nuxeo.ecm.platform.ui.web.tag.fn.DocumentModelFunctions;
import org.nuxeo.ecm.webapp.helpers.ResourcesAccessor;
import org.nuxeo.geolocalization.GeoLocalization;
import org.nuxeo.geolocalization.GeoLocalizationConstant;

@Name("geoLocalizationManager")
@Scope(ScopeType.CONVERSATION)
@AutomaticDocumentBasedInvalidation
public class GeoLocalizationManagerBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Log log = LogFactory
			.getLog(GeoLocalizationManagerBean.class);

	@In(create = true, required = false)
	protected transient CoreSession documentManager;

	private JSONArray allMarker;

	public String addGeoLocalization(DocumentModel document) {
		document.addFacet(GeoLocalizationConstant.LOCALIZATION_FACET_NAME);
		return null;
	}

	public String updateGeoLocalization(DocumentModel document) throws ClientException {
		documentManager.saveDocument(document);
		return null;
	}

	public String loadAllMarker() throws ClientException, JSONException {
		JSONArray array = new JSONArray();
		DocumentModelList documents = documentManager
				.query("Select * FROM Document WHERE ecm:mixinType = 'GeoLocalization'");
		for (DocumentModel documentModel : documents) {
			GeoLocalization geoLocalizedDoc = documentModel
					.getAdapter(GeoLocalization.class);
			JSONObject marker = new JSONObject();
			marker.put("lon", geoLocalizedDoc.getLongitude());
			marker.put("lat", geoLocalizedDoc.getLatitude());
			marker.put("title", documentModel.getTitle());
			marker.put("link",
					DocumentModelFunctions.documentUrl(documentModel));
			array.put(marker);
		}
		setAllMarker(array);
		return null;
	}

	public String goToMap() {
		return "geo_localization_map";
	}

	public void setAllMarker(JSONArray allMarker) {
		this.allMarker = allMarker;
	}

	public String getAllMarker() {
		return allMarker.toString();
	}
}
