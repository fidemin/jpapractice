<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>


<body>

<div class="container">


    <div class="jumbotron">
        <h1>HELLO SHOP</h1>
        <p class="lead">회원 기능</p>
        <p>
            <a class="btn btn-lg btn-primary" href="/members/new">회원 가입</a>
            <a class="btn btn-lg btn-primary" href="/members">회원 목록</a>
        </p>
        <p class="lead">상품 기능</p>
        <p>
            <a class="btn btn-lg btn-primary" href="/items/new">상품 등록</a>
            <a class="btn btn-lg btn-primary" href="/items">상품 목록</a>
        </p>
        <p class="lead">주문 기능</p>
        <p>
            <a class="btn btn-lg btn-success" href="/order">상품 주문</a>
            <a class="btn btn-lg btn-success" href="/orders">주문 내역</a>
        </p>
    </div>

</div> <!-- /container -->

</body>
</html>