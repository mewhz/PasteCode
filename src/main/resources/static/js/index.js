$(document).ready(()=>{
    $("#list").click(()=>{
        let id = $("#identifying").val();
        $("#list").attr("href", "/list?id=" + id);
    })
});