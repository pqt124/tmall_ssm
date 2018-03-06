<%--
  Created by IntelliJ IDEA.
  User: stl
  Date: 2018/1/5
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>

<div class="productDetailDiv" >
    <div class="productDetailTopPart">
        <a href="#nowhere" class="productDetailTopPartSelectedLink selected">商品详情</a>
        <a href="#nowhere" class="productDetailTopReviewLink">累计评价 <span class="productDetailTopReviewLinkNumber">${p.reviewCount}</span> </a>
    </div>

    <div class="productParamterPart">
        <div class="productParamter">产品参数：</div>

        <div class="productParamterList">
            <c:forEach items="${ptvs}" var="ptv">
                <span>${ptv.getProperty().getName()}:  ${fn:substring(ptv.getValue(), 0, 10)} </span>
            </c:forEach>
        </div>
        <div style="clear:both"></div>
    </div>

    <div class="productDetailImagesPart">
        <c:forEach items="${p.productDetailImages}" var="pi">
            <img src="img/productDetail/${pi.id}.jpg">
        </c:forEach>
    </div>
</div>
