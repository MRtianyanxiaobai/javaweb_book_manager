<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>购物车</title>
    <%-- 静态包含 base标签、css样式、jQuery文件 --%>
    <%@ include file="/pages/common/head.jsp"%>


</head>
<body>

<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif" >
    <span class="wel_word">购物车</span>

    <%--静态包含，登录 成功之后的菜单 --%>
    <%@ include file="/pages/common/login_success_menu.jsp"%>

</div>

<div id="main">

    <table>
        <tr>
            <td>商品名称</td>
            <td>数量</td>
            <td>单价</td>
            <td>金额</td>

        </tr>
        <c:if test="${empty requestScope.orderitems}">
            <%--如果购物车为空的情况--%>
            <tr>
                <td colspan="5"><a href="index.jsp">当前订单为空！！！</a>
                </td>
            </tr>
        </c:if>
        <c:if test="${not empty requestScope.orderitems}">
            <c:set value="0" var="count"/>
            <c:set value="0" var="totalPrice"/>
            <c:forEach items="${requestScope.orderitems}" var="orderitem">
                <tr>
                    <td>${orderitem.name}</td>
                    <td>
                            ${orderitem.count}
                            <c:set value="${count+orderitem.count}" var="count"/>
                    </td>
                    <td>
                            ${orderitem.price}
                            <c:set value="${orderitem.price * orderitem.count+totalPrice}" var="totalPrice"/>
                    </td>
                    <td>${orderitem.totalPrice}</td>
                </tr>

            </c:forEach>
        </c:if>




    </table>
    <c:if test="${not empty requestScope.orderitems}">
        <div class="cart_info">

            <span class="cart_span">订单中共有<span class="b_count">${count}</span>件商品</span>
            <span class="cart_span">总金额<span class="b_price">${totalPrice}</span>元</span>
            <span class="cart_span"><a href="manager/orderServlet?action=orders">回到订单界面</a> </span>
        </div>
    </c:if>

</div>


<%--静态包含页脚内容--%>
<%@include file="/pages/common/footer.jsp"%>


</body>
</html>