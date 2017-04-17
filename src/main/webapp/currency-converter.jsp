<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
        </form>
        <div class="logout">
            <h4 class="text-right">Welcome ${pageContext.request.userPrincipal.name} <a href="#" onclick="document.forms['logoutForm'].submit()">Logout</a></h4>
        </div>
    </c:if>

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
                    <tr class="currencyHistoryRate" id="${exchangeRate.id}" onclick="setUpdateForm('${exchangeRate.id}');">
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