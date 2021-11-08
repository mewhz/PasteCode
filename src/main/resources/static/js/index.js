$(document).ready(()=>{
    $("#list").click(()=>{
        let id = $("#identifying").val();
        if (id === "" || id.length === 0){
            swal("未输入标识码", "未输入标识码，请输入标识码重试！", "error");
            throw SyntaxError();
        }
        $("#list").attr("href", "/list?id=" + id);
    })
});