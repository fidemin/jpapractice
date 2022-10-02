<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<jsp:include page="../fragments/head.jsp"/>
<body>

<div class="container">

    <jsp:include page="../fragments/bodyHeader.jsp" />

    <form role="form" action="/order" method="post">

        <div class="form-group">
            <label for="user">주문회원</label>
            <select name="userId" id="user" class="form-control">
                <option value="">회원선택</option>
                <c:forEach var="user" items="${users}">
                    <option value="${user.id}">${user.name}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="item">상품명</label>
            <select name="itemId" id="item" class="form-control">
                <option value="">상품선택</option>
                <c:forEach var="item" items="${items}">
                    <option value="${item.id}">${item.name} 가격: ${item.price} 남은수량: ${item.stockQuantity}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="count">주문수량</label>
            <input type="number" name="count" class="form-control" id="count" placeholder="주문 수량을 입력하세요">
        </div>

        <button type="submit" class="btn btn-default">Submit</button>
    </form>
    <br/>
    <jsp:include page="../fragments/footer.jsp" />

</div> <!-- /container -->

</body>
</html>
