/*
 *  Copyright (C) 2010 Red Hat, Inc. All rights reserved.
 *
 *  This is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License as
 *  published by the Free Software Foundation; either version 2.1 of
 *  the License, or (at your option) any later version.
 *
 *  This software is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this software; if not, write to the Free
 *  Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 *  02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.gatein.analyticportal;

import java.io.IOException;
import java.util.Set;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.jboss.gatein.model.AnalyticProfile;

/**
 * {@code GAPortlet}
 *
 * Created on Apr 30, 2010, 1:52:22 PM
 *
 * @author <a href="mailto:nabil.benothman@gmail.com">Nabil Benothman</a> 
 * @author <a href="mailto:anne.mikiela@unine.ch">Anne Leticia Mikiela</a>
 * @version 1.0
 */
public class GAPortlet extends GenericPortlet {

    private static String jspDir = null;
    private static String viewPage = null;
    private static String editPage = null;
    private static String helpPage = null;
    private static String errorPage = null;

    /**
     * Create a new {@code GAPortlet} instance
     */
    public GAPortlet() {
        super();
    }

    /*
     * (non-Javadoc)
     * @see javax.portlet.GenericPortlet.init(javax.portlet.PortletConfig)
     */
    @Override
    public void init(PortletConfig config) throws PortletException {
        super.init(config);
        this.initParams();
    }

    /* (non-Javadoc)
     * @see javax.portlet.GenericPortlet.init()
     */
    @Override
    public void init() throws PortletException {
        super.init();
        this.initParams();
    }

    /**
     * Initialize the static parameter which represents the location of view,
     * edit, and help pages and also the directory containing those files
     */
    private void initParams() {
        PortletConfig config = getPortletConfig();
        jspDir = config.getInitParameter("jspDir");
        editPage = config.getInitParameter("EditPage");
        viewPage = config.getInitParameter("ViewPage");
        helpPage = config.getInitParameter("HelpPage");
        errorPage = config.getInitParameter("ErrorPage");
    }

    /* (non-Javadoc)
     * @see javax.portlet.GenericPortlet.doEdit(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    @Override
    public void doEdit(RenderRequest request, RenderResponse response) throws IOException, PortletException {
        forward(request, response, editPage);
    }

    /* (non-Javadoc)
     * @see javax.portlet.GenericPortlet.doView(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    @Override
    public void doView(RenderRequest request, RenderResponse response) throws IOException, PortletException {
        forward(request, response, viewPage);
    }

    /* (non-Javadoc)
     * @see javax.portlet.GenericPortlet.doHelp(javax.portlet.RenderRequest, javax.portlet.RenderResponse)
     */
    @Override
    public void doHelp(RenderRequest request, RenderResponse response) throws IOException, PortletException {
        forward(request, response, helpPage);
    }

    /* (non-Javadoc)
     * @see javax.portlet.GenericPortlet.processAction(javax.portlet.ActionRequest, javax.portlet.ActionResponse)
     */
    @Override
    public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException {

        String trackId = request.getParameter("trackId");
        if(trackId != null) {
            trackId = trackId.trim();
        }
        String lang = request.getParameter("language");
        String auth = request.getParameter("authenticated");
        String groups[] = request.getParameterValues("groups");
        if (groups == null) {
            groups = new String[]{};
        }

        PortletSession session = request.getPortletSession(true);
        // create a new analytic profile
        AnalyticProfile profile = new AnalyticProfile();
        // Thevalidation of the trackId will be done in the validate method
        profile.setTrackId(trackId);
        // adding groups to profile
        for (String group : groups) {
            profile.addGroup(group);
        }
        // to avoid NullPointerException
        profile.setAuthenticated("OK".equals(auth));
        // to avoid NullPointerException
        profile.setLanguage("OK".equals(lang));

        String error = "";
        String message = "";

        try {
            validate(profile);
            PortletPreferences prefs = request.getPreferences();
            storeProfile(prefs, profile);
            message = "The preferences are stored successfully!";
        } catch (ValidationException vex) {
            error = vex.getMessage();
        } catch (Exception ex) {
            error = "Storing preferences fails due to some problems";
        } finally {
            session.setAttribute("error", error);
            session.setAttribute("message", message);
        }

    }

    /**
     * Set the error session attribute to the given message and forward to the
     * default error page
     * 
     * @param request The render request parameter
     * @param response The render response parameter
     * @param message The error message to set and display
     * @throws PortletException 
     * @throws IOException
     */
    protected void errorAction(RenderRequest request, RenderResponse response, String message)
            throws PortletException, IOException {

        request.getPortletSession(true).setAttribute("error", message);
        forward(request, response, errorPage);
    }

    /**
     * Forward to request to the given page
     * @param request The render request parameter
     * @param response The render response parameter
     * @param page The destination page
     * @throws PortletException
     * @throws IOException
     */
    private void forward(RenderRequest request, RenderResponse response, String page)
            throws PortletException, IOException {
        response.setContentType("text/html");
        PortletRequestDispatcher portletReqDisp = getPortletContext().getRequestDispatcher(page);
        portletReqDisp.include(request, response);
    }

    /**
     * Store the analytic profile values in the preferences
     *
     * @param profile The analytic profile to store in the preferences
     */
    private void storeProfile(PortletPreferences prefs, AnalyticProfile profile) throws Exception {
        // Setting the trackId
        prefs.setValue("trackId", profile.getTrackId());
        // Setting the authentication variable, stored as boolean value
        prefs.setValue("trackAuth", String.valueOf(profile.isAuthenticated()));
        // Setting the language variable, stored as boolean value
        prefs.setValue("trackLang", String.valueOf(profile.getLanguage()));
        // Setting the groups variables, stored as list of values
        String[] groups = new String[profile.getGroups().size()];
        prefs.setValues("groups", profile.getGroups().toArray(groups));
        // storing preferences
        prefs.store();
    }

    /**
     * Validate the given profile. This operation is necessary before storing
     * data in the preferences. If the profile is valid, the return value will be
     * the empty list, else a list containing all violation messages.
     *
     * @param profile The profile to be validated
     * @return A list of violation messages.
     */
    private void validate(AnalyticProfile profile) throws ValidationException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<AnalyticProfile>> constraintViolations = validator.validate(profile);
        if (!constraintViolations.isEmpty()) {
            throw new ValidationException(constraintViolations.iterator().next().getMessage());
        }
    }
}
