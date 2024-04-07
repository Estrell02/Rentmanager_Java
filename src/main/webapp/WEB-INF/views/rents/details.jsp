<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <div class="content-wrapper">
        <section class="content-header">
            <h1>
                Details de la reservation
            </h1>
        </section>

        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-body no-padding">
                            <table class="table table-striped">
                                <tr>
                                    <th>ID</th>
                                    <th>ID vehicule</th>

                                    <th>ID client</th>

                                    <th>Debut</th>
                                    <th>Fin</th>
                                </tr>
                                <tr>

                                    <td>${reservation.id()}</td>
                                    <td>${reservation.vehicle_id()}</td>
                                    <td>${reservation.client_id()}</td>
                                    <td>${reservation.debut()}</td>
                                    <td>${reservation.fin()}</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>