<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>
<script>
    $(function(){

        $("#editForm").submit(function(){
            if(!checkEmpty("name","分类名称"))
                return false;
            return true;
        });
    });


</script>
<div class="panel panel-warning addDiv">
    <div class="panel-heading">编辑分类</div>
    <div class="panel-body">
            <form method="post" id="editForm" action="admin_category_edit_do?id=${c.id}" enctype="multipart/form-data">
            <table class="editTable">
                <tr>
                    <td>分类名称</td>
                    <td><input  id="name" name="name" type="text" class="form-control" value="${c.name}"></td>
                </tr>
                <tr>
                    <td>分类圖片</td>
                    <td>
                        <input id="categoryPic" accept="image/*" type="file" name="image" />
                    </td>
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