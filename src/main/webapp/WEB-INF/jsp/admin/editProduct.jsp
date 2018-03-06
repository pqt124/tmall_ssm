<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<script>
    $(function(){

        $("#editForm").submit(function(){
            if(!checkEmpty("name","产品名称"))
                return false;
            return true;
        });
    });


</script>
<div class="panel panel-warning addDiv">
    <div class="panel-heading">编辑属性</div>
    <div class="panel-body">
            <form method="post" id="editForm" action="admin_product_update?id=${p.id}&&cid=${p.cid}" enctype="multipart/form-data">
            <table class="editTable">
                <tr>
                    <td>产品名称</td>
                    <td><input  id="name" name="name" type="text" class="form-control" value="${p.name}"></td>
                </tr>
                <tr>
                    <td>产品小标题</td>
                    <td><input  id="subTitle" name="subTitle" type="text" class="form-control" value="${p.subTitle}"></td>
                </tr>
                <tr>
                    <td>原价格</td>
                    <td><input  id="originalPrice" name="originalPrice" type="text" class="form-control" value="${p.originalPrice}"></td>
                </tr>
                <tr>
                    <td>优惠价格</td>
                    <td><input  id="promotePrice"  name="promotePrice" type="text" class="form-control" value="${p.promotePrice}"></td>
                </tr>
               <tr>
                   <td>库存</td>
                   <td><input  id="stock" name="stock" type="text" class="form-control" value="${p.stock}"></td>
               </tr>

                <tr class="submitTR">
                    <td colspan="2" align="center">
                        <button type="submit" class="btn btn-success">提 交</button>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>