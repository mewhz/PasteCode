$(document).ready(()=>{
    $("#button").click(()=>{
        let text = $("#code").text();
        navigator.clipboard.writeText(text);
        toastr.success("复制成功，快去粘贴吧！");
    });
});