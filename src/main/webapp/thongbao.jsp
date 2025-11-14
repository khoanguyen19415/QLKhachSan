<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<%
    String success = (String) session.getAttribute("success");
    String error = (String) session.getAttribute("error");
    if (success != null) {
%>
<script>
    Swal.fire({
        position: "center",
        icon: "success",
        title: "<%= success%>",
        showConfirmButton: false,
        timer: 2000
    });
</script>
<%
    session.removeAttribute("success"); // ? Quan tr?ng
} else if (error != null) {
%>
<script>
    Swal.fire({
        position: "center",
        icon: "error",
        title: "<%= error%>",
        showConfirmButton: false,
        timer: 2000
    });
</script>
<%
        session.removeAttribute("error");
    }
%>
