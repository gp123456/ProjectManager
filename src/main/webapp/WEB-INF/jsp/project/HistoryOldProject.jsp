<%-- 
    Document   : History
    Created on : Aug 5, 2014, 11:45:05 PM
    Author     : user1
--%>

<script>
    $(function () {
        fillSearchCriteriaProject();
    });
</script>

<h1>History of Old Project</h1>
<input type="hidden" id="project-version" value=${prj_version} />
<div class="formLayout"><jsp:include page="../project/searchCritiria.jsp"/></div>