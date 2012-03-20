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
package org.jboss.gatein.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * {@code AnalyticProfile}
 *
 * Created on Apr 30, 2010, 1:52:22 PM
 *
 *
 * @author <a href="mailto:nabil.benothman@gmail.com">Nabil Benothman</a>
 * @author <a href="mailto:anne.mikiela@unine.ch">Anne Leticia Mikiela</a>
 * @version 1.0
 */
public class AnalyticProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    @NotNull
    @NotEmpty
    @Length(min = 9, max = 15, message = "Track ID length must be between 9 and 15")
    @Pattern(regexp = "[a-zA-Z0-9]{1,2}-[a-zA-Z0-9]{5,10}-[a-zA-Z0-9]{1,2}", message = "Invalid Track ID format")
    private String trackId;
    @NotNull
    private List<String> groups;
    private boolean language;
    private boolean authenticated;

    /**
     * Create a new instance of {@code AnalyticProfile}
     */
    public AnalyticProfile() {
        this("", new ArrayList<String>(), false, false);
    }

    /**
     * Create a new instance of {@code AnalyticProfile} within initialization of different fields
     *
     * @param trackId The track id for the Google analytics account
     * @param groups The list of groups
     * @param lang
     * @param auth  
     */
    public AnalyticProfile(String trackId, List<String> groups, boolean lang, boolean auth) {
        this.trackId = trackId;
        this.groups = groups;
        this.language = lang;
        this.authenticated = auth;
    }

    /**
     * Add the given group to the list of groups
     *
     * @param group The group name to add to the list
     * @return <tt>True</tt> if the operation finish with success else <tt>False</tt>
     */
    public boolean addGroup(String group) {
        return this.groups.add(group);
    }

    /**
     * Remove the given group, if exists, from the list of groups
     *
     * @param group The group name to be removed
     * @return <tt>True</tt> if the operation finish with success else <tt>False</tt>
     */
    public boolean removeGroup(String group) {
        return this.groups.remove(group);
    }

    /**
     * @return the trackId
     */
    public String getTrackId() {
        return trackId;
    }

    /**
     * @param trackId the trackId to set
     */
    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    /**
     * @return the groups
     */
    public List<String> getGroups() {
        return this.groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    /**
     * @return the language
     */
    public boolean getLanguage() {
        return language;
    }

    /**
     * @param language the language to set
     */
    public void setLanguage(boolean language) {
        this.language = language;
    }

    /**
     * @return the authenticated
     */
    public boolean isAuthenticated() {
        return authenticated;
    }

    /**
     * @param authenticated the authenticated to set
     */
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
