<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ include file="/init.jsp" %>

<!-- TODO: Switch to language keys/SessionErrors for errors -->
<!-- Checks if user is logged in -->

<c:choose>
    <c:when test="${!themeDisplay.isSignedIn()}">
        <div>
            <div>
                <!-- Listing errors -->

                <!-- Checking if String coming from VC is null -->
                <c:set value="${param.actionResult}" var="result" />

                <c:if test="${result.length() != null}">
                <c:set value="${fn:length(result)}" var="resultlen" />
                <c:set value="${fn:substring(result, 1, (resultlen-1))}" var="presult" />
                <c:set value="${fn:split(presult, ',')}" var="errors" />
                <c:set value="${fn:length(errors)}" var="len" />

                <ul style="color:red">
                <c:forEach items="${errors}" var="error">
                <li> ${error}</li>
                </c:forEach>
                </ul>
                </c:if>
            </div>

            <form action="<portlet:actionURL name="register" />" method="post">
                <aui:fieldset-group markupView="lexicon">
                    <h1> Basic Info </h1>
                    <aui:input label="First Name" name="first_name" type="text"
                               required="true" />
                    <aui:input label="Last Name" name="last_name" type="text"
                               required="true" />
                    <aui:input label="Email" name="email_address" type="email"
                               required="true"/>
                    <aui:input label="Username" name="username" type="text"
                               required="true"/>

                    <aui:select class="select" label="Gender" name="male"
                                required="true">
                        <aui:option value="1">Male</aui:option>
                        <aui:option value="0">Female</aui:option>
                    </aui:select>

                    <label>Birthday</label>
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

                    <aui:input label="Password" name="password1" type="password"
                                required="true"/>
                    <aui:input label="Repeat Password" name="password2" type="password"
                                required="true"/>

                    <h2>Phone</h2>
                    <aui:input label="Home Phone" name="home_phone"
                               type="number" />
                    <aui:input label="Mobile Phone" name="mobile_phone"
                               type="number" />

                    <h2>Billing Address</h2>
                    <aui:input label="Address Line 1" name="address" type="text"
                                required="true"/>
                    <aui:input label="Address Line 2" name="address2" type="text" />
                    <aui:input label="City" name="city" type="text"
                               required="true"/>

                    <!-- Region special case. Uses getRegions() to list all regions in US
                    (country code 19), then lists them in a select tag. Returns RegionId to
                    Controller -->

                    <% List<Region> states = RegionServiceUtil.getRegions(19); %>
                    <aui:select class="select" label="State" name="state"
                                required="true">
                    <% for(Region state : states){ %>
                        <aui:option value="<%= state.getRegionId() %>">
                            <%= state.getRegionCode() + " - " + state.getName() %>
                        </aui:option>
                    <% } %>
                    </aui:select>

                    <aui:input label="Zip Code" name="zip" type="text"
                                required="true"/>

                    <aui:select
                        class="select"
                        label="Security Question"
                        name="security_question"
                        required="true"
                    >
                        <aui:option value="What is your mother's maiden name?">
                            What is your mother's maiden name?</aui:option>
                        <aui:option value="What is the make of your first car?">
                            What is the make of your first car?</aui:option>
                        <aui:option value="What is your high school mascot?">
                            What is your high school mascot?</aui:option>
                        <aui:option value="Who is your favorite actor?">
                            Who is your favorite actor?</aui:option>
                    </aui:select>

                    <aui:input label="Security Answer" name="security_answer"
                               type="text" required="true"/>

                    <label for="accepted_tou">I have read, understand, and agree with
                        the <a href="/tou" target="_blank">Terms of Use</a>
                        governing my
                        access to and
                        use of the Acme Movie Fanatics web site.</label>
                    <aui:input
                        class="checkbox"
                        label="Accept"
                        name="accepted_tou"
                        type="checkbox"
                        required="true"
                    />
                </aui:fieldset-group>

                <aui:button-row>
                    <aui:button label="Submit" type="submit" />
                    <aui:button onclick="javascript:window.history.back(-1)" type="cancel" />
                </aui:button-row>
            </form>
        </div>
    </c:when>
    <c:otherwise>
        <p>You're already logged in, get <aui:a href="/web/guest">exploring!</aui:a></p>
    </c:otherwise>
</c:choose>