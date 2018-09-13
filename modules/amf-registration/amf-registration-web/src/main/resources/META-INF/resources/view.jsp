<%@ include file="/init.jsp" %>

<!-- TODO: Switch to SessionErrors for errors -->
<!-- Checks if user is logged in -->

<c:choose>
    <c:when test="${!themeDisplay.isSignedIn()}">
        <div>
            <liferay-ui:success key="registration-successful" message="registration-successful"/>
            <form action="<portlet:actionURL name="/processForm" />"
                  method="post">
                <aui:fieldset-group markupView="lexicon">
                    <h1>
                        <liferay-ui:message key="basic-info"/>
                    </h1>
                    <liferay-ui:error key="name-max-length-50-characters-per-line"
                                      message="name-max-length-50-characters-per-line"/>
                    <liferay-ui:error key="name-must-be-alphanumeric"
                                      message="name-must-be-alphanumeric"/>
                    <aui:input label="first-name" name="first_name"
                               type="text"
                               required="true" />
                    <liferay-ui:error key="please-add-a-first-name"
                                      message="please-add-a-first-name"/>
                    <aui:input label="last-name" name="last_name"
                               type="text"
                               required="true" />
                    <liferay-ui:error key="please-add-a-last-name"
                                      message="please-add-a-last-name"/>
                    <aui:input label="email" name="email_address"
                               type="email"
                               required="true"/>
                    <liferay-ui:error key="please-enter-an-email" message="please-enter-an-email"/>
                    <liferay-ui:error key="email-max-length-255-characters"
                                      message="email-max-length-255-characters"/>
                   <liferay-ui:error key="please-enter-a-valid-email-address"
                                     message="please-enter-a-valid-email-address"/>
                    <liferay-ui:error key="email-is-in-use"
                                      message="email-is-in-use"/>
                    <aui:input label="username" name="username"
                               type="text"
                               required="true"/>
                    <liferay-ui:error key="please-enter-a-username"
                                      message="please-enter-a-username"/>
                    <liferay-ui:error key="username-must-be-between-4-and-16-characters"
                                      message="username-must-be-between-4-and-16-characters"/>
                    <liferay-ui:error key="username-must-be-alphanumeric"
                                      message="username-must-be-alphanumeric"/>
                    <liferay-ui:error key="username-is-in-use"
                                      message="username-is-in-use"/>

                    <aui:select class="select" label="gender"
                                name="male"
                                required="true">
                        <aui:option value="1">Male</aui:option>
                        <aui:option value="0">Female</aui:option>
                    </aui:select>

                    <label>
                        <liferay-ui:message key="birthday"/>
                    </label>
                    <% Calendar today = Calendar.getInstance(); %>
                    <liferay-ui:input-date
                        dayParam="b_day"
                        dayValue="<%= today.get(Calendar.DAY_OF_MONTH) %>"
                        monthParam="b_month"
                        monthValue="<%= today.get(Calendar.MONTH) %>"
                        name="DoB"
                        yearParam="b_year"
                        yearValue="<%= today.get(Calendar.YEAR) - 13 %>"
                        required="true"
                    />
                    <liferay-ui:error key="birthday-is-not-a-valid-date"
                                      message="birthday-is-not-a-valid-date"/>
                    <liferay-ui:error key="must-be-13-years-of-age-or-older"
                                      message="must-be-13-years-of-age-or-older"/>

                    <aui:input label="password" name="password1" type="password"
                                required="true"/>
                    <aui:input label="repeat-password" name="password2"
                               type="password"
                                required="true"/>
                    <liferay-ui:error key="please-enter-a-password-and-repeat"
                                      message="please-enter-a-password-and-repeat"/>
                    <liferay-ui:error key="password-must-be-at-least-6-characters-long"
                                      message="password-must-be-at-least-6-characters-long"/>
                    <liferay-ui:error key="password-must-have-at-least-one-uppercase-letter"
                                      message="password-must-have-at-least-one-uppercase-letter"/>
                    <liferay-ui:error key="password-must-be-at-least-one-number"
                                      message="password-must-be-at-least-one-number"/>
                    <liferay-ui:error key="password-must-have-at-least-one-special-character"
                                      message="password-must-have-at-least-one-special-character"/>
                    <liferay-ui:error key="passwords-do-not-match"
                                      message="passwords-do-not-match"/>

                    <h2>Phone</h2>
                    <aui:input label="home-phone" name="home_phone"
                               type="number" />
                    <aui:input label="mobile-phone" name="mobile_phone"
                               type="number" />
                    <liferay-ui:error key="please-enter-a-valid-phone-number"
                                      message="please-enter-a-valid-phone-number"/>
                    <liferay-ui:error key="please-enter-a-10-digit-phone-number"
                                      message="please-enter-a-10-digit-phone-number"/>


                    <h2>Billing Address</h2>
                    <aui:input label="address-line-1" name="address" type="text"
                                required="true"/>
                    <aui:input label="address-line-2" name="address2"
                               type="text" />
                    <aui:input label="city" name="city" type="text"
                               required="true"/>
                    <liferay-ui:error key="please-enter-an-address-starting-on-line-1"
                                      message="please-enter-an-address-starting-on-line-1"/>
                    <liferay-ui:error key="address-max-length-255-characters-per-line"
                                      message="address-max-length-255-characters-per-line"/>
                    <liferay-ui:error key="invalid-characters-in-address-line-1"
                                      message="invalid-characters-in-address-line-1"/>
                    <liferay-ui:error key="invalid-characters-in-address-line-2"
                                      message="invalid-characters-in-address-line-2"/>
                    <liferay-ui:error key="please-enter-a-city"
                                      message="please-enter-a-city"/>
                    <liferay-ui:error key="invalid-characters-in-city"
                                      message="invalid-characters-in-city"/>
                    <liferay-ui:error key="city-max-length-255-characters"
                                      message="city-max-length-255-characters"/>


                    <!-- Region special case. Uses getRegions() to list all regions in US
                    (country code 19), then lists them in a select tag. Returns RegionId to
                    Controller -->

                    <% List<Region> states = RegionServiceUtil.getRegions(19); %>
                    <aui:select class="select" label="state" name="state"
                                required="true">
                    <% for(Region state : states){ %>
                        <aui:option value="<%= state.getRegionId() %>">
                            <%= state.getRegionCode() + " - " + state.getName() %>
                        </aui:option>
                    <% } %>
                    </aui:select>
                    <liferay-ui:error key="please-choose-a-state"
                                      message="please-choose-a-state"/>
                    <liferay-ui:error key="please-choose-a-state-with-a-valid-region-id"
                                      message="please-choose-a-state-with-a-valid-region-id"/>

                    <aui:input label="zip-code" name="zip" type="text"
                                required="true"/>
                    <liferay-ui:error key="please-enter-a-zip-code"
                                      message="please-enter-a-zip-code"/>
                    <liferay-ui:error key="invalid-characters-in-zip-code"
                                      message="invalid-characters-in-zip-code"/>
                    <liferay-ui:error key="please-enter-a-5-digit-zip-code"
                                      message="please-enter-a-5-digit-zip-code"/>


                    <aui:select
                        class="select"
                        label="security-question"
                        name="security_question"
                        required="true"
                    >
                        <aui:option value="what-is-your-mothers-maiden-name">
                            <liferay-ui:message key="what-is-your-mothers-maiden-name"/>
                        </aui:option>
                        <aui:option value="what-is-the-make-of-your-first-car">
                            <liferay-ui:message key="what-is-the-make-of-your-first-car"/>
                        </aui:option>
                        <aui:option value="what-is-your-highschool-mascot">
                            <liferay-ui:message key="what-is-your-highschool-mascot"/>
                        </aui:option>
                        <aui:option value="who-is-your-favorite-actor">
                            <liferay-ui:message key="who-is-your-favorite-actor"/>
                        </aui:option>
                    </aui:select>

                    <aui:input label="security-answer" name="security_answer"
                               type="text" required="true"/>
                    <liferay-ui:error key="please-select-select-a-security-question-and-answer"
                                      message="please-select-select-a-security-question-and-answer"/>
                    <liferay-ui:error key="please-select-a-security-question-from-the-list"
                                      message="please-select-a-security-question-from-the-list"/>
                    <liferay-ui:error key="please-answer-security-question"
                                      message="please-answer-security-question"/>
                    <liferay-ui:error key="answer-max-length-255-characters"
                                      message="answer-max-length-255-characters"/>


                    <label for="accepted_tou">
                        <liferay-ui:message key="i-have-read-understand-and-agree-with-the"/>
                        <a href="/tou" target="_blank">
                            <liferay-ui:message key="terms-of-use"/>
                        </a>
                        <liferay-ui:message key="governing-my-access-to-and-use-of-the-acme-movie-fanatics-web-site"/>
                    </label>
                    <liferay-ui:error key="please-accept-terms-of-use"
                                      message="please-accept-terms-of-use"/>

                    <aui:input
                        class="checkbox"
                        label="accept"
                        name="accepted_tou"
                        type="checkbox"
                        required="true"
                    />
                </aui:fieldset-group>

                <aui:button-row>
                    <aui:button label="submit" type="submit" />
                    <aui:button onclick="javascript:window.history.back(-1)" type="cancel" />
                </aui:button-row>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        <p>
            <liferay-ui:message key="youre-already-logged-in-get"/>
            <aui:a href="<%=PortalUtil.getHomeURL(request) %>">
                <liferay-ui:message key="exploring"/>
            </aui:a>
        </p>
    </c:otherwise>
</c:choose>