
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<% if (request.getAttribute("success") != null) { %>
<script>
Swal.fire({
   position: "center",
   icon: "success",
   title: "<%= request.getAttribute("success") %>",
   showConfirmButton: false,
   timer: 2000
});
</script>
<% } else if (request.getAttribute("error") != null) { %>
<script>
Swal.fire({
   position: "center",
   icon: "error",
   title: "<%= request.getAttribute("error") %>",
   showConfirmButton: false,
   timer: 2000
});
</script>
<% } %>
