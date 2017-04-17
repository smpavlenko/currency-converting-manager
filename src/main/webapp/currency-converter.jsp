<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html lang="en">
<head>
    <title>Currency Converter</title>
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>
<body>
<div class="container">

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <div class="form-group">
               <h4 class="text-right">Welcome ${pageContext.request.userPrincipal.name} <button class="btn btn-lg btn-primary" type="submit">Log Out</button></h4>
            </div>
        </form>
    </c:if>

    <form:form method="POST" modelAttribute="exchangeRatesRequest" class="form-signin">
        <spring:bind path="from">
            <div class="form-group">
                <span class="currency">From</span><form:select path="from" items="${currencies}"/>
            </div>
        </spring:bind>
        <spring:bind path="to">
            <div class="form-group">
                <span class="currency">To</span><form:select path="to" items="${currencies}"/>
            </div>
        </spring:bind>
        <button id="getRate" class="btn btn-lg btn-primary" type="submit">Get Rate</button>
    </form:form>

    <c:if test="${!empty recentExchanges}">
        <center>
            <table class="exchangeRateTable">
                <tr>
                    <th>From</th>
                    <th>To</th>
                    <th>Rate</th>
                    <th>Exchange Date</th>
                </tr>

                <c:forEach items="${recentExchanges}" var="exchangeRate">
                    <tr id="${exchangeRate.id}">
                        <td><c:out value="${exchangeRate.from}"/></td>
                        <td><c:out value="${exchangeRate.to}"/></td>
                        <td><c:out value="${exchangeRate.rate}"/></td>
                        <td><c:out value="${exchangeRate.dateCreated.month}"/></td>
                    </tr>
                </c:forEach>
            </table>
            </center>
    </c:if>
</div>
</body>
</html>