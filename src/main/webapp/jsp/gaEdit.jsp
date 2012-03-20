<%--

    Copyright (C) 2010 Red Hat, Inc. All rights reserved

    This is free software; you can redistribute it and/or modify it
    under the terms of the GNU Lesser General Public License as
    published by the Free Software Foundation; either version 2.1 of
    the License, or (at your option) any later version.

    This software is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this software; if not, write to the Free
    Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
    02110-1301 USA, or see the FSF site: http://www.fsf.org.

    @author Nabil Benothman & Anne Leticia Mikiela
--%>

<%@ page session="true" %>
<%@ page import="javax.portlet.PortletPreferences"%>
<%@ page import="org.exoplatform.services.organization.OrganizationService"%>
<%@ page import="org.exoplatform.services.organization.Group"%>
<%@ page import="org.exoplatform.container.ExoContainer"%>
<%@ page import="org.exoplatform.container.ExoContainerContext"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects/>

<%
    PortletPreferences prefs = renderRequest.getPreferences();
    String trackId = prefs.getValue("trackId", "");
    String auth = prefs.getValue("trackAuth", "");
    String lang = prefs.getValue("trackLang", "");
    String error = portletSession.getAttribute("error") != null ? (String) portletSession.getAttribute("error") : "";
    String message = portletSession.getAttribute("message") != null ? (String) portletSession.getAttribute("message") : "";

    String prefGroups[] = prefs.getValues("groups", new String[]{});
    List<String> list = new ArrayList<String>();
    for (String e : prefGroups) {
        list.add(e);
    }
%>

<div class="portlet-section-header">
    <table border="0">
        <tbody>
            <tr>
                <!-- td align="left"><img src="/AnalyticPortal/img/gateIn-logo.png" alt="GateIn Logo image" width="180" height="50"></td -->
                <td align="left"><img src="/AnalyticPortal/img/google-analytics-logo.png" alt="GoogleAnalytics  Logo image" width="210" height="80"></td>

            </tr>
        </tbody>
    </table>
</div>
<div class="portlet-section-body">
    <form action="<portlet:actionURL />" method="POST" name="editForm" onsubmit="validate();">
        <table border="0" cellpadding="0" cellspacing="0">
            <tr><td align="center" colspan="3"><font size="3" color="red"> <%=error%></font><font size="3" color="green"> <%=message%></font></td></tr>
            <tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
            <tr>
                <td align="right">Track ID : </td>
                <td align="left">
                    <input type="text" value="<%=trackId%>" id="trackId" name="trackId" onblur="validateTrackId();" />
                    &nbsp;<span id="tid_message"></span>
                </td>
                <td align="left" width="50">&nbsp;</td>
            </tr>
            <tr>
                <td align="right">Language : </td>
                <td align="left"><input type="checkbox" value="OK" name="language" <%= "true".equals(lang) ? "checked" : ""%>  /></td>
                <td align="center">&nbsp;</td>
            </tr>
            <tr>
                <td align="right">Authenticated : </td>
                <td align="left"><input type="checkbox" value="OK" name="authenticated"  <%= "true".equals(auth) ? "checked" : ""%> /></td>
                <td align="center">&nbsp;</td>
            </tr>
            <tr>
                <td align="right" valign="top">Groups : </td>
                <td align="left">
                    <select name="groups" multiple="multiple" size=6>

                        <%
                            ExoContainer container = ExoContainerContext.getContainerByName("portal");
                            OrganizationService orgService = (OrganizationService) container.getComponentInstanceOfType(OrganizationService.class);
                            java.util.Collection<Group> groups = orgService.getGroupHandler().getAllGroups();

                            for (Group o : groups) {
                                // A group having a null parent id means that it is a root group
                                // we are interested only to root groups other than platform
                                if (o.getParentId() == null && !o.getGroupName().equals("platform")) {
                        %>
                        <option value="<%=o.getGroupName()%>" <%= list.contains(o.getGroupName()) ? "SELECTED" : ""%>><%=o.getLabel()%></option>
                        <%
                                }
                            }


                        %>

                    </select>
                </td>
                <td align="center">&nbsp;</td>
            </tr>
            <tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
            <tr><td>&nbsp;</td><td><input type="submit" value="Save" name="saveAction" class="CloseButton" /></td><td>&nbsp;</td></tr>
        </table>
    </form>
</div>


<script type="text/javascript">

    var message = "";

    function validateTrackId() {
        var text = document.getElementById('trackId').value;

        var div_msg = document.getElementById('tid_message');
        var regexp=/\b[a-z0-9]{1,2}-[a-z0-9]{5,10}-[a-z0-9]{1,2}\b/i;

        if(text.length < 9 || text.length > 15) {
            div_msg.innerHTML = "<img src=\"/AnalyticPortal/img/error_icon.jpg\" width=\"15\" height=\"15\" title=\"Track ID length must be in [9-15]\" />";
            message = "Track ID length must be in [9-15]";
            return false;
        }

        if(!regexp.test(text)) {
            div_msg.innerHTML = "<img src=\"/AnalyticPortal/img/error_icon.jpg\" width=\"15\" height=\"15\" alt=\"Error Icon\" title=\"Invalid Track ID format\" />";
            message ="Invalid Track ID format";
            return false;
        }

        div_msg.innerHTML = "<img src=\"/AnalyticPortal/img/ok_icon.png\" width=\"15\" height=\"15\" alt=\"OK Icon\" title=\"Track ID well formed\" />";
        message = "";
        return true;
    }

    // validate the formular before submitting
    function validate() {

        if(!validateTrackId()) {
            alert(message);
            return false;
        }

        //document.editForm.submit();
        return true;
    }

</script>
